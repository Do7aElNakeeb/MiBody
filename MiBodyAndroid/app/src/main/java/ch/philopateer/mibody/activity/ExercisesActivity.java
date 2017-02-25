package ch.philopateer.mibody.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import ch.philopateer.mibody.R;
import ch.philopateer.mibody.fragments.ExercisesFragment;

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

        fragmentTransaction.replace(R.id.exercisesFragment, exercisesFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();

    }

}
