package com.liferay.mobile.formsscreenletdemo.view;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.liferay.mobile.formsscreenletdemo.R;

/**
 * @author Victor Oliveira
 */
public class WorkflowActivity extends AppCompatActivity {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workflow);

		LinearLayout linearLayout = findViewById(R.id.form_detail_error_view);
		TextView titleTextView = findViewById(R.id.error_title);

		String title = getString(R.string.sorry_workflow_is_not_read_yet);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			String workflowInfo = extras.getString("workflow");

			if (workflowInfo != null) {
				title += "\n\n But your Workflow info is here: \n" + workflowInfo;
			}
		}

		titleTextView.setText(title);

		linearLayout.setVisibility(View.VISIBLE);
	}
}
