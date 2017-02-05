package com.mibody.app.activity;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.mibody.app.R;
import com.mibody.app.app.AppConfig;
import com.mibody.app.app.AppController;
import com.mibody.app.fragments.RegisterOne;
import com.mibody.app.fragments.RegisterThree;
import com.mibody.app.fragments.RegisterTwo;
import com.mibody.app.fragments.WorkoutPlayAuto;
import com.mibody.app.helper.SessionManager;
import com.mibody.app.helper.ViewPagerAdapter;
import com.mibody.app.listener.OnBtnClickListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by NakeebMac on 10/1/16.
 */

public class Register extends AppCompatActivity {
    private static final String TAG = Register.class.getSimpleName();

    ViewPager viewPager;

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
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager();

    }

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        RegisterOne registerOne = new RegisterOne();
        registerOne.initListener(new OnBtnClickListener() {
            @Override
            public void onBtnClick() {
                viewPager.setCurrentItem(1);
            }
        });

        RegisterTwo registerTwo = new RegisterTwo();
        registerTwo.initListener(new OnBtnClickListener() {
            @Override
            public void onBtnClick() {
                viewPager.setCurrentItem(2);
            }
        });

        RegisterThree registerThree = new RegisterThree();
        registerThree.initListener(new OnBtnClickListener() {
            @Override
            public void onBtnClick() {
                Toast.makeText(Register.this, "submit", Toast.LENGTH_LONG).show();
            }
        });

        adapter.addFragment(registerOne, "1");
        adapter.addFragment(registerTwo, "2");
        adapter.addFragment(registerThree, "3");
        viewPager.setAdapter(adapter);
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

                        //session.insertData(name, email, mobile, password, weight, regID, created_at);

                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                        // Launch login activity
                        Intent intent = new Intent(Register.this, Landing.class);
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
                params.put("password", password);
                params.put("mobile", mobile);
                params.put("weight", weight);
                params.put("height", password);
                params.put("regID", regID);

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
            Intent i = new Intent(Register.this, WorkoutPlayAuto.class);
            i.putExtra(AppConfig.EXTRA_DEVICE_ADDRESS, address);
            startActivity(i);
        }
    };

}