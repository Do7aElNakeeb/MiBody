package com.mibody.app.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mibody.app.R;
import com.mibody.app.app.AppConfig;
import com.mibody.app.app.ExerciseItem;
import com.mibody.app.helper.PlayGifView;
import com.squareup.picasso.Picasso;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.exercise_details, null);

        TextView exerciseName;
        TextView exerciseDescription;
        ImageView exerciseImg;
        ImageView exerciseGIF;

        exerciseGIF = (ImageView) view.findViewById(R.id.exGIF);

        exerciseName = (TextView) view.findViewById(R.id.exName);
        exerciseImg = (ImageView) view.findViewById(R.id.exImg);
        exerciseDescription = (TextView) view.findViewById(R.id.exDescription);

        Glide.with(getActivity()).load(AppConfig.URL_SERVER + "ExGif/" + exerciseItem.getGIF()).asGif().into(exerciseGIF);
        Glide.with(getActivity()).load(AppConfig.URL_SERVER + "ExImage/" + exerciseItem.getImage()).into(exerciseImg);

        exerciseName.setText(exerciseItem.getName());
        exerciseDescription.setText(exerciseItem.getDescription());


        return view;
    }

    public void setExercise(ExerciseItem exerciseItem){
        this.exerciseItem = exerciseItem;
    }
}
