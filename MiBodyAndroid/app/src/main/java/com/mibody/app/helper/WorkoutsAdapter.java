package com.mibody.app.helper;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
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
import com.mibody.app.activity.WorkoutPlayActivity;
import com.mibody.app.app.ExerciseItem;
import com.mibody.app.app.WorkoutExItem;
import com.mibody.app.app.WorkoutItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NakeebMac on 10/6/16.
 */

public class WorkoutsAdapter extends RecyclerView.Adapter<WorkoutsAdapter.ViewHolder> {

    private List<WorkoutItem> arrayList;
    private Context context;

    private ItemClickListener clickListener;


    public WorkoutsAdapter(Context context, List<WorkoutItem> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // This method will inflate the custom layout and return as viewholder
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());

        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(R.layout.workout_rv_item, parent, false);
        final RecyclerViewHolder listHolder = new RecyclerViewHolder(mainGroup);
        return new ViewHolder(mainGroup);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final WorkoutItem model = arrayList.get(position);

        //Bitmap image = BitmapFactory.decodeResource(context.getResources(), model.Image);// This will convert drawbale image into

        // setting title
        holder.workoutName.setText(model.workoutName);
        String workoutExercises = "";
        for (int i =0; i < model.exercisesList.size(); i++) {
            if (i == model.exercisesList.size()-1){
                workoutExercises += model.exercisesList.get(i).name;
            }
            else {
                workoutExercises += model.exercisesList.get(i).name + ", ";
            }
        }
        holder.workoutExercises.setText(workoutExercises);
//        Picasso.with(context).load("").into(holder.workoutImage);
        //holder.imageView.setImageBitmap(image);
/*
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(context);
                dialog.setTitle("WorkoutFragment Information");
                dialog.setContentView(R.layout.custom_workout);

                EditText workoutEditTxt = (EditText) dialog.findViewById(R.id.workout_name);
                EditText workoutRepsET = (EditText) dialog.findViewById(R.id.workoutRepeat);
                Button playWorkoutBtn = (Button) dialog.findViewById(R.id.save_btn);
                playWorkoutBtn.setText("Play WorkoutFragment");
                workoutEditTxt.setText(model.workoutName);
                workoutRepsET.setText(String.valueOf(model.workoutReps));
                workoutEditTxt.setEnabled(false);
                workoutRepsET.setEnabled(false);
                final RecyclerView ExercisesSetGrid = (RecyclerView) dialog.findViewById(R.id.exercises_set_grid);
                // ExercisesSetGrid.setHasFixedSize(true);
                ExercisesSetGrid.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

                WorkoutSQLAdapter WAdapter = new WorkoutSQLAdapter(context, arrayList.get(holder.getAdapterPosition()).exercisesList);

                if (arrayList.get(holder.getAdapterPosition()).exercisesList.size() == 0)
                    Log.d("zeroooo", arrayList.get(holder.getAdapterPosition()).exercisesList.toString() + "-" + holder.getAdapterPosition());
                ExercisesSetGrid.setAdapter(WAdapter);

                playWorkoutBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(context, WorkoutPlayActivity.class);
                        // put extras here
                        i.putExtra("WorkoutItem", arrayList.get(holder.getAdapterPosition()));
                        i.putExtra("weight", "workoutPlay");
                        i.putParcelableArrayListExtra("WorkoutItemExList", arrayList.get(holder.getAdapterPosition()).exercisesList);
                        dialog.dismiss();
                        context.startActivity(i);

                    }
                });

                dialog.show();

            }
        });
*/
    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CardView workoutCV;
        ImageView workoutImage;
        TextView workoutName;
        TextView workoutExercises;

        public ViewHolder(View view) {
            super(view);
            /*
            workoutCV = (CardView) view.findViewById(R.id.workoutsItemCV);
            workoutImage = (ImageView) view.findViewById(R.id.workoutsItemImage);
            */
            workoutName = (TextView) view.findViewById(R.id.workoutsItemName);
            workoutExercises = (TextView) view.findViewById(R.id.workoutsItemExercises);
        }
    }
}
