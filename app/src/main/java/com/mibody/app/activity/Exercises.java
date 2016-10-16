package com.mibody.app.activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.innodroid.expandablerecycler.ExpandableRecyclerAdapter;
import com.mibody.app.R;
import com.mibody.app.app.ExerciseItem;
import com.mibody.app.app.ExercisesGroup;
import com.mibody.app.helper.ExercisesItemAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NakeebMac on 10/1/16.
 */

public class Exercises extends Fragment {

    ExercisesItemAdapter ExpAdapter;
    ArrayList<ExercisesGroup> ExpListItems;
    ExpandableListView ExpandList;
    RecyclerView exercisesRV;

    String exercises_names[] = { "Exercise A", "Exercise B", "Exercise C", "Exercise D",
            "Exercise E", "Exercise F", "Exercise G", "Exercise H", "Exercise I", "Exercise J", "Exercise K" };

    String country_names[] = { "Description A", "Description B", "Description C", "Description D",
            "Description E", "Description F", "Description G", "Description H", "Description I",
            "Description J", "Description K", "Japan", "Costa Rica", "Uruguay",
            "Italy", "England", "France", "Switzerland", "Ecuador",
            "Honduras", "Agrentina", "Nigeria", "Bosnia and Herzegovina",
            "Iran", "Germany", "United States", "Portugal", "Ghana",
            "Belgium", "Algeria", "Russia", "Korea Republic" };

    int Images[] = { R.drawable.gym1, R.drawable.gym2,
            R.drawable.gym1, R.drawable.gym2, R.drawable.gym1,
            R.drawable.gym2, R.drawable.gym1, R.drawable.gym2,
            R.drawable.gym1, R.drawable.gym2, R.drawable.gym1 };

    public Exercises() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_exercises, container, false);
        exercisesRV = (RecyclerView) view.findViewById(R.id.expandableListView);
        ExercisesItemAdapter adapter = new ExercisesItemAdapter(this.getActivity());
        adapter.setMode(ExpandableRecyclerAdapter.MODE_ACCORDION);
        exercisesRV.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        exercisesRV.setAdapter(adapter);
        // Inflate the layout for this fragment
        return view;
    }
 /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

     /*   ExpandList = (ExpandableListView) findViewById(R.id.expandableListView);
        ExpListItems = SetStandardGroups();
        ExpAdapter = new ExercisesItemAdapter(Exercises.this, ExpListItems);
        ExpandList.setAdapter(ExpAdapter);
*/
    /*
        exercisesRV = (RecyclerView) findViewById(R.id.expandableListView);
        ExercisesItemAdapter adapter = new ExercisesItemAdapter(this);
        adapter.setMode(ExpandableRecyclerAdapter.MODE_ACCORDION);
        exercisesRV.setLayoutManager(new LinearLayoutManager(this));
        exercisesRV.setAdapter(adapter);

    }
    */
/*
    public ArrayList<ExercisesGroup> SetStandardGroups() {

        String exercises_names[] = { "Exercise A", "Exercise B", "Exercise C", "Exercise D",
                "Exercise E", "Exercise F", "Exercise G", "Exercise H", "Exercise I", "Exercise J", "Exercise K" };

        String country_names[] = { "Description A", "Description B", "Description C", "Description D",
                "Description E", "Description F", "Description G", "Description H", "Description I",
                "Description J", "Description K", "Japan", "Costa Rica", "Uruguay",
                "Italy", "England", "France", "Switzerland", "Ecuador",
                "Honduras", "Agrentina", "Nigeria", "Bosnia and Herzegovina",
                "Iran", "Germany", "United States", "Portugal", "Ghana",
                "Belgium", "Algeria", "Russia", "Korea Republic" };

        int Images[] = { R.drawable.gym1, R.drawable.gym2,
                R.drawable.gym1, R.drawable.gym2, R.drawable.gym1,
                R.drawable.gym2, R.drawable.gym1, R.drawable.gym2,
                R.drawable.gym1, R.drawable.gym2, R.drawable.gym1 };

        ArrayList<ExercisesGroup> list = new ArrayList<ExercisesGroup>();

        ArrayList<ExerciseItem> ch_list;

        int size = 11;
        int j = 0;

        for (String exercise_name : exercises_names) {
            ExercisesGroup gru = new ExercisesGroup();
            gru.setName(exercise_name);

            ch_list = new ArrayList<ExerciseItem>();
            if (j<size){
                ExerciseItem ch = new ExerciseItem(Images[j], country_names[j]);
                ch.getDescription().setText(country_names[j]);
          //      ch.getImage().setImageResource(Images[j]);
                ch.setImage(Images[j]);
                ch_list.add(ch);
                j++;
            }

            gru.setItems(ch_list);
            list.add(gru);

      //      size = size + 4;
        }

        return list;
    }
    */
}