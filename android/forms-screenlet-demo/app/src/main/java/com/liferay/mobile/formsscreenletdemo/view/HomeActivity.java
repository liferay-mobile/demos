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
import com.liferay.mobile.formsscreenletdemo.presenter.HomePresenter;
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

	public ThingScreenlet userPortrait;
	private DrawerLayout drawerLayout;
	private HomePresenter homePresenter = new HomePresenter();
	private NavigationView navigationView;
	private Toolbar toolbar;
	private TextView userName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		toolbar = findViewById(R.id.home_toolbar);
		setSupportActionBar(toolbar);

		homePresenter.onActivityCreated(this);

		Button formButton = findViewById(R.id.forms_button);
		formButton.setOnClickListener(this::startFormActivity);

		if (savedInstanceState == null) {
			homePresenter.checkForDraft();
		}

		setupNavigationDrawer();

		if (savedInstanceState == null) {
			try {
				homePresenter.loadPortrait();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		homePresenter.onActivityDestroyed();
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
			case R.id.blog_postings:
				startDemoActivity(BlogPostingsActivity.class);
				break;
			case R.id.take_care:
				startDemoActivity(TakeCareListActivity.class);
				break;
			case R.id.special_offers:
				startDemoActivity(SpecialOffersActivity.class);
				break;
			case R.id.sign_out:
				homePresenter.signOut();
				break;
		}
	}

	public void startDemoActivity(Class<?> clazz) {
		Intent intent = new Intent(this, clazz);
		startActivity(intent);
	}

	private void setupDrawerContent(NavigationView navigationView) {
		navigationView.setNavigationItemSelectedListener(item -> {
			selectDrawerItem(item);
			return true;
		});
	}

	public void setupDialog() {
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

	private void startFormActivity(View view) {
		Intent intent = new Intent(HomeActivity.this, FormsActivity.class);
		startActivity(intent);
	}
}
