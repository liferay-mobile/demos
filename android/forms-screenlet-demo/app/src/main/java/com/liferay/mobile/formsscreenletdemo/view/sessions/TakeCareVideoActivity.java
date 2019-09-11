package com.liferay.mobile.formsscreenletdemo.view.sessions;

import android.graphics.Color;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.liferay.mobile.screens.asset.AssetEntry;
import com.liferay.mobile.screens.asset.display.AssetDisplayInnerScreenletListener;
import com.liferay.mobile.screens.asset.display.AssetDisplayListener;
import com.liferay.mobile.screens.asset.display.AssetDisplayScreenlet;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.util.AndroidUtil;
import com.liferay.mobile.formsscreenletdemo.R;

public class TakeCareVideoActivity extends AppCompatActivity implements AssetDisplayListener,
	AssetDisplayInnerScreenletListener {

	AssetDisplayScreenlet assetDisplayScreenlet;
	private final String ENTRY_ID = "entryId";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_take_care_video);

		assetDisplayScreenlet = findViewById(R.id.asset_display_screenlet);

		long entryId = getIntent().getLongExtra(ENTRY_ID, 0);

		assetDisplayScreenlet.setEntryId(entryId);
		assetDisplayScreenlet.loadAsset();
		assetDisplayScreenlet.setListener(this);
		assetDisplayScreenlet.setConfigurationListener(this);
	}

	@Override
	public void error(Exception e, String userAction) {
		int icon = R.drawable.default_error_icon;
		int backgroundColor =
			ContextCompat.getColor(this, com.liferay.mobile.screens.viewsets.lexicon.R.color.lightRed);
		String message = getString(R.string.request_failed);

		AndroidUtil.showCustomSnackbar(assetDisplayScreenlet, message, Snackbar.LENGTH_LONG, backgroundColor, Color.WHITE,
			icon);
	}

	@Override
	public void onRetrieveAssetSuccess(AssetEntry assetEntry) {

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
