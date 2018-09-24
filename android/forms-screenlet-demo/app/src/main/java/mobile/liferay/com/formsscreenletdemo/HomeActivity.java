package mobile.liferay.com.formsscreenletdemo;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Luísa Lima
 */
public class HomeActivity extends AppCompatActivity
    implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button formButton = findViewById(R.id.forms_button);
        formButton.setOnClickListener(this);

        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.login_status_bar_color));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, FormsActivity.class);
        startActivity(intent);
    }


}
