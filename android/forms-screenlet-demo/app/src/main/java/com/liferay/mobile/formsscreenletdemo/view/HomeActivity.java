package com.liferay.mobile.formsscreenletdemo.view;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.formsscreenletdemo.R;
import com.liferay.mobile.formsscreenletdemo.view.login.LoginActivity;
import com.liferay.mobile.formsscreenletdemo.view.sessions.SpecialOffersActivity;
import com.liferay.mobile.formsscreenletdemo.view.sessions.TakeCareListActivity;
import com.liferay.mobile.push.Push;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.context.storage.CredentialsStorageBuilder;
import com.liferay.mobile.screens.ddm.form.model.FormInstanceRecord;
import com.liferay.mobile.screens.ddm.form.service.openapi.FetchLatestDraftServiceOpenAPI;
import com.liferay.mobile.screens.userportrait.UserPortraitScreenlet;
import com.liferay.mobile.screens.util.LiferayLogger;
import org.json.JSONException;

import rx.android.schedulers.AndroidSchedulers;

import static java.lang.Long.parseLong;

/**
 * @author Luísa Lima
 * @author Victor Oliveira
 */
public class HomeActivity extends AppCompatActivity {

	private FetchLatestDraftServiceOpenAPI client;

	private DrawerLayout drawerLayout;
	private UserPortraitScreenlet userPortrait;
	private Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		toolbar = findViewById(R.id.home_toolbar);
		setSupportActionBar(toolbar);
		setupForPushNotification();

		Button formButton = findViewById(R.id.forms_button);
		formButton.setOnClickListener(this::startFormActivity);
		long formInstanceId = parseLong(getString(R.string.insurance_form_id));

		String serverURL = getResources().getString(R.string.liferay_server);
		client = new FetchLatestDraftServiceOpenAPI(serverURL);

		if (savedInstanceState == null) {
			checkForDraft(formInstanceId);
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
		String pushSenderId = getString(R.string.push_sender_id);

		if (pushSenderId.isEmpty()) return;

		Session session = SessionContext.createSessionFromCurrentSession();
		int portalVersion = getResources().getInteger(R.integer.liferay_portal_version);

		try {
			Push.with(session).withPortalVersion(portalVersion).onSuccess(jsonObject -> {
				try {
					String registrationId = jsonObject.getString("token");
					LiferayLogger.d("Device registrationId: " + registrationId);
				} catch (JSONException e) {
					LiferayLogger.e(e.getMessage(), e);
				}
			}).onFailure(e -> LiferayLogger.e(e.getMessage(), e)
			).register(this, pushSenderId);

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
			case R.id.insurance_quote:
				startActivity(FormsActivity.class);
				break;
			case R.id.my_policies:
				startActivity(PoliciesListActivity.class);
				break;
			//case R.id.blog_postings:
			//	startActivity(BlogPostingsActivity.class);
			//	break;
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

	private void checkForDraft(long formInstanceId) {
		client.fetchLatestDraft(formInstanceId).observeOn(
			AndroidSchedulers.mainThread()
		).subscribe(
			this::onDraftLoaded,
			this::logError
		);
	}

	private void logError(Throwable e) {
		LiferayLogger.e(e.getMessage());
	}

	private void loadPortrait() {
		userPortrait.setUserId(SessionContext.getUserId());
		userPortrait.load();
	}

	private void onDraftLoaded(FormInstanceRecord formInstanceRecord) {
		if (formInstanceRecord != null) {
			setupDialog();
		}
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

		NavigationView navigationView = findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this::onOptionsItemSelected);

		setupDrawerContent(navigationView);

		View navHeaderView = navigationView.getHeaderView(0);
		User currentUser = SessionContext.getCurrentUser();

		if (currentUser != null) {
			TextView nameView = navHeaderView.findViewById(R.id.user_name);
			nameView.setText(currentUser.getFullName());

			TextView emailView = navHeaderView.findViewById(R.id.user_email);
			emailView.setText(currentUser.getEmail());
		}

		userPortrait = navHeaderView.findViewById(R.id.user_portrait);
	}

	private void startFormActivity(View view) {
		Intent intent = new Intent(HomeActivity.this, FormsActivity.class);
		startActivity(intent);
	}
}
