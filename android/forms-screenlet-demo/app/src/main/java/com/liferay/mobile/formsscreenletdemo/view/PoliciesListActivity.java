package com.liferay.mobile.formsscreenletdemo.view;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.View;

import com.liferay.mobile.formsscreenletdemo.R;
import com.liferay.mobile.formsscreenletdemo.util.Constants;
import com.liferay.mobile.formsscreenletdemo.util.DemoUtil;
import com.liferay.mobile.screens.asset.AssetEntry;
import com.liferay.mobile.screens.asset.list.AssetListScreenlet;
import com.liferay.mobile.screens.base.list.BaseListListener;

import java.util.List;

/**
 * @author Vitória Mendes
 * @author Luísa Lima
 */
public class PoliciesListActivity extends AppCompatActivity implements BaseListListener<AssetEntry> {

    private AssetListScreenlet assetListScreenlet;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_policies_list);
        toolbar = findViewById(R.id.my_policies_toolbar);
        setSupportActionBar(toolbar);

        assetListScreenlet = findViewById(R.id.my_policies);
        assetListScreenlet.setListener(this);
    }

    @Override
    public void onListPageFailed(int startRow, Exception e) {
        DemoUtil.showErrorSnackBar(this, assetListScreenlet);
    }

    @Override
    public void onListPageReceived(int startRow, int endRow, List entries, int rowCount) {

    }

    @Override
    public void onListItemSelected(AssetEntry element, View view) {
        Intent intent = new Intent(this, PolicyDetailActivity.class);
        intent.putExtra(Constants.ENTRY_ID_KEY, Long.valueOf(element.getValues().get(Constants.ENTRY_ID_KEY).toString()));
        intent.putExtra(Constants.ASSET_ENTRY_URL, element.getValues().get(Constants.ASSET_ENTRY_URL).toString());
        intent.putExtra(Constants.ASSET_ENTRY_TITLE, element.getValues().get(Constants.ASSET_ENTRY_TITLE).toString());
        startActivity(intent);
    }

    @Override
    public void error(Exception e, String userAction) {
        DemoUtil.showErrorSnackBar(this, assetListScreenlet);
    }
}
