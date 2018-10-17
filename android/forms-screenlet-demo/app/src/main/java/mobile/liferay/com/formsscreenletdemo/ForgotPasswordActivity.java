package mobile.liferay.com.formsscreenletdemo;

import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.liferay.mobile.screens.auth.forgotpassword.ForgotPasswordListener;
import com.liferay.mobile.screens.auth.forgotpassword.ForgotPasswordScreenlet;
import com.liferay.mobile.screens.util.AndroidUtil;

public class ForgotPasswordActivity extends AppCompatActivity implements ForgotPasswordListener {

	private CoordinatorLayout coordinatorLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgot_password);

		coordinatorLayout = findViewById(R.id.coordinator_forgot_password);

		ForgotPasswordScreenlet forgotPasswordScreenlet = findViewById(R.id.forgot_password_screenlet);
		forgotPasswordScreenlet.setAnonymousApiPassword(getString(R.string.liferay_anonymousApiPassword));
		forgotPasswordScreenlet.setAnonymousApiUserName(getString(R.string.liferay_anonymousApiUserName));
		forgotPasswordScreenlet.setListener(this);
	}

	@Override
	public void onForgotPasswordRequestSuccess(boolean passwordSent) {
		int backgroundColor =
			ContextCompat.getColor(this, com.liferay.mobile.screens.viewsets.lexicon.R.color.green_default);
		String message = getString(R.string.request_completed);

		AndroidUtil.showCustomSnackbar(coordinatorLayout, message, Snackbar.LENGTH_LONG, backgroundColor, Color.WHITE);
	}

	@Override
	public void onForgotPasswordRequestFailure(Exception e) {
		int icon = R.drawable.default_error_icon;
		int backgroundColor =
			ContextCompat.getColor(this, com.liferay.mobile.screens.viewsets.lexicon.R.color.lightRed);

		String message = getString(R.string.request_failed);

		AndroidUtil.showCustomSnackbar(coordinatorLayout, message, Snackbar.LENGTH_LONG, backgroundColor,Color.WHITE,
			icon);
	}

}
