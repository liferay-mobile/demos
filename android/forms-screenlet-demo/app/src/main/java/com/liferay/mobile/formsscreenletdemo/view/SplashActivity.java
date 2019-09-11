package com.liferay.mobile.formsscreenletdemo.view;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.liferay.mobile.formsscreenletdemo.R;
import com.liferay.mobile.formsscreenletdemo.view.login.LoginActivity;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.storage.CredentialsStorageBuilder;

public class SplashActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		new Handler().postDelayed(() -> {

			LiferayScreensContext.init(this);
			initNotificationChannels(getApplicationContext());
			SessionContext.loadStoredCredentialsAndServer(CredentialsStorageBuilder.StorageType.SHARED_PREFERENCES);

			if (SessionContext.hasUserInfo()) {
				startActivity(HomeActivity.class);
			} else {
				startActivity(LoginActivity.class);
			}

			finish();

		}, 2000);
	}

	public void initNotificationChannels(Context context) {
		if (Build.VERSION.SDK_INT < 26) {
			return;
		}

		NotificationManager notificationManager =
			(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		NotificationChannel channel = new NotificationChannel("default", "TGF", NotificationManager.IMPORTANCE_HIGH);
		channel.setDescription("TGF Notifications");

		notificationManager.createNotificationChannel(channel);
	}

	private void startActivity(Class<?> clazz) {
		Intent intent = new Intent(SplashActivity.this, clazz);
		startActivity(intent);
	}
}
