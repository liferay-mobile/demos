package com.liferay.mobile.formsscreenletdemo.presenter;

import com.liferay.apio.consumer.model.Thing;
import com.liferay.mobile.formsscreenletdemo.view.HomeActivity;

/**
 * @author Luísa Lima
 */
public interface HomeViewContract {
	interface HomeView {

		void onSignOutCompleted();

		void showDraftDialog();
	}

	interface HomePresenter {

		void checkForDraft();

		void loadDraft(Thing thing);

		void loadUserPortrait() throws Exception;

		void onActivityCreated(HomeActivity homeActivity);

		void onActivityDestroyed();

		void signOut();
	}
}
