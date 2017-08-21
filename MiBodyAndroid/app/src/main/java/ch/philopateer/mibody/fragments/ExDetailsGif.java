package ch.philopateer.mibody.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.Arrays;

import ch.philopateer.mibody.R;
import ch.philopateer.mibody.app.AppConfig;
import ch.philopateer.mibody.object.ExerciseItem;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by mamdouhelnakeeb on 8/1/17.
 */

public class ExDetailsGif extends Fragment {

    public String exName;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.exercise_details_gif_fragment, null);

        GifImageView mGigImageView = (GifImageView) view.findViewById(R.id.exGIF2);

        GifDrawable gifDrawable = null;
        try {
            gifDrawable = new GifDrawable(getResources(), AppConfig.exercises_gifs[Arrays.asList(AppConfig.exercises_names).indexOf(exName)]);

        } catch (IOException e) {
            e.printStackTrace();
        }
        mGigImageView.setImageDrawable(gifDrawable);


        return view;
    }
}
