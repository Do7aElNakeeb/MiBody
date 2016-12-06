package com.mibody.app.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mibody.app.R;
import com.mibody.app.app.AppConfig;
import com.mibody.app.app.AppController;
import com.mibody.app.app.WorkoutItem;
import com.mibody.app.helper.SQLiteHandler;
import com.mibody.app.helper.WorkoutsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mamdouhelnakeeb on 12/6/16.
 */

public class WorkoutsItemsActivity extends AppCompatActivity {

    RecyclerView workoutsRV;
    WorkoutsAdapter workoutsAdapter;
    private static final String TAG = WorkoutsItemsActivity.class.getSimpleName();
    private ProgressDialog pDialog;

    String workoutsType = "";
    ArrayList<WorkoutItem> workoutItemArrayList;
    SQLiteHandler sqLiteHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workouts_items_activity);

        workoutsRV = (RecyclerView) findViewById(R.id.workoutsRV);
        workoutsRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        workoutsRV.setItemAnimator(new DefaultItemAnimator());

        Intent intent = getIntent();
        workoutsType = intent.getStringExtra("type");



    }


    private void loadWorkouts(){

        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Loading Movies ...");
        showDialog();


        StringRequest strReq = new StringRequest(Request.Method.GET, AppConfig.URL_SERVER + "workouts.php",
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "MoviesDB Response: " + response);
                        hideDialog();

                        workoutItemArrayList = new ArrayList<WorkoutItem>();

                        try {
                            // Extract JSON array from the response
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray resultsArr = jsonObject.getJSONArray("results");
                            System.out.println(jsonObject.length());
                            // If no of array elements is not zero
                            if(resultsArr.length() != 0){

                                Log.d("resultsArray", resultsArr.toString());
                                // Loop through each array element, get JSON object
                                for (int i = 0; i < resultsArr.length(); i++) {
                                    // Get JSON object
                                    JSONObject obj = (JSONObject) resultsArr.get(i);

                                    // DB QueryValues Object to insert into Movies ArrayList
                                    String id = obj.get("id").toString();
                                    String name = obj.get("original_title").toString();
                                    String description = obj.get("overview").toString();
                                    String rating = obj.get("vote_average").toString();
                                    String image = obj.get("poster_path").toString();
                                    String release_date = obj.get("release_date").toString().substring(0, 4);

                                    workoutItemArrayList.add(new WorkoutItem());

                                }

                                workoutsAdapter = new WorkoutsAdapter(getApplicationContext(), workoutItemArrayList);
                                workoutsRV.setAdapter(workoutsAdapter);

                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());

                workoutItemArrayList = sqLiteHandler.getWorkouts(null);


                if (workoutItemArrayList.size() != 0) {
                    Log.d("sizen", String.valueOf(workoutItemArrayList.size()) + String.valueOf(workoutItemArrayList.get(0).exercisesList.size()));



                    workoutsAdapter = new WorkoutsAdapter(getApplicationContext(), workoutItemArrayList);
                    workoutsRV.setAdapter(workoutsAdapter);
                }
                else {
                    Toast.makeText(getApplicationContext(), "There is no personalised workouts", Toast.LENGTH_LONG).show();
                }

                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("workoutsType", workoutsType);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }


    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
