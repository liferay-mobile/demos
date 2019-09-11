package com.liferay.mobile.formsscreenletdemo.push;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;

import androidx.core.app.NotificationCompat;
import com.liferay.mobile.formsscreenletdemo.R;
import com.liferay.mobile.formsscreenletdemo.view.WorkflowActivity;
import com.liferay.mobile.push.PushNotificationsService;
import com.liferay.mobile.screens.util.LiferayLogger;
import java.util.Random;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Victor Oliveira
 */
public class PushService extends PushNotificationsService {

	@Override
	public void onPushNotification(JSONObject jsonObject) {
		super.onPushNotification(jsonObject);

		if (jsonObject != null && jsonObject.has("notificationMessage")) {

			try {
				String message = jsonObject.getString("notificationMessage");
				String title = jsonObject.getString("from");

				Intent homeIntent = new Intent(getApplicationContext(), WorkflowActivity.class);
				homeIntent.putExtra("workflow", jsonObject.toString());

				PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, homeIntent, PendingIntent.FLAG_UPDATE_CURRENT);

				NotificationCompat.Builder notificationBuilder =
					new NotificationCompat.Builder(this, "default")
						.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.icon))
						.setSmallIcon(R.drawable.icon)
						.setContentTitle(title)
						.setContentText(message)
						.setPriority(NotificationCompat.PRIORITY_MAX)
						.setAutoCancel(true)
						.setContentIntent(pendingIntent)
						.setDefaults(Notification.DEFAULT_ALL);

				NotificationManager notificationManager =
					(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

				Random random = new Random();
				int myRandomInt = random.nextInt(10000);
				notificationManager.notify(myRandomInt, notificationBuilder.build());

			} catch (JSONException e) {
				LiferayLogger.e(e.getMessage(), e);
			}
		}
	}
}