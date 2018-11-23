package com.liferay.mobile.formsscreenletdemo.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import com.liferay.mobile.formsscreenletdemo.R;
import com.liferay.mobile.formsscreenletdemo.util.Constants;
import com.liferay.mobile.formsscreenletdemo.util.DemoUtil;
import com.liferay.mobile.screens.thingscreenlet.screens.ThingScreenlet;
import com.liferay.mobile.screens.thingscreenlet.screens.views.Detail;
import kotlin.Unit;

/**
 * @author Luísa Lima
 */
public class FAQDetailActivity extends AppCompatActivity {

	private ThingScreenlet FAQDetailScreenlet;
	private Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_faq_detail);

		FAQDetailScreenlet = findViewById(R.id.faq_content);
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
		String thingId = getIntent().getStringExtra(Constants.THING_ID_KEY);

		FAQDetailScreenlet.load(thingId, Detail.INSTANCE, DemoUtil.getCredentials(), thingScreenlet -> Unit.INSTANCE,
			exception -> Unit.INSTANCE);
	}
}
