package com.liferay.mobile.formsscreenletdemo.view;

import android.content.Context;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.liferay.mobile.formsscreenletdemo.R;
import com.liferay.mobile.screens.asset.AssetEntry;
import com.liferay.mobile.screens.asset.list.view.AssetListViewModel;
import com.liferay.mobile.screens.base.list.BaseListAdapter;
import com.liferay.mobile.screens.base.list.BaseListScreenletView;
import com.liferay.mobile.screens.viewsets.defaultviews.asset.list.AssetListAdapter;

public class AssetListDemoView extends BaseListScreenletView<AssetEntry, BaseListAdapter.ViewHolder, AssetListAdapter>
        implements AssetListViewModel {

    public AssetListDemoView(Context context) {
        super(context);
    }

    public AssetListDemoView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public AssetListDemoView(Context context, AttributeSet attributeSet, int defaultStyle) {
        super(context, attributeSet, defaultStyle);
    }

    @Override
    protected AssetListAdapter createListAdapter(int itemLayoutId, int itemProgressLayoutId) {
        return new AssetListAdapter(itemLayoutId, itemProgressLayoutId, this);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.layout_policy_item;
    }

    @Override
    protected RecyclerView.ItemDecoration getDividerDecoration() {
        return new DividerItemDecoration(getContext(), 0);
    }
}
