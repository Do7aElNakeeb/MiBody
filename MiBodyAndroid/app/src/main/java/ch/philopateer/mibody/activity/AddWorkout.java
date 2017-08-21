package ch.philopateer.mibody.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ch.philopateer.mibody.R;
import ch.philopateer.mibody.app.AppConfig;
import ch.philopateer.mibody.object.ExerciseItem;
import ch.philopateer.mibody.object.WorkoutExItem;
import ch.philopateer.mibody.object.WorkoutItem;
import ch.philopateer.mibody.helper.ExercisesAdapter;
import ch.philopateer.mibody.adapter.WorkoutExItemAdapter;

import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

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

    //SQLiteHandler sqLiteHandler;
    EditText WorkoutNameET;
    //EditText workoutRepeat;
    int workoutReps = 1;
    ImageView AddExercise, workoutNameEditBtn;
    //ImageButton workoutRepeatBtn;
    TextView WorkoutNameTxtView, SaveWorkout;

    LinearLayout dragExLL, workoutExSetTubesLL;

    SwitchCompat repsTimeSwitch;
    boolean repsTimeBool = false;

    int WorkoutExItemsRVHeight;
    RelativeLayout workoutExDetailsLayout, addWorkoutRL, workoutExSetTubesRL;
    ImageView b1, b2, b3, r1, r2, r3, g1, g2, g3, n1, n2, n3, exTubeIV1, exTubeIV2, exTubeIV3;
    TextView repsTxtView, restTxtView, exRepsTV;
    EditText repsEdtTxt, restEdtTxt;
    ImageView repsMinusBtn, repsPlusBtn, restMinusBtn, restPlusBtn, exRepsPlusIV, exRepsMinusIV, workoutExSetTubesDoneIV;
    //CardView exRepsPlusBtn;
    ProgressBar restTimePB;

    RecyclerView ExercisesRV, WorkoutExItemsRV;
    ArrayList<ExerciseItem> exerciseItemArrayList;

    ArrayList<WorkoutExItem> workoutExItemArrayList;

    ExercisesAdapter exercisesAdapter;
    WorkoutExItemAdapter workoutExItemAdapter;
    LinearLayoutManager linearLayoutManager;
    SnapHelper snapHelper;

    private int focusedItem = 0, selectedItem = 0;

    int flag1st = 0;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    WorkoutItem workoutItem;
    boolean editMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_ex_set_details);


        WorkoutNameET = (EditText) findViewById(R.id.workoutNameET);
        WorkoutNameTxtView = (TextView) findViewById(R.id.workoutNameTxtView);
        SaveWorkout = (TextView) findViewById(R.id.save_btn);

        Bundle bundle = getIntent().getExtras();
        if (bundle.containsKey("workoutItem")) {
            workoutItem = (WorkoutItem) getIntent().getParcelableExtra("workoutItem");
            workoutItem.JSONtoArray();

            workoutExItemArrayList = workoutItem.exercisesList;
            workoutExItemArrayList.add(new WorkoutExItem());
            WorkoutNameTxtView.setText(workoutItem.workoutName);
            SaveWorkout.setText("Save");
            editMode = true;
            Log.d("workoutItemExArSize2", String.valueOf(workoutItem.exercisesList.size()));
        }
        else {
            workoutExItemArrayList = new ArrayList<WorkoutExItem>();
            workoutExItemArrayList.add(new WorkoutExItem());
        }

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        addWorkoutRL = (RelativeLayout) findViewById(R.id.addWorkoutRL);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // Progress dialog
        pDialog = new ProgressDialog(AddWorkout.this);
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);

        dragExLL = (LinearLayout) findViewById(R.id.dragExLL);
        dragExLL.setVisibility(View.VISIBLE);

        workoutNameEditBtn = (ImageView) findViewById(R.id.workoutNameEditBtn);

        AddExercise = (ImageView) findViewById(R.id.add_exercise);


        initWorkoutExDetails();
        initWorkoutViews(size.x);

        WorkoutExItemsRVHeight = linearLayoutManager.getHeight();

        workoutNameEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (WorkoutNameET.getVisibility() == View.VISIBLE) {
                    WorkoutNameTxtView.setText(WorkoutNameET.getText().toString());
                    WorkoutNameTxtView.setVisibility(View.VISIBLE);
                    WorkoutNameET.setVisibility(View.GONE);
                    workoutNameEditBtn.setScaleX(-1);
                    workoutNameEditBtn.setScaleY(1);
                    workoutNameEditBtn.setImageResource(R.drawable.pen_icon);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(WorkoutNameET.getWindowToken(), 0);
                }else {
                    WorkoutNameTxtView.setVisibility(View.INVISIBLE);
                    WorkoutNameET.setText(WorkoutNameTxtView.getText().toString());
                    WorkoutNameET.setVisibility(View.VISIBLE);
                    workoutNameEditBtn.setScaleX((float) 1.5);
                    workoutNameEditBtn.setScaleY((float) 1.5);
                    workoutNameEditBtn.setImageResource(R.drawable.true_mark_icon);

                    WorkoutNameET.requestFocus();
                    WorkoutNameET.selectAll();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);

                }
            }
        });

        WorkoutNameET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                Boolean action = false;
                if (i == EditorInfo.IME_ACTION_DONE) {

                    WorkoutNameTxtView.setText(WorkoutNameET.getText().toString());
                    WorkoutNameTxtView.setVisibility(View.VISIBLE);
                    WorkoutNameET.setVisibility(View.GONE);
                    workoutNameEditBtn.setScaleX(-1);
                    workoutNameEditBtn.setImageResource(R.drawable.pen_icon);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(WorkoutNameET.getWindowToken(), 0);
                    action = true;
                }
                return action;
            }
        });

        AddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                workoutExItemArrayList.add(new WorkoutExItem());
                workoutExItemAdapter.notifyItemInserted(workoutExItemArrayList.size());
                workoutExItemAdapter.setFocusedItem(workoutExItemAdapter.getItemCount() - 2);
                WorkoutExItemsRV.smoothScrollToPosition(workoutExItemAdapter.getItemCount());
            }
        });

        SaveWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JSONArray jsonArray = new JSONArray();
                for (int i=0; i < workoutExItemArrayList.size()-1; i++) {
                    jsonArray.put(workoutExItemArrayList.get(i).getJSONObject());
                    Log.d("AddWJSONobject", workoutExItemArrayList.get(i).getJSONObject().toString());
                }
                Log.d ("JSONObjectSQL", jsonArray.toString());

                addWorkout(new WorkoutItem("", WorkoutNameTxtView.getText().toString(), workoutReps, String.valueOf(jsonArray), "personalised"));
                Log.d("SjsonToServer", String.valueOf(jsonArray));
                Log.d("jsonToServer", jsonArray.toString());
                finish();
            }
        });
    }

    private void initWorkoutExDetails(){

        workoutExDetailsLayout = (RelativeLayout) findViewById(R.id.workoutExerciseDetails);
        workoutExDetailsLayout.setVisibility(View.INVISIBLE);
        workoutExSetTubesRL = (RelativeLayout) findViewById(R.id.workoutExSetTubesRL);
        workoutExSetTubesLL = (LinearLayout) findViewById(R.id.workoutExSetTubesLL);
        
        // Ropes
        b1 = (ImageView) findViewById(R.id.ropes_black1);
        b2 = (ImageView) findViewById(R.id.ropes_black2);
        b3 = (ImageView) findViewById(R.id.ropes_black3);
        r1 = (ImageView) findViewById(R.id.ropes_red1);
        r2 = (ImageView) findViewById(R.id.ropes_red2);
        r3 = (ImageView) findViewById(R.id.ropes_red3);
        g1 = (ImageView) findViewById(R.id.ropes_grey1);
        g2 = (ImageView) findViewById(R.id.ropes_grey2);
        g3 = (ImageView) findViewById(R.id.ropes_grey3);
        n1 = (ImageView) findViewById(R.id.ropes_none1);
        n2 = (ImageView) findViewById(R.id.ropes_none2);
        n3 = (ImageView) findViewById(R.id.ropes_none3);
        exTubeIV1 = (ImageView) findViewById(R.id.exTubeIV1);
        exTubeIV2 = (ImageView) findViewById(R.id.exTubeIV2);
        exTubeIV3 = (ImageView) findViewById(R.id.exTubeIV3);
        workoutExSetTubesDoneIV = (ImageView) findViewById(R.id.workoutExSetTubesDoneIV);

        repsTimeSwitch = (SwitchCompat) findViewById(R.id.repsTimeSwitch);


        // Reps
        repsTxtView = (TextView) findViewById(R.id.reps_txtview);
        repsEdtTxt = (EditText) findViewById(R.id.reps_edttxt);
        repsMinusBtn = (ImageView) findViewById(R.id.reps_minus_btn);
        repsPlusBtn = (ImageView) findViewById(R.id.reps_plus_btn);
        exRepsTV = (TextView) findViewById(R.id.exercise_reps_txtview);
        exRepsPlusIV = (ImageView) findViewById(R.id.exReps_plus_btn);
        exRepsMinusIV = (ImageView) findViewById(R.id.exReps_minus_btn);

        // Rest
        restTxtView = (TextView) findViewById(R.id.rest_time_txtview);
        restEdtTxt = (EditText) findViewById(R.id.rest_time_edttxt);
        restMinusBtn = (ImageView) findViewById(R.id.rest_minus_btn);
        restPlusBtn = (ImageView) findViewById(R.id.rest_plus_btn);
        restTimePB = (ProgressBar) findViewById(R.id.restProgressBar);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b1.setImageResource(R.drawable.ropes_dot_black_selected);
                r1.setImageResource(R.drawable.ropes_dot_red);
                g1.setImageResource(R.drawable.ropes_dot_grey);
                n1.setImageResource(R.drawable.ropes_dot_none);
                exTubeIV1.setImageResource(R.drawable.ropes_dot_black_selected);
                workoutExItemArrayList.get(selectedItem).rope1 = "B";

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b2.setImageResource(R.drawable.ropes_dot_black_selected);
                r2.setImageResource(R.drawable.ropes_dot_red);
                g2.setImageResource(R.drawable.ropes_dot_grey);
                n2.setImageResource(R.drawable.ropes_dot_none);
                exTubeIV2.setImageResource(R.drawable.ropes_dot_black_selected);
                workoutExItemArrayList.get(selectedItem).rope2 = "B";
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b3.setImageResource(R.drawable.ropes_dot_black_selected);
                r3.setImageResource(R.drawable.ropes_dot_red);
                g3.setImageResource(R.drawable.ropes_dot_grey);
                n3.setImageResource(R.drawable.ropes_dot_none);
                exTubeIV3.setImageResource(R.drawable.ropes_dot_black_selected);
                workoutExItemArrayList.get(selectedItem).rope3 = "B";
            }
        });

        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b1.setImageResource(R.drawable.ropes_dot_black);
                r1.setImageResource(R.drawable.ropes_dot_red_selected);
                g1.setImageResource(R.drawable.ropes_dot_grey);
                n1.setImageResource(R.drawable.ropes_dot_none);
                exTubeIV1.setImageResource(R.drawable.ropes_dot_red_selected);
                workoutExItemArrayList.get(selectedItem).rope1 = "R";
            }
        });
        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b2.setImageResource(R.drawable.ropes_dot_black);
                r2.setImageResource(R.drawable.ropes_dot_red_selected);
                g2.setImageResource(R.drawable.ropes_dot_grey);
                n2.setImageResource(R.drawable.ropes_dot_none);
                exTubeIV2.setImageResource(R.drawable.ropes_dot_red_selected);
                workoutExItemArrayList.get(selectedItem).rope2 = "R";
            }
        });
        r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b3.setImageResource(R.drawable.ropes_dot_black);
                r3.setImageResource(R.drawable.ropes_dot_red_selected);
                g3.setImageResource(R.drawable.ropes_dot_grey);
                n3.setImageResource(R.drawable.ropes_dot_none);
                exTubeIV3.setImageResource(R.drawable.ropes_dot_red_selected);
                workoutExItemArrayList.get(selectedItem).rope3 = "R";
            }
        });

        g1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b1.setImageResource(R.drawable.ropes_dot_black);
                r1.setImageResource(R.drawable.ropes_dot_red);
                n1.setImageResource(R.drawable.ropes_dot_none);
                g1.setImageResource(R.drawable.ropes_dot_grey_selected);
                exTubeIV1.setImageResource(R.drawable.ropes_dot_grey_selected);
                workoutExItemArrayList.get(selectedItem).rope1 = "G";
            }
        });
        g2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b2.setImageResource(R.drawable.ropes_dot_black);
                r2.setImageResource(R.drawable.ropes_dot_red);
                n2.setImageResource(R.drawable.ropes_dot_none);
                g2.setImageResource(R.drawable.ropes_dot_grey_selected);
                exTubeIV2.setImageResource(R.drawable.ropes_dot_grey_selected);
                workoutExItemArrayList.get(selectedItem).rope2 = "G";
            }
        });
        g3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b3.setImageResource(R.drawable.ropes_dot_black);
                r3.setImageResource(R.drawable.ropes_dot_red);
                n3.setImageResource(R.drawable.ropes_dot_none);
                g3.setImageResource(R.drawable.ropes_dot_grey_selected);
                exTubeIV3.setImageResource(R.drawable.ropes_dot_grey_selected);
                workoutExItemArrayList.get(selectedItem).rope3 = "G";
            }
        });

        n1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b1.setImageResource(R.drawable.ropes_dot_black);
                r1.setImageResource(R.drawable.ropes_dot_red);
                n1.setImageResource(R.drawable.ropes_dot_none_selected);
                g1.setImageResource(R.drawable.ropes_dot_grey);
                exTubeIV1.setImageResource(R.drawable.ropes_dot_none_selected);
                workoutExItemArrayList.get(selectedItem).rope1 = "N";
            }
        });
        n2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b2.setImageResource(R.drawable.ropes_dot_black);
                r2.setImageResource(R.drawable.ropes_dot_red);
                n2.setImageResource(R.drawable.ropes_dot_none_selected);
                g2.setImageResource(R.drawable.ropes_dot_grey);
                exTubeIV2.setImageResource(R.drawable.ropes_dot_none_selected);
                workoutExItemArrayList.get(selectedItem).rope2 = "N";
            }
        });
        n3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b3.setImageResource(R.drawable.ropes_dot_black);
                r3.setImageResource(R.drawable.ropes_dot_red);
                n3.setImageResource(R.drawable.ropes_dot_none_selected);
                g3.setImageResource(R.drawable.ropes_dot_grey);
                exTubeIV3.setImageResource(R.drawable.ropes_dot_none_selected);
                workoutExItemArrayList.get(selectedItem).rope3 = "N";
            }
        });

        repsMinusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(repsTxtView.getText().toString()) > 0) {
                    repsTxtView.setText(String.valueOf(Integer.parseInt(repsTxtView.getText().toString()) - 1));
                    workoutExItemArrayList.get(selectedItem).reps = Integer.parseInt(repsTxtView.getText().toString().trim());
                }
            }
        });
        repsPlusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repsTxtView.setText(String.valueOf(Integer.parseInt(repsTxtView.getText().toString()) + 1));
                workoutExItemArrayList.get(selectedItem).reps = Integer.parseInt(repsTxtView.getText().toString().trim());
            }
        });

        restMinusBtn.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(Integer.parseInt(restTxtView.getText().toString()) > 0){
                    restTxtView.setText(String.valueOf(Integer.parseInt(restTxtView.getText().toString()) - 1));
                    restTimePB.setProgress(Integer.parseInt(restTxtView.getText().toString()));
                    workoutExItemArrayList.get(selectedItem).restTime = Integer.parseInt(restTxtView.getText().toString().trim());
                }
                return true;
            }
        });
        restPlusBtn.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(Integer.parseInt(restTxtView.getText().toString()) < 200) {
                    restTxtView.setText(String.valueOf(Integer.parseInt(restTxtView.getText().toString()) + 1));
                    restTimePB.setProgress(Integer.parseInt(restTxtView.getText().toString()));
                    workoutExItemArrayList.get(selectedItem).restTime = Integer.parseInt(restTxtView.getText().toString().trim());
                }
                return true;
            }
        });

        exRepsMinusIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(workoutExItemArrayList.get(selectedItem).exReps > 1) {
                    workoutExItemArrayList.get(selectedItem).exReps--;
                    exRepsTV.setText(String.valueOf(workoutExItemArrayList.get(selectedItem).exReps));
                    workoutExItemAdapter.notifyDataSetChanged();
                }
            }
        });

        exRepsPlusIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                workoutExItemArrayList.get(selectedItem).exReps++;
                exRepsTV.setText(String.valueOf(workoutExItemArrayList.get(selectedItem).exReps));
                workoutExItemAdapter.notifyDataSetChanged();
            }
        });

        workoutExSetTubesLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (workoutExSetTubesRL.getVisibility() == View.VISIBLE){
                    workoutExSetTubesRL.setVisibility(View.GONE);
                    WorkoutExItemsRV.setVisibility(View.VISIBLE);
                }
                else {
                    WorkoutExItemsRV.setVisibility(View.GONE);
                    workoutExSetTubesRL.setVisibility(View.VISIBLE);
                }
            }
        });

        workoutExSetTubesDoneIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                workoutExSetTubesRL.setVisibility(View.GONE);
                WorkoutExItemsRV.setVisibility(View.VISIBLE);
            }
        });

        repsTimeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                workoutExItemArrayList.get(selectedItem).repsTimeBool = b;
                if (b){
                    workoutExItemArrayList.get(selectedItem).reps = 0;
                    workoutExItemArrayList.get(selectedItem).exTime = Integer.parseInt(repsTxtView.getText().toString().trim());
                }
                else {
                    workoutExItemArrayList.get(selectedItem).exTime = 0;
                    workoutExItemArrayList.get(selectedItem).reps = Integer.parseInt(repsTxtView.getText().toString().trim());
                }
            }
        });

    }

    private void updateWorkoutExItemDetails(){
        switch (workoutExItemArrayList.get(selectedItem).rope1){
            case "B":
                b1.setImageResource(R.drawable.ropes_dot_black_selected);
                r1.setImageResource(R.drawable.ropes_dot_red);
                g1.setImageResource(R.drawable.ropes_dot_grey);
                n1.setImageResource(R.drawable.ropes_dot_none);
                exTubeIV1.setImageResource(R.drawable.ropes_dot_black_selected);
                break;
            case "R":
                r1.setImageResource(R.drawable.ropes_dot_red_selected);
                b1.setImageResource(R.drawable.ropes_dot_black);
                g1.setImageResource(R.drawable.ropes_dot_grey);
                n1.setImageResource(R.drawable.ropes_dot_none);
                exTubeIV1.setImageResource(R.drawable.ropes_dot_red_selected);
                break;
            case "G":
                b1.setImageResource(R.drawable.ropes_dot_black);
                r1.setImageResource(R.drawable.ropes_dot_red);
                g1.setImageResource(R.drawable.ropes_dot_grey_selected);
                n1.setImageResource(R.drawable.ropes_dot_none);
                exTubeIV1.setImageResource(R.drawable.ropes_dot_grey_selected);
            case "N":
                b1.setImageResource(R.drawable.ropes_dot_black);
                r1.setImageResource(R.drawable.ropes_dot_red);
                g1.setImageResource(R.drawable.ropes_dot_grey);
                n1.setImageResource(R.drawable.ropes_dot_none_selected);
                exTubeIV1.setImageResource(R.drawable.ropes_dot_none_selected);
                break;
            default:
                break;
        }
        switch (workoutExItemArrayList.get(selectedItem).rope2){
            case "B":
                b2.setImageResource(R.drawable.ropes_dot_black_selected);
                r2.setImageResource(R.drawable.ropes_dot_red);
                g2.setImageResource(R.drawable.ropes_dot_grey);
                n2.setImageResource(R.drawable.ropes_dot_none);
                exTubeIV2.setImageResource(R.drawable.ropes_dot_black_selected);
                break;
            case "R":
                b2.setImageResource(R.drawable.ropes_dot_black);
                g2.setImageResource(R.drawable.ropes_dot_grey);
                r2.setImageResource(R.drawable.ropes_dot_red_selected);
                exTubeIV2.setImageResource(R.drawable.ropes_dot_red_selected);
                n2.setImageResource(R.drawable.ropes_dot_none);
                break;
            case "G":
                b2.setImageResource(R.drawable.ropes_dot_black);
                r2.setImageResource(R.drawable.ropes_dot_red);
                g2.setImageResource(R.drawable.ropes_dot_grey_selected);
                exTubeIV2.setImageResource(R.drawable.ropes_dot_grey_selected);
                n2.setImageResource(R.drawable.ropes_dot_none);
                break;
            case "N":
                b2.setImageResource(R.drawable.ropes_dot_black);
                r2.setImageResource(R.drawable.ropes_dot_red);
                g2.setImageResource(R.drawable.ropes_dot_grey);
                n2.setImageResource(R.drawable.ropes_dot_none_selected);
                exTubeIV2.setImageResource(R.drawable.ropes_dot_none_selected);
                break;
            default:
                break;
        }
        switch (workoutExItemArrayList.get(selectedItem).rope3){
            case "B":
                b3.setImageResource(R.drawable.ropes_dot_black_selected);
                r3.setImageResource(R.drawable.ropes_dot_red);
                g3.setImageResource(R.drawable.ropes_dot_grey);
                n3.setImageResource(R.drawable.ropes_dot_none);
                exTubeIV3.setImageResource(R.drawable.ropes_dot_black_selected);
                break;
            case "R":
                r3.setImageResource(R.drawable.ropes_dot_red_selected);
                b3.setImageResource(R.drawable.ropes_dot_black);
                g3.setImageResource(R.drawable.ropes_dot_grey);
                n3.setImageResource(R.drawable.ropes_dot_none);
                exTubeIV3.setImageResource(R.drawable.ropes_dot_red_selected);
                break;
            case "G":
                b3.setImageResource(R.drawable.ropes_dot_black);
                r3.setImageResource(R.drawable.ropes_dot_red);
                g3.setImageResource(R.drawable.ropes_dot_grey_selected);
                n3.setImageResource(R.drawable.ropes_dot_none);
                exTubeIV3.setImageResource(R.drawable.ropes_dot_grey_selected);
                break;
            case "N":
                b3.setImageResource(R.drawable.ropes_dot_black);
                r3.setImageResource(R.drawable.ropes_dot_red);
                g3.setImageResource(R.drawable.ropes_dot_grey);
                n3.setImageResource(R.drawable.ropes_dot_none_selected);
                exTubeIV3.setImageResource(R.drawable.ropes_dot_none_selected);
                break;
            default:
                break;
        }

        workoutExItemArrayList.get(selectedItem).name = workoutExItemAdapter.workoutExItemArrayList.get(selectedItem).name;
        repsTxtView.setText(String.valueOf(workoutExItemArrayList.get(selectedItem).reps));
        restTxtView.setText(String.valueOf(workoutExItemArrayList.get(selectedItem).restTime));
        restTimePB.setProgress(workoutExItemArrayList.get(selectedItem).restTime);
        exRepsTV.setText(String.valueOf(workoutExItemArrayList.get(selectedItem).exReps));

        repsTimeSwitch.setChecked(workoutExItemArrayList.get(selectedItem).repsTimeBool);

    }

    private void initWorkoutViews(int scrSize){

        // API Exercises
        ExercisesRV = (RecyclerView) findViewById(R.id.addWorkoutExercisesRV);
        LinearLayoutManager layoutManager = new LinearLayoutManager(AddWorkout.this, LinearLayoutManager.HORIZONTAL, false);
        ExercisesRV.setLayoutManager(layoutManager);

        exerciseItemArrayList = new ArrayList<ExerciseItem>();
        for (int i=0; i<AppConfig.exercises_names.length; i++){
            exerciseItemArrayList.add(i, new ExerciseItem(String.valueOf(i),
                    AppConfig.exercises_names[i],
                    AppConfig.exercises_names[i] + ".png",
                    AppConfig.exercises_names[i]+ ".png",
                    "",
                    AppConfig.exercises_discreptions[i], ""));
        }

        exercisesAdapter = new ExercisesAdapter(this, 0, exerciseItemArrayList, 0, null);
        ExercisesRV.setAdapter(exercisesAdapter);

        WorkoutExItemsRV = (RecyclerView) findViewById(R.id.addWorkoutUserExercisesRV);
        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        WorkoutExItemsRV.setLayoutManager(linearLayoutManager);

        snapHelper = new LinearSnapHelper(){

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

                    focusedItem = targetPosition;
                    workoutExItemAdapter.setFocusedItem(focusedItem);
                    return targetPosition;
                }
        };

        snapHelper.attachToRecyclerView(WorkoutExItemsRV);
        WorkoutExItemsRV.setOnFlingListener(snapHelper);

        WorkoutExItemsRV.smoothScrollToPosition(3);
        focusedItem = 1;

        workoutExItemAdapter = new WorkoutExItemAdapter(context, workoutExItemArrayList, scrSize, new WorkoutExItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(WorkoutExItem workoutExItem, int position) {

                Log.d("ClickedFocused", String.valueOf(focusedItem));
                selectedItem = position - 1;
                updateWorkoutExItemDetails();

                if (position >= focusedItem){
                    WorkoutExItemsRV.smoothScrollToPosition(position + 1);
                }
                else if (position < focusedItem){
                    WorkoutExItemsRV.smoothScrollToPosition(position - 1);
                }
                focusedItem = position;
                workoutExItemAdapter.setFocusedItem(focusedItem);
                if (workoutExItem.name.equals("Drop here")){
                    dragExLL.setVisibility(View.VISIBLE);
                    workoutExDetailsLayout.setVisibility(View.INVISIBLE);
                    Toast.makeText(context, "Drag Exercise and Drop Here Firstly", Toast.LENGTH_LONG).show();
                }
                else{
                    if (workoutExDetailsLayout.getVisibility() == View.INVISIBLE){
                        workoutExDetailsLayout.setVisibility(View.VISIBLE);
                        dragExLL.setVisibility(View.GONE);

                        //workoutExItemAdapter.setSelectedItem(position);
                    }
                    else {
                        dragExLL.setVisibility(View.VISIBLE);
                        workoutExDetailsLayout.setVisibility(View.INVISIBLE);

                        workoutExItemAdapter.setFocusedItem(position);
                        workoutExItemAdapter.setSelectedItem(-1);

                    }
                }
            }
        });
        WorkoutExItemsRV.setAdapter(workoutExItemAdapter);

        if (flag1st == 0) {
            WorkoutExItemsRV.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    WorkoutExItemsRV.getViewTreeObserver().removeOnPreDrawListener(this);
                    WorkoutExItemsRVHeight = WorkoutExItemsRV.getHeight();
                    flag1st = 1;
                    return true;
                }
            });
        }
    }

    private void addWorkout(WorkoutItem workoutItem){

        if (editMode) {

            FirebaseDatabase.getInstance().getReference().child("users").child(firebaseAuth.getCurrentUser().getUid()).child("workouts").child(this.workoutItem.workoutID)
                    .child("exercisesJSON").setValue(workoutItem.exercisesJSON);
            FirebaseDatabase.getInstance().getReference().child("users").child(firebaseAuth.getCurrentUser().getUid()).child("workouts").child(this.workoutItem.workoutID)
                    .child("workoutName").setValue(workoutItem.workoutName);

        }
        else {
            String key = databaseReference.child("/users/" + firebaseAuth.getCurrentUser().getUid() + "/workouts/").push().getKey();

            workoutItem.workoutID = key;

            Map<String, Object> postValues = workoutItem.toMap();

            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put("/users/" + firebaseAuth.getCurrentUser().getUid() + "/workouts/" + key, postValues);
            databaseReference.updateChildren(childUpdates);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (workoutExDetailsLayout.getVisibility() == View.VISIBLE) {

                dragExLL.setVisibility(View.VISIBLE);
                workoutExDetailsLayout.setVisibility(View.INVISIBLE);
                workoutExItemAdapter.setSelectedItem(-1);
            }
            else {
                finish();
            }
            return true;
        }

        // If it wasn't the Back key, bubble up to the default
        // system behavior
        return super.onKeyDown(keyCode, event);
    }
}
