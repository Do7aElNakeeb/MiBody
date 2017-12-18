package ch.philopateer.mibody.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ch.philopateer.mibody.R;
import ch.philopateer.mibody.object.WorkoutItem;
import ch.philopateer.mibody.helper.SQLiteHandler;
import ch.philopateer.mibody.helper.WorkoutsAdapter;

import java.util.ArrayList;

/**
 * Created by mamdouhelnakeeb on 12/6/16.
 */

public class WorkoutsItemsActivity extends AppCompatActivity {

    RecyclerView workoutsRV;
    WorkoutsAdapter workoutsAdapter;
    FloatingActionButton addWorkoutBtn;
    TextView workoutTypeTxt;
    private static final String TAG = WorkoutsItemsActivity.class.getSimpleName();
    private ProgressDialog pDialog;

    String user_id = "NULL";
    String workoutsType = "";
    ArrayList<WorkoutItem> workoutItemArrayList;
    //SQLiteHandler sqLiteHandler;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;


    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workouts_items_activity);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(firebaseAuth.getCurrentUser().getUid());

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        /*
        sqLiteHandler = new SQLiteHandler(this);

        try {
            sqLiteHandler.open();

        } catch (Exception e) {
            Log.i("hello", "hello");
        }
        */

        initViews();

        Intent intent = getIntent();
        workoutsType = intent.getStringExtra("type");
        workoutTypeTxt.setText(workoutsType);

        if (workoutsType.equals("predefined")) {
            addWorkoutBtn.setVisibility(View.GONE);
            Toast.makeText(this, "Soon in PRO version", Toast.LENGTH_SHORT).show();
        }
        else {
            addWorkoutBtn.setVisibility(View.VISIBLE);
            SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
            user_id = sharedPreferences.getString("user_id", "");
            loadWorkouts();
        }

        addWorkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), AddWorkout.class);
                intent.putExtra("edit", false);
                startActivity(intent);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                loadWorkouts();
            }
        });
    }

    private void initViews(){

        workoutTypeTxt = (TextView) findViewById(R.id.workoutsItemsType);
        addWorkoutBtn = (FloatingActionButton) findViewById(R.id.add_workout_btn);
        workoutsRV = (RecyclerView) findViewById(R.id.workoutsRV);
        workoutsRV.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        workoutsRV.setItemAnimator(new DefaultItemAnimator());
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);


    }

    private void initAdapter(){

        workoutItemArrayList = new ArrayList<WorkoutItem>();
        workoutsAdapter = new WorkoutsAdapter(getApplicationContext(), workoutItemArrayList, new WorkoutsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final WorkoutItem workoutItem, final int position, Boolean longClick) {

                if (longClick){
                    AlertDialog.Builder builderSingle = new AlertDialog.Builder(WorkoutsItemsActivity.this);
                    //builderSingle.setIcon(R.drawable.ic_launcher);
                    builderSingle.setTitle(workoutItem.workoutName);

                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(WorkoutsItemsActivity.this, android.R.layout.select_dialog_item);
                    arrayAdapter.add("Edit");
                    arrayAdapter.add("Remove");

                    builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String choice = arrayAdapter.getItem(which);

                            switch (which){
                                case 0:
                                    Intent intent = new Intent(WorkoutsItemsActivity.this, AddWorkout.class);
                                    intent.putExtra("workoutItem", workoutItem);
                                    startActivity(intent);
                                    break;
                                case 1:
                                    workoutItemArrayList.remove(position);
                                    workoutsAdapter.notifyItemRemoved(position);
                                    FirebaseDatabase.getInstance().getReference()
                                            .child("users")
                                            .child(firebaseAuth.getCurrentUser().getUid())
                                            .child("workouts")
                                            .child(workoutItem.workoutID)
                                            .removeValue();
                                    break;
                            }

                            Log.d("selectionIs", choice);
                            dialog.dismiss();

                        }
                    });
                    builderSingle.show();
                }
                else {

                    Intent intent = new Intent(getBaseContext(), WorkoutPlay.class);
                    intent.putExtra("WorkoutItem", workoutItem);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Log.d("exListNullCH", String.valueOf(workoutItem.exercisesList.size()));
                    startActivity(intent);
                }
            }
        });

    }
    private void loadWorkouts(){


        ConnectivityManager ConnectionManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=ConnectionManager.getActiveNetworkInfo();
        if(networkInfo == null || !networkInfo.isConnected())
        {
            Toast.makeText(this, "Network Not Available", Toast.LENGTH_SHORT).show();
            return;

        }

//        pDialog.setMessage("Loading Workouts ...");
//        showDialog();

        initAdapter();
        swipeRefreshLayout.setRefreshing(true);

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                workoutItemArrayList.clear();

                for (DataSnapshot workoutsSnapShot : dataSnapshot.child("workouts").getChildren()){
                    WorkoutItem workoutItem = workoutsSnapShot.getValue(WorkoutItem.class);
                    WorkoutItem workoutItem1 = new WorkoutItem(workoutItem.workoutID, workoutItem.workoutName, 1, workoutItem.exercisesJSON, "personalized", workoutItem.wTime);
                    workoutItemArrayList.add(workoutItem1);
                }

                workoutsRV.setAdapter(workoutsAdapter);

//                hideDialog();

                swipeRefreshLayout.setRefreshing(false);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

//                hideDialog();

                swipeRefreshLayout.setRefreshing(false);
            }

        };

        databaseReference.addListenerForSingleValueEvent(valueEventListener);

    }
