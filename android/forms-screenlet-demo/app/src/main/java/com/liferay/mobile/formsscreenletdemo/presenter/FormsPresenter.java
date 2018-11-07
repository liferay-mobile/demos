package com.liferay.mobile.formsscreenletdemo.presenter;

import android.view.View;
import com.liferay.apio.consumer.cache.ThingsCache;
import com.liferay.mobile.formsscreenletdemo.R;
import com.liferay.mobile.formsscreenletdemo.contract.FormsViewContract;
import com.liferay.mobile.formsscreenletdemo.util.Constants;
import com.liferay.mobile.formsscreenletdemo.util.DemoUtil;
import com.liferay.mobile.formsscreenletdemo.util.ResourceType;
import com.liferay.mobile.formsscreenletdemo.view.FormsActivity;
import com.liferay.mobile.screens.thingscreenlet.screens.views.Detail;
import kotlin.Unit;

/**
 * @author Luísa Lima
 */
public class FormsPresenter implements FormsViewContract.FormsPresenter {

	private FormsActivity formsActivity;

	public void onCreateActivity(FormsActivity formsActivity) {
		this.formsActivity = formsActivity;
	}

	public void onActivityDestroyed() {
		this.formsActivity = null;
	}

	public void loadResource() {
		String url = DemoUtil.getResourcePath(formsActivity.getResources().getString(R.string.liferay_server),
			Constants.FORM_INSTANCE_ID, ResourceType.FORMS);

		formsActivity.progressBar.show(formsActivity.getString(R.string.loading_form));
		formsActivity.formsScreenlet.setVisibility(View.GONE);

		ThingsCache.clearCache();

		formsActivity.formsScreenlet.load(url, Detail.INSTANCE, DemoUtil.getCredentials(), thingScreenlet -> {
			formsActivity.hideProgress();
			formsActivity.errorLayout.setVisibility(View.GONE);
			formsActivity.recyclerViewWorkaround();

			return Unit.INSTANCE;
		}, e -> formsActivity.showError(e.getMessage()));
	}
}
