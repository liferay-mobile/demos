package com.liferay.mobile.formsscreenletdemo.view;

import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import com.liferay.mobile.formsscreenletdemo.R;
import com.liferay.mobile.formsscreenletdemo.util.DemoUtil;
import com.liferay.mobile.screens.rating.AssetRating;
import com.liferay.mobile.screens.rating.RatingListener;
import com.liferay.mobile.screens.rating.RatingScreenlet;
import com.liferay.mobile.screens.thingscreenlet.screens.ThingScreenlet;
import com.liferay.mobile.screens.thingscreenlet.screens.views.Detail;
import com.liferay.mobile.screens.util.AndroidUtil;
import kotlin.Unit;

/**
 * @author Luísa Lima
 */
public class FaqActivity extends AppCompatActivity {

	public static final String THING_ID_EXTRA = "thingId";
	private ThingScreenlet faqContent;
	private Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_faq);

		faqContent = findViewById(R.id.faq_content);
		toolbar = findViewById(R.id.faq_toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);


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
		String url = getIntent().getStringExtra(THING_ID_EXTRA);

		faqContent.load(url, Detail.INSTANCE, DemoUtil.getCredentials(), thingScreenlet -> Unit.INSTANCE,
			exception -> Unit.INSTANCE);
	}

}
