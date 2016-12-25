package com.mibody.app.app;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.mibody.app.helper.WorkoutsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nakeebimac on 10/18/16.
 */

public class WorkoutItem implements Parcelable{

    public String workoutID;
    public String workoutName;
    public ArrayList<WorkoutExItem> exercisesList;
    public int workoutReps;
    public String exercisesJSON;
    public String workoutType;

    public WorkoutItem(String workoutName, ArrayList<WorkoutExItem> exercisesList, int workoutReps){
        this.workoutName = workoutName;
        this.exercisesList = exercisesList;
        this.workoutReps = workoutReps;
    }

    public WorkoutItem(){

    }

    public WorkoutItem(String workoutID, String workoutName, int workoutReps, String exercisesJSON, String workoutType){
        this.workoutID = workoutID;
        this.workoutName = workoutName;
        this.workoutReps = workoutReps;
        this.exercisesJSON = exercisesJSON;
        this.workoutType = workoutType;

        try {
            JSONArray resultsArr = new JSONArray(exercisesJSON);
            System.out.println(resultsArr.length());
            // If no of array elements is not zero
            if(resultsArr.length() != 0){

                Log.d("resultsArray", resultsArr.toString());
                // Loop through each array element, get JSON object
                for (int i = 0; i < resultsArr.length(); i++) {
                    // Get JSON object
                    JSONObject obj = (JSONObject) resultsArr.get(i);

                    // DB QueryValues Object to insert into Movies ArrayList
                    String name = obj.get("name").toString();
                    String image = obj.get("image").toString();
                    String ropes = obj.get("ropes").toString();
                    int reps = obj.getInt("reps");
                    int restTime = obj.getInt("restTime");
                    int exReps = obj.getInt("exReps");

                    exercisesList.add(new WorkoutExItem(name, image, ropes, reps, restTime, exReps));

                }

            }
        }
        catch (JSONException e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    protected WorkoutItem(Parcel in) {
        workoutID = in.readString();
        workoutName = in.readString();
        workoutReps = in.readInt();
        exercisesJSON = in.readString();
        workoutType = in.readString();
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
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(workoutID);
        parcel.writeString(workoutName);
        parcel.writeInt(workoutReps);
        parcel.writeString(exercisesJSON);
        parcel.writeString(workoutType);
    }
}