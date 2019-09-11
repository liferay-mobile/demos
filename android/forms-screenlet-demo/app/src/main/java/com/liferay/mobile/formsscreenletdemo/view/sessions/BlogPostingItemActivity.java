package com.liferay.mobile.formsscreenletdemo.view.sessions;

import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import com.liferay.mobile.formsscreenletdemo.R;
import com.liferay.mobile.screens.asset.AssetEntry;
import com.liferay.mobile.screens.asset.display.AssetDisplayListener;
import com.liferay.mobile.screens.blogs.BlogsEntryDisplayScreenlet;

/**
 * @author Paulo Cruz
 */
public class BlogPostingItemActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, AssetDisplayListener {

	public static final String BLOG_POST_ID = "blogPostId";

	private SwipeRefreshLayout swipeRefreshLayout;
	private BlogsEntryDisplayScreenlet blogEntryScreenlet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blog_posting_item);

		blogEntryScreenlet = findViewById(R.id.blog_item_screenlet);
		blogEntryScreenlet.setListener(this);
		swipeRefreshLayout = findViewById(R.id.pull_to_refresh);
		swipeRefreshLayout.setOnRefreshListener(this);

		if (savedInstanceState == null) {
			loadResource();
		}
	}

	private void loadResource() {
		showProgress();

		String blogEntryIdStr = getIntent().getStringExtra(BLOG_POST_ID);

		if (blogEntryIdStr != null) {
			long blogEntryId = Long.parseLong(blogEntryIdStr);

			blogEntryScreenlet.setEntryId(blogEntryId);

			blogEntryScreenlet.load();
		}
	}

	private void hideProgress() {
		swipeRefreshLayout.setRefreshing(false);
		blogEntryScreenlet.setVisibility(View.VISIBLE);
	}

	private void showProgress() {
		swipeRefreshLayout.setRefreshing(true);
		blogEntryScreenlet.setVisibility(View.GONE);
	}

	@Override
	public void onRefresh() {
		loadResource();
	}

	@Override
	public void onRetrieveAssetSuccess(AssetEntry assetEntry) {
		hideProgress();
	}

	@Override
	public void error(Exception e, String userAction) {
		hideProgress();
	}
}
