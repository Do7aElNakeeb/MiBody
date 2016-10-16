package com.mibody.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;

import com.mibody.app.R;
import com.mibody.app.app.ExerciseItem;
import com.mibody.app.app.ExercisesGroup;
import com.mibody.app.helper.ExercisesItemAdapter;
import com.mibody.app.helper.GIFView;

import java.util.ArrayList;

/**
 * Created by NakeebMac on 10/1/16.
 */

public class Exercises extends AppCompatActivity {

    ExercisesItemAdapter ExpAdapter;
    ArrayList<ExercisesGroup> ExpListItems;
    ExpandableListView ExpandList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        ExpandList = (ExpandableListView) findViewById(R.id.expandableListView);
        ExpListItems = SetStandardGroups();
        ExpAdapter = new ExercisesItemAdapter(Exercises.this, ExpListItems);
        ExpandList.setAdapter(ExpAdapter);

    }

    public ArrayList<ExercisesGroup> SetStandardGroups() {

        String exercises_names[] = { "Exercise A", "Exercise B", "Exercise C", "Exercise D",
                "Exercise E", "Exercise F", "Exercise G", "Exercise H", "Exercise I", "Exercise J", "Exercise K" };

        String country_names[] = { "Brazil", "Mexico", "Croatia", "Cameroon",
                "Netherlands", "chile", "Spain", "Australia", "Colombia",
                "Greece", "Ivory Coast", "Japan", "Costa Rica", "Uruguay",
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
/*
        for (String exercise_name : exercises_names) {
            ExercisesGroup gru = new ExercisesGroup();
            gru.setName(exercise_name);

            ch_list = new ArrayList<ExerciseItem>();
            if (j<size){
                ExerciseItem ch = new ExerciseItem();
                ch.getDescription().setText(country_names[j]);
                ch.getImage().setImageResource(Images[j]);
                ch_list.add(ch);
                j++;
            }

            gru.setItems(ch_list);
            list.add(gru);

      //      size = size + 4;
        }
*/
        return list;
    }
}
