package com.mibody.app.app;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nakeebimac on 10/18/16.
 */

public class WorkoutItem implements Parcelable {

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

    private WorkoutItem(Parcel in) {
        workoutName = in.readString();
        workoutReps = in.readInt();
        Image = in.readInt();
        exercisesJSON = in.readString();
    }

    public static final Creator<WorkoutItem> CREATOR = new Creator<WorkoutItem>() {
        @Override
        public WorkoutItem createFromParcel(Parcel in) {
            return new WorkoutItem(in);
        }

        @Override
        public WorkoutItem[] newArray(int size) {
            return new WorkoutItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(workoutName);
        dest.writeInt(workoutReps);
        dest.writeInt(Image);
        dest.writeString(exercisesJSON);
    }
}