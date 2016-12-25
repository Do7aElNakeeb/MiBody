package com.mibody.app.app;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.mibody.app.helper.SQLiteHandler;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by NakeebMac on 10/5/16.
 */

public class WorkoutExItem {

    public String name;
    public String exerciseImage;
    public String ropes;
    public int reps;
    public int restTime;
    public int exReps;

    public WorkoutExItem(String name, String exerciseImage, String ropes, int reps, int restTime, int exReps) {
        this.name = name;
        this.exerciseImage = exerciseImage;
        this.ropes = ropes;
        this.reps = reps;
        this.restTime = restTime;
        this.exReps = exReps;
    }

    public WorkoutExItem(){

    }

    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("name", name);
            obj.put("image", exerciseImage);
            obj.put("ropes", ropes);
            obj.put("reps", reps);
            obj.put("restTime", restTime);
            obj.put("exReps", exReps);
        } catch (JSONException e) {
            Log.d( "JSONObject", "DefaultListItem.toString JSONException: "+e.getMessage());
        }
        return obj;
    }

}
