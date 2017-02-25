package ch.philopateer.mibody.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import ch.philopateer.mibody.app.WorkoutItem;
import ch.philopateer.mibody.fragments.WorkoutPlayAuto;

/**
 * Created by NakeebMac on 10/23/16.
 */

public class WorkoutPlayActivity extends AppCompatActivity implements BTDeviceList.OnDeviceSelected{

    WorkoutItem workoutItem;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(ch.philopateer.mibody.R.layout.workouts_fragment);

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
            WorkoutPlayAuto workoutPlayAuto = new WorkoutPlayAuto(workoutItem);
            workoutPlayAuto.setBTAddress(sharedPreferences.getString("BT_MAC", ""));

            fragmentTransaction.add(ch.philopateer.mibody.R.id.workoutPlayFragment, workoutPlayAuto, "WorkoutFragment Play");
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.commit();
        }

        else {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            BTDeviceList btDeviceList = new BTDeviceList();

            fragmentTransaction.add(ch.philopateer.mibody.R.id.workoutPlayFragment, btDeviceList, "WorkoutFragment Play");
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
            ft.replace(ch.philopateer.mibody.R.id.workoutPlayFragment, weight, "Weight");
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(null);
            ft.commit();
        }
        else {
            WorkoutPlayAuto workoutPlayAuto = new WorkoutPlayAuto(workoutItem);
            workoutPlayAuto.setBTAddress(address);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(ch.philopateer.mibody.R.id.workoutPlayFragment, workoutPlayAuto, "Movie Details Fragment");
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(null);
            ft.commit();
        }

    }
}
