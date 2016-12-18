package com.mibody.app.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mibody.app.R;
import com.mibody.app.app.AppConfig;
import com.mibody.app.app.AppController;
import com.mibody.app.app.ExerciseItem;
import com.mibody.app.app.WorkoutExItem;
import com.mibody.app.app.WorkoutItem;
import com.mibody.app.helper.ExercisesAdapter;
import com.mibody.app.helper.RecyclerViewAdapter;
import com.mibody.app.helper.SQLiteHandler;
import com.mibody.app.helper.WorkoutExItemAdapter;

import android.content.ClipData;
import android.graphics.drawable.Drawable;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by NakeebMac on 10/4/16.
 */

public class AddWorkout extends AppCompatActivity {

    private static final String TAG = AddWorkout.class.getSimpleName();

    Context context = this;
    ProgressDialog pDialog;

    SQLiteHandler sqLiteHandler;
    EditText WorkoutName;
    EditText workoutRepeat;
    int workoutReps = 0;
    Button AddExercise;
    ImageButton workoutRepeatBtn;
    Button SaveWorkout;
    GridView ExercisesGrid;
    RecyclerView ExercisesSetGrid;

    RecyclerView ExercisesRV, UserExercisesRV;
    ArrayList<ExerciseItem> exerciseItemArrayList, userExerciseItemArrayList;

    WorkoutExItemAdapter WAdapter;
    String exercises_names[] = { "Exercise A", "Exercise B", "Exercise C", "Exercise D", "Exercise E",
            "Exercise F", "Exercise G", "Exercise H", "Exercise I", "Exercise J", "Exercise K" };


    int Images[] = { R.drawable.ex1, R.drawable.ex2,
            R.drawable.ex3, R.drawable.ex4, R.drawable.ex5,
            R.drawable.ex6, R.drawable.ex7, R.drawable.ex8,
            R.drawable.ex9, R.drawable.ex10, R.drawable.ex11 };

