package com.liferay.mobile.formsscreenletdemo.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.liferay.apio.consumer.model.Thing;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.formsscreenletdemo.R;
import com.liferay.mobile.formsscreenletdemo.service.APIOFetchResourceService;
import com.liferay.mobile.formsscreenletdemo.util.Constants;
import com.liferay.mobile.formsscreenletdemo.util.DemoUtil;
import com.liferay.mobile.formsscreenletdemo.util.ResourceType;
import com.liferay.mobile.formsscreenletdemo.view.login.LoginActivity;
import com.liferay.mobile.formsscreenletdemo.view.sessions.SpecialOffersActivity;
import com.liferay.mobile.formsscreenletdemo.view.sessions.TakeCareListActivity;
import com.liferay.mobile.formsscreenletdemo.view.sessions.BlogPostingsActivity;
import com.liferay.mobile.push.Push;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.storage.CredentialsStorageBuilder;
import com.liferay.mobile.screens.ddm.form.model.FormInstanceRecord;
import com.liferay.mobile.screens.ddm.form.service.APIOFetchLatestDraftService;
import com.liferay.mobile.screens.thingscreenlet.screens.ThingScreenlet;
import com.liferay.mobile.screens.thingscreenlet.screens.views.Custom;
import com.liferay.mobile.screens.util.AndroidUtil;
import com.liferay.mobile.screens.util.LiferayLogger;
import kotlin.Unit;
import org.json.JSONException;

/**
 * @author Luísa Lima
 * @author Victor Oliveira
 */
public class HomeActivity extends AppCompatActivity {

	private DrawerLayout drawerLayout;
	private NavigationView navigationView;
	private ThingScreenlet userPortrait;
	private Toolbar toolbar;
	private TextView userName;
	private static final int PORTRAIT_WIDTH = 90;
	private static final int PORTRAIT_HEIGHT = 90;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		toolbar = findViewById(R.id.home_toolbar);
		setSupportActionBar(toolbar);
		setupForPushNotification();

		Button formButton = findViewById(R.id.forms_button);
		formButton.setOnClickListener(this::startFormActivity);

		if (savedInstanceState == null) {
			checkForDraft();
		}

		setupNavigationDrawer();

		if (savedInstanceState == null) {
			try {
				loadPortrait();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void setupForPushNotification() {
		Session session = SessionContext.createSessionFromCurrentSession();

		try {
			Push.with(session).withPortalVersion(71).onSuccess(jsonObject -> {

				try {
					String registrationId = jsonObject.getString("token");
					LiferayLogger.d("Device registrationId: " + registrationId);
				} catch (JSONException e) {
					LiferayLogger.e(e.getMessage(), e);
				}

			}).onFailure(e -> {
				LiferayLogger.e(e.getMessage(), e);
			}).register(this, getString(R.string.push_sender_id));

		} catch (Exception e) {
			LiferayLogger.e(e.getMessage(), e);
		}
	}

	@Override
	public void onBackPressed() {
		if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
			drawerLayout.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				drawerLayout.openDrawer(GravityCompat.START);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void selectDrawerItem(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.my_policies:
				startActivity(PoliciesListActivity.class);
				break;
			case R.id.blog_postings:
				startActivity(BlogPostingsActivity.class);
				break;
			case R.id.take_care:
				startActivity(TakeCareListActivity.class);
				break;
			case R.id.special_offers:
				startActivity(SpecialOffersActivity.class);
				break;
			case R.id.help:
				startActivity(HelpActivity.class);
				break;
			case R.id.sign_out:
				signOut();
				break;
		}
	}

	private void signOut() {
		SessionContext.logout();
		SessionContext.removeStoredCredentials(CredentialsStorageBuilder.StorageType.SHARED_PREFERENCES);

		finish();

		startActivity(LoginActivity.class);
	}

	private void startActivity(Class<?> clazz) {
		Intent intent = new Intent(this, clazz);
		startActivity(intent);
	}

	private void checkForDraft() {
		String server = getResources().getString(R.string.liferay_server);
		String url = DemoUtil.getResourcePath(server, Constants.FORM_INSTANCE_ID, ResourceType.FORMS);

		new APIOFetchResourceService().fetchResource(url, this::onThingLoaded, this::logError);
	}

	private Unit logError(Exception e) {
		LiferayLogger.e(e.getMessage());
		return Unit.INSTANCE;
	}

	private Unit onThingLoaded(Thing thing) {
		loadDraft(thing);
		return Unit.INSTANCE;
	}

	private void loadDraft(Thing thing) {
		new APIOFetchLatestDraftService().fetchLatestDraft(thing, this::onDraftLoaded, this::logError);
	}

	private void loadPortrait() throws Exception {
		String url = DemoUtil.getResourcePath(getResources().getString(R.string.liferay_server),
			SessionContext.getUserId(), ResourceType.PERSON);

		userPortrait.load(url, new Custom("portrait"), SessionContext.getCredentialsFromCurrentSession());
	}

	private Unit onDraftLoaded(Thing thing) {
		if (thing != null) {
			FormInstanceRecord formInstanceRecord = FormInstanceRecord.getConverter().invoke(thing);

			if (formInstanceRecord != null) {
				setupDialog();
			}
		}

		return Unit.INSTANCE;
	}

	private void setupDrawerContent(NavigationView navigationView) {
		navigationView.setNavigationItemSelectedListener(item -> {
			selectDrawerItem(item);
			return true;
		});
	}

	private void setupDialog() {
		LayoutInflater inflater = getLayoutInflater();
		View dialogView = inflater.inflate(R.layout.custom_dialog, null);
		Button positiveButton = dialogView.findViewById(R.id.dialog_positive_button);
		Button negativeButton = dialogView.findViewById(R.id.dialog_negative_button);

		AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
		builder.setView(dialogView);
		AlertDialog alertDialog = builder.create();

		negativeButton.setOnClickListener(v -> alertDialog.dismiss());
		positiveButton.setOnClickListener(view -> {
			alertDialog.dismiss();
			startFormActivity(view);
		});

		alertDialog.show();
	}

	private void setupNavigationDrawer() {
		drawerLayout = findViewById(R.id.drawer_layout);
		ActionBarDrawerToggle toggle =
			new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
		drawerLayout.addDrawerListener(toggle);
		toggle.syncState();

		navigationView = findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this::onOptionsItemSelected);

		setupDrawerContent(navigationView);

		View navHeaderView = navigationView.getHeaderView(0);
		userName = navHeaderView.findViewById(R.id.user_name);
		userName.setText(SessionContext.getCurrentUser().getFullName());

		userPortrait = navHeaderView.findViewById(R.id.user_portrait);
	}

	private Unit showError(String message) {

		int icon = R.drawable.default_error_icon;
		int backgroundColor =
			ContextCompat.getColor(this, com.liferay.mobile.screens.viewsets.lexicon.R.color.lightRed);

		AndroidUtil.showCustomSnackbar(userPortrait, message, Snackbar.LENGTH_LONG, backgroundColor, Color.WHITE, icon);

		return Unit.INSTANCE;
	}

	private void startFormActivity(View view) {
		Intent intent = new Intent(HomeActivity.this, FormsActivity.class);
		startActivity(intent);
	}
}
