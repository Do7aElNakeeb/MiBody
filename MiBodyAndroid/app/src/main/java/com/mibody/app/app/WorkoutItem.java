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

public class WorkoutItem implements Parcelable {

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
                    int RestT = obj.getInt("workoutName");
                    String name = obj.get("workoutName").toString();
                    int RepsT = obj.getInt("workoutName");
                    String rgb = obj.get("workoutName").toString();
                    int setReps = obj.getInt("workoutName");

                    exercisesList.add(new WorkoutExItem(name, RestT, RepsT, rgb, setReps));

                }

            }
        }
        catch (JSONException e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private WorkoutItem(Parcel in) {
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
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(workoutID);
        dest.writeString(workoutName);
        dest.writeInt(workoutReps);
        dest.writeString(exercisesJSON);
        dest.writeString(workoutType);
    }
}