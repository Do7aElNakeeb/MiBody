package com.mibody.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.mibody.app.R;
import com.mibody.app.app.WorkoutItem;
import com.mibody.app.fragments.WorkoutPlayAuto;
import com.mibody.app.fragments.WorkoutPlayManual;

/**
 * Created by mamdouhelnakeeb on 1/28/17.
 */

public class WorkoutPlay extends AppCompatActivity {

    WorkoutItem workoutItem;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workouts_fragment);
        intent = getIntent();
        workoutItem = intent.getParcelableExtra("WorkoutItem");

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        WorkoutPlayManual workoutPlayManual = new WorkoutPlayManual(workoutItem);

        fragmentTransaction.add(R.id.workoutPlayFragment, workoutPlayManual, "WorkoutFragment Play");
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();

    }
}
