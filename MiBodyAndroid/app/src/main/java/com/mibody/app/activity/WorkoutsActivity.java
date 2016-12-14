package com.mibody.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import com.mibody.app.R;

/**
 * Created by mamdouhelnakeeb on 12/6/16.
 */

public class WorkoutsActivity extends AppCompatActivity {

    CardView predefined, personalised;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workouts);

        predefined = (CardView) findViewById(R.id.predefinedCV);
        personalised = (CardView) findViewById(R.id.personalisedCV);

        predefined.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkoutsActivity.this, WorkoutsItemsActivity.class);
                intent.putExtra("type", "predefined");
                startActivity(intent);
            }
        });

        personalised.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WorkoutsActivity.this, WorkoutsItemsActivity.class);
                intent.putExtra("type", "personalised");
                startActivity(intent);
            }
        });
    }
}
