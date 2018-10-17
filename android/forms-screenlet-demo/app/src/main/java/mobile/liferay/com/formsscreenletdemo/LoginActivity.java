package mobile.liferay.com.formsscreenletdemo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.liferay.mobile.screens.auth.login.LoginListener;
import com.liferay.mobile.screens.auth.login.LoginScreenlet;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.context.storage.CredentialsStorageBuilder;
import com.liferay.mobile.screens.util.AndroidUtil;
import mobile.liferay.com.formsscreenletdemo.util.FormsUtil;

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

		FormsUtil.setLightStatusBar(this, getWindow());

		TextView forgotPasswordText = findViewById(R.id.liferay_forgot_link);
		forgotPasswordText.setOnClickListener(this::startForgotPasswordActivity);

		setDefaultValues();
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
