package com.liferay.mobile.formsscreenletdemo.presenter;

import android.support.design.widget.NavigationView;
import android.view.MenuItem;
import android.view.View;
import com.liferay.apio.consumer.model.Thing;
import com.liferay.mobile.formsscreenletdemo.view.HomeActivity;
import com.liferay.mobile.screens.thingscreenlet.screens.ThingScreenlet;
import kotlin.Unit;

/**
 * @author Luísa Lima
 */
public interface HomeViewContract {
	interface HomeView {

		void selectDrawerItem(MenuItem item);

		void startActivity(Class<?> clazz);

		void setupDrawerContent(NavigationView navigationView);

		void setupDialog();

		void setupNavigationDrawer();

	}
	interface HomePresenter {

		void checkForDraft();

		void loadDraft(Thing thing);

		void loadPortrait() throws Exception;

		void onActivityCreated(HomeActivity homeActivity);

		void onActivityDestroyed();

		void signOut();

	}
}
