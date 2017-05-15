package ch.philopateer.mibody.fragments;

import android.graphics.Point;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.philopateer.mibody.R;
import ch.philopateer.mibody.object.ExerciseItem;
import ch.philopateer.mibody.helper.ExercisesAdapter;

import java.util.ArrayList;

/**
 * Created by NakeebMac on 10/1/16.
 */

public class Exercises extends Fragment {

    RecyclerView exercisesRV;

    ArrayList<ExerciseItem> arrayList;
    int fragmentHeight;

    public Exercises() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setArrayList(ArrayList<ExerciseItem> exerciseItemArrayList, int fragmentHeight){
        arrayList = exerciseItemArrayList;
        this.fragmentHeight = fragmentHeight;
        Log.d("arraySent", String.valueOf(arrayList.size()));
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        exercisesRV.setHasFixedSize(true);
        exercisesRV.setLayoutManager(new GridLayoutManager(this.getActivity(), 3));

        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);


        ExercisesAdapter exercisesAdapter = new ExercisesAdapter(this.getActivity(), fragmentHeight, arrayList, 1, new ExercisesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ExerciseItem exerciseItem, int position) {
                ExerciseDetails exerciseDetails = new ExerciseDetails();
                exerciseDetails.setExercise(exerciseItem, position);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(ch.philopateer.mibody.R.id.exercisesFragment, exerciseDetails, "Exercise Details Fragment");
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        exercisesRV.setAdapter(exercisesAdapter);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(ch.philopateer.mibody.R.layout.exercises_page_fragment, container, false);

        exercisesRV = (RecyclerView) view.findViewById(R.id.expandableListView);

        return view;
    }

}