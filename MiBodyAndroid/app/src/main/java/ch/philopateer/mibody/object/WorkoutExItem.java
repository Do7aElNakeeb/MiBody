package ch.philopateer.mibody.object;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by NakeebMac on 10/5/16.
 */

public class WorkoutExItem {

    public String name;
    public String exerciseImage;
    public String rope1 = "B";
    public String rope2 = "B";
    public String rope3 = "B";
    public int reps;
    public int restTime;
    public int exReps;

    public WorkoutExItem(String name, String exerciseImage, String rope1, String rope2, String rope3, int reps, int restTime, int exReps) {
        this.name = name;
        this.exerciseImage = exerciseImage;
        this.rope1 = rope1;
        this.rope2 = rope2;
        this.rope3 = rope3;
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
            obj.put("rope1", rope1);
            obj.put("rope2", rope2);
            obj.put("rope3", rope3);
            obj.put("reps", String.valueOf(reps));
            obj.put("restTime", String.valueOf(restTime));
            obj.put("exReps", String.valueOf(exReps));
        } catch (JSONException e) {
            Log.d( "JSONObject", "DefaultListItem.toString JSONException: "+e.getMessage());
        }
        return obj;
    }

}
