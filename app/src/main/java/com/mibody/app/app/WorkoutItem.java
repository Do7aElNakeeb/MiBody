package com.mibody.app.app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nakeebimac on 10/18/16.
 */

public class WorkoutItem implements Serializable {

    public String workoutName;
    public ArrayList<WorkoutExItem> exercisesList;
    public int workoutReps;
    public int Image;
    public String exercisesJSON;

    public WorkoutItem(String workoutName, ArrayList<WorkoutExItem> exercisesList, int workoutReps){
        this.workoutName = workoutName;
        this.exercisesList = exercisesList;
        this.workoutReps = workoutReps;
    }
    public WorkoutItem(){

    }
}