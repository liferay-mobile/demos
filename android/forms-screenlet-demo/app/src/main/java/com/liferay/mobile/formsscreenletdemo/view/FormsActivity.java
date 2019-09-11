package com.liferay.mobile.formsscreenletdemo.view;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;
import com.liferay.mobile.formsscreenletdemo.R;
import com.liferay.mobile.formsscreenletdemo.util.DemoUtil;
import com.liferay.mobile.screens.base.ModalProgressBarWithLabel;
import com.liferay.mobile.screens.ddm.form.DDMFormListener;
import com.liferay.mobile.screens.ddm.form.DDMFormScreenlet;
import com.liferay.mobile.screens.ddm.form.model.FormInstance;
import com.liferay.mobile.screens.ddm.form.model.FormInstanceRecord;
import com.liferay.mobile.screens.util.AndroidUtil;

/**
 * @author Luísa Lima
 */
public class FormsActivity extends AppCompatActivity
    implements SwipeRefreshLayout.OnRefreshListener, SwipeRefreshLayout.OnChildScrollUpCallback,
    DDMFormListener {

    private LinearLayout errorLayout;
    private DDMFormScreenlet formsScreenlet;
    private ModalProgressBarWithLabel progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forms);
        setupToolbar();

        formsScreenlet = findViewById(R.id.forms_screenlet);
        errorLayout = findViewById(R.id.form_detail_error_view);
        progressBar = findViewById(R.id.liferay_modal_progress);
        swipeRefreshLayout = findViewById(R.id.pull_to_refresh);
        progressBar.disableDimBackground();
        formsScreenlet.setDDMFormListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);

        DemoUtil.setLightStatusBar(this, getWindow());

        if (savedInstanceState == null) {
            loadResource();
        }
    }

    private void loadResource() {
        progressBar.show(getString(R.string.loading_form));
        formsScreenlet.setVisibility(View.GONE);
        formsScreenlet.load();
    }

    /*
     * TODO: Find another solution
     */
    private void recyclerViewWorkaround() {
        ScrollView scrollView = formsScreenlet.findViewById(R.id.multipage_scroll_view);

        swipeRefreshLayout.getViewTreeObserver().addOnScrollChangedListener(() -> {
            if (scrollView != null && scrollView.getScrollY() == 0) {
                swipeRefreshLayout.setEnabled(true);
            } else {
                swipeRefreshLayout.setEnabled(false);
            }
        });
    }

    private void hideProgress() {
        progressBar.hide();
        swipeRefreshLayout.setRefreshing(false);
        formsScreenlet.setVisibility(View.VISIBLE);
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.forms_toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.toolbar_text_color));
        setSupportActionBar(toolbar);
    }

    private void showError(String message) {
        hideProgress();

        int icon = R.drawable.default_error_icon;
        int textColor = Color.WHITE;
        int backgroundColor =
            ContextCompat.getColor(this, com.liferay.mobile.screens.viewsets.lexicon.R.color.lightRed);

        AndroidUtil.showCustomSnackbar(formsScreenlet, message, Snackbar.LENGTH_LONG, backgroundColor, textColor, icon);
    }

    @Override
    public boolean canChildScrollUp(@NonNull SwipeRefreshLayout parent, @Nullable View child) {
        return false;
    }

    @Override
    public void onDraftLoaded(@NonNull FormInstanceRecord formInstanceRecord) {

    }

    @Override
    public void onDraftSaved(@NonNull FormInstanceRecord formInstanceRecord) {

    }

    @Override
    public void onError(Throwable throwable) {
        showError(throwable.getMessage());
    }

    @Override
    public void onFormLoaded(@NonNull FormInstance formInstance) {
        hideProgress();
        errorLayout.setVisibility(View.GONE);
        recyclerViewWorkaround();
    }

    @Override
    public void onFormSubmitted(@NonNull FormInstanceRecord formInstanceRecord) {
        LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
        View layout = inflater.inflate(R.layout.toast_layout_default, findViewById(R.id.toast_layout_default));

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

        new Handler().postDelayed(this::finish, 500);
    }

    @Override
    public void onRefresh() {
        loadResource();
    }
}
