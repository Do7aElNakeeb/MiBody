package com.mibody.app.app;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nakeebimac on 10/18/16.
 */

public class WorkoutItem {

    public String workoutName;
    public List<WorkoutExItem> exercisesList;
    public int workoutReps;
    public int Image;
    public String exercisesJSON;

    public WorkoutItem(String workoutName, List<WorkoutExItem> exercisesList, int workoutReps){
        this.workoutName = workoutName;
        this.exercisesList = exercisesList;
        this.workoutReps = workoutReps;
    }
    public WorkoutItem(){

    }
}