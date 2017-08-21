package ch.philopateer.mibody.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
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

    RecyclerView workoutsPlayItemsRV;
    TextView workoutName, exerciseName, exerciseReps, processActionTV, counterHintTV, elapsedTimeTV, elapsedTimePlayStopTV;
    ImageView elapsedTimePlayStopIV;
    Boolean isPlaying = false;

    CardView processActionCV;
    ProgressBar processActionPB;

    TextToSpeech textToSpeech;

    int targertPos = 0;
    RelativeLayout saveBtn;
    public WorkoutItem workoutItem;
    WorkoutPlayExItemsAdapter workoutPlayExItemsAdapter;
    public ArrayList<Integer> achExReps;
    public ArrayList<Long> achExTime;
    int focusedItem = -1;

    GifImageView mGigImageView;
    GifDrawable gifDrawable = null;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    long MillisecondTime, StartTime, TimeBuff, UpdateTime, exTime = 0L ;

    Handler handler;

    int Seconds, Minutes, MilliSeconds ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.workout_play_fragment, container, false);


        initTxtToSpeech();
        handler = new Handler();

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

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        workoutsPlayItemsRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        achExReps = new ArrayList<Integer>();
        achExTime = new ArrayList<Long>();

        for (int i = 0; i < workoutItem.exercisesList.size(); i++){
            achExReps.add(workoutItem.exercisesList.get(i).reps);
            achExTime.add(workoutItem.exercisesList.get(i).exTime);

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
        workoutsPlayItemsRV.smoothScrollToPosition(focusedItem + 3);


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

                if (processActionTV.getText().toString().equals("START")){

                    exTime = 0L;
                    focusedItem++;
                    workoutsPlayItemsRV.scrollToPosition(focusedItem + 1);
                    exerciseName.setText(workoutItem.exercisesList.get(focusedItem).name);
                    workoutPlayExItemsAdapter.setFocusedItem(focusedItem + 1);
                    processActionPB.setMax(workoutItem.exercisesList.get(focusedItem).restTime);
                    processActionPB.setProgress(workoutItem.exercisesList.get(focusedItem).restTime);
                    workoutPlayExItemsAdapter.setItemAction(0);
                    processActionTV.setText("REST");
                    counterHintTV.setText("Press REST to begin rest time");
                    speak("Press REST to begin rest time");

                }
                else if (processActionTV.getText().toString().equals("REST")){

                    achExTime.set(focusedItem, exTime);
                    workoutPlayExItemsAdapter.setItemAction(1);
                    processActionPB.setMax(workoutItem.exercisesList.get(focusedItem).restTime);
                    counterHintTV.setText("Modify exercise reps unless they matches the objective");
                    speak("Modify exercise reps unless they matches the objective");

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
                                counterHintTV.setText("Press DONE to finish workout and get statistics");
                                speak("Press DONE to finish workout and get statistics");
                            }
                            else {
                                processActionTV.setText("START");
                                loadGIF(workoutItem.exercisesList.get(focusedItem + 1).name);
                                workoutsPlayItemsRV.smoothScrollToPosition(focusedItem + 3);
                                exerciseName.setText(workoutItem.exercisesList.get(focusedItem + 1).name);
                                counterHintTV.setText("Press START to begin " + workoutItem.exercisesList.get(focusedItem + 1).name + " exercise");
                                speak("Press START to begin " + workoutItem.exercisesList.get(focusedItem + 1).name + " exercise");
                            }
                        }
                    }.start();
                }
                else if (processActionTV.getText().toString().equals("DONE")){

                    addWorkoutToCalender();
                }
            }
        });


        elapsedTimePlayStopIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isPlaying){
                    elapsedTimePlayStopIV.setImageResource(R.drawable.play_icon2);
                    elapsedTimePlayStopTV.setText("PLAY");
                    isPlaying = false;

                    TimeBuff += MillisecondTime;

                    handler.removeCallbacks(runnable);
                }
                else {
                    elapsedTimePlayStopIV.setImageResource(R.drawable.pause_icon);
                    elapsedTimePlayStopTV.setText("PAUSE");
                    isPlaying = true;

                    StartTime = SystemClock.uptimeMillis();
                    handler.postDelayed(runnable, 0);

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

    private void initTxtToSpeech() {
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
    }

    private void speak(String text){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }else{
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    public Runnable runnable = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);

            Minutes = Seconds / 60;

            Seconds = Seconds % 60;

            exTime = Seconds;

            MilliSeconds = (int) (UpdateTime % 1000);

            elapsedTimeTV.setText("" + Minutes + ":"
                    + String.format("%02d", Seconds) + "mins");

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
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}