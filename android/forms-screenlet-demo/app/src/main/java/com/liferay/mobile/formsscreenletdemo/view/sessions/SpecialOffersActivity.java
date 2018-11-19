package com.liferay.mobile.formsscreenletdemo.view.sessions;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.liferay.mobile.formsscreenletdemo.R;
import com.liferay.mobile.screens.util.AndroidUtil;
import com.liferay.mobile.screens.web.WebListener;
import com.liferay.mobile.screens.web.WebScreenlet;
import com.liferay.mobile.screens.web.WebScreenletConfiguration;

public class SpecialOffersActivity extends AppCompatActivity implements WebListener {

	WebScreenlet webScreenlet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_special_offers);

		webScreenlet = findViewById(R.id.web_screenlet);
		webScreenlet.setListener(this);

		WebScreenletConfiguration config = new WebScreenletConfiguration.Builder("/web/guest/special-offers")
				.addRawCss(R.raw.special_offers_custom, "special_offers_custom.css")
				.load();

		webScreenlet.setWebScreenletConfiguration(config);
		webScreenlet.load();
	}

	@Override
	public void error(Exception e, String userAction) {
		int icon = R.drawable.default_error_icon;
		int backgroundColor =
			ContextCompat.getColor(this, com.liferay.mobile.screens.viewsets.lexicon.R.color.lightRed);

		AndroidUtil.showCustomSnackbar(webScreenlet, userAction, Snackbar.LENGTH_LONG, backgroundColor, Color.WHITE,
			icon);
	}

	@Override
	public void onPageLoaded(String url) {

	}

	@Override
	public void onScriptMessageHandler(String namespace, String body) {

	}
}
