package com.mibody.app.fragments;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mibody.app.R;
import com.mibody.app.activity.Dimensions;
import com.mibody.app.app.WorkoutExItem;
import com.mibody.app.app.WorkoutItem;
import com.mibody.app.helper.WorkoutPlayExItemsAdapter;
import com.mibody.app.listener.OnBtnClickListener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by mamdouhelnakeeb on 1/28/17.
 */

public class WorkoutPlayManual extends Fragment {

    RecyclerView workoutsPlayItemsRV;
    TextView workoutName, exerciseName, exerciseReps, exerciseRestTime, nextExerciseName;
    ImageView nextExerciseBtn;

    int targertPos = 0;
    RelativeLayout saveBtn;
    public WorkoutItem workoutItem, workoutItem2;
    WorkoutPlayExItemsAdapter workoutPlayExItemsAdapter;
    public ArrayList<WorkoutExItem> achWorkoutExItemArrayList;
    public ArrayList<Integer> achExReps;
    int focusedItem = 0;

    int exReps = 1;

    public OnBtnClickListener onBtnClickListener;

    public void initListener(OnBtnClickListener onBtnClickListener){
        this.onBtnClickListener = onBtnClickListener;
    }

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
        saveBtn = (RelativeLayout) view.findViewById(R.id.save_btn);

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        workoutsPlayItemsRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
/*
        workoutItem2 = workoutItem;
        achWorkoutExItemArrayList = workoutItem2.exercisesList;

        achWorkoutExItemArrayList = new ArrayList<WorkoutExItem>(workoutItem.exercisesList.size());
        Collections.copy(achWorkoutExItemArrayList, workoutItem.exercisesList);
*/
        achExReps = new ArrayList<Integer>();
        for (int i = 0; i < workoutItem.exercisesList.size(); i++){
            achExReps.add(0);
            //achWorkoutExItemArrayList.add(workoutExItem);
            Log.d("RepsObjAch", String.valueOf(achExReps.get(i)) + " - " + String.valueOf(workoutItem.exercisesList.get(i).reps));
            //achWorkoutExItemArrayList.get(i).reps = 0;
            Log.d("RepsObjAch2", String.valueOf(achExReps.get(i)) + " - " + String.valueOf(workoutItem.exercisesList.get(i).reps));
        }

        workoutPlayExItemsAdapter = new WorkoutPlayExItemsAdapter(getActivity(), size.x, workoutItem.exercisesList, achExReps, new WorkoutPlayExItemsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                workoutsPlayItemsRV.smoothScrollToPosition(position + 1);
            }
        });
        workoutsPlayItemsRV.setAdapter(workoutPlayExItemsAdapter);

//        workoutsPlayItemsRV.stopScroll();

        workoutName.setText(workoutItem.workoutName);

        SnapHelper snapHelper = new LinearSnapHelper(){

            @Override
            public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
                View centerView = findSnapView(layoutManager);
                if (centerView == null)
                    return RecyclerView.NO_POSITION;

                int position = layoutManager.getPosition(centerView);
                int targetPosition = -1;
                if (layoutManager.canScrollHorizontally()) {
                    if (velocityX < 0) {
                        targetPosition = position - 1;
                    } else {
                        targetPosition = position + 1;
                    }
                }

                if (layoutManager.canScrollVertically()) {
                    if (velocityY < 0) {
                        targetPosition = position - 1;
                    } else {
                        targetPosition = position + 1;
                    }
                }

                final int firstItem = 0;
                final int lastItem = layoutManager.getItemCount() - 1;
                targetPosition = Math.min(lastItem, Math.max(targetPosition, firstItem));
                if (targetPosition == 0){
                    targetPosition = 1;
                }
                else if (targetPosition == workoutPlayExItemsAdapter.getItemCount() - 1){
                    targetPosition = workoutPlayExItemsAdapter.getItemCount() - 2;
                }
                targertPos = targetPosition;
                //workoutPlayExItemsAdapter.setFocusedItem(focusedItem + 1);
                //workoutsPlayItemsRV.scrollToPosition(focusedItem + 1);
                return targetPosition;
            }
        };
        snapHelper.attachToRecyclerView(workoutsPlayItemsRV);
        workoutsPlayItemsRV.setOnFlingListener(snapHelper);

        initWorkoutItem();

        nextExerciseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                focusedItem++;
                initWorkoutItem();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                achExReps = workoutPlayExItemsAdapter.achWorkoutExItemArrayList;
                onBtnClickListener.onBtnClick();
                /*
                Intent intent = new Intent(getActivity(), Dimensions.class);
                getActivity().startActivity(intent);
                getActivity().finish();
                */
            }
        });
        return view;
    }

    private void initWorkoutItem(){
        if(exReps <= workoutItem.exercisesList.get(focusedItem).exReps){
            if (focusedItem == workoutItem.exercisesList.size() - 1 && exReps == workoutItem.exercisesList.get(focusedItem).exReps){
                nextExerciseName.setText("Finish");
                nextExerciseBtn.setVisibility(View.GONE);
            }
            exReps++;
            workoutPlayExItemsAdapter.setFocusedItem(focusedItem + 1);
            workoutsPlayItemsRV.smoothScrollToPosition(focusedItem + 1);
            exerciseReps.setText(String.valueOf(exReps) + "/" +  String.valueOf(workoutItem.exercisesList.get(focusedItem).exReps) + " Reps.");
            exerciseName.setText(workoutItem.exercisesList.get(focusedItem).name);
            exerciseRestTime.setText(String.valueOf(workoutItem.exercisesList.get(focusedItem).restTime));

        }
        else {
            exReps = 1;
            focusedItem++;
            workoutPlayExItemsAdapter.setFocusedItem(focusedItem + 1);
            workoutsPlayItemsRV.smoothScrollToPosition(focusedItem + 1);
            exerciseReps.setText(String.valueOf(exReps) + "/" +  String.valueOf(workoutItem.exercisesList.get(focusedItem).exReps) + " Reps.");
            exerciseName.setText(workoutItem.exercisesList.get(focusedItem).name);
            exerciseRestTime.setText(String.valueOf(workoutItem.exercisesList.get(focusedItem).restTime));

            if (focusedItem != workoutItem.exercisesList.size() - 1) {
                nextExerciseName.setText(workoutItem.exercisesList.get(focusedItem + 1).name);
            }

        }
    }

    public WorkoutPlayManual(WorkoutItem workoutItem){
        this.workoutItem = workoutItem;
    }
}