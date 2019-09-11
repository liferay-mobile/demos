package com.liferay.mobile.formsscreenletdemo.view.sessions;

import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import com.liferay.mobile.formsscreenletdemo.util.DemoUtil;
import com.liferay.mobile.screens.thingscreenlet.screens.ThingScreenlet;
import com.liferay.mobile.screens.thingscreenlet.screens.views.Detail;
import kotlin.Unit;
import com.liferay.mobile.formsscreenletdemo.R;

/**
 * @author Paulo Cruz
 */
public class BlogPostingItemActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

	public static final String THING_ID_EXTRA = "thingId";

	private SwipeRefreshLayout swipeRefreshLayout;
	private ThingScreenlet blogItemScreenlet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blog_posting_item);

		blogItemScreenlet = findViewById(R.id.blog_item_screenlet);
		swipeRefreshLayout = findViewById(R.id.pull_to_refresh);
		swipeRefreshLayout.setOnRefreshListener(this);

		if (savedInstanceState == null) {
			loadResource();
		}
	}

	private void loadResource() {
		showProgress();

		ThingsCache.clearCache();

		String url = getIntent().getStringExtra(THING_ID_EXTRA);

		blogItemScreenlet.load(url, Detail.INSTANCE, DemoUtil.getCredentials(), thingScreenlet -> {
			hideProgress();

			return Unit.INSTANCE;
		}, exception -> {
			hideProgress();

			return Unit.INSTANCE;
		});
	}

	private void hideProgress() {
		swipeRefreshLayout.setRefreshing(false);
		blogItemScreenlet.setVisibility(View.VISIBLE);
	}

	private void showProgress() {
		swipeRefreshLayout.setRefreshing(true);
		blogItemScreenlet.setVisibility(View.GONE);
	}

	@Override
	public void onRefresh() {
		loadResource();
	}
}
