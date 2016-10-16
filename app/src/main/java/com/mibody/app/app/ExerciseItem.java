package com.mibody.app.app;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by NakeebMac on 10/1/16.
 */

public class ExerciseItem {

    private int Image;
    private TextView Description;
    private String name;

    public ExerciseItem(int image, String title) {
        super();
        this.Image = image;
        this.name = title;
    }
    public ExerciseItem(){

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

    public TextView getDescription() {
        return Description;
    }

    public void setDescription(TextView Description) {
        this.Description = Description;
    }
}
