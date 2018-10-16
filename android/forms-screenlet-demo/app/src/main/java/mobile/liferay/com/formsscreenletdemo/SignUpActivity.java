package mobile.liferay.com.formsscreenletdemo;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.liferay.mobile.screens.auth.signup.SignUpListener;
import com.liferay.mobile.screens.auth.signup.SignUpScreenlet;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.util.AndroidUtil;

public class SignUpActivity extends AppCompatActivity implements SignUpListener {

	private CoordinatorLayout coordinatorLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);

		coordinatorLayout = findViewById(R.id.coordinator_signup);

		SignUpScreenlet signUpScreenlet = findViewById(R.id.signup_screenlet);
		signUpScreenlet.setAnonymousApiPassword(getString(R.string.liferay_anonymousApiPassword));
		signUpScreenlet.setAnonymousApiUserName(getString(R.string.liferay_anonymousApiUserName));
		signUpScreenlet.setListener(this);
	}

	@Override
	public void onSignUpSuccess(User user) {
		int backgroundColor =
			ContextCompat.getColor(this, com.liferay.mobile.screens.viewsets.lexicon.R.color.green_default);
		int textColor = Color.WHITE;
		String message = getString(R.string.request_completed);

		AndroidUtil.showCustomSnackbar(coordinatorLayout, message, Snackbar.LENGTH_LONG, backgroundColor, textColor);

		startLoginActivity();
	}

	@Override
	public void onSignUpFailure(Exception e) {
		int icon = R.drawable.default_error_icon;
		int backgroundColor =
			ContextCompat.getColor(this, com.liferay.mobile.screens.viewsets.lexicon.R.color.lightRed);
		int textColor = Color.WHITE;
		String message = getString(R.string.request_failed);

		AndroidUtil.showCustomSnackbar(coordinatorLayout, message, Snackbar.LENGTH_LONG, backgroundColor, textColor,
			icon);
	}

	private void startLoginActivity(){
		Intent intent = new Intent(this, LoginActivity.class);
		finish();
		startActivity(intent);
	}
}
