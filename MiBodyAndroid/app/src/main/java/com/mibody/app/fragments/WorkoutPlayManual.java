package com.mibody.app.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.mibody.app.R;
import com.mibody.app.app.WorkoutExItem;
import com.mibody.app.app.WorkoutItem;
import com.mibody.app.helper.WorkoutPlayExItemsAdapter;

import java.util.ArrayList;

/**
 * Created by mamdouhelnakeeb on 1/28/17.
 */

public class WorkoutPlayManual extends Fragment {

    RecyclerView workoutsPlayItemsRV;
    TextView workoutName, exerciseName, exerciseReps, exerciseRestTime, nextExerciseName;
    ImageView nextExerciseBtn;

    WorkoutItem workoutItem;
    WorkoutPlayExItemsAdapter workoutPlayExItemsAdapter;
    int focusedItem = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.workout_play_fragment, container, false);

        workoutsPlayItemsRV = (RecyclerView) view.findViewById(R.id.workoutPlayItemsRV);
        workoutName = (TextView) view.findViewById(R.id.workoutName);
        exerciseName = (TextView) view.findViewById(R.id.exerciseName);
        exerciseReps = (TextView) view.findViewById(R.id.exerciseReps);
        exerciseRestTime = (TextView) view.findViewById(R.id.exerciseRestTime);
        nextExerciseName = (TextView) view.findViewById(R.id.nextExerciseName);
        nextExerciseBtn = (ImageView) view.findViewById(R.id.nextExerciseBtn);

        workoutsPlayItemsRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        workoutPlayExItemsAdapter = new WorkoutPlayExItemsAdapter(getActivity(), workoutItem.exercisesList);
        workoutsPlayItemsRV.setAdapter(workoutPlayExItemsAdapter);

//        workoutsPlayItemsRV.stopScroll();

        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(workoutsPlayItemsRV);

        initWorkoutItem();

        nextExerciseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                focusedItem++;
                initWorkoutItem();
            }
        });

        return view;
    }

    private void initWorkoutItem(){
        workoutPlayExItemsAdapter.setFocusedItem(focusedItem + 1);
        workoutsPlayItemsRV.scrollToPosition(focusedItem + 1);
        workoutName.setText(workoutItem.workoutName);
        exerciseName.setText(workoutItem.exercisesList.get(focusedItem).name);
        exerciseReps.setText(workoutItem.exercisesList.get(focusedItem).exReps);
        exerciseRestTime.setText(workoutItem.exercisesList.get(focusedItem).restTime);
        nextExerciseName.setText(workoutItem.exercisesList.get(focusedItem + 1).name);
    }

    public WorkoutPlayManual(WorkoutItem workoutItem){
        this.workoutItem = workoutItem;
    }
}
