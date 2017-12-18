package ch.philopateer.mibody.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import ch.philopateer.mibody.R;
import ch.philopateer.mibody.activity.Dimensions;
import ch.philopateer.mibody.activity.Landing;
import ch.philopateer.mibody.activity.StatisticsActivity;
import ch.philopateer.mibody.adapter.StatisticsExAdapter;
import ch.philopateer.mibody.object.WorkoutExItem;
import ch.philopateer.mibody.object.WorkoutItem;

/**
 * Created by mamdouhelnakeeb on 8/26/17.
 */

public class StatisticsGraph extends Fragment {

    LinearLayout homeBtnLL;
    RecyclerView exercisesStatisticsRV;
    FrameLayout yAxisFL;

    StatisticsExAdapter statisticsExAdapter;

    int maxReps = 0, maxTime = 0;

    WorkoutItem workoutItem = null;


    public StatisticsGraph(WorkoutItem workoutItem, int maxReps, int maxTime){

        this.workoutItem = workoutItem;
        this.maxReps = maxReps;
        this.maxTime = maxTime;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.statistics_graph, container, false);

        homeBtnLL = (LinearLayout) view.findViewById(R.id.homeBtnLL);
        yAxisFL = (FrameLayout) view.findViewById(R.id.yAxisFL);

        exercisesStatisticsRV = (RecyclerView) view.findViewById(R.id.exercisesStatisticsRV);

        //updateUserDetails();
        initExStatisticsRV();

        homeBtnLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((StatisticsActivity)getActivity()).getViewPager().setCurrentItem(0);
                //Intent intent = new Intent(getActivity(), Dimensions.class);
                //startActivity(intent);
                //getActivity().finish();
            }
        });



        return view;
    }

    private void initExStatisticsRV(){



        yAxisFL.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                yAxisFL.getViewTreeObserver().removeOnPreDrawListener(this);

                statisticsExAdapter = new StatisticsExAdapter(getActivity(), yAxisFL.getHeight(), maxReps, maxTime, workoutItem.exercisesList);
                exercisesStatisticsRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                exercisesStatisticsRV.setAdapter(statisticsExAdapter);

                return true;
            }
        });

    }
}
