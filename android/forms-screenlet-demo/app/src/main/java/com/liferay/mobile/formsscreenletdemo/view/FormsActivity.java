package com.liferay.mobile.formsscreenletdemo.view;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;
import com.liferay.apio.consumer.cache.ThingsCache;
import com.liferay.apio.consumer.model.Thing;
import com.liferay.mobile.formsscreenletdemo.util.DemoUtil;
import com.liferay.mobile.formsscreenletdemo.util.ResourceType;
import com.liferay.mobile.screens.base.ModalProgressBarWithLabel;
import com.liferay.mobile.screens.thingscreenlet.screens.ThingScreenlet;
import com.liferay.mobile.screens.thingscreenlet.screens.events.ScreenletEvents;
import com.liferay.mobile.screens.thingscreenlet.screens.views.BaseView;
import com.liferay.mobile.screens.thingscreenlet.screens.views.Detail;
import com.liferay.mobile.screens.thingscreenlet.screens.views.Scenario;
import com.liferay.mobile.screens.util.AndroidUtil;
import com.liferay.mobile.screens.viewsets.defaultviews.ddm.events.FormEvents;
import kotlin.Unit;
import com.liferay.mobile.formsscreenletdemo.R;
import com.liferay.mobile.formsscreenletdemo.util.Constants;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Luísa Lima
 */
public class FormsActivity extends AppCompatActivity
	implements ScreenletEvents, SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnChildScrollUpCallback {

	private LinearLayout errorLayout;
	private ThingScreenlet formsScreenlet;
	private ModalProgressBarWithLabel progressBar;
	private SwipeRefreshLayout swipeRefreshLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forms);

		formsScreenlet = findViewById(R.id.forms_screenlet);
		errorLayout = findViewById(R.id.form_detail_error_view);
		progressBar = findViewById(R.id.liferay_modal_progress);
		swipeRefreshLayout = findViewById(R.id.pull_to_refresh);
		progressBar.disableDimBackground();
		formsScreenlet.setScreenletEvents(this);
		swipeRefreshLayout.setOnRefreshListener(this);

		DemoUtil.setLightStatusBar(this, getWindow());

		if (savedInstanceState == null) {
			loadResource();
		}
	}

	private void loadResource() {
		String url = DemoUtil.getResourcePath(getResources().getString(R.string.liferay_server),
			Constants.FORM_INSTANCE_ID, ResourceType.FORMS);

		progressBar.show(getString(R.string.loading_form));
		formsScreenlet.setVisibility(View.GONE);

		ThingsCache.clearCache();

		formsScreenlet.load(url, Detail.INSTANCE, DemoUtil.getCredentials(), thingScreenlet -> {
			hideProgress();
			errorLayout.setVisibility(View.GONE);

			recyclerViewWorkaround();

			return Unit.INSTANCE;
		}, e -> showError(e.getMessage()));
	}

	/*
	 * TODO: Find another solution
	 */
	private void recyclerViewWorkaround() {
		ScrollView scrollView = formsScreenlet.findViewById(R.id.multipage_scroll_view);

		swipeRefreshLayout.getViewTreeObserver().addOnScrollChangedListener(() -> {
			if (scrollView != null && scrollView.getScrollY() == 0) {
				swipeRefreshLayout.setEnabled(true);
			} else {
				swipeRefreshLayout.setEnabled(false);
			}
		});
	}

	private void hideProgress() {
		progressBar.hide();
		swipeRefreshLayout.setRefreshing(false);
		formsScreenlet.setVisibility(View.VISIBLE);
	}

	private Unit showError(String message) {
		hideProgress();

		int icon = R.drawable.default_error_icon;
		int textColor = Color.WHITE;
		int backgroundColor =
			ContextCompat.getColor(this, com.liferay.mobile.screens.viewsets.lexicon.R.color.lightRed);

		AndroidUtil.showCustomSnackbar(formsScreenlet, message, Snackbar.LENGTH_LONG, backgroundColor, textColor, icon);

		return Unit.INSTANCE;
	}

	@Override
	public <T extends BaseView> void onCustomEvent(@NotNull String name, @NotNull ThingScreenlet screenlet,
		@Nullable T parentView, @NotNull Thing thing) {
		if (name.equals(FormEvents.SUBMIT_SUCCESS.name())) {
			LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
			View layout = inflater.inflate(R.layout.toast_layout_default, findViewById(R.id.toast_layout_default));

			Toast toast = new Toast(getApplicationContext());
			toast.setDuration(Toast.LENGTH_LONG);
			toast.setView(layout);
			toast.show();

			new Handler().postDelayed(this::finish, 500);
		}
	}

	@Nullable
	@Override
	public <T extends BaseView> View.OnClickListener onClickEvent(@NotNull T baseView, @NotNull View view,
		@NotNull Thing thing) {
		return null;
	}

	@Nullable
	@Override
	public <T extends BaseView> Integer onGetCustomLayout(@NotNull ThingScreenlet screenlet, @Nullable T parentView,
		@NotNull Thing thing, @NotNull Scenario scenario) {
		return null;
	}

	@Override
	public void onRefresh() {
		loadResource();
	}

	@Override
	public boolean canChildScrollUp(@NonNull SwipeRefreshLayout parent,
		@android.support.annotation.Nullable View child) {
		return true;
	}
}
