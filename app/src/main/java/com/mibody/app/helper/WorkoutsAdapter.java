package com.mibody.app.helper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mibody.app.R;
import com.mibody.app.activity.AddWorkout;
import com.mibody.app.activity.BTDeviceList;
import com.mibody.app.activity.WorkoutPlay;
import com.mibody.app.app.ExerciseItem;
import com.mibody.app.app.WorkoutExItem;
import com.mibody.app.app.WorkoutItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NakeebMac on 10/6/16.
 */

public class WorkoutsAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private List<WorkoutItem> arrayList;
    private Context context;

    private ItemClickListener clickListener;


    public WorkoutsAdapter(Context context, List<WorkoutItem> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // This method will inflate the custom layout and return as viewholder
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());

        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(
                R.layout.exercises_item, parent, false);
        final RecyclerViewHolder listHolder = new RecyclerViewHolder(mainGroup);
        return listHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        final WorkoutItem model = arrayList.get(position);

        Bitmap image = BitmapFactory.decodeResource(context.getResources(), model.Image);// This will convert drawbale image into

        // setting title
        holder.title.setText(model.workoutName);

        holder.imageView.setImageBitmap(image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(context);
                dialog.setTitle("Workout Information");
                dialog.setContentView(R.layout.custom_workout);

                EditText workoutEditTxt = (EditText) dialog.findViewById(R.id.workout_name);
                EditText workoutRepsET = (EditText) dialog.findViewById(R.id.workoutRepeat);
                Button playWorkoutBtn = (Button) dialog.findViewById(R.id.save_btn);
                playWorkoutBtn.setText("Play Workout");
                workoutEditTxt.setText(model.workoutName);
                workoutRepsET.setText(String.valueOf(model.workoutReps));
                workoutEditTxt.setEnabled(false);
                workoutRepsET.setEnabled(false);
                final RecyclerView ExercisesSetGrid = (RecyclerView) dialog.findViewById(R.id.exercises_set_grid);
                // ExercisesSetGrid.setHasFixedSize(true);
                ExercisesSetGrid.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

                WorkoutSQLAdapter WAdapter = new WorkoutSQLAdapter(context, arrayList.get(position).exercisesList);

                if (arrayList.get(position).exercisesList.size() == 0)
                    Log.d("zeroooo", arrayList.get(position).exercisesList.toString() + "-" + position);
                ExercisesSetGrid.setAdapter(WAdapter);

                playWorkoutBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(context, BTDeviceList.class);
                        // put extras here

                        context.startActivity(i);
                    }
                });

                dialog.show();


/*
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                LayoutInflater inflater = LayoutInflater.from(context);
                View dialogView = inflater.inflate(R.layout.custom_workout, null);
                builder.setView(dialogView);
                builder.set

                EditText WorkoutName;
                TextView exercisesSetText;
                EditText workoutRepeat;
                int workoutReps = 0;
                Button AddExercise;
                ImageButton workoutRepeatBtn;
                Button SaveWorkout;
                GridView ExercisesGrid;
                RecyclerView ExercisesSetGrid = (RecyclerView) dialogView.findViewById(R.id.exercises_set_grid);

                exercisesSetText = (TextView) dialogView.findViewById(R.id.exercises_set_txt);
                WorkoutName = (EditText) dialogView.findViewById(R.id.workout_name);
                workoutRepeat = (EditText) dialogView.findViewById(R.id.workoutRepeat);
                workoutRepeatBtn = (ImageButton) dialogView.findViewById(R.id.workoutRepeatBtn);
                AddExercise = (Button) dialogView.findViewById(R.id.add_exercise);
                SaveWorkout = (Button) dialogView.findViewById(R.id.save_btn);

                ExercisesSetGrid.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

         //       arrayList.get(position).exercisesList.add(new WorkoutExItem());

                WorkoutSQLAdapter WAdapter = new WorkoutSQLAdapter(context, arrayList.get(position).exercisesList);
                ExercisesSetGrid.setAdapter(WAdapter);// set adapter on recyclerview
                WAdapter.notifyDataSetChanged();// Notify the adapter

               // initWorkoutViews(ExercisesSetGrid, arrayList.get(position).exercisesList);

                v.setEnabled(false);
                WorkoutName.setText(arrayList.get(position).workoutName);


                AddExercise.setVisibility(View.GONE);
                exercisesSetText.setText("Exercises");

                SaveWorkout.setVisibility(View.GONE);

                final Dialog alertDialog =  builder.create();

                builder.setNeutralButton("Play Workout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();
*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }
}
