package com.mibody.app.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mibody.app.R;
import com.mibody.app.app.ExerciseItem;
import com.mibody.app.helper.PlayGifView;

/**
 * Created by NakeebMac on 10/13/16.
 */

public class ExerciseDetails extends Fragment {

    ExerciseItem exerciseItem;

    public ExerciseDetails() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.exercise_details, null);

        TextView exerciseName;
        TextView exerciseDescription;
        ImageView exerciseImg;
        PlayGifView playGifView;

        playGifView = (PlayGifView) view.findViewById(R.id.viewGif);

        exerciseName = (TextView) view.findViewById(R.id.exName);
        exerciseImg = (ImageView) view.findViewById(R.id.exImg);
        exerciseDescription = (TextView) view.findViewById(R.id.exDescription);

        playGifView.setImageResource(R.drawable.seven_seg);
        exerciseName.setText(exerciseItem.getName());
        exerciseImg.setImageResource(exerciseItem.getImage());
        exerciseDescription.setText(exerciseItem.getDescription());


        return view;
    }

    public void setExercise(ExerciseItem exerciseItem){
        this.exerciseItem = exerciseItem;
    }
}
