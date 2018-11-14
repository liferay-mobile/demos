package com.liferay.mobile.formsscreenletdemo.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.liferay.mobile.screens.dlfile.display.pdf.OnSwipeTouchListener;
import com.liferay.mobile.screens.dlfile.display.pdf.SwipeListener;
import com.liferay.mobile.screens.util.LiferayLogger;
import com.liferay.mobile.screens.viewsets.defaultviews.dlfile.display.PdfDisplayView;
import java.io.File;
import java.io.IOException;

/**
 * @author Luísa Lima
 */
public class PdfDisplayDemoView extends PdfDisplayView {

	private int currentPage;
	private Button nextPage;
	private Button previousPage;
	private ImageView imagePdf;
	private PdfRenderer renderer;
	private ProgressBar progressBarHorizontal;
	private TextView progressText;
	private Matrix matrix;

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

		imagePdf = findViewById(com.liferay.mobile.screens.R.id.liferay_pdf_renderer);

		progressText = findViewById(com.liferay.mobile.screens.R.id.liferay_asset_progress_number);
		progressBarHorizontal = findViewById(com.liferay.mobile.screens.R.id.liferay_asset_progress_horizontal);

		previousPage = findViewById(com.liferay.mobile.screens.R.id.liferay_previous_page);
		nextPage = findViewById(com.liferay.mobile.screens.R.id.liferay_next_page);
	}

	@Override
	public void loadFileEntry(String url) {
		render(url);
	}

	private void render(String url) {
		if (Build.VERSION.SDK_INT >= 21) {
			renderInLollipop(url);
		} else {
			getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
		}
	}

	private void renderInLollipop(String url) {
		renderPdfInImageView(url);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	private void renderPdfInImageView(String url) {
		progressBar.setVisibility(VISIBLE);
		try {
			renderer = new PdfRenderer(ParcelFileDescriptor.open(new File(url), ParcelFileDescriptor.MODE_READ_ONLY));
			matrix = imagePdf.getImageMatrix();
			renderPdfPage(0);
		} catch (IOException e) {
			LiferayLogger.e("Error rendering PDF", e);
		}
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	private void changeCurrentPage(int i) {
		currentPage += i;

		if (currentPage < 0) {
			currentPage = 0;
		} else if (currentPage > renderer.getPageCount() - 1) {
			currentPage = renderer.getPageCount() - 1;
		}

		renderPdfPage(currentPage);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	private void renderPdfPage(int page) {
		PdfRenderer.Page renderedPage = renderer.openPage(page);
		Bitmap bitmap = Bitmap.createBitmap(renderedPage.getWidth(), renderedPage.getHeight(), Bitmap.Config.ARGB_8888);
		Rect rect = new Rect(0, 0, renderedPage.getWidth(), renderedPage.getHeight());
		renderedPage.render(bitmap, rect, matrix, PdfRenderer.Page.RENDER_MODE_FOR_PRINT);
		imagePdf.setImageMatrix(matrix);
		imagePdf.setImageBitmap(bitmap);
		imagePdf.setOnTouchListener(new OnSwipeTouchListener(getContext(), new SwipeListener() {
			@Override
			public void onSwipeLeft() {
				changeCurrentPage(+1);
			}

			@Override
			public void onSwipeRight() {
				changeCurrentPage(-1);
			}
		}));
		renderedPage.close();

		hideProgressBar();
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	private void hideProgressBar() {
		nextPage.setEnabled(currentPage != renderer.getPageCount() - 1);
		previousPage.setEnabled(currentPage != 0);

		progressBarHorizontal.setVisibility(GONE);
		progressBar.setVisibility(GONE);
		progressText.setVisibility(GONE);
	}
}