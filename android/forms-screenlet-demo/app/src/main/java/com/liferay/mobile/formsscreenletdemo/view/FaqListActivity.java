package com.liferay.mobile.formsscreenletdemo.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import com.liferay.apio.consumer.model.Thing;
import com.liferay.mobile.formsscreenletdemo.R;
import com.liferay.mobile.formsscreenletdemo.util.Constants;
import com.liferay.mobile.formsscreenletdemo.util.DemoUtil;
import com.liferay.mobile.formsscreenletdemo.util.ResourceType;
import com.liferay.mobile.screens.thingscreenlet.model.BlogPosting;
import com.liferay.mobile.screens.thingscreenlet.screens.ThingScreenlet;
import com.liferay.mobile.screens.thingscreenlet.screens.events.ScreenletEvents;
import com.liferay.mobile.screens.thingscreenlet.screens.views.BaseView;
import com.liferay.mobile.screens.thingscreenlet.screens.views.Detail;
import com.liferay.mobile.screens.thingscreenlet.screens.views.Row;
import com.liferay.mobile.screens.thingscreenlet.screens.views.Scenario;
import kotlin.Unit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Luísa Lima
 */
public class FaqListActivity extends AppCompatActivity implements ScreenletEvents {

	private ThingScreenlet faqPopularList;
	private Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_faq_list);

		toolbar = findViewById(R.id.faq_list_toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);

		faqPopularList = findViewById(R.id.faq_polular);
		faqPopularList.setScreenletEvents(this);

		BlogPosting.DEFAULT_VIEWS.put(Detail.INSTANCE, R.layout.layout_blog_posting_detail);
		BlogPosting.DEFAULT_VIEWS.put(Row.INSTANCE, R.layout.layout_blog_posting_row);

		if (savedInstanceState == null) {
			loadFaqPopularListResource();
		}
	}

	@Override
	public boolean onSupportNavigateUp() {
		onBackPressed();
		return true;
	}

	private void loadFaqPopularListResource() {

		String url = DemoUtil.getResourcePath(getResources().getString(R.string.liferay_server),
			Constants.CONTENT_SPACE_ID, ResourceType.BLOGS);

		faqPopularList.load(url, Detail.INSTANCE, DemoUtil.getCredentials(), thingScreenlet -> Unit.INSTANCE,
			exception -> Unit.INSTANCE);
	}

	@Nullable
	@Override
	public <T extends BaseView> View.OnClickListener onClickEvent(@NotNull T baseView, @NotNull View view,
		@NotNull Thing thing) {
		Intent intent = new Intent(this, FaqActivity.class);
		intent.putExtra(FaqActivity.THING_ID_EXTRA, thing.getId());
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
