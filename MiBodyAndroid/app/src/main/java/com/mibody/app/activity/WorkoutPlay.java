package com.mibody.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mibody.app.R;
import com.mibody.app.app.WorkoutItem;
import com.mibody.app.fragments.StatisticsFragment;
import com.mibody.app.fragments.WorkoutPlayAuto;
import com.mibody.app.fragments.WorkoutPlayManual;
import com.mibody.app.listener.OnBtnClickListener;

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
        Bundle bundle = getIntent().getExtras();
        workoutItem = intent.getParcelableExtra("WorkoutItem");
        workoutItem = new WorkoutItem(workoutItem.workoutID, workoutItem.workoutName, workoutItem.workoutReps, workoutItem.exercisesJSON, workoutItem.workoutType);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final WorkoutPlayManual workoutPlayManual = new WorkoutPlayManual(workoutItem);

        //Log.d("exListNullCH22", String.valueOf(workoutItem.exercisesList.size()));

        fragmentTransaction.add(R.id.workoutPlayFragment, workoutPlayManual, "WorkoutFragment Play");
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();

        workoutPlayManual.initListener(new OnBtnClickListener() {
            @Override
            public void onBtnClick() {
                StatisticsFragment statisticsFragment = new StatisticsFragment(workoutPlayManual.workoutItem.exercisesList, workoutPlayManual.achExReps);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                //Log.d("exListNullCH22", String.valueOf(workoutItem.exercisesList.size()));

                fragmentTransaction.add(R.id.workoutPlayFragment, statisticsFragment, "Statistics");
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.commit();

            }
        });


    }
}
