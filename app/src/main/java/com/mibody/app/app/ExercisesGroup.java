package com.mibody.app.app;

import java.util.ArrayList;

/**
 * Created by NakeebMac on 10/1/16.
 */

public class ExercisesGroup {
    private String name;
    public ArrayList<ExercisesGroup> invisibleChildren;
    public int type;
    private ExerciseItem exerciseItem;

    public ExercisesGroup(){

    }
    public ExercisesGroup(int type, String name){
        this.name = name;
        this.type = type;
    }

    public ExercisesGroup(int type, ExerciseItem exerciseItem){
        this.exerciseItem = exerciseItem;
        this.type = type;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
