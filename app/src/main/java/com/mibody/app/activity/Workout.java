package com.mibody.app.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.mibody.app.R;
import com.mibody.app.app.AppConfig;
import com.mibody.app.app.ExerciseItem;
import com.mibody.app.app.WorkoutExItem;
import com.mibody.app.app.WorkoutItem;
import com.mibody.app.helper.ItemClickListener;
import com.mibody.app.helper.RecyclerViewAdapter;
import com.mibody.app.helper.SQLiteHandler;
import com.mibody.app.helper.WorkoutExItemAdapter;
import com.mibody.app.helper.WorkoutSQLAdapter;
import com.mibody.app.helper.WorkoutsAdapter;

import java.util.ArrayList;
import java.util.List;

public class Workout extends Fragment {

    RecyclerView workoutsRV;
    List<WorkoutItem> workoutItemArrayList;
    SQLiteHandler sqLiteHandler;

    public Workout() {
        // Required empty public constructor
    }

    String workouts_names[] = { "Exercise A", "Exercise B", "Exercise C", "Exercise D",
            "Exercise E", "Exercise F", "Exercise G", "Exercise H", "Exercise I", "Exercise J", "Exercise K" };

    String country_names[] = { "Description A", "Description B", "Description C", "Description D",
            "Description E", "Description F", "Description G", "Description H", "Description I",
            "Description J", "Description K", "Japan", "Costa Rica", "Uruguay",
            "Italy", "England", "France", "Switzerland", "Ecuador",
            "Honduras", "Agrentina", "Nigeria", "Bosnia and Herzegovina",
            "Iran", "Germany", "United States", "Portugal", "Ghana",
            "Belgium", "Algeria", "Russia", "Korea Republic" };

    int Images[] = { R.drawable.ex1, R.drawable.ex2,
            R.drawable.ex3, R.drawable.ex4, R.drawable.ex5,
            R.drawable.ex6, R.drawable.ex7, R.drawable.ex8,
            R.drawable.ex9, R.drawable.ex10, R.drawable.ex11 };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sqLiteHandler = new SQLiteHandler(this.getActivity());
        try {
            sqLiteHandler.open();

        } catch (Exception e) {
            Log.i("hello", "hello");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view1 = inflater.inflate(R.layout.activity_workout, container, false);
        FloatingActionButton fab = (FloatingActionButton) view1.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Intent i = new Intent(getActivity(), AddWorkout.class);
                startActivity(i);
            }
        });

        workoutItemArrayList = sqLiteHandler.getWorkouts(null);


        if (workoutItemArrayList.size() != 0){
            Log.d("sizen", String.valueOf(workoutItemArrayList.size()) + String.valueOf(workoutItemArrayList.get(0).exercisesList.size()) );
            workoutsRV = (RecyclerView) view1.findViewById(R.id.expandableListView);
            workoutsRV.setHasFixedSize(true);
            workoutsRV.setLayoutManager(new GridLayoutManager(this.getActivity(), 3, LinearLayoutManager.VERTICAL, false));

            workoutsRV.setItemAnimator(new DefaultItemAnimator());

            WorkoutsAdapter adapter = new WorkoutsAdapter(this.getActivity(), workoutItemArrayList);
            workoutsRV.setAdapter(adapter);

            adapter.setClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int position) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                    LayoutInflater inflater = getActivity().getLayoutInflater();
                    View dialogView = inflater.inflate(R.layout.custom_workout, null);
                    builder.setView(dialogView);



/*
                    final AlertDialog dialog = new AlertDialog.Builder(getContext())
                            .setView(getLayoutInflater(getActivity()).inflate(R.layout.content_workout, null)).create();
                    dialog.setTitle("Workout Information");
                    dialog.setContentView(R.layout.custom_workout);
*/
                    EditText WorkoutName;
                    TextView exercisesSetText;
                    EditText workoutRepeat;
                    int workoutReps = 0;
                    Button AddExercise;
                    ImageButton workoutRepeatBtn;
                    Button SaveWorkout;
                    GridView ExercisesGrid;
                    RecyclerView ExercisesSetGrid = (RecyclerView) dialogView.findViewById(R.id.exercises_set_grid);
                    RecyclerView ExercisesRV;
                    WorkoutExItemAdapter WAdapter;

                    exercisesSetText = (TextView) dialogView.findViewById(R.id.exercises_set_txt);
                    WorkoutName = (EditText) dialogView.findViewById(R.id.workout_name);
                    workoutRepeat = (EditText) dialogView.findViewById(R.id.workoutRepeat);
                    workoutRepeatBtn = (ImageButton) dialogView.findViewById(R.id.workoutRepeatBtn);
                    AddExercise = (Button) dialogView.findViewById(R.id.add_exercise);
                    SaveWorkout = (Button) dialogView.findViewById(R.id.save_btn);

//                    initWorkoutViews(ExercisesSetGrid, workoutItemArrayList.get(position).exercisesList);

                    view.setEnabled(false);
                    WorkoutName.setText(workoutItemArrayList.get(position).workoutName);


                    AddExercise.setVisibility(View.GONE);
                    exercisesSetText.setText("Exercises");

                    SaveWorkout.setVisibility(View.GONE);

                    builder.setNeutralButton("Play Workout", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    AlertDialog alertDialog =  builder.create();
                    alertDialog.show();

                }
            });
        }




        // Inflate the layout for this fragment
        return view1;
    }

    private void initWorkoutViews(RecyclerView ExercisesSetGrid, ArrayList<WorkoutExItem> workoutExItemArrayList){

        // ExercisesSetGrid.setHasFixedSize(true);
        ExercisesSetGrid.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
/*
        workoutItemArrayList = new ArrayList<>();
        workoutItemArrayList.add(new WorkoutItem());
*/

        workoutExItemArrayList.add(new WorkoutExItem());

        WorkoutSQLAdapter WAdapter = new WorkoutSQLAdapter(getActivity(), workoutExItemArrayList);
        ExercisesSetGrid.setAdapter(WAdapter);// set adapter on recyclerview
        WAdapter.notifyDataSetChanged();// Notify the adapter

    }


   /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent i = new Intent(getApplicationContext(), AddWorkout.class);
                startActivity(i);
                finish();
            }

        });
    }
*/
}
