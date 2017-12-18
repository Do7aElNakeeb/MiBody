package ch.philopateer.mibody.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import ch.philopateer.mibody.R;
import ch.philopateer.mibody.object.WorkoutItem;
import ch.philopateer.mibody.fragments.WorkoutPlayManual;

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
        workoutItem = new WorkoutItem(workoutItem.workoutID, workoutItem.workoutName, workoutItem.workoutReps, workoutItem.exercisesJSON, workoutItem.workoutType, workoutItem.wTime);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final WorkoutPlayManual workoutPlayManual = new WorkoutPlayManual(workoutItem);

        //Log.d("exListNullCH22", String.valueOf(workoutItem.exercisesList.size()));

        fragmentTransaction.add(R.id.workoutPlayFragment, workoutPlayManual, "WorkoutFragment Play");
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();

        /*
        workoutPlayManual.initListener(new OnBtnClickListener() {
            @Override
            public void onBtnClick() {

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


                fragmentTransaction.add(R.id.workoutPlayFragment, statisticsActivity, "Statistics");
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.commit();

            }
        });
*/

    }
}
