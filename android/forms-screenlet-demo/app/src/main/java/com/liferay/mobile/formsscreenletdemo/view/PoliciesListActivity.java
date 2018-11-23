package com.liferay.mobile.formsscreenletdemo.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
        DemoUtil.showNegativeSnackBar(this, assetListScreenlet);
    }

    @Override
    public void onListPageReceived(int startRow, int endRow, List entries, int rowCount) {

    }

    @Override
    public void onListItemSelected(AssetEntry element, View view) {
        Intent intent = new Intent(this, PolicyDetailActivity.class);
        intent.putExtra(Constants.ENTRY_ID_KEY, Long.valueOf(element.getValues().get(Constants.ENTRY_ID_KEY).toString()));
        startActivity(intent);
    }

    @Override
    public void error(Exception e, String userAction) {
        DemoUtil.showNegativeSnackBar(this, assetListScreenlet);
    }
}
