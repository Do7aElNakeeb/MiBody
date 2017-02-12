package com.mibody.app.fragments;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mibody.app.R;
import com.mibody.app.activity.Dimensions;
import com.mibody.app.app.WorkoutExItem;
import com.mibody.app.helper.StatisticsExAdapter;

import java.util.ArrayList;

/**
 * Created by mamdouhelnakeeb on 2/7/17.
 */

public class StatisticsFragment extends Fragment{

    RelativeLayout dimensionsBtn, homeBtn;
    ImageView filterBtn, userImage;
    TextView nameTV, heightTV, heightUnitTV, wegihtTv, weightUnitTV, ageTV, imcTV;
    RecyclerView exercisesStatisticsRV, performStatisticsRV;
    FrameLayout yAxisFL;

    StatisticsExAdapter statisticsExAdapter;

    int maxReps = 0;
    ArrayList<WorkoutExItem> workoutExItemArrayListObj;
    ArrayList<Integer> workoutExItemArrayListAch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.statistics_activity, container, false);

        dimensionsBtn = (RelativeLayout) view.findViewById(R.id.dimensionBtn);
        homeBtn = (RelativeLayout) view.findViewById(R.id.homeBtn);
        filterBtn = (ImageView) view.findViewById(R.id.statisticsFilterBtn);
        userImage = (ImageView) view.findViewById(R.id.userIV);
        nameTV = (TextView) view.findViewById(R.id.userNameTxtView);
        heightTV = (TextView) view.findViewById(R.id.userHeightTxtView);
        heightUnitTV = (TextView) view.findViewById(R.id.userHeightUnitTxtView);
        wegihtTv = (TextView) view.findViewById(R.id.userWeightTxtView);
        weightUnitTV = (TextView) view.findViewById(R.id.userWeightUnitTxtView);
        ageTV = (TextView) view.findViewById(R.id.ageTxtView);
        imcTV = (TextView) view.findViewById(R.id.imcTxtView);
        yAxisFL = (FrameLayout) view.findViewById(R.id.yAxisFL);

        exercisesStatisticsRV = (RecyclerView) view.findViewById(R.id.exercisesStatisticsRV);
        performStatisticsRV = (RecyclerView) view.findViewById(R.id.performStatisticsRV);

        initExStatisticsRV();

        dimensionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Dimensions.class);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }

    private void initExStatisticsRV(){
        RelativeLayout.LayoutParams statisticsyAxisParams = (RelativeLayout.LayoutParams) yAxisFL.getLayoutParams();

        for (int i=0; i< workoutExItemArrayListObj.size(); i++){
            workoutExItemArrayListObj.get(i).reps = workoutExItemArrayListObj.get(i).reps * workoutExItemArrayListObj.get(i).exReps;
            if (workoutExItemArrayListObj.get(i).reps >= maxReps){
                maxReps = workoutExItemArrayListObj.get(i).reps;
            }
        }

        for (int i=0; i< workoutExItemArrayListAch.size(); i++){
            workoutExItemArrayListAch.set(i, workoutExItemArrayListAch.get(i));
            if (workoutExItemArrayListAch.get(i) >= maxReps){
                maxReps = workoutExItemArrayListAch.get(i);
            }
        }

        yAxisFL.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                yAxisFL.getViewTreeObserver().removeOnPreDrawListener(this);

                statisticsExAdapter = new StatisticsExAdapter(getActivity(), yAxisFL.getHeight(), maxReps, workoutExItemArrayListObj, workoutExItemArrayListAch);
                exercisesStatisticsRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                exercisesStatisticsRV.setAdapter(statisticsExAdapter);
                return true;
            }
        });



    }


    public StatisticsFragment(ArrayList<WorkoutExItem> workoutExItemArrayListObj, ArrayList<Integer> workoutExItemArrayListAch){
        this.workoutExItemArrayListObj = workoutExItemArrayListObj;
        this.workoutExItemArrayListAch = workoutExItemArrayListAch;
    }
}