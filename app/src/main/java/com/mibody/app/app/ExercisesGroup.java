package com.mibody.app.app;

import java.util.ArrayList;

/**
 * Created by NakeebMac on 10/1/16.
 */

public class ExercisesGroup {
    private String Name;
    private ArrayList<ExerciseItem> Items;

    public String getName() {
        return Name;
    }
    public void setName(String name) {
        this.Name = name;
    }
    public ArrayList<ExerciseItem> getItems() {
        return Items;
    }
    public void setItems(ArrayList<ExerciseItem> Items) {
        this.Items = Items;
    }
}
