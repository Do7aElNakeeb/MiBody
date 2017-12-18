package ch.philopateer.mibody.object;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by NakeebMac on 10/5/16.
 */

public class WorkoutExItem {

    public String name = "Drop here";
    public String exerciseImage;
    public String rope1 = "N";
    public String rope2 = "N";
    public String rope3 = "N";
    public int reps = 1;
    public int actualReps = 0;
    public int restTime = 5;
    public int exReps = 1;
    public long exTime = 0;
    public long actualExTime = 0;
    public boolean repsTimeBool = false;

    public WorkoutExItem(String name, String exerciseImage, String rope1, String rope2, String rope3, int reps, int restTime, int exReps, long exTime, boolean repsTimeBool) {
        this.name = name;
        this.exerciseImage = exerciseImage;
        this.rope1 = rope1;
        this.rope2 = rope2;
        this.rope3 = rope3;
        this.reps = reps;
        this.restTime = restTime;
        this.exReps = exReps;
        this.exTime = exTime;
        this.repsTimeBool = repsTimeBool;
    }

    public WorkoutExItem(String name, String exerciseImage, String rope1, String rope2, String rope3, int reps, int actualReps, int restTime, int exReps, long exTime, long actualExTime, boolean repsTimeBool) {
        this.name = name;
        this.exerciseImage = exerciseImage;
        this.rope1 = rope1;
        this.rope2 = rope2;
        this.rope3 = rope3;
        this.reps = reps;
        this.actualReps = actualReps;
        this.restTime = restTime;
        this.exReps = exReps;
        this.exTime = exTime;
        this.actualExTime = actualExTime;
        this.repsTimeBool = repsTimeBool;
    }

    public WorkoutExItem(){

    }

    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("name", name);
            obj.put("image", exerciseImage);
            obj.put("rope1", rope1);
            obj.put("rope2", rope2);
            obj.put("rope3", rope3);
            obj.put("reps", String.valueOf(reps));
            obj.put("actualReps", String.valueOf(actualReps));
            obj.put("restTime", String.valueOf(restTime));
            obj.put("exReps", String.valueOf(exReps));
            obj.put("exTime", String.valueOf(exTime));
            obj.put("actualExTime", String.valueOf(actualExTime));
            obj.put("repsTimeBool", String.valueOf(repsTimeBool));
        } catch (JSONException e) {
            Log.d( "JSONObject", "DefaultListItem.toString JSONException: "+e.getMessage());
        }
        return obj;
    }

}
