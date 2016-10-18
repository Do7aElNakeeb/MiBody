package com.mibody.app.app;

import java.util.ArrayList;

/**
 * Created by nakeebimac on 10/18/16.
 */

public class WorkoutItem {

    public String workoutName;
    public ArrayList<WorkoutExItem> exercisesList;
    public int workoutReps;

    public WorkoutItem(String workoutName, ArrayList<WorkoutExItem> exercisesList, int workoutReps){
        this.workoutName = workoutName;
        this.exercisesList = exercisesList;
        this.workoutReps = workoutReps;
    }
    public WorkoutItem(){

    }
}