    ArrayList<WorkoutExItem> workoutExItemArrayList;
    ArrayList<WorkoutItem> workoutItemArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_workout);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        WorkoutName = (EditText) findViewById(R.id.workout_name);
        workoutRepeat = (EditText) findViewById(R.id.workoutRepeat);
        workoutRepeatBtn = (ImageButton) findViewById(R.id.workoutRepeatBtn);
        AddExercise = (Button) findViewById(R.id.add_exercise);
        SaveWorkout = (Button) findViewById(R.id.save_btn);
        sqLiteHandler = new SQLiteHandler(this);


        try {
            sqLiteHandler.open();

        } catch (Exception e) {
            Log.i("hello", "hello");
        }

        initWorkoutViews();

        workoutRepeatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workoutReps++;
                workoutRepeat.setText(String.valueOf(workoutReps));
            }
        });

        SaveWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONArray jsonArray = new JSONArray();
                for (int i=0; i < workoutExItemArrayList.size(); i++) {
                    jsonArray.put(workoutExItemArrayList.get(i).getJSONObject());
                }
                Log.d ("JSONObjectSQL", jsonArray.toString());

                addWorkout(new WorkoutItem("", WorkoutName.getText().toString(), workoutReps, jsonArray.toString(), "personalised"));
                //sqLiteHandler.addWorkout(new WorkoutItem(WorkoutName.getText().toString(), workoutReps, "pic", jsonArray.toString()));
                finish();
            }
        });

    }



    private void initWorkoutViews(){
        ExercisesSetGrid = (RecyclerView) findViewById(R.id.exercises_set_grid);
       // ExercisesSetGrid.setHasFixedSize(true);
        ExercisesSetGrid.setLayoutManager(new LinearLayoutManager(AddWorkout.this, LinearLayoutManager.HORIZONTAL, false));
/*
        workoutItemArrayList = new ArrayList<>();
        workoutItemArrayList.add(new WorkoutItem());
*/
        // API exercises
        ExercisesRV = (RecyclerView) findViewById(R.id.addWorkoutExercisesRV);
        ExercisesRV.setLayoutManager(new LinearLayoutManager(AddWorkout.this, LinearLayoutManager.HORIZONTAL, false));

        exerciseItemArrayList = new ArrayList<ExerciseItem>();
        exerciseItemArrayList = sqLiteHandler.getExercises(null);

        ExercisesAdapter exercisesAdapter = new ExercisesAdapter(this, exerciseItemArrayList, 0);
        ExercisesRV.setAdapter(exercisesAdapter);


        // User selected exercises RV
        LinearLayoutManager layoutManager = new LinearLayoutManager(AddWorkout.this, LinearLayoutManager.HORIZONTAL, false);
        UserExercisesRV = (RecyclerView) findViewById(R.id.addWorkoutUserExercisesRV);
        UserExercisesRV.setLayoutManager(layoutManager);


        userExerciseItemArrayList = new ArrayList<ExerciseItem>();
        userExerciseItemArrayList.add(exerciseItemArrayList.get(0));

        final ExercisesAdapter userExercisesAdapter = new ExercisesAdapter(this, userExerciseItemArrayList, 1);
        UserExercisesRV.setAdapter(userExercisesAdapter);

        LinearSnapHelper snapHelper  = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(UserExercisesRV);


        workoutExItemArrayList = new ArrayList<>();
        workoutExItemArrayList.add(new WorkoutExItem());

        WAdapter = new WorkoutExItemAdapter(this, workoutExItemArrayList);
        ExercisesSetGrid.setAdapter(WAdapter);// set adapter on recyclerview
//        WAdapter.notifyDataSetChanged();// Notify the adapter

        AddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //workoutExItemArrayList.add(new WorkoutExItem());
                //WAdapter.notifyItemInserted(workoutExItemArrayList.size());
                userExerciseItemArrayList.add(exerciseItemArrayList.get(0));
                userExercisesAdapter.notifyItemInserted(userExerciseItemArrayList.size());
                UserExercisesRV.scrollToPosition(userExerciseItemArrayList.size());
            }
        });


    }

    public final static class MyTouchListener implements OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                        view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.VISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }

    public static class MyDragListener implements OnDragListener {
        /*
        Drawable enterShape = getResources().getDrawable(
                R.drawable.gym1);
        Drawable normalShape = getResources().getDrawable(R.drawable.gym1);
*/
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
//                    v.setBackgroundDrawable(enterShape);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
//                    v.setBackgroundDrawable(normalShape);
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    CardView view = (CardView) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    //owner.removeView(view);
                    CardView container = (CardView) v;
                    container = view;
                    view.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
//                    v.setBackgroundDrawable(normalShape);
                default:
                    break;
            }
            return true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            sqLiteHandler.open();

        } catch (Exception e) {
            Log.i("hello", "hello");
        }
    }

    private void addWorkout(final WorkoutItem workoutItem){

        // Tag used to cancel the request
        String tag_string_req = "req_register";

        //if the email and password are not empty
        //displaying a progress dialog

        pDialog.setMessage("Signing in please wait ...");
        showDialog();

        SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
        final String user_id = sharedPreferences.getString("user_id", "");

        StringRequest strReq = new StringRequest(com.android.volley.Request.Method.POST, AppConfig.URL_SERVER + "addWorkout.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    String message = jObj.getString("message");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in Shared Preferences

                        String id = jObj.get("name").toString();

                        sqLiteHandler.addWorkout(new WorkoutItem(id, workoutItem.workoutName, workoutItem.workoutReps, workoutItem.exercisesJSON, "personalised"));
                        // Inserting row in users table
                        //db.addUser(id, name, email, mobile, carBrand, carModel, carYear, regID, created_at);

                    }

                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", user_id);
                params.put("name", workoutItem.workoutName);
                params.put("reps", String.valueOf(workoutItem.workoutReps));
                params.put("exercises", workoutItem.exercisesJSON);
                params.put("type", "personalised");

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
