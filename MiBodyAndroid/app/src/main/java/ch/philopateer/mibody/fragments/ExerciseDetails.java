package ch.philopateer.mibody.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import ch.philopateer.mibody.R;
import ch.philopateer.mibody.app.AppConfig;
import ch.philopateer.mibody.object.ExerciseItem;

import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.util.Arrays;

/**
 * Created by NakeebMac on 10/13/16.
 */

public class ExerciseDetails extends Fragment {

    ExerciseItem exerciseItem;
    int position;

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

        if (exerciseItem.getGIF().isEmpty()){
            Glide.with(getActivity()).load(AppConfig.exercises_gifs[Arrays.asList(AppConfig.exercises_names).indexOf(exerciseItem.getName())]).asGif()
                    .crossFade().into(exerciseGIF);
        }
        else {
            Glide.with(getActivity()).load(AppConfig.URL_SERVER + "ExGif/" + exerciseItem.getGIF()).asGif()
                    .crossFade().into(exerciseGIF);
        }

        new Picasso.Builder(getActivity())
                .downloader(new OkHttpDownloader(getActivity(), Integer.MAX_VALUE))
                .build()
                .load(AppConfig.URL_SERVER + "ExImage/" + exerciseItem.getImage())
                .placeholder(AppConfig.exercises_imgs[Arrays.asList(AppConfig.exercises_names).indexOf(exerciseItem.getName())]).into(exerciseImg);


        exerciseName.setText(exerciseItem.getName());
        exerciseDescription.setText(AppConfig.exercises_discreptions[Arrays.asList(AppConfig.exercises_names).indexOf(exerciseItem.getName())]);


        return view;
    }

    public void setExercise(ExerciseItem exerciseItem, int position){
        this.exerciseItem = exerciseItem;
        this.position = position;
    }
}
