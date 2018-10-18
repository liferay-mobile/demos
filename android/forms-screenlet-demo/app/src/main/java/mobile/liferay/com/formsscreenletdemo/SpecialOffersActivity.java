package mobile.liferay.com.formsscreenletdemo;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import com.liferay.mobile.screens.util.AndroidUtil;
import com.liferay.mobile.screens.webcontent.WebContent;
import com.liferay.mobile.screens.webcontent.display.WebContentDisplayListener;
import com.liferay.mobile.screens.webcontent.display.WebContentDisplayScreenlet;

public class SpecialOffersActivity extends AppCompatActivity implements WebContentDisplayListener {

	WebContentDisplayScreenlet webContentDisplayScreenlet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_special_offers);

		webContentDisplayScreenlet = findViewById(R.id.web_content_display_screenlet);
		webContentDisplayScreenlet.setCustomCssFile(R.raw.special_offers_custom);
		webContentDisplayScreenlet.setListener(this);
		webContentDisplayScreenlet.load();
	}

	@Override
	public void error(Exception e, String userAction) {
		int icon = R.drawable.default_error_icon;
		int backgroundColor =
			ContextCompat.getColor(this, com.liferay.mobile.screens.viewsets.lexicon.R.color.lightRed);

		AndroidUtil.showCustomSnackbar(webContentDisplayScreenlet, userAction, Snackbar.LENGTH_LONG, backgroundColor,
			Color.WHITE, icon);
	}

	@Override
	public WebContent onWebContentReceived(WebContent html) {
		return null;
	}

	@Override
	public boolean onUrlClicked(String url) {
		return false;
	}

	@Override
	public boolean onWebContentTouched(View view, MotionEvent event) {
		return false;
	}
}
