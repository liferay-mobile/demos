package com.liferay.mobile.formsscreenletdemo.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.liferay.mobile.formsscreenletdemo.R;
import com.liferay.mobile.screens.asset.AssetEntry;
import com.liferay.mobile.screens.asset.list.AssetListScreenlet;
import com.liferay.mobile.screens.base.list.BaseListListener;

import java.util.List;

/**
 * @author Vitória Mendes
 * @author Luísa Lima
 */
public class MyPoliciesActivity extends AppCompatActivity implements BaseListListener<AssetEntry> {

    private Toolbar toolbar;
    private AssetListScreenlet assetListScreenlet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_policies);
        toolbar = findViewById(R.id.my_policies_toolbar);
        setSupportActionBar(toolbar);

        assetListScreenlet = findViewById(R.id.my_policies);
        assetListScreenlet.setListener(this);
    }

    @Override
    public void onListPageFailed(int startRow, Exception e) {

    }

    @Override
    public void onListPageReceived(int startRow, int endRow, List entries, int rowCount) {

    }

    @Override
    public void onListItemSelected(AssetEntry element, View view) {

    }

    @Override
    public void error(Exception e, String userAction) {

    }
}
