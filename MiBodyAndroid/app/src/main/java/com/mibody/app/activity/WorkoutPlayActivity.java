package com.mibody.app.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mibody.app.R;
import com.mibody.app.app.WorkoutItem;
import android.content.SharedPreferences.Editor;

/**
 * Created by NakeebMac on 10/23/16.
 */

public class WorkoutPlayActivity extends AppCompatActivity implements BTDeviceList.OnDeviceSelected{

    WorkoutItem workoutItem;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.workouts_fragment);

        intent = getIntent();
        workoutItem = intent.getParcelableExtra("WorkoutItem");
        if (!intent.getStringExtra("weight").equals("weight")) {
            workoutItem.exercisesJSON = intent.getStringExtra("WorkoutItemExJSON");
        }
        if (workoutItem == null){
            Log.d("WorkoutItem", "nulllll");
        }

        SharedPreferences sharedPreferences = getSharedPreferences("BT3", MODE_PRIVATE);

        if (!sharedPreferences.getString("BT_MAC", "").isEmpty()){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            WorkoutPlay workoutPlay = new WorkoutPlay(workoutItem);
            workoutPlay.setBTAddress(sharedPreferences.getString("BT_MAC", ""));

            fragmentTransaction.add(R.id.workoutPlayFragment, workoutPlay, "WorkoutFragment Play");
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.commit();
        }

        else {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            BTDeviceList btDeviceList = new BTDeviceList();

            fragmentTransaction.add(R.id.workoutPlayFragment, btDeviceList, "WorkoutFragment Play");
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.commit();
        }

    }

    @Override
    public void onDeviceSelected(String address) {
        Log.d("Movie Name1", address);
        if (intent.getStringExtra("weight").equals("weight")){
            Weight weight = new Weight();
            weight.setBTAddress(address);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.workoutPlayFragment, weight, "Weight");
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(null);
            ft.commit();
        }
        else {
            WorkoutPlay workoutPlay = new WorkoutPlay(workoutItem);
            workoutPlay.setBTAddress(address);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.workoutPlayFragment, workoutPlay, "Movie Details Fragment");
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(null);
            ft.commit();
        }

    }
}
