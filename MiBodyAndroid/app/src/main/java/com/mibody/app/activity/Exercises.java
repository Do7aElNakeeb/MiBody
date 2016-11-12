package com.mibody.app.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.innodroid.expandablerecycler.ExpandableRecyclerAdapter;
import com.mibody.app.R;
import com.mibody.app.app.ExerciseItem;
import com.mibody.app.app.ExercisesGroup;
import com.mibody.app.app.WorkoutItem;
import com.mibody.app.helper.ExercisesItemAdapter;
import com.mibody.app.helper.ItemClickListener;
import com.mibody.app.helper.RecyclerViewAdapter;
import com.mibody.app.helper.WorkoutExItemAdapter;

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

    int Images[] = { R.drawable.ex1, R.drawable.ex2,
            R.drawable.ex3, R.drawable.ex4, R.drawable.ex5,
            R.drawable.ex6, R.drawable.ex7, R.drawable.ex8,
            R.drawable.ex9, R.drawable.ex10, R.drawable.ex11 };

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
        exercisesRV.setHasFixedSize(true);
        exercisesRV.setLayoutManager(new GridLayoutManager(this.getActivity(), 3, LinearLayoutManager.VERTICAL, false));

        exercisesRV.setItemAnimator(new DefaultItemAnimator());
        ArrayList<ExerciseItem> arrayList = new ArrayList<>();
        for (int i = 0; i < exercises_names.length; i++) {
            arrayList.add(new ExerciseItem(Images[i], exercises_names[i]));
        }

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this.getActivity(), arrayList);
        exercisesRV.setAdapter(adapter);

        adapter.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                LayoutInflater inflater = getActivity().getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.exercise_details, null);
                builder.setView(dialogView);



/*
                    final AlertDialog dialog = new AlertDialog.Builder(getContext())
                            .setView(getLayoutInflater(getActivity()).inflate(R.layout.content_workout, null)).create();
                    dialog.setTitle("Workout Information");
                    dialog.setContentView(R.layout.custom_workout);
*/

                TextView exerciseName;
                TextView exerciseDescription;
                ImageView exerciseImg;



                exerciseName = (TextView) dialogView.findViewById(R.id.exName);
                exerciseImg = (ImageView) dialogView.findViewById(R.id.exImg);
                exerciseDescription = (TextView) dialogView.findViewById(R.id.exDescription);

                exerciseName.setText(exercises_names[position]);
                exerciseImg.setImageResource(Images[position]);
                exerciseDescription.setText(country_names[position]);


                AlertDialog alertDialog =  builder.create();
                alertDialog.show();
            }
        });
/*
        ExercisesItemAdapter adapter = new ExercisesItemAdapter(this.getActivity());
        adapter.setMode(ExpandableRecyclerAdapter.MODE_ACCORDION);
        exercisesRV.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        exercisesRV.setAdapter(adapter);
        */
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