package com.mibody.app.helper;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.mibody.app.R;

/**
 * Created by NakeebMac on 10/11/16.
 */

public class AddWorkoutRecyclerViewHolder extends RecyclerView.ViewHolder {

    public ImageView ExerciseImg;
    public Button RGB;
    public Button RestPlus;
    public Button RestMinus;
    public Button AddEx;
    public EditText RestTime;
    public EditText Reps;
    public Button AddExercise;
    public Button RemoveExercise;
    public ImageButton setRepeatBtn;
    public EditText setRepeat;
    public int RestT;


    public AddWorkoutRecyclerViewHolder(View itemView) {
        super(itemView);
        this.ExerciseImg = (ImageView) itemView.findViewById(R.id.exercise1_img);
        this.RGB = (Button) itemView.findViewById(R.id.rgb_btn);
        this.RestPlus = (Button) itemView.findViewById(R.id.rest1_plus);
        this.RestMinus = (Button) itemView.findViewById(R.id.rest1_minus);
        this.AddEx = (Button) itemView.findViewById(R.id.add_exercise);
        this.RestTime = (EditText) itemView.findViewById(R.id.rest1_count);
        this.Reps = (EditText) itemView.findViewById(R.id.reps1_count);
        this.RemoveExercise = (Button) itemView.findViewById(R.id.remove_exercise);
        this.setRepeat = (EditText) itemView.findViewById(R.id.setRepeat);
        this.setRepeatBtn = (ImageButton) itemView.findViewById(R.id.setRepeatBtn);

    }




}
