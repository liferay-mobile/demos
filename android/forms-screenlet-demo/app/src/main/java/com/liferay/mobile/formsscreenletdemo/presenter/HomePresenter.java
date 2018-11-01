package com.liferay.mobile.formsscreenletdemo.presenter;

import com.liferay.apio.consumer.model.Thing;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.formsscreenletdemo.R;
import com.liferay.mobile.formsscreenletdemo.service.APIOFetchResourceService;
import com.liferay.mobile.formsscreenletdemo.util.Constants;
import com.liferay.mobile.formsscreenletdemo.util.DemoUtil;
import com.liferay.mobile.formsscreenletdemo.util.ResourceType;
import com.liferay.mobile.formsscreenletdemo.view.HomeActivity;
import com.liferay.mobile.formsscreenletdemo.view.login.LoginActivity;
import com.liferay.mobile.push.Push;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.storage.CredentialsStorageBuilder;
import com.liferay.mobile.screens.ddm.form.model.FormInstanceRecord;
import com.liferay.mobile.screens.ddm.form.service.APIOFetchLatestDraftService;
import com.liferay.mobile.screens.thingscreenlet.screens.views.Custom;
import com.liferay.mobile.screens.util.LiferayLogger;
import kotlin.Unit;
import org.json.JSONException;

/**
 * @author Luísa Lima
 */
public class HomePresenter implements HomeViewContract.HomePresenter {
	private HomeActivity homeActivity;

	@Override
	public void onActivityCreated(HomeActivity homeActivity) {
		this.homeActivity = homeActivity;

		setupForPushNotification();
	}

	@Override
	public void onActivityDestroyed() {
		this.homeActivity = null;
	}

	@Override
	public void checkForDraft() {
		String server = this.homeActivity.getResources().getString(R.string.liferay_server);
		String url = DemoUtil.getResourcePath(server, Constants.FORM_INSTANCE_ID, ResourceType.FORMS);

		new APIOFetchResourceService().fetchResource(url, this::onThingLoaded, this::logError);
	}

	@Override
	public void loadPortrait() throws Exception {
		String url = DemoUtil.getResourcePath(this.homeActivity.getResources().getString(R.string.liferay_server),
			SessionContext.getUserId(), ResourceType.PERSON);

		homeActivity.userPortrait.load(url, new Custom("portrait"),
			SessionContext.getCredentialsFromCurrentSession());

	}

	private Unit onThingLoaded(Thing thing) {
		loadDraft(thing);
		return Unit.INSTANCE;
	}

	private void setupForPushNotification() {
		Session session = SessionContext.createSessionFromCurrentSession();

		try {
			Push.with(session).withPortalVersion(71).onSuccess(jsonObject -> {

				try {
					String registrationId = jsonObject.getString("token");
					LiferayLogger.d("Device registrationId: " + registrationId);
				} catch (JSONException e) {
					LiferayLogger.e(e.getMessage(), e);
				}

			}).onFailure(e -> {
				LiferayLogger.e(e.getMessage(), e);
			}).register(this.homeActivity, this.homeActivity.getString(R.string.push_sender_id));

		} catch (Exception e) {
			LiferayLogger.e(e.getMessage(), e);
		}
	}

	@Override
	public void loadDraft(Thing thing) {
		new APIOFetchLatestDraftService().fetchLatestDraft(thing, this::onDraftLoaded, this::logError);
	}

	private Unit onDraftLoaded(Thing thing) {
		if (thing != null) {
			FormInstanceRecord formInstanceRecord = FormInstanceRecord.getConverter().invoke(thing);

			if (formInstanceRecord != null) {
				this.homeActivity.setupDialog();
			}
		}

		return Unit.INSTANCE;
	}

	@Override
	public void signOut() {
		SessionContext.logout();
		SessionContext.removeStoredCredentials(CredentialsStorageBuilder.StorageType.SHARED_PREFERENCES);

		this.homeActivity.finish();
		this.homeActivity.startDemoActivity(LoginActivity.class);
	}

	private Unit logError(Exception e) {
		LiferayLogger.e(e.getMessage());
		return Unit.INSTANCE;	}
}
