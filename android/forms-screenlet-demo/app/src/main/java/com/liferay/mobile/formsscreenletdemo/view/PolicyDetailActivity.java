package com.liferay.mobile.formsscreenletdemo.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.liferay.mobile.formsscreenletdemo.R;
import com.liferay.mobile.formsscreenletdemo.util.Constants;
import com.liferay.mobile.formsscreenletdemo.util.DemoUtil;
import com.liferay.mobile.screens.asset.AssetEntry;
import com.liferay.mobile.screens.asset.display.AssetDisplayInnerScreenletListener;
import com.liferay.mobile.screens.asset.display.AssetDisplayListener;
import com.liferay.mobile.screens.asset.display.AssetDisplayScreenlet;
import com.liferay.mobile.screens.base.BaseScreenlet;

/**
 * @author Vitória Mendes
 * @author Luísa Lima
 */
public class PolicyDetailActivity extends AppCompatActivity implements AssetDisplayListener, AssetDisplayInnerScreenletListener {

    private AssetDisplayScreenlet assetDisplayScreenlet;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policy_detail);

        toolbar = findViewById(R.id.my_policy_toolbar);
        setSupportActionBar(toolbar);

        assetDisplayScreenlet = findViewById(R.id.my_policy);

        long entryId = getIntent().getLongExtra(Constants.ENTRY_ID_KEY, 0);

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
        DemoUtil.showNegativeSnackBar(this, assetDisplayScreenlet);
    }

    @Override
    public void onConfigureChildScreenlet(AssetDisplayScreenlet screenlet, BaseScreenlet innerScreenlet, AssetEntry assetEntry) {

    }

    @Override
    public View onRenderCustomAsset(AssetEntry assetEntry) {
        return null;
    }
}
