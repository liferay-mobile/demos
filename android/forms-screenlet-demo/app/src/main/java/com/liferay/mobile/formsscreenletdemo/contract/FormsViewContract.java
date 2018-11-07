package com.liferay.mobile.formsscreenletdemo.contract;

import com.liferay.mobile.formsscreenletdemo.view.FormsActivity;
import kotlin.Unit;

/**
 * @author Luísa Lima
 */
public interface FormsViewContract {
	interface FormsView {

		void hideProgress();

		void recyclerViewWorkaround();

		Unit showError(String message);
	}

	interface FormsPresenter {

		void onActivityCreated(FormsActivity formsActivity);

		void onActivityDestroyed();

		void loadResource();
	}
}
