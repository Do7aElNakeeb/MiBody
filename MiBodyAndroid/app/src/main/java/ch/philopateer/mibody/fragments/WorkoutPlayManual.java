package ch.philopateer.mibody.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;

import ch.philopateer.mibody.R;
import ch.philopateer.mibody.activity.StatisticsActivity;
import ch.philopateer.mibody.app.AppConfig;
import ch.philopateer.mibody.object.WorkoutExItem;
import ch.philopateer.mibody.object.WorkoutItem;
import ch.philopateer.mibody.helper.WorkoutPlayExItemsAdapter;
import ch.philopateer.mibody.listener.OnBtnClickListener;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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

    // TODO timer for time setted exercies

    RecyclerView workoutsPlayItemsRV;
    TextView workoutName, exerciseName, exerciseReps, processActionTV, counterHintTV, elapsedTimeTV, elapsedTimePlayStopTV;
    ImageView elapsedTimePlayStopIV;
    Boolean isPlaying = false, isExPlaying = false;
    Boolean sameEx = false;

    CardView processActionCV;
    ProgressBar processActionPB;

    TextToSpeech textToSpeech;

    int targertPos = 0;
    RelativeLayout saveBtn;
    public WorkoutItem workoutItem;
    WorkoutPlayExItemsAdapter workoutPlayExItemsAdapter;
    public ArrayList<Integer> achExReps, actualExReps, exRepsCounterArr;
    public ArrayList<Long> achExTime;
    int focusedItem = 0;

    GifImageView mGigImageView;
    GifDrawable gifDrawable = null;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    int wTime = 0;

    long exMilliSec, exStartTime, exTimeBuff, exTime = 0L ;

    Handler handler, exTimeHandler;

    int Seconds, Minutes, MilliSeconds;

    MediaPlayer mediaPlayer;

    LinearLayout workoutPauseTimeResumeLL;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.workout_play_fragment, container, false);

        initSounds();
        handler = new Handler();
        exTimeHandler = new Handler();

        // init Firebase authentication and database
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        workoutsPlayItemsRV = (RecyclerView) view.findViewById(R.id.workoutPlayItemsRV);

        mGigImageView = (GifImageView) view.findViewById(R.id.exGIF2);

        workoutName = (TextView) view.findViewById(R.id.workoutName);
        exerciseName = (TextView) view.findViewById(R.id.exerciseName);
        exerciseReps = (TextView) view.findViewById(R.id.exerciseReps);

        processActionCV = (CardView) view.findViewById(R.id.processActionCV);
        processActionTV = (TextView) view.findViewById(R.id.processActionTV);
        counterHintTV = (TextView) view.findViewById(R.id.counterHintTV);
        processActionPB = (ProgressBar) view.findViewById(R.id.processActionPB);

        elapsedTimeTV = (TextView) view.findViewById(R.id.elapsedTimeTV);
        elapsedTimePlayStopTV = (TextView) view.findViewById(R.id.elapsedTimePlayStopTV);
        elapsedTimePlayStopIV = (ImageView) view.findViewById(R.id.elapsedTimePlayStopIV);

        saveBtn = (RelativeLayout) view.findViewById(R.id.save_btn);

        workoutPauseTimeResumeLL = (LinearLayout) view.findViewById(R.id.blackLL);

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        workoutsPlayItemsRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        achExReps = new ArrayList<Integer>();
        actualExReps = new ArrayList<Integer>();
        achExTime = new ArrayList<Long>();
        exRepsCounterArr = new ArrayList<>();

        for (int i = 0; i < workoutItem.exercisesList.size(); i++){
            achExReps.add(workoutItem.exercisesList.get(i).reps);
            actualExReps.add(0);
            achExTime.add(0L);
            exRepsCounterArr.add(workoutItem.exercisesList.get(i).exReps);

            Log.d("repsTimeBoolwpm", String.valueOf(workoutItem.exercisesList.get(i).repsTimeBool));
            Log.d("RepsObjAch", String.valueOf(achExReps.get(i)) + " - " + String.valueOf(workoutItem.exercisesList.get(i).reps));
        }

        workoutPlayExItemsAdapter = new WorkoutPlayExItemsAdapter(getActivity(), size.x, workoutItem.exercisesList, achExReps);

        workoutsPlayItemsRV.setAdapter(workoutPlayExItemsAdapter);

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

                return targetPosition;
            }
        };
        snapHelper.attachToRecyclerView(workoutsPlayItemsRV);
        workoutsPlayItemsRV.setOnFlingListener(snapHelper);
        workoutsPlayItemsRV.smoothScrollToPosition(focusedItem + 2);


        loadGIF(workoutItem.exercisesList.get(0).name);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addWorkoutToCalender();
            }
        });


        exerciseName.setText(workoutItem.exercisesList.get(0).name);
        counterHintTV.setText("Press START to begin " + workoutItem.exercisesList.get(0).name + " exercise");

        speak("Press START to begin " + workoutItem.exercisesList.get(0).name + " exercise");

        processActionCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("setReps", String.valueOf(workoutItem.exercisesList.get(focusedItem).exReps));

                if (processActionTV.getText().toString().equals("START")){

                    isExPlaying = true;

                    if(workoutItem.exercisesList.get(focusedItem).exReps > 0){
                        sameEx = true;
                    }
                    else {
                        focusedItem++;
                        sameEx = false;
                    }

                    Log.d("repsTimeBoolean", String.valueOf(workoutItem.exercisesList.get(focusedItem).repsTimeBool));

                    if (workoutItem.exercisesList.get(focusedItem).repsTimeBool){


                        Log.d("repsTimeBoolwpm", "Time Ex");
                        processActionPB.setMax((int) workoutItem.exercisesList.get(focusedItem).exTime);
                        processActionPB.getProgressDrawable().setColorFilter(ContextCompat.getColor(getActivity(), R.color.MiBodyOrange), PorterDuff.Mode.SRC_IN);

                        workoutPlayExItemsAdapter.setFocusedItem(focusedItem + 1);

                        new CountDownTimer(workoutItem.exercisesList.get(focusedItem).exTime * 1000, 1000){

                            @Override
                            public void onTick(long l) {
                                processActionTV.setText(String.valueOf(l / 1000));
                                processActionPB.setProgress((int) l / 1000);
                                Log.d("exTimeProg", String.valueOf(processActionPB.getProgress()));

                                mediaPlayer.start();
                                if (((int) l / 1000) <= 5 && ((int) l / 1000) > 0){
                                    speak(String.valueOf((int) l / 1000));
                                }

                            }

                            @Override
                            public void onFinish() {

                                isExPlaying = false;

                                exTime = 0L;
                                startExTime();

                                workoutPlayExItemsAdapter.setItemAction(0);

                                workoutItem.exercisesList.get(focusedItem).exReps--;
                                exerciseName.setText(workoutItem.exercisesList.get(focusedItem).name);
                                processActionPB.setMax(workoutItem.exercisesList.get(focusedItem).restTime);
                                processActionPB.setProgress(workoutItem.exercisesList.get(focusedItem).restTime);

                                workoutsPlayItemsRV.scrollToPosition(focusedItem + 1);
                                workoutPlayExItemsAdapter.setFocusedItem(focusedItem + 1);

                                processActionTV.setText("REST");

                                processActionPB.setProgress((int) workoutItem.exercisesList.get(focusedItem).exTime);
                                processActionPB.getProgressDrawable().setColorFilter(ContextCompat.getColor(getActivity(), R.color.MiBodyGrey3), PorterDuff.Mode.SRC_IN);

                                counterHintTV.setText("Press REST to begin rest time");
                                speak("Press REST to begin rest time");
                            }
                        }.start();

                    }
                    else {

                        exTime = 0L;
                        startExTime();

                        workoutPlayExItemsAdapter.setItemAction(0);

                        workoutItem.exercisesList.get(focusedItem).exReps--;
                        exerciseName.setText(workoutItem.exercisesList.get(focusedItem).name);
                        processActionPB.setMax(workoutItem.exercisesList.get(focusedItem).restTime);
                        processActionPB.setProgress(workoutItem.exercisesList.get(focusedItem).restTime);

                        processActionPB.getProgressDrawable().setColorFilter(ContextCompat.getColor(getActivity(), R.color.MiBodyGrey3), PorterDuff.Mode.SRC_IN);

                        workoutsPlayItemsRV.scrollToPosition(focusedItem + 1);
                        workoutPlayExItemsAdapter.setFocusedItem(focusedItem + 1);

                        processActionTV.setText("REST");
                        counterHintTV.setText("Press REST to begin rest time");
                        speak("Press REST to begin rest time");
                    }

                }
                else if (processActionTV.getText().toString().equals("REST")){

                    endExTime();
                    isExPlaying = false;

                    if (!workoutItem.exercisesList.get(focusedItem).repsTimeBool) {
                        achExTime.set(focusedItem, achExTime.get(focusedItem) + exTime);
                    }
                    else {
                        achExTime.set(focusedItem, achExTime.get(focusedItem) + workoutItem.exercisesList.get(focusedItem).exTime);
                    }

                    Log.d("exTime1", String.valueOf(exTime));

                    Log.d("exTime2", String.valueOf(achExTime.get(focusedItem)));
                    /*
                    if(workoutItem.exercisesList.get(focusedItem).exReps > 0){

                        Log.d("exTime1", String.valueOf(exTime));
                        achExTime.set(focusedItem, achExTime.get(focusedItem) + exTime);
                        sameEx = true;
                    }
                    else {
                        Log.d("exTime2", String.valueOf(exTime));
                        achExTime.set(focusedItem, exTime);
                        sameEx = false;
                    }
                    */

                    Log.d(workoutItem.exercisesList.get(focusedItem).name + " time", String.valueOf(achExTime.get(focusedItem)));

                    workoutPlayExItemsAdapter.setItemAction(1);
                    processActionPB.setMax(workoutItem.exercisesList.get(focusedItem).restTime);
                    processActionPB.getProgressDrawable().setColorFilter(ContextCompat.getColor(getActivity(), R.color.MiBodyGrey3), PorterDuff.Mode.SRC_IN);
                    counterHintTV.setText("Modify exercise reps unless they match the objective");
                    speak("Modify exercise reps unless they matches the objective");


                    new CountDownTimer(workoutItem.exercisesList.get(focusedItem).restTime * 1000, 1000){

                        @Override
                        public void onTick(long l) {

                            mediaPlayer.start();
                            processActionTV.setText(String.valueOf(l / 1000));
                            processActionPB.setProgress((int) l / 1000);
                            Log.d("prog", String.valueOf(processActionPB.getProgress()));
                        }

                        @Override
                        public void onFinish() {

                            isExPlaying = false;

                            actualExReps.set(focusedItem, actualExReps.get(focusedItem) + workoutPlayExItemsAdapter.achWorkoutExItemArrayList.get(focusedItem));

                            Log.d("actualExRepsTotal", String.valueOf(actualExReps.get(focusedItem)));
                            Log.d("actualExReps", String.valueOf(workoutPlayExItemsAdapter.achWorkoutExItemArrayList.get(focusedItem)));

                            workoutPlayExItemsAdapter.setItemAction(0);
                            processActionPB.setProgress(workoutItem.exercisesList.get(focusedItem).restTime);
                            processActionPB.getProgressDrawable().setColorFilter(ContextCompat.getColor(getActivity(), R.color.MiBodyGrey3), PorterDuff.Mode.SRC_IN);

                            workoutPlayExItemsAdapter.setFocusedItem(-1);

                            if (focusedItem == workoutItem.exercisesList.size() - 1 && workoutItem.exercisesList.get(focusedItem).exReps == 0){
                                processActionTV.setText("DONE");
                                counterHintTV.setText("Press DONE to finish workout and get statistics");
                                speak("Press DONE to finish workout and get statistics");
                            }
                            else {
                                processActionTV.setText("START");


                                if(workoutItem.exercisesList.get(focusedItem).exReps > 0){

                                    counterHintTV.setText("Press START to begin " + workoutItem.exercisesList.get(focusedItem).name + " exercise");
                                    speak("Press START to begin " + workoutItem.exercisesList.get(focusedItem).name + " exercise");
                                    sameEx = true;
                                }
                                else {
                                    loadGIF(workoutItem.exercisesList.get(focusedItem + 1).name);
                                    workoutsPlayItemsRV.smoothScrollToPosition(focusedItem + 3);
                                    exerciseName.setText(workoutItem.exercisesList.get(focusedItem + 1).name);
                                    counterHintTV.setText("Press START to begin " + workoutItem.exercisesList.get(focusedItem + 1).name + " exercise");
                                    speak("Press START to begin " + workoutItem.exercisesList.get(focusedItem + 1).name + " exercise");
                                    sameEx = false;
                                }
                            }
                        }
                    }.start();
                }
                else if (processActionTV.getText().toString().equals("DONE")){

                    addWorkoutToCalender();
                }

                if (focusedItem != -1)
                    Log.d("setReps", String.valueOf(workoutItem.exercisesList.get(focusedItem).exReps));

            }
        });

        resumeTimer();

        elapsedTimePlayStopIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isPlaying){
                    if (!isExPlaying){
                        pauseTimer();
                        workoutPauseTimeResumeLL.setVisibility(View.VISIBLE);
                    }
                    else {
                        Toast.makeText(getActivity(), "Please pause at your REST time", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    resumeTimer();
                    workoutPauseTimeResumeLL.setVisibility(View.GONE);
                }
            }
        });


        workoutPauseTimeResumeLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resumeTimer();
                workoutPauseTimeResumeLL.setVisibility(View.GONE);
            }
        });

        return view;
    }

    private void addWorkoutToCalender(){

        pauseTimer();
        achExReps = workoutPlayExItemsAdapter.achWorkoutExItemArrayList;

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < workoutItem.exercisesList.size(); i++) {
            workoutItem.exercisesList.get(i).actualReps = actualExReps.get(i);
            workoutItem.exercisesList.get(i).reps *= exRepsCounterArr.get(i);
            workoutItem.exercisesList.get(i).exTime *= exRepsCounterArr.get(i);
            workoutItem.exercisesList.get(i).actualExTime = achExTime.get(i);
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
        workoutItem.wTime = wTime;


        Log.d("wTime4", String.valueOf(wTime));

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


    private void initSounds() {
        textToSpeech = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.US);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "This Language is not supported");
                    }

                    speak("Hello, Press START to begin " + workoutItem.exercisesList.get(0).name + " exercise");

                } else {
                    Log.e("TTS", "Initilization Failed!");
                }
            }
        });

        mediaPlayer = MediaPlayer.create(getActivity(), R.raw.beep);

    }

    private void speak(String text){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_ADD, null, null);
        }else{
            textToSpeech.speak(text, TextToSpeech.QUEUE_ADD, null);
        }
    }

    private void resumeTimer(){
        elapsedTimePlayStopIV.setImageResource(R.drawable.pause_icon);
        elapsedTimePlayStopTV.setText("PAUSE");
        isPlaying = true;

        StartTime = SystemClock.uptimeMillis();
        handler.postDelayed(runnable, 0);
    }

    private void pauseTimer(){
        elapsedTimePlayStopIV.setImageResource(R.drawable.play_icon2);
        elapsedTimePlayStopTV.setText("PLAY");
        isPlaying = false;

        TimeBuff += MillisecondTime;

        handler.removeCallbacks(runnable);
    }

    private void startExTime(){
        exStartTime = SystemClock.uptimeMillis();
        exTimeHandler.postDelayed(exTimeRunnable, 0);
    }

    private void endExTime(){
        exTimeBuff += exMilliSec;
        exTimeHandler.removeCallbacks(exTimeRunnable);
    }

    public Runnable runnable = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);

            wTime = Seconds;

            Log.d("wTime3", String.valueOf(wTime));

            Minutes = Seconds / 60;

            Seconds = Seconds % 60;


            MilliSeconds = (int) (UpdateTime % 1000);

            elapsedTimeTV.setText("" + Minutes + ":"
                    + String.format("%02d", Seconds) + " mins");

            handler.postDelayed(this, 0);
        }

    };

    public Runnable exTimeRunnable = new Runnable() {

        public void run() {

            exMilliSec = SystemClock.uptimeMillis() - exStartTime;

            exTime = (int) (exMilliSec / 1000);


            handler.postDelayed(this, 0);
        }

    };

    private void loadGIF(String name){

        try {
            gifDrawable = new GifDrawable(getResources(), AppConfig.exercises_gifs[Arrays.asList(AppConfig.exercises_names).indexOf(name)]);

        } catch (IOException e) {
            e.printStackTrace();
        }
        mGigImageView.setImageDrawable(gifDrawable);
    }

    public WorkoutPlayManual(WorkoutItem workoutItem){
        this.workoutItem = workoutItem;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }

        if (mediaPlayer != null)
            mediaPlayer.stop();

        pauseTimer();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }

        if (mediaPlayer != null)
            mediaPlayer.stop();

        pauseTimer();

    }


}