package com.mibody.app.app;

import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by NakeebMac on 10/1/16.
 */

public class ExerciseItem {

    private int Image;
    private String Description;
    private String name;
    //public int type;
    //public List<ExerciseItem> invisibleChildren;

    public ExerciseItem(){

    }

    public ExerciseItem(int image,String name, String Description) {
        this.Image = image;
        this.name = name;
        this.Description = Description;
    }

    public ExerciseItem(int image, String name) {
        this.Image = image;
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setName (String name){
        this.name = name;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int Image) {
        this.Image = Image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }
}
