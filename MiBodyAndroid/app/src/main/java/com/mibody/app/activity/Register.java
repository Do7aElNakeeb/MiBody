package com.mibody.app.activity;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.iid.FirebaseInstanceId;
import com.mibody.app.R;
import com.mibody.app.app.AppConfig;
import com.mibody.app.app.AppController;
import com.mibody.app.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by NakeebMac on 10/1/16.
 */

public class Register extends AppCompatActivity {
    private static final String TAG = Register.class.getSimpleName();

    //defining view objects
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextMobile;
    private EditText editTextName;
    private EditText editTextWeight;
    private Button buttonSignup;
    private Button weightBtn;
    String name;
    String email;
    String mobile;
    String password;
    String weight;

    private ProgressDialog pDialog;
    Button btnLinkToLogin;
    private SessionManager session;

//    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private BluetoothAdapter mBtAdapter;
    private ArrayAdapter<String> mPairedDevicesArrayAdapter;
    TextView BTState;

    SharedPreferences prefs;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //initializing views
        editTextName = (EditText) findViewById(R.id.name);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextMobile = (EditText) findViewById(R.id.mobile);
        editTextPassword = (EditText) findViewById(R.id.password);
        editTextWeight = (EditText) findViewById(R.id.weight);

        buttonSignup = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);
        weightBtn = (Button) findViewById(R.id.weightBtn);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        prefs = getSharedPreferences("UserDetails", MODE_PRIVATE);

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn().equals("1")) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }

        weightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), WorkoutPlayActivity.class);
                intent.putExtra("weight", "weight");
                startActivity(intent);
                /*
                SharedPreferences prefs = getSharedPreferences("BT", MODE_PRIVATE);
                String MacAddress = prefs.getString("BT_MAC", "");

                final AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());

                LayoutInflater inflater = getLayoutInflater();

                if (!MacAddress.isEmpty()){
                    // go to weight dialog
                    View dialogView = inflater.inflate(R.layout.device_list, null);
                    builder.setView(dialogView);
                }
                else {
                    // go to bluetooth dialog


                    /*

                    View dialogView = inflater.inflate(R.layout.device_list, null);
                    builder.setView(dialogView);

                    checkBTState();

                    BTState = (TextView) findViewById(R.id.connecting);
                    BTState.setTextSize(40);
                    BTState.setText(" ");

                    // Initialize array adapter for paired devices
                    mPairedDevicesArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.device_name);

                    // Find and set up the ListView for paired devices
                    ListView pairedListView = (ListView) findViewById(R.id.paired_devices);
                    pairedListView.setAdapter(mPairedDevicesArrayAdapter);
                    pairedListView.setOnItemClickListener(mDeviceClickListener);

                    // Get the local Bluetooth adapter
                    mBtAdapter = BluetoothAdapter.getDefaultAdapter();

                    // Get a set of currently paired devices and append to 'pairedDevices'
                    Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();

                    // Add previosuly paired devices to the array
                    if (pairedDevices.size() > 0) {
                        findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);//make title viewable
                        for (BluetoothDevice device : pairedDevices) {
                            mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                        }
                    } else {
                        String noDevices = getResources().getText(R.string.none_paired).toString();
                        mPairedDevicesArrayAdapter.add(noDevices);
                    }



                }
            */

            }
        });

        //attaching listener to button
        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting email and password from edit texts
                name = editTextName.getText().toString().trim();
                email = editTextEmail.getText().toString().trim();
                mobile = editTextMobile.getText().toString().trim();
                password  = editTextPassword.getText().toString().trim();
                weight  = editTextWeight.getText().toString().trim();

                //checking if email and passwords are empty
                if(TextUtils.isEmpty(name)){
                    Toast.makeText(getApplicationContext(),"Please enter name",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"Please enter email",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(mobile)){
                    Toast.makeText(getApplicationContext(),"Please enter mobile",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(),"Please enter password",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(weight)){
                    Toast.makeText(getApplicationContext(),"Please enter your weight",Toast.LENGTH_LONG).show();
                    return;
                }

                if (name.equals("guest") && password.equals("guest")){
                    session.insertData(name, email, mobile, password, weight, null, null);
                    Toast.makeText(getApplicationContext(), "Welcome Guest!", Toast.LENGTH_LONG).show();
                    // Launch login activity
                    Intent intent = new Intent(Register.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    String regID = FirebaseInstanceId.getInstance().getToken();
                    registerUser(name, email, mobile, password, weight, regID);
                }


            }
        });



        // Link to Register Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void registerUser(final String name, final String email, final String mobile, final String password, final String weight, final String regID){

        // Tag used to cancel the request
        String tag_string_req = "req_register";

        //if the email and password are not empty
        //displaying a progress dialog

        pDialog.setMessage("Registering please wait ...");
        showDialog();

        StringRequest strReq = new StringRequest(com.android.volley.Request.Method.POST, AppConfig.URL_SERVER + "register.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in sqlite
                        String message = jObj.getString("message");
                        String name = jObj.getString("name");
                        String email = jObj.getString("email");
                        String mobile = jObj.getString("email");
                        String weight = jObj.getString("weight");
                        String regID = jObj.getString("regID");
                        String created_at = jObj.getString("created_at");

                        session.insertData(name, email, mobile, password, weight, regID, created_at);

                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                        // Launch login activity
                        Intent intent = new Intent(Register.this, Exercises.class);
                        startActivity(intent);
                        finish();
                    } else {

                        // Error occurred in registration. Get the error
                        String errorMsg = jObj.getString("message");
                        Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("email", email);
                params.put("mobile", mobile);
                params.put("password", password);
                params.put("weight", weight);
                params.put("regID", regID);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    @Override
    protected void onResume() {
        super.onResume();

        editTextWeight.setText(prefs.getString("weight", ""));

    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private void checkBTState() {
        // Check device has Bluetooth and that it is turned on
        mBtAdapter=BluetoothAdapter.getDefaultAdapter(); // CHECK THIS OUT THAT IT WORKS!!!
        if(mBtAdapter==null) {
            Toast.makeText(getBaseContext(), "Device does not support Bluetooth", Toast.LENGTH_SHORT).show();
        } else {
            if (mBtAdapter.isEnabled()) {
                Log.d(TAG, "...Bluetooth ON...");
            } else {
                //Prompt user to turn on Bluetooth
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);

            }
        }
    }

    // Set up on-click listener for the list (nicked this - unsure)
    AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {

            BTState.setText("Connecting...");
            // Get the device MAC address, which is the last 17 chars in the View
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);

            SharedPreferences prefs = getSharedPreferences("BT", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("BT_MAC", address);
            editor.apply();

            // Make an intent to start next activity while taking an extra which is the MAC address.
            Intent i = new Intent(Register.this, WorkoutPlay.class);
            i.putExtra(AppConfig.EXTRA_DEVICE_ADDRESS, address);
            startActivity(i);
        }
    };
}