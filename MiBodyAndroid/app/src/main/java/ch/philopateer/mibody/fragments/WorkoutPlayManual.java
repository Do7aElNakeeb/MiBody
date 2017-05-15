package ch.philopateer.mibody.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;

import ch.philopateer.mibody.R;
import ch.philopateer.mibody.activity.StatisticsActivity;
import ch.philopateer.mibody.object.WorkoutExItem;
import ch.philopateer.mibody.object.WorkoutItem;
import ch.philopateer.mibody.helper.WorkoutPlayExItemsAdapter;
import ch.philopateer.mibody.listener.OnBtnClickListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by mamdouhelnakeeb on 1/28/17.
 */

public class WorkoutPlayManual extends Fragment {

    RecyclerView workoutsPlayItemsRV;
    TextView workoutName, exerciseName, exerciseReps, processActionTV; // exerciseRestTime, nextExerciseName;
    ImageView nextExerciseBtn;

    CardView processActionCV;
    ProgressBar processActionPB;

    int targertPos = 0;
    RelativeLayout saveBtn;
    public WorkoutItem workoutItem, workoutItem2;
    WorkoutPlayExItemsAdapter workoutPlayExItemsAdapter;
    public ArrayList<WorkoutExItem> achWorkoutExItemArrayList;
    public ArrayList<Integer> achExReps;
    int focusedItem = -1;

    int exReps = 1;


    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    public OnBtnClickListener onBtnClickListener;

    public void initListener(OnBtnClickListener onBtnClickListener){
        this.onBtnClickListener = onBtnClickListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.workout_play_fragment, container, false);

        // init Firebase authentication and database
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        workoutsPlayItemsRV = (RecyclerView) view.findViewById(R.id.workoutPlayItemsRV);
        workoutName = (TextView) view.findViewById(R.id.workoutName);
        exerciseName = (TextView) view.findViewById(R.id.exerciseName);
        exerciseReps = (TextView) view.findViewById(R.id.exerciseReps);
        /*
        exerciseRestTime = (TextView) view.findViewById(R.id.exerciseRestTime);
        nextExerciseName = (TextView) view.findViewById(R.id.nextExerciseName);
        nextExerciseBtn = (ImageView) view.findViewById(R.id.nextExerciseBtn);
        */
        processActionCV = (CardView) view.findViewById(R.id.processActionCV);
        processActionTV = (TextView) view.findViewById(R.id.processActionTV);
        processActionPB = (ProgressBar) view.findViewById(R.id.processActionPB);

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
            achExReps.add(workoutItem.exercisesList.get(i).reps);
            //achWorkoutExItemArrayList.add(workoutExItem);
            Log.d("RepsObjAch", String.valueOf(achExReps.get(i)) + " - " + String.valueOf(workoutItem.exercisesList.get(i).reps));
            //achWorkoutExItemArrayList.get(i).reps = 0;
            Log.d("RepsObjAch2", String.valueOf(achExReps.get(i)) + " - " + String.valueOf(workoutItem.exercisesList.get(i).reps));
        }

        workoutPlayExItemsAdapter = new WorkoutPlayExItemsAdapter(getActivity(), size.x, workoutItem.exercisesList, achExReps);

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
        workoutsPlayItemsRV.smoothScrollToPosition(focusedItem + 3);

        //initWorkoutItem();

/*
        nextExerciseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                focusedItem++;
                initWorkoutItem();
            }
        });
*/
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*
                SharedPreferences objPrefs = getActivity().getSharedPreferences("ExObjStatistics", Context.MODE_PRIVATE);
                SharedPreferences.Editor objEditor = objPrefs.edit();

                SharedPreferences achPrefs = getActivity().getSharedPreferences("ExAchStatistics", Context.MODE_PRIVATE);
                SharedPreferences.Editor achEditor = achPrefs.edit();

                for (int i = 0; i < workoutItem.exercisesList.size(); i++) {
                    achEditor.putInt(workoutItem.exercisesList.get(i).name, achPrefs.getInt(workoutItem.exercisesList.get(i).name, 0) + achExReps.get(i));
                    objEditor.putInt(workoutItem.exercisesList.get(i).name, objPrefs.getInt(workoutItem.exercisesList.get(i).name, 0) + workoutItem.exercisesList.get(i).reps);
                }
                objEditor.apply();
                achEditor.apply();
                */

                addWorkoutToCalender();

                //onBtnClickListener.onBtnClick();
                /*
                Intent intent = new Intent(getActivity(), Dimensions.class);
                getActivity().startActivity(intent);
                getActivity().finish();
                */
            }
        });


        exerciseName.setText(workoutItem.exercisesList.get(0).name);

        processActionCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (processActionTV.getText().toString().equals("START")){
                    focusedItem++;
                    workoutsPlayItemsRV.scrollToPosition(focusedItem + 1);
                    exerciseName.setText(workoutItem.exercisesList.get(focusedItem).name);
                    workoutPlayExItemsAdapter.setFocusedItem(focusedItem + 1);
                    processActionPB.setMax(workoutItem.exercisesList.get(focusedItem).restTime);
                    processActionPB.setProgress(workoutItem.exercisesList.get(focusedItem).restTime);
                    workoutPlayExItemsAdapter.setItemAction(0);
                    processActionTV.setText("REST");

                }
                else if (processActionTV.getText().toString().equals("REST")){

                    workoutPlayExItemsAdapter.setItemAction(1);
                    processActionPB.setMax(workoutItem.exercisesList.get(focusedItem).restTime);

                    new CountDownTimer(workoutItem.exercisesList.get(focusedItem).restTime * 1000, 1000){

                        @Override
                        public void onTick(long l) {
                            processActionTV.setText(String.valueOf(l / 1000));
                            processActionPB.setProgress((int) l / 1000);
                            Log.d("prog", String.valueOf(processActionPB.getProgress()));
                        }

                        @Override
                        public void onFinish() {
                            workoutPlayExItemsAdapter.setItemAction(0);
                            processActionPB.setProgress(workoutItem.exercisesList.get(focusedItem).restTime);
                            workoutPlayExItemsAdapter.setFocusedItem(-1);

                            if (focusedItem == workoutItem.exercisesList.size() - 1){
                                processActionTV.setText("DONE");
                            }
                            else {
                                processActionTV.setText("START");
                                workoutsPlayItemsRV.smoothScrollToPosition(focusedItem + 3);
                                exerciseName.setText(workoutItem.exercisesList.get(focusedItem + 1).name);
                            }
                        }
                    }.start();
                }
                else if (processActionTV.getText().toString().equals("DONE")){
                    addWorkoutToCalender();
                }
            }
        });

        return view;
    }

    private void addWorkoutToCalender(){


        achExReps = workoutPlayExItemsAdapter.achWorkoutExItemArrayList;

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < workoutItem.exercisesList.size(); i++) {
            workoutItem.exercisesList.get(i).exReps = achExReps.get(i);
            jsonArray.put(workoutItem.exercisesList.get(i).getJSONObject());
            Log.d("AddWJSONobject", workoutItem.exercisesList.get(i).getJSONObject().toString());
        }

        long timeInMilliSeconds = System.currentTimeMillis();
        long monthInMilliseconds = 0, dayInMilliseconds = 0;
        SimpleDateFormat monthFormatter = new SimpleDateFormat("MM/yyyy", Locale.US);
        monthFormatter.setTimeZone(TimeZone.getDefault());

        SimpleDateFormat dayFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        monthFormatter.setTimeZone(TimeZone.getDefault());

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMilliSeconds);
        String monthYear = monthFormatter.format(calendar.getTime());
        String dayMonthYear = dayFormatter.format(calendar.getTime());

        try {
            Date mDate = monthFormatter.parse(monthYear);
            monthInMilliseconds = mDate.getTime();

            Date dDate = dayFormatter.parse(dayMonthYear);
            dayInMilliseconds = dDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.d("dateInMillis", String.valueOf(monthInMilliseconds));
        Log.d("timeInMillis", String.valueOf(timeInMilliSeconds));


        workoutItem.workoutID = String.valueOf(timeInMilliSeconds);
        workoutItem.exercisesJSON = jsonArray.toString();

        databaseReference.child("/users/" + firebaseAuth.getCurrentUser().getUid() + "/statistics/"
                + String.valueOf(monthInMilliseconds)
                + "/" + String.valueOf(dayInMilliseconds)
                + "/" + String.valueOf(timeInMilliSeconds))
                .push();

        Map<String, Object> postValues = workoutItem.toMap();

        Map<String, Object> childUpdates = new HashMap<>();

        childUpdates.put("/users/" + firebaseAuth.getCurrentUser().getUid() + "/statistics/"
                + String.valueOf(monthInMilliseconds)
                + "/" + String.valueOf(dayInMilliseconds)
                + "/" + String.valueOf(timeInMilliSeconds), postValues);

        databaseReference.updateChildren(childUpdates);

        // intent to Statistics Activity
        Intent intent = new Intent(getActivity().getBaseContext(), StatisticsActivity.class);
        intent.putExtra("workoutItemStats", workoutItem);
        startActivity(intent);
        getActivity().finish();

    }

/*
    private void initWorkoutItem(){
        if(exReps <= workoutItem.exercisesList.get(focusedItem).exReps){
            if (focusedItem == workoutItem.exercisesList.size() - 1 && exReps == workoutItem.exercisesList.get(focusedItem).exReps){
                //nextExerciseName.setText("Finish");
                nextExerciseBtn.setVisibility(View.GONE);
            }
            exReps++;
            workoutPlayExItemsAdapter.setFocusedItem(focusedItem + 1);
            workoutsPlayItemsRV.smoothScrollToPosition(focusedItem + 1);
            exerciseReps.setText(String.valueOf(exReps) + "/" +  String.valueOf(workoutItem.exercisesList.get(focusedItem).exReps) + " Reps.");
            exerciseName.setText(workoutItem.exercisesList.get(focusedItem).name);
            //exerciseRestTime.setText(String.valueOf(workoutItem.exercisesList.get(focusedItem).restTime));

        }
        else {
            exReps = 1;
            focusedItem++;
            workoutPlayExItemsAdapter.setFocusedItem(focusedItem + 1);
            workoutsPlayItemsRV.smoothScrollToPosition(focusedItem + 1);
            exerciseReps.setText(String.valueOf(exReps) + "/" +  String.valueOf(workoutItem.exercisesList.get(focusedItem).exReps) + " Reps.");
            exerciseName.setText(workoutItem.exercisesList.get(focusedItem).name);
            //exerciseRestTime.setText(String.valueOf(workoutItem.exercisesList.get(focusedItem).restTime));

            if (focusedItem != workoutItem.exercisesList.size() - 1) {
               // nextExerciseName.setText(workoutItem.exercisesList.get(focusedItem + 1).name);
            }

        }
    }
*/
    public WorkoutPlayManual(WorkoutItem workoutItem){
        this.workoutItem = workoutItem;
    }
}