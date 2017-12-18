package ch.philopateer.mibody.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import ch.philopateer.mibody.fragments.Exercises;
import ch.philopateer.mibody.fragments.WorkoutFragment;
import ch.philopateer.mibody.adapter.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ch.philopateer.mibody.R.layout.activity_main);

        toolbar = (Toolbar) findViewById(ch.philopateer.mibody.R.id.toolbar);
        setSupportActionBar(toolbar);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(ch.philopateer.mibody.R.id.viewpager);
        //setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(ch.philopateer.mibody.R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Exercises(), "Exercises");
        adapter.addFragment(new WorkoutFragment(), "Workouts");
        viewPager.setAdapter(adapter);
    }

}