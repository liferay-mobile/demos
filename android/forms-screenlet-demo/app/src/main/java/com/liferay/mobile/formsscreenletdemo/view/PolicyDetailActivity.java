package com.liferay.mobile.formsscreenletdemo.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.liferay.mobile.formsscreenletdemo.R;
import com.liferay.mobile.screens.asset.AssetEntry;
import com.liferay.mobile.screens.asset.display.AssetDisplayInnerScreenletListener;
import com.liferay.mobile.screens.asset.display.AssetDisplayListener;
import com.liferay.mobile.screens.asset.display.AssetDisplayScreenlet;
import com.liferay.mobile.screens.base.BaseScreenlet;
import com.liferay.mobile.screens.util.AndroidUtil;

/**
 * @author Vitória Mendes
 * @author Luísa Lima
 */
public class PolicyDetailActivity extends AppCompatActivity implements AssetDisplayListener, AssetDisplayInnerScreenletListener {

    private AssetDisplayScreenlet assetDisplayScreenlet;
    private Toolbar toolbar;
    private final String ENTRY_ID = "entryId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy);

        toolbar = findViewById(R.id.my_policy_toolbar);
        setSupportActionBar(toolbar);

        assetDisplayScreenlet = findViewById(R.id.my_policy);

        long entryId = getIntent().getLongExtra(ENTRY_ID, 0);

        assetDisplayScreenlet.setEntryId(entryId);
        assetDisplayScreenlet.loadAsset();
        assetDisplayScreenlet.setListener(this);
        assetDisplayScreenlet.setConfigurationListener(this);
    }

    @Override
    public void onRetrieveAssetSuccess(AssetEntry assetEntry) {
        toolbar.setTitle(assetEntry.getTitle());
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
    public void onConfigureChildScreenlet(AssetDisplayScreenlet screenlet, BaseScreenlet innerScreenlet, AssetEntry assetEntry) {

    }

    @Override
    public View onRenderCustomAsset(AssetEntry assetEntry) {
        return null;
    }
}
