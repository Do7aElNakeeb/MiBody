package com.mibody.app.app;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by NakeebMac on 10/5/16.
 */

public class WorkoutExItem {

    private ImageView ExerciseImg;
    private Button RGB;
    private Button RestPlus;
    private Button RestMinus;
    private Button AddEx;
    private EditText RestTime;
    private EditText Reps;
    private int RestT;

    public int getRestT(){
        return RestT;
    }

    public void setRestT(int RestT){
        this.RestT = RestT;
    }

    public ImageView getExerciseImg(){
        return ExerciseImg;
    }

    public void setExerciseImg (ImageView ExerciseImg){
        this.ExerciseImg = ExerciseImg;
    }

    public Button getRGB(){
        return RGB;
    }

    public void setRGB (Button RGB){
        this.RGB = RGB;
    }

    public Button getRestPlus(){
        return RestPlus;
    }

    public void setRestPlus (Button RestPlus){
        this.RestPlus = RestPlus;
    }

    public Button getRestMinus(){
        return RestMinus;
    }

    public void setRestMinus (Button RestMinus){
        this.RestMinus = RestMinus;
    }

    public Button getAddEx(){
        return AddEx;
    }

    public void setAddEx (Button AddEx){
        this.AddEx = AddEx;
    }

    public EditText getRestTime(){
        return RestTime;
    }

    public void setRestTime (EditText RestTime){
        this.RestTime = RestTime;
    }

    public EditText getReps(){
        return Reps;
    }

    public void setReps (EditText Reps){
        this.Reps = Reps;
    }
}
