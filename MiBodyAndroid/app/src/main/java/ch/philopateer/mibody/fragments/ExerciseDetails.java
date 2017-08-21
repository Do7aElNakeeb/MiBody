package ch.philopateer.mibody.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import ch.philopateer.mibody.R;
import ch.philopateer.mibody.app.AppConfig;
import ch.philopateer.mibody.helper.ViewPagerAdapter;
import ch.philopateer.mibody.object.ExerciseItem;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by NakeebMac on 10/13/16.
 */

public class ExerciseDetails extends AppCompatActivity {

    ExerciseItem exerciseItem;
    int position;

    ViewPager viewPager;
    TabLayout tabLayout;

    /*
    public ExerciseDetails() {
        // Required empty public constructor
    }
    */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_details);

        Bundle bundle = getIntent().getExtras();

        this.exerciseItem = (ExerciseItem) bundle.getParcelable("exItem");

        TextView exerciseName;
        ImageView exerciseImg;

        viewPager = (ViewPager) findViewById(R.id.exDetailsVP);
        tabLayout = (TabLayout) findViewById(R.id.exDetailsTL);

        setupViewPager(viewPager);


        exerciseName = (TextView) findViewById(R.id.exName);
        exerciseImg = (ImageView) findViewById(R.id.exImg);

        new Picasso.Builder(this)
                .downloader(new OkHttpDownloader(this, Integer.MAX_VALUE))
                .build()
                .load(AppConfig.URL_SERVER + "ExImage/" + exerciseItem.getImage())
                .placeholder(AppConfig.exercises_imgs[Arrays.asList(AppConfig.exercises_names).indexOf(exerciseItem.getName())]).into(exerciseImg);


        exerciseName.setText(exerciseItem.getName());

    }
    /*

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.exercise_details, null);

        TextView exerciseName;
        TextView exerciseDescription;
        ImageView exerciseImg;
        ImageView exerciseGIF;

        viewPager = (ViewPager) view.findViewById(R.id.exDetailsVP);
        tabLayout = (TabLayout) view.findViewById(R.id.exDetailsTL);

        setupViewPager(viewPager);

        exerciseGIF = (ImageView) view.findViewById(R.id.exGIF);

        exerciseName = (TextView) view.findViewById(R.id.exName);
        exerciseImg = (ImageView) view.findViewById(R.id.exImg);
        exerciseDescription = (TextView) view.findViewById(R.id.exDescription);

/*
        GifImageView mGigImageView = (GifImageView) view.findViewById(R.id.exGIF2);

        GifDrawable gifDrawable = null;
        try {
            gifDrawable = new GifDrawable(getResources(), AppConfig.exercises_gifs[Arrays.asList(AppConfig.exercises_names).indexOf(exerciseItem.getName())]);

        } catch (IOException e) {
            e.printStackTrace();
        }
        mGigImageView.setImageDrawable(gifDrawable);
*/

        /*
        if (exerciseItem.getGIF().isEmpty()){
            Glide.with(getActivity()).load(AppConfig.exercises_gifs[Arrays.asList(AppConfig.exercises_names).indexOf(exerciseItem.getName())]).asGif()
                    .crossFade().into(exerciseGIF);
        }
        else {
            Glide.with(getActivity()).load(AppConfig.URL_SERVER + "ExGif/" + exerciseItem.getGIF()).asGif()
                    .crossFade().into(exerciseGIF);
        }
*/
        /*
        new Picasso.Builder(getActivity())
                .downloader(new OkHttpDownloader(getActivity(), Integer.MAX_VALUE))
                .build()
                .load(AppConfig.URL_SERVER + "ExImage/" + exerciseItem.getImage())
                .placeholder(AppConfig.exercises_imgs[Arrays.asList(AppConfig.exercises_names).indexOf(exerciseItem.getName())]).into(exerciseImg);


        exerciseName.setText(exerciseItem.getName());
        //exerciseDescription.setText(AppConfig.exercises_discreptions[Arrays.asList(AppConfig.exercises_names).indexOf(exerciseItem.getName())]);

        return view;
    }
*/
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        ExDetailsGif exDetailsGif = new ExDetailsGif();
        exDetailsGif.exName = exerciseItem.getName();

        ExDetailsDesc exDetailsDesc = new ExDetailsDesc();
        exDetailsDesc.exDesc = AppConfig.exercises_discreptions[Arrays.asList(AppConfig.exercises_names).indexOf(exerciseItem.getName())];

        adapter.addFragment(exDetailsGif, "Exercise");
        adapter.addFragment(exDetailsDesc, "Description");

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);


    }

    public void setExercise(ExerciseItem exerciseItem, int position){
        this.exerciseItem = exerciseItem;
        this.position = position;
    }

}
