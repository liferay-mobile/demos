package com.liferay.mobile.formsscreenletdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.liferay.mobile.formsscreenletdemo.R;
import com.liferay.mobile.screens.rating.AssetRating;
import com.liferay.mobile.screens.viewsets.defaultviews.rating.BaseRatingView;

import static com.liferay.mobile.screens.rating.RatingScreenlet.DELETE_RATING_ACTION;
import static com.liferay.mobile.screens.rating.RatingScreenlet.UPDATE_RATING_ACTION;

/**
 * @author Luísa Lima
 */
public class ThumbsRatingDemoView extends BaseRatingView implements View.OnClickListener {

	public ThumbsRatingDemoView(Context context) {
		super(context);
	}

	public ThumbsRatingDemoView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ThumbsRatingDemoView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public ThumbsRatingDemoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
	}

	@Override
	protected void setButton(View textView) {
		((ImageView) textView).setImageResource(
			textView.getId() == R.id.positiveRatingButton ? R.drawable.rating_button_like_checked
				: R.drawable.rating_button_dislike_checked);
	}

	@Override
	protected void setEmptyState(TextView textView, View view, int rating, AssetRating assetRating) {
		textView.setText(getContext().getString(com.liferay.mobile.screens.R.string.rating_total, rating));
		((ImageView) view).setImageResource(
			view.getId() == R.id.positiveRatingButton ? R.drawable.rating_button_like_unchecked
				: R.drawable.rating_button_dislike_unchecked);
	}

	@Override
	protected void clicked(double score, double userScore) {
		String actionName = score == userScore ? DELETE_RATING_ACTION : UPDATE_RATING_ACTION;
		getScreenlet().performUserAction(actionName, score);
	}
}
