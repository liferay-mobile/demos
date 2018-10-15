package mobile.liferay.com.formsscreenletdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.liferay.mobile.screens.auth.forgotpassword.ForgotPasswordListener;
import com.liferay.mobile.screens.auth.forgotpassword.ForgotPasswordScreenlet;

public class ForgotPasswordActivity extends AppCompatActivity implements ForgotPasswordListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgot_password);

		ForgotPasswordScreenlet screenlet = findViewById(R.id.forgot_password_screenlet);
		screenlet.setListener(this);
	}

	@Override
	public void onForgotPasswordRequestSuccess(boolean passwordSent) {

	}

	@Override
	public void onForgotPasswordRequestFailure(Exception e) {

	}
}
