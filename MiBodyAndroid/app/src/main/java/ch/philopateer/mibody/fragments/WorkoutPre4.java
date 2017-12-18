package ch.philopateer.mibody.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;

import ch.philopateer.mibody.R;
import ch.philopateer.mibody.app.AppConfig;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by mamdouhelnakeeb on 8/23/17.
 */

public class WorkoutPre4 extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.workouts_predefined_sel_workout, container, false);

        GifImageView mGigImageView = (GifImageView) view.findViewById(R.id.exGIF);

        GifDrawable gifDrawable = null;
        try {
            gifDrawable = new GifDrawable(getResources(), AppConfig.exercises_gifs[1]);

        } catch (IOException e) {
            e.printStackTrace();
        }
        mGigImageView.setImageDrawable(gifDrawable);

        return view;
    }
}
