package ch.philopateer.mibody.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import ch.philopateer.mibody.R;

/**
 * Created by mamdouhelnakeeb on 3/9/17.
 */

public class Settings extends AppCompatActivity {

    Button logoutBtn;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        logoutBtn = (Button) findViewById(R.id.logoutBtn);

        prefs = getSharedPreferences("UserDetails", MODE_PRIVATE);
        editor = prefs.edit();

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("isLoggedIn", "0");
                editor.apply();
                startActivity(new Intent(Settings.this, Login.class));
                finish();

            }
        });

    }
}
