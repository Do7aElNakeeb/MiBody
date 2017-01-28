package com.mibody.app.activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mibody.app.R;
import com.mibody.app.app.ExerciseItem;
import com.mibody.app.fragments.ExerciseDetails;
import com.mibody.app.helper.ExercisesAdapter;
import com.mibody.app.helper.ItemClickListener;

import java.util.ArrayList;

/**
 * Created by NakeebMac on 10/1/16.
 */

public class Exercises extends Fragment {

    RecyclerView exercisesRV;

    ArrayList<ExerciseItem> arrayList;

    public Exercises() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setArrayList(ArrayList<ExerciseItem> exerciseItemArrayList){
        arrayList = exerciseItemArrayList;
        Log.d("arraySent", String.valueOf(arrayList.size()));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        exercisesRV = (RecyclerView) getView().findViewById(R.id.expandableListView);
        exercisesRV.setHasFixedSize(true);
        exercisesRV.setLayoutManager(new GridLayoutManager(this.getActivity(), 3, LinearLayoutManager.VERTICAL, false));

        exercisesRV.setItemAnimator(new DefaultItemAnimator());


        ExercisesAdapter exercisesAdapter = new ExercisesAdapter(this.getActivity(), arrayList, 1);
        exercisesRV.setAdapter(exercisesAdapter);

        /*
        exercisesAdapter.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {

                ExerciseDetails exerciseDetails = new ExerciseDetails();
                exerciseDetails.setExercise(arrayList.get(position));
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.add(R.id.exercisesFragment, exerciseDetails, "Exercise Details Fragment");
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.exercises_page_fragment, container, false);

    }

}