package com.mibody.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mibody.app.R;
import com.mibody.app.app.ExerciseItem;
import com.mibody.app.fragments.ExercisesFragment;
import com.mibody.app.fragments.RegisterTwo;
import com.mibody.app.helper.ViewPagerAdapter;

import java.util.ArrayList;

/**
 * Created by mamdouhelnakeeb on 12/2/16.
 */

public class ExercisesActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ExercisesFragment exercisesFragment = new ExercisesFragment();

        RegisterTwo registerTwo = new RegisterTwo();

        fragmentTransaction.add(R.id.exercisesFragment, exercisesFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();

    }

}
