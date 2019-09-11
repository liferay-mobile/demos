package com.liferay.mobile.formsscreenletdemo.view.sessions;

import android.content.Intent;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import com.liferay.mobile.screens.asset.list.AssetListScreenlet;
import com.liferay.mobile.screens.base.list.BaseListListener;
import com.liferay.mobile.screens.blogs.BlogsEntry;
import com.liferay.mobile.formsscreenletdemo.R;

import java.util.List;

/**
 * @author Paulo Cruz
 */
public class BlogPostingsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener,
	BaseListListener<BlogsEntry> {

	private SwipeRefreshLayout swipeRefreshLayout;
	private AssetListScreenlet blogListScreenlet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_blog_postings);

		blogListScreenlet = findViewById(R.id.blogs_screenlet);
		blogListScreenlet.setListener(this);
		blogListScreenlet.setPortletItemName(getString(R.string.blog_postings_filter));

		swipeRefreshLayout = findViewById(R.id.pull_to_refresh);
		swipeRefreshLayout.setOnRefreshListener(this);

		if (savedInstanceState == null) {
			loadResource();
		}
	}

	private void loadResource() {
		showProgress();

		blogListScreenlet.loadPage(0);
	}

	private void hideProgress() {
		swipeRefreshLayout.setRefreshing(false);
		blogListScreenlet.setVisibility(View.VISIBLE);
	}

	private void showProgress() {
		swipeRefreshLayout.setRefreshing(true);
		blogListScreenlet.setVisibility(View.GONE);
	}

	@Override
	public void onRefresh() {
		loadResource();
	}

	@Override
	public void onListPageFailed(int i, Exception e) {
		hideProgress();
	}

	@Override
	public void onListPageReceived(int i, int i1, List<BlogsEntry> list, int i2) {
		hideProgress();
	}

	@Override
	public void onListItemSelected(BlogsEntry blogsEntry, View view) {
		Intent intent = new Intent(BlogPostingsActivity.this, BlogPostingItemActivity.class);
		intent.putExtra(BlogPostingItemActivity.BLOG_POST_ID, blogsEntry.getEntryId());
		startActivity(intent);
	}

	@Override
	public void error(Exception e, String userAction) {

	}
}
