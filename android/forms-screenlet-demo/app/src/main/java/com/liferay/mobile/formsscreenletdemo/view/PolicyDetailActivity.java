package com.liferay.mobile.formsscreenletdemo.view;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.liferay.mobile.formsscreenletdemo.R;
import com.liferay.mobile.formsscreenletdemo.util.Constants;
import com.liferay.mobile.formsscreenletdemo.util.DemoUtil;
import com.liferay.mobile.screens.asset.AssetEntry;
import com.liferay.mobile.screens.asset.display.AssetDisplayInnerScreenletListener;
import com.liferay.mobile.screens.asset.display.AssetDisplayListener;
import com.liferay.mobile.screens.asset.display.AssetDisplayScreenlet;
import com.liferay.mobile.screens.base.BaseScreenlet;

import static com.liferay.mobile.screens.context.LiferayScreensContext.getContext;

/**
 * @author Vitória Mendes
 * @author Luísa Lima
 */
public class PolicyDetailActivity extends AppCompatActivity
	implements AssetDisplayListener, AssetDisplayInnerScreenletListener {

	private AssetDisplayScreenlet assetDisplayScreenlet;
	private Toolbar toolbar;
	private DownloadManager downloadManager;
	private String assetEntryUrl;
	private String assetEntryTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_policy_detail);

		toolbar = findViewById(R.id.my_policy_toolbar);
		setSupportActionBar(toolbar);

		assetDisplayScreenlet = findViewById(R.id.my_policy);

		long entryId = getIntent().getLongExtra(Constants.ENTRY_ID_KEY, 0);
		assetEntryUrl = getIntent().getStringExtra(Constants.ASSET_ENTRY_URL);
		assetEntryTitle = getIntent().getStringExtra(Constants.ASSET_ENTRY_TITLE);

		assetDisplayScreenlet.setEntryId(entryId);
		assetDisplayScreenlet.loadAsset();
		assetDisplayScreenlet.setListener(this);
		assetDisplayScreenlet.setConfigurationListener(this);

		downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.policy_detail_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.download_policy) {
			int permissionCheck = ContextCompat.checkSelfPermission(this,
				Manifest.permission.WRITE_EXTERNAL_STORAGE);

			if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
				ActivityCompat.requestPermissions(this, new String[] {
					Manifest.permission.WRITE_EXTERNAL_STORAGE },
					112);
			} else {
				String url = getResources().getString(R.string.liferay_server) + assetEntryUrl;
				new DownloadPolicyService().downloadPolicy(url, assetEntryTitle, downloadManager,
					() -> runOnUiThread(() -> showDownloadCompletedToast()));
			}
		}

		return super.onOptionsItemSelected(item);
	}

	private void showDownloadCompletedToast() {
		Toast toast = new Toast(getContext());
		toast.setDuration(Toast.LENGTH_SHORT);
		String message =
			getResources().getString(R.string.download_file) + " " + assetEntryTitle + " " + getResources().getString(
				R.string.completed);

		LayoutInflater inflater = LayoutInflater.from(getContext());
		View layout = inflater.inflate(R.layout.toast_layout_default, findViewById(R.id.toast_layout_default));
		TextView ratingToastMessage = layout.findViewById(R.id.toast_message);
		ratingToastMessage.setText(message);
		toast.setView(layout);
		toast.show();
	}

	@Override
	public void onRetrieveAssetSuccess(AssetEntry assetEntry) {
		toolbar.setTitle(assetEntry.getTitle());
	}

	@Override
	public void error(Exception e, String userAction) {
		DemoUtil.showErrorSnackBar(this, assetDisplayScreenlet);
	}

	@Override
	public void onConfigureChildScreenlet(AssetDisplayScreenlet screenlet, BaseScreenlet innerScreenlet,
		AssetEntry assetEntry) {

	}

	@Override
	public View onRenderCustomAsset(AssetEntry assetEntry) {
		return null;
	}
}
