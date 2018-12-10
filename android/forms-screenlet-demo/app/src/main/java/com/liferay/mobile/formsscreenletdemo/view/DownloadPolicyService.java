package com.liferay.mobile.formsscreenletdemo.view;

import android.app.DownloadManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;

/**
 * @author Luísa Lima
 */
class DownloadPolicyService {

	public DownloadPolicyService() {
	}

	public void downloadPolicy(String url, String fileName, DownloadManager downloadManager, ICallback onSuccess) {
		Uri uri = Uri.parse(url);
		long downloadReference;

		DownloadManager.Request request = new DownloadManager.Request(uri);
		request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
		request.setAllowedOverRoaming(false);
		request.setTitle(fileName + ".pdf");
		request.setDescription("policy");
		request.setVisibleInDownloadsUi(true);
		request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
		request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName + ".pdf");
		downloadReference = downloadManager.enqueue(request);

		new Thread(() -> {
			boolean downloading = true;

			while (downloading) {

				DownloadManager.Query q = new DownloadManager.Query();
				q.setFilterById(downloadReference);

				Cursor cursor = downloadManager.query(q);
				cursor.moveToFirst();

				if (cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
					== DownloadManager.STATUS_SUCCESSFUL) {
					downloading = false;

					onSuccess.invoke();
				}

				cursor.close();
			}
		}).start();
	}
}