/*
    private void loadWorkouts(final String workoutsType){

        // Tag used to cancel the request
        String tag_string_req = "req_register";

        pDialog.setMessage("Loading Workouts ...");
        showDialog();



        StringRequest strReq = new StringRequest(Request.Method.POST, AppConfig.URL_SERVER + "workouts.php",
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        //Log.d(TAG, "MiBody Workouts Response: " + response);
                        response = response.replaceAll("&quot;", "\"");
                        response = response.replace("]\"", "]");
                        response = response.replace("\"[", "[");
                        Log.d(TAG, "MiBody Workouts Response2: " + response);
                        hideDialog();

                        workoutItemArrayList = new ArrayList<WorkoutItem>();

                        try {
                            // Extract JSON array from the response
                            JSONArray resultsArr = new JSONArray(response);

                            System.out.println(resultsArr.length());
                            // If no of array elements is not zero
                            if(resultsArr.length() != 0){

                                Log.d("resultsArray", resultsArr.toString());
                                // Loop through each array element, get JSON object
                                for (int i = 0; i < resultsArr.length(); i++) {
                                    // Get JSON object
                                    JSONObject obj = (JSONObject) resultsArr.get(i);

                                    // DB QueryValues Object to insert into Movies ArrayList
                                    String id = obj.get("id").toString();
                                    String WorkoutName = obj.get("name").toString();
                                    int WorkoutReps = Integer.parseInt(obj.get("reps").toString());
                                    String ExercisesJSON = obj.get("exercises").toString();
                                    String WorkoutType = obj.get("type").toString();

                                    workoutItemArrayList.add(new WorkoutItem(id , WorkoutName, WorkoutReps, ExercisesJSON, WorkoutType));
                                    sqLiteHandler.addWorkout(new WorkoutItem(id, WorkoutName, WorkoutReps, ExercisesJSON, WorkoutType));

                                }

                                workoutsAdapter = new WorkoutsAdapter(getApplicationContext(), workoutItemArrayList);
                                workoutsRV.setAdapter(workoutsAdapter);

                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error: " + error.getMessage());

                workoutItemArrayList = new ArrayList<WorkoutItem>();

                workoutItemArrayList = sqLiteHandler.getWorkouts("type="+'"'+ workoutsType +'"');

                if (workoutItemArrayList.size() != 0) {
                    Log.d("wSize", String.valueOf(workoutItemArrayList.size()));
                    Log.d("eSize", String.valueOf(workoutItemArrayList.get(0).exercisesList.size()));

                    workoutsAdapter = new WorkoutsAdapter(getApplicationContext(), workoutItemArrayList);
                    workoutsRV.setAdapter(workoutsAdapter);
                }
                else {
                    Toast.makeText(getApplicationContext(), "There is no " +  workoutsType +  " workouts", Toast.LENGTH_SHORT).show();
                }

                //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", user_id);
                params.put("type", workoutsType);
                Log.d("params", user_id + " - " + workoutsType);
                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
*/
    @Override
    protected void onResume() {
        super.onResume();
        if (workoutsType.equals("predefined")) {
            addWorkoutBtn.setVisibility(View.GONE);
            Toast.makeText(this, "Soon in PRO version", Toast.LENGTH_LONG).show();
        }
        else {
            loadWorkouts();
        }
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