package com.liferay.mobile.formsscreenletdemo.view.sessions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.liferay.apio.consumer.cache.ThingsCache;
import com.liferay.apio.consumer.model.Thing;
import com.liferay.mobile.formsscreenletdemo.util.DemoUtil;
import com.liferay.mobile.formsscreenletdemo.util.ResourceType;
import com.liferay.mobile.screens.thingscreenlet.screens.ThingScreenlet;
import com.liferay.mobile.screens.thingscreenlet.screens.events.ScreenletEvents;
import com.liferay.mobile.screens.thingscreenlet.screens.views.BaseView;
import com.liferay.mobile.screens.thingscreenlet.screens.views.Detail;
import com.liferay.mobile.screens.thingscreenlet.screens.views.Scenario;
import kotlin.Unit;
import com.liferay.mobile.formsscreenletdemo.R;
import com.liferay.mobile.formsscreenletdemo.util.Constants;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Paulo Cruz
 */
public class BlogPostingsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,
	ScreenletEvents {

	private SwipeRefreshLayout swipeRefreshLayout;
	private ThingScreenlet blogsScreenlet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blog_postings);

		blogsScreenlet = findViewById(R.id.blogs_screenlet);
		blogsScreenlet.setScreenletEvents(this);

		swipeRefreshLayout = findViewById(R.id.pull_to_refresh);
		swipeRefreshLayout.setOnRefreshListener(this);

		if (savedInstanceState == null) {
			loadResource();
		}
	}

	private void loadResource() {
		showProgress();

		ThingsCache.clearCache();

		String url = DemoUtil.getResourcePath(getResources().getString(R.string.liferay_server),
			Constants.CONTENT_SPACE_ID, ResourceType.BLOGS);

		blogsScreenlet.load(url, Detail.INSTANCE, DemoUtil.getCredentials(), thingScreenlet -> {
			hideProgress();

			return Unit.INSTANCE;
		}, exception -> {
			hideProgress();

			return Unit.INSTANCE;
		});
	}

	private void hideProgress() {
		swipeRefreshLayout.setRefreshing(false);
		blogsScreenlet.setVisibility(View.VISIBLE);
	}

	private void showProgress() {
		swipeRefreshLayout.setRefreshing(true);
		blogsScreenlet.setVisibility(View.GONE);
	}

	@Override
	public void onRefresh() {
		loadResource();
	}

	@Nullable
	@Override
	public <T extends BaseView> View.OnClickListener onClickEvent(@NotNull T baseView, @NotNull View view,
		@NotNull Thing thing) {

		Intent intent = new Intent(BlogPostingsActivity.this, BlogPostingItemActivity.class);
		intent.putExtra(BlogPostingItemActivity.THING_ID_EXTRA, thing.getId());
		startActivity(intent);

		return null;
	}

	@Nullable
	@Override
	public <T extends BaseView> Integer onGetCustomLayout(@NotNull ThingScreenlet screenlet, @Nullable T parentView,
		@NotNull Thing thing, @NotNull Scenario scenario) {
		return null;
	}

	@Override
	public <T extends BaseView> void onCustomEvent(@NotNull String name, @NotNull ThingScreenlet screenlet,
		@Nullable T parentView, @NotNull Thing thing) {

	}
}
