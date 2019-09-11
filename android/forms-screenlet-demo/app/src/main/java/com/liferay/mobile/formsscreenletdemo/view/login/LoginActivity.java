package com.liferay.mobile.formsscreenletdemo.view.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.liferay.mobile.formsscreenletdemo.util.DemoUtil;
import com.liferay.mobile.formsscreenletdemo.view.HomeActivity;
import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.auth.login.LoginScreenlet;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.util.AndroidUtil;
import com.liferay.mobile.formsscreenletdemo.R;

/**
 * @author Luísa Lima
 */
public class LoginActivity extends AppCompatActivity implements LoginListener {

	private CoordinatorLayout coordinatorLayout;
	private LoginScreenlet loginScreenlet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		coordinatorLayout = findViewById(R.id.coordinator);
		loginScreenlet = findViewById(R.id.login);
		loginScreenlet.setListener(this);

		DemoUtil.setLightStatusBar(this, getWindow());

		TextView forgotPasswordText = findViewById(R.id.liferay_forgot_link);
		forgotPasswordText.setOnClickListener(this::startForgotPasswordActivity);

		TextView signUpText = findViewById(R.id.liferay_signup_textview);
		signUpText.setOnClickListener(this::startSignUpActivity);

		setDefaultValues();
	}

	private void startSignUpActivity(View view) {
		Intent intent = new Intent(this, SignUpActivity.class);
		startActivity(intent);
	}

	private void startForgotPasswordActivity(View view) {
		Intent intent = new Intent(this, ForgotPasswordActivity.class);
		startActivity(intent);
	}

	private void startHomeActivity() {
		Intent intent = new Intent(this, HomeActivity.class);
		finish();
		startActivity(intent);
	}

	@Override
	public void onLoginSuccess(User user) {
		startHomeActivity();
	}

	@Override
	public void onLoginFailure(Exception e) {
		int icon = R.drawable.default_error_icon;
		int backgroundColor =
			ContextCompat.getColor(this, com.liferay.mobile.screens.viewsets.lexicon.R.color.lightRed);
		int textColor = Color.WHITE;
		String message = getString(R.string.login_failed);

		AndroidUtil.showCustomSnackbar(coordinatorLayout, message, Snackbar.LENGTH_LONG, backgroundColor, textColor,
			icon);
	}

	@Override
	public void onAuthenticationBrowserShown() {

	}

	private void setDefaultValues() {
		EditText login = loginScreenlet.findViewById(R.id.liferay_login);
		login.setText(getString(R.string.default_email));

		EditText password = loginScreenlet.findViewById(R.id.liferay_password);
		password.setText(getString(R.string.default_password));
	}
}
