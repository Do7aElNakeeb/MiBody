package com.mibody.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mibody.app.R;
import com.mibody.app.app.ExerciseItem;
import com.mibody.app.helper.ViewPagerAdapter;

import java.util.ArrayList;

/**
 * Created by mamdouhelnakeeb on 12/2/16.
 */

public class ExercisesActivity extends AppCompatActivity {

    ViewPager exerciseViewPager;

    String exercises_names[] = { "Exercise A", "Exercise B", "Exercise C", "Exercise D",
            "Exercise E", "Exercise F", "Exercise G", "Exercise H", "Exercise I", "Exercise J", "Exercise K" };

    String exercises_descriptions[] = { "Description A", "Description B", "Description C", "Description D",
            "Description E", "Description F", "Description G", "Description H", "Description I",
            "Description J", "Description K", "Japan", "Costa Rica", "Uruguay",
            "Italy", "England", "France", "Switzerland", "Ecuador",
            "Honduras", "Agrentina", "Nigeria", "Bosnia and Herzegovina",
            "Iran", "Germany", "United States", "Portugal", "Ghana",
            "Belgium", "Algeria", "Russia", "Korea Republic" };

    int Images[] = { R.drawable.ex1, R.drawable.ex2,
            R.drawable.ex3, R.drawable.ex4, R.drawable.ex5,
            R.drawable.ex6, R.drawable.ex7, R.drawable.ex8,
            R.drawable.ex9, R.drawable.ex10, R.drawable.ex11 };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        exerciseViewPager = (ViewPager) findViewById(R.id.exercisesViewpager);
        setupViewPager(exerciseViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        ArrayList<ExerciseItem> exerciseItemArrayList = new ArrayList<ExerciseItem>();


        for (int i = 0; i<= exercises_names.length; i++){
            if((i % 9 == 0 || i == exercises_names.length) && i != 0){
                Exercises exercises = new Exercises();
                exercises.setArrayList(exerciseItemArrayList);
                adapter.addFragment(exercises, "ExPage" + String.valueOf(i));
                exerciseItemArrayList = new ArrayList<ExerciseItem>();
                Log.d("ExPage Added", String.valueOf(i));
            }
            if(i != exercises_names.length) {
                exerciseItemArrayList.add(new ExerciseItem(Images[i], exercises_names[i], exercises_descriptions[i]));
                Log.d("Ex Added", String.valueOf(i));
            }

        }

        viewPager.setAdapter(adapter);
    }

}
