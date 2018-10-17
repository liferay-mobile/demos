package mobile.liferay.com.formsscreenletdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.liferay.mobile.screens.asset.AssetEntry;
import com.liferay.mobile.screens.asset.list.AssetListScreenlet;
import com.liferay.mobile.screens.base.list.BaseListListener;
import com.liferay.mobile.screens.context.LiferayScreensContext;
import com.liferay.mobile.screens.context.LiferayServerContext;
import com.liferay.mobile.screens.viewsets.defaultviews.DefaultAnimation;
import java.util.List;

import static net.openid.appauth.internal.Logger.info;

public class TakeCareListActivity extends AppCompatActivity implements BaseListListener {

	private AssetListScreenlet assetListScreenlet;
	private long classNameId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_take_care_list);

		classNameId = getIntent().getLongExtra("classNameId", 0);

		assetListScreenlet = findViewById(R.id.asset_list_screenlet);
		assetListScreenlet.setClassNameId(classNameId);

		if (classNameId == Long.parseLong(
			LiferayScreensContext.getContext().getResources().getString(R.string.default_class_name_id_microblogs))
			|| classNameId == Long.parseLong(
			LiferayScreensContext.getContext().getResources().getString(R.string.default_class_name_id_organization))
			|| classNameId == Long.parseLong(
			LiferayScreensContext.getContext().getResources().getString(R.string.default_class_name_id_site))
			|| classNameId == Long.parseLong(
			LiferayScreensContext.getContext().getResources().getString(R.string.default_class_name_id_user))) {

			assetListScreenlet.setGroupId(Long.parseLong(getResources().getString(R.string.liferay_parent_group_id)));
		}

		assetListScreenlet.setListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();

		LiferayServerContext.setGroupId(Long.parseLong(getResources().getString(R.string.liferay_group_id)));
		assetListScreenlet.loadPage(0);
	}

	@Override
	public void onListPageFailed(int startRow, Exception e) {

	}

	@Override
	public void onListPageReceived(int startRow, int endRow, List entries, int rowCount) {

	}

	@Override
	public void onListItemSelected(Object element, View view) {

	}

	@Override
	public void error(Exception e, String userAction) {

	}
}
