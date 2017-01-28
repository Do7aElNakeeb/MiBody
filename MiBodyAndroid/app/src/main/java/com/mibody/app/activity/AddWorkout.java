package com.mibody.app.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
    ImageView AddExercise;
    ImageButton workoutRepeatBtn;
    TextView SaveWorkout;


    RelativeLayout workoutExDetailsLayout;
    ImageView b1, b2, b3, r1, r2, r3, y1, y2, y3;
    TextView repsTxtView, restTxtView, exRepsTxtView;
    EditText repsEdtTxt, restEdtTxt;
    ImageView repsMinusBtn, repsPlusBtn, restMinusBtn, restPlusBtn, exRepsPlusBtn;

    RecyclerView ExercisesRV, WorkoutExItemsRV;
    ArrayList<ExerciseItem> exerciseItemArrayList;

    ArrayList<WorkoutExItem> workoutExItemArrayList;

    ExercisesAdapter exercisesAdapter;
    WorkoutExItemAdapter workoutExItemAdapter;
    LinearLayoutManager linearLayoutManager;
    SnapHelper snapHelper;

    private float itemWidth;
    private float padding;
    private float firstItemWidth;
    private float allPixels;

    private int focusedItem = 0, selectedItem = 0;

    private static final int NUM_ITEMS = 5;
    private static final String BUNDLE_LIST_PIXELS = "allPixels";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_workout);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        itemWidth = getResources().getDimension(R.dimen.item_width);
        padding = (size.x - itemWidth) / 2;
        firstItemWidth = getResources().getDimension(R.dimen.padding_item_width);

        allPixels = 0;

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        WorkoutName = (EditText) findViewById(R.id.workout_name);
        workoutRepeat = (EditText) findViewById(R.id.workoutRepeat);
        workoutRepeatBtn = (ImageButton) findViewById(R.id.workoutRepeatBtn);
        AddExercise = (ImageView) findViewById(R.id.add_exercise);
        SaveWorkout = (TextView) findViewById(R.id.save_btn);

        sqLiteHandler = new SQLiteHandler(this);


        try {
            sqLiteHandler.open();

        } catch (Exception e) {
            Log.i("hello", "hello");
        }

        initWorkoutExDetails();
        initWorkoutViews();

        AddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //workoutExItemArrayList.add(new WorkoutExItem());
                //WAdapter.notifyItemInserted(workoutExItemArrayList.size());
                workoutExItemArrayList.add(new WorkoutExItem());
                workoutExItemAdapter.notifyItemInserted(workoutExItemArrayList.size()); // 0 1 2 *3
                workoutExItemAdapter.setFocusedItem(workoutExItemAdapter.getItemCount() - 2);
                WorkoutExItemsRV.smoothScrollToPosition(workoutExItemAdapter.getItemCount());
            }
        });

        WorkoutExItemsRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int fPos = linearLayoutManager.findLastCompletelyVisibleItemPosition() - 1; // 0 1 2 3 4 5 6 7 8 9  2 1 2

                //fPos = (fPos/2) + 1;
               /*synchronized (this)*/ {
                    if(newState == RecyclerView.SCROLL_STATE_IDLE){
                        //workoutExItemAdapter.setFocusedItem(fPos);
                        //recyclerView.removeOnScrollListener(this);
                        //Log.d("FocusedFlinged", String.valueOf(fPos) + " -- " + String.valueOf(linearLayoutManager.findLastVisibleItemPosition()) + " -- " + String.valueOf(linearLayoutManager.findFirstVisibleItemPosition()));
                    }
                }
            }

            int flag = 0;

            /**
             * TODO
             * Solve Scrolling problems
             * */

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int fPos = linearLayoutManager.findLastCompletelyVisibleItemPosition() - 1; // 0 1 2 3 4 5 6 7 8 9  2 1 2
                Log.d("FocusedPos", String.valueOf(fPos));

                if (linearLayoutManager.findLastVisibleItemPosition() == workoutExItemAdapter.getItemCount() - 1) {
                    workoutExItemAdapter.setFocusedItem(workoutExItemAdapter.getItemCount() - 1);
                    //recyclerView.stopScroll();
                    flag = 1;
                } else if (flag == 0){
                    workoutExItemAdapter.setFocusedItem(fPos);
                    flag = 0;
                }

            }
        });

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

    private void initWorkoutExDetails(){

        workoutExDetailsLayout = (RelativeLayout) findViewById(R.id.workoutExerciseDetails);
        workoutExDetailsLayout.setVisibility(View.GONE);

        // Ropes
        b1 = (ImageView) findViewById(R.id.ropes_black1);
        b2 = (ImageView) findViewById(R.id.ropes_black2);
        b3 = (ImageView) findViewById(R.id.ropes_black3);
        r1 = (ImageView) findViewById(R.id.ropes_red1);
        r2 = (ImageView) findViewById(R.id.ropes_red2);
        r3 = (ImageView) findViewById(R.id.ropes_red3);
        y1 = (ImageView) findViewById(R.id.ropes_yellow1);
        y2 = (ImageView) findViewById(R.id.ropes_yellow2);
        y3 = (ImageView) findViewById(R.id.ropes_yellow3);

        // Reps
        repsTxtView = (TextView) findViewById(R.id.reps_txtview);
        repsEdtTxt = (EditText) findViewById(R.id.reps_edttxt);
        repsMinusBtn = (ImageView) findViewById(R.id.reps_minus_btn);
        repsPlusBtn = (ImageView) findViewById(R.id.reps_plus_btn);

        // Rest
        restTxtView = (TextView) findViewById(R.id.rest_time_txtview);
        restEdtTxt = (EditText) findViewById(R.id.rest_time_edttxt);
        restMinusBtn = (ImageView) findViewById(R.id.rest_minus_btn);
        restPlusBtn = (ImageView) findViewById(R.id.rest_plus_btn);

        // Exercise Reps
        exRepsTxtView = (TextView) findViewById(R.id.exercise_reps_txtview);
        exRepsPlusBtn = (ImageView) findViewById(R.id.exercise_reps_circle_btn);
    }

    private void initWorkoutViews(){

        // API Exercises
        ExercisesRV = (RecyclerView) findViewById(R.id.addWorkoutExercisesRV);
        LinearLayoutManager layoutManager = new LinearLayoutManager(AddWorkout.this, LinearLayoutManager.HORIZONTAL, false);
        ExercisesRV.setLayoutManager(layoutManager);

        exerciseItemArrayList = new ArrayList<ExerciseItem>();
        exerciseItemArrayList = sqLiteHandler.getExercises(null);

        exercisesAdapter = new ExercisesAdapter(this, exerciseItemArrayList, 0);
        ExercisesRV.setAdapter(exercisesAdapter);

        // Workout Exercises RV
        //getWorkoutExItemsRV();
        /*
        WorkoutExItemsRV = (RecyclerView) findViewById(R.id.addWorkoutUserExercisesRV);
        WorkoutExItemsRV.setLayoutManager(new LinearLayoutManager(AddWorkout.this, LinearLayoutManager.HORIZONTAL, false));

        workoutExItemArrayList = new ArrayList<WorkoutExItem>();
        workoutExItemArrayList.add(new WorkoutExItem());

        workoutExItemAdapter = new WorkoutExItemAdapter(context, workoutExItemArrayList);
        WorkoutExItemsRV.setAdapter(workoutExItemAdapter);
*/
        WorkoutExItemsRV = (RecyclerView) findViewById(R.id.addWorkoutUserExercisesRV);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        WorkoutExItemsRV.setLayoutManager(linearLayoutManager);

        snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(WorkoutExItemsRV);
        WorkoutExItemsRV.setOnFlingListener(snapHelper);


        workoutExItemArrayList = new ArrayList<WorkoutExItem>();
        workoutExItemArrayList.add(new WorkoutExItem());
        workoutExItemArrayList.add(new WorkoutExItem());
        workoutExItemArrayList.add(new WorkoutExItem());
        WorkoutExItemsRV.smoothScrollToPosition(3);
        focusedItem = 2;

        workoutExItemAdapter = new WorkoutExItemAdapter(context, workoutExItemArrayList, new WorkoutExItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(WorkoutExItem workoutExItem, int position, WorkoutExItemAdapter.ViewHolder viewHolder) {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) viewHolder.LLtoMove.getLayoutParams();
                if (position >= focusedItem){
                    WorkoutExItemsRV.smoothScrollToPosition(position + 1);
                }
                else if (position < focusedItem){
                    WorkoutExItemsRV.smoothScrollToPosition(position - 1);
                }
                focusedItem = position;
                workoutExItemAdapter.setFocusedItem(position);
                if (workoutExItem.name != null){
                    workoutExDetailsLayout.setVisibility(View.GONE);
                    Toast.makeText(context, "Drag Exercise and Drop Here Firstly", Toast.LENGTH_LONG).show();
                }
                else{
                    if (params.topMargin == 0 && workoutExDetailsLayout.getVisibility() == View.GONE){
                        workoutExDetailsLayout.setVisibility(View.VISIBLE);
                        workoutExItemAdapter.setSelectedItem(position);
                    }
                    else {
                        workoutExDetailsLayout.setVisibility(View.GONE);
                        workoutExItemAdapter.setSelectedItem(-1);
                    }
                    Toast.makeText(context, "Set Exercise Details", Toast.LENGTH_LONG).show();
                }
            }
        });
        WorkoutExItemsRV.setAdapter(workoutExItemAdapter);

    }

    public void getWorkoutExItemsRV() {
        WorkoutExItemsRV = (RecyclerView) findViewById(R.id.addWorkoutUserExercisesRV);
/*
        if (WorkoutExItemsRV != null) {
            WorkoutExItemsRV.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setFocusedItem();
                }
            }, 3000);
            WorkoutExItemsRV.postDelayed(new Runnable() {
                @Override
                public void run() {
                    WorkoutExItemsRV.smoothScrollToPosition(workoutExItemAdapter.getItemCount()-2);
                    setFocusedItem();
                }
            }, 500);
        }
*/
        ViewTreeObserver vto = WorkoutExItemsRV.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                WorkoutExItemsRV.getViewTreeObserver().removeOnPreDrawListener(this);
                int finalWidth = WorkoutExItemsRV.getMeasuredWidth();
                itemWidth = getResources().getDimension(R.dimen.item_width);
                padding = (finalWidth - itemWidth) / 1;
                firstItemWidth = padding ;
                allPixels = 0;

                final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                WorkoutExItemsRV.setLayoutManager(linearLayoutManager);
                WorkoutExItemsRV.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        synchronized (this) {
                            if(newState == RecyclerView.SCROLL_STATE_IDLE){
                                calculatePositionAndScroll(recyclerView);
                            }
                        }

                    }

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        allPixels += dx;
                    }
                });

                workoutExItemArrayList = new ArrayList<WorkoutExItem>();
                workoutExItemArrayList.add(new WorkoutExItem());

                //workoutExItemAdapter = new WorkoutExItemAdapter(context, workoutExItemArrayList /*, (int) firstItemWidth */);
                WorkoutExItemsRV.setAdapter(workoutExItemAdapter);
                //workoutExItemAdapter.setFocusedItem(workoutExItemAdapter.getItemCount() - 2);

                return true;
            }
        });
    }

    /* this if most important, if expectedPositionDate < 0 recyclerView will return to nearest item*/

    @Override
    protected void onResume() {
        super.onResume();

        try {
            sqLiteHandler.open();

        } catch (Exception e) {
            Log.i("hello", "hello");
        }

    }

    private void calculatePositionAndScroll(RecyclerView recyclerView) {
        int expectedPosition = Math.round((allPixels + padding - firstItemWidth) / itemWidth);
        // Special cases for the padding items
        if (expectedPosition == -1) {
            expectedPosition = 0;
        } else if (expectedPosition >= recyclerView.getAdapter().getItemCount() - 2) {
            expectedPosition--;
        }
        scrollListToPosition(recyclerView, expectedPosition);
    }

    private void scrollListToPosition(RecyclerView recyclerView, int expectedPosition) {
        float targetScrollPos = expectedPosition * itemWidth + firstItemWidth - padding;
        float missingPx = targetScrollPos - allPixels;
        if (missingPx != 0) {
            recyclerView.smoothScrollBy((int) missingPx, 0);
            setFocusedItem();
        }
    }

    private void setFocusedItem() {
        int expectedPosition = Math.round((allPixels + padding - firstItemWidth) / itemWidth);
        int setPosition = expectedPosition + 1;
//        set color here
        workoutExItemAdapter.setFocusedItem(setPosition);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        allPixels = savedInstanceState.getFloat(BUNDLE_LIST_PIXELS);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putFloat(BUNDLE_LIST_PIXELS, allPixels);
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

    private void addWorkout(final WorkoutItem workoutItem){

        // Tag used to cancel the request
        String tag_string_req = "req_register";

        //if the email and password are not empty
        //displaying a progress dialog

        pDialog.setMessage("Saving Workout ...");
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
                        // Workout successfully stored in MySQL
                        String id = jObj.get("id").toString();
                        sqLiteHandler.addWorkout(new WorkoutItem(id, workoutItem.workoutName, workoutItem.workoutReps, workoutItem.exercisesJSON, "personalised"));
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
