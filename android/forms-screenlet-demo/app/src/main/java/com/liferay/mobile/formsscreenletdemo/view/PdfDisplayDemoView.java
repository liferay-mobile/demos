package com.liferay.mobile.formsscreenletdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.liferay.mobile.formsscreenletdemo.R;
import com.liferay.mobile.screens.viewsets.defaultviews.dlfile.display.PdfDisplayView;

/**
 * @author Luísa Lima
 */
public class PdfDisplayDemoView extends PdfDisplayView {

	private Button nextPage;
	private Button previousPage;
	private Button goToPageButton;
	private EditText goToPage;
	private LinearLayout linearLayoutButtons;
	private ImageView imagePdf;
	private ProgressBar progressBarHorizontal;
	private TextView progressText;
	private TextView title;

	public PdfDisplayDemoView(Context context) {
		super(context);
	}

	public PdfDisplayDemoView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PdfDisplayDemoView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		imagePdf = findViewById(R.id.liferay_pdf_renderer);

		progressText = findViewById(R.id.liferay_asset_progress_number);
		progressBarHorizontal = findViewById(R.id.liferay_asset_progress_horizontal);

		goToPage = findViewById(R.id.liferay_go_to_page);
		goToPageButton = findViewById(R.id.liferay_go_to_page_submit);

		previousPage = findViewById(R.id.liferay_previous_page);
		nextPage = findViewById(R.id.liferay_next_page);

		linearLayoutButtons = findViewById(R.id.liferay_linear_buttons);

		title = findViewById(R.id.liferay_asset_title);
	}

	@Override
	protected void onPageRendered() {
		super.onPageRendered();

		hideElements();
	}

	private void hideElements() {
		linearLayoutButtons.setVisibility(GONE);
		title.setVisibility(GONE);
	}

}