package com.liferay.mobile.formsscreenletdemo.view;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import com.liferay.mobile.formsscreenletdemo.R;
import com.liferay.mobile.formsscreenletdemo.util.Constants;
import com.liferay.mobile.formsscreenletdemo.util.DemoUtil;
import com.liferay.mobile.screens.rating.AssetRating;
import com.liferay.mobile.screens.rating.RatingListener;
import com.liferay.mobile.screens.rating.RatingScreenlet;
import com.liferay.mobile.screens.thingscreenlet.screens.ThingScreenlet;
import com.liferay.mobile.screens.thingscreenlet.screens.views.Detail;
import java.util.Objects;
import kotlin.Unit;

/**
 * @author Luísa Lima
 */
public class FAQDetailActivity extends AppCompatActivity implements RatingListener {

	private ThingScreenlet faqDetailScreenlet;
	private RatingScreenlet ratingScreenlet;
	private Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_faq_detail);

		ratingScreenlet = findViewById(R.id.faq_rating);
		faqDetailScreenlet = findViewById(R.id.faq_content);
		toolbar = findViewById(R.id.faq_toolbar);
		setSupportActionBar(toolbar);

		ActionBar actionBar = Objects.requireNonNull(getSupportActionBar());
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);

		if (savedInstanceState == null) {
			loadResource();
		}
	}

	@Override
	public boolean onSupportNavigateUp() {
		onBackPressed();
		return true;
	}

	private void loadResource() {
		String thingId = getIntent().getStringExtra(Constants.THING_ID_KEY);

		faqDetailScreenlet.load(thingId, Detail.INSTANCE, DemoUtil.getCredentials(), thingScreenlet -> {

			Long entryId = Long.parseLong(thingId.split("/")[thingId.split("/").length - 1]);
			setupRatingScreenlet(entryId);

			return Unit.INSTANCE;
		}, exception -> showError());
	}

	private void setupRatingScreenlet(Long entryId) {
		ratingScreenlet.setClassPK(entryId);
		ratingScreenlet.setClassName(Constants.BLOG_POSTING_CLASS_NAME);
		ratingScreenlet.load();
		ratingScreenlet.setAutoLoad(true);
		ratingScreenlet.setListener(this);
	}

	private Unit showError() {
		DemoUtil.showErrorSnackBar(this, ratingScreenlet);

		return Unit.INSTANCE;
	}

	@Override
	public void error(Exception e, String userAction) {
		DemoUtil.showErrorSnackBar(this, faqDetailScreenlet);
	}

	@Override
	public void onRatingOperationSuccess(AssetRating assetRating) {

	}
}
