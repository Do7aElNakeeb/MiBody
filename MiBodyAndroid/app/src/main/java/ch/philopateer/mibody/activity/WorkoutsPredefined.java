package ch.philopateer.mibody.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import ch.philopateer.mibody.R;
import ch.philopateer.mibody.fragments.WorkoutPre1;
import ch.philopateer.mibody.fragments.WorkoutPre2;
import ch.philopateer.mibody.fragments.WorkoutPre3;
import ch.philopateer.mibody.fragments.WorkoutPre4;
import ch.philopateer.mibody.adapter.ViewPagerAdapter;

/**
 * Created by mamdouhelnakeeb on 8/23/17.
 */

public class WorkoutsPredefined extends AppCompatActivity {

    ViewPager predifinedFiltersVP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workouts_predifined_activity);


        setupViewPager();

        findViewById(R.id.backBtnLL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void setupViewPager() {

        predifinedFiltersVP = (ViewPager) findViewById(R.id.workoutPreVP);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new WorkoutPre1(), "1");
        adapter.addFragment(new WorkoutPre2(), "2");
        adapter.addFragment(new WorkoutPre3(), "3");
        adapter.addFragment(new WorkoutPre4(), "4");
        predifinedFiltersVP.setAdapter(adapter);
    }

}
