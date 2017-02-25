package ch.philopateer.mibody.activity;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.iid.FirebaseInstanceId;

import ch.philopateer.mibody.app.AppConfig;
import ch.philopateer.mibody.app.AppController;
import ch.philopateer.mibody.fragments.RegisterOne;
import ch.philopateer.mibody.fragments.RegisterThree;
import ch.philopateer.mibody.fragments.RegisterTwo;
import ch.philopateer.mibody.helper.SessionManager;
import ch.philopateer.mibody.helper.ViewPagerAdapter;
import ch.philopateer.mibody.listener.OnBtnClickListener;

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

    private Uri fileUri; // file url to store image/video
    private Bitmap bitmap;

    private ProgressDialog pDialog;
    Button btnLinkToLogin;
    private SessionManager session;

//    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private BluetoothAdapter mBtAdapter;
    private ArrayAdapter<String> mPairedDevicesArrayAdapter;
    TextView BTState;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    int from = 0;
    String fromStr = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ch.philopateer.mibody.R.layout.register_activity);

        from = getIntent().getExtras().getInt("from");
        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        // Session manager
        session = new SessionManager(this);

        viewPager = (ViewPager) findViewById(ch.philopateer.mibody.R.id.viewpager);
        setupViewPager();

    }

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        final RegisterOne registerOne;
        final RegisterTwo registerTwo;
        final RegisterThree registerThree;

        if (from == 0){
            registerOne = new RegisterOne();
            registerTwo = new RegisterTwo();
            registerThree = new RegisterThree();
            fromStr = "Registering";
        }
        else if (from == 1){
            // retrieve data from shared prefs
            prefs = getSharedPreferences("UserDetails", MODE_PRIVATE);

            registerOne = new RegisterOne(prefs.getString("name", ""), prefs.getString("email", ""),
                    prefs.getString("mobile", ""), prefs.getString("password", ""), prefs.getString("photo", ""));

            registerTwo = new RegisterTwo(prefs.getString("gender", ""), prefs.getString("dob", ""),
                    prefs.getString("weight", ""), prefs.getString("height", ""));

            registerThree = new RegisterThree(Integer.parseInt(prefs.getString("units", "0")), Float.parseFloat(prefs.getString("weight", "")),
                    Float.parseFloat(prefs.getString("height", "")), prefs.getString("BMI", ""));

            fromStr = "Updating";

        }
        else {
            // retrieve data from shared prefs
            prefs = getSharedPreferences("UserDetails", MODE_PRIVATE);

            registerOne = new RegisterOne(prefs.getString("name", ""), prefs.getString("email", ""),
                    prefs.getString("mobile", ""), prefs.getString("password", ""), prefs.getString("photo", ""));

            registerTwo = new RegisterTwo(prefs.getString("gender", ""), prefs.getString("dob", ""),
                    prefs.getString("weight", ""), prefs.getString("height", ""));

            registerThree = new RegisterThree(Integer.parseInt(prefs.getString("units", "0")), Float.parseFloat(prefs.getString("weight", "0")),
                    Float.parseFloat(prefs.getString("height", "0")), prefs.getString("BMI", ""));

            fromStr = "Registering";
        }

        registerOne.initListener(new OnBtnClickListener() {
            @Override
            public void onBtnClick() {
                viewPager.setCurrentItem(1);
            }
        });


        registerTwo.initListener(new OnBtnClickListener() {
            @Override
            public void onBtnClick() {
                registerThree.weight = Float.parseFloat(registerTwo.weight);
                registerThree.height = Float.parseFloat(registerTwo.height);
                registerThree.BMI = "0";
                viewPager.setCurrentItem(2);
            }
        });

        registerThree.initListener(new OnBtnClickListener() {
            @Override
            public void onBtnClick() {

                registerUser(registerOne.name, registerOne.email, registerOne.mobile, registerOne.password, registerOne.newPassword, registerOne.photo,
                        registerTwo.gender, registerTwo.dob, registerTwo.weight, registerTwo.height, String.valueOf(registerThree.units),
                        registerThree.BMI,  FirebaseInstanceId.getInstance().getToken());
            }
        });

        adapter.addFragment(registerOne, "1");
        adapter.addFragment(registerTwo, "2");
        adapter.addFragment(registerThree, "3");
        viewPager.setAdapter(adapter);
    }


    private void registerUser(final String name, final String email, final String mobile, final String password, final String newPassword, final String photo,
                              final String gender, final String dob, final String weight, final String height, final String units, final String BMI, final String regID){

        // Tag used to cancel the request
        String tag_string_req = "req_register";

        //if the email and password are not empty
        //displaying a progress dialog

        pDialog.setMessage(fromStr +  " please wait ...");
        showDialog();
        String regORup;
        if (from == 1){
            regORup = "updateUser.php?";
        }
        else {
            regORup = "register.php?";
        }

        Log.d("regUrl", AppConfig.URL_SERVER + regORup);

        StringBuilder stringBuilder = new StringBuilder(AppConfig.URL_SERVER + regORup)
                .append("name=").append(name)
                .append("&email=").append(email)
                .append("&mobile=").append(mobile)
                .append("&password=").append(password)
                .append("&gender=").append(gender)
                .append("&dob=").append(dob)
                .append("&weight=").append(weight)
                .append("&height=").append(height)
                .append("&units=").append(units)
                .append("&BMI=").append(BMI)
                .append("&regID=").append(regID);

        if (from == 1){
            stringBuilder.append("&user_id=").append(prefs.getString("user_id", ""))
                .append("&newPassword=").append(newPassword);
        }

        StringRequest strReq = new StringRequest(Request.Method.POST, stringBuilder.toString(), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        String message = jObj.get("message").toString();
                        String user_id = jObj.get("user_id").toString();


                        session.insertData(user_id, name, email, mobile, gender, dob, weight, height, units, BMI, regID);

                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                        if (!photo.equals("")){
                            uploadPhoto(user_id, photo);
                        }
                        else {
                            // Launch login activity
                            Intent intent = new Intent(Register.this, Landing.class);
                            startActivity(intent);
                            finish();
                        }



                    } else {

                        // Error occurred in registration. Get the error
                        String errorMsg = jObj.get("message").toString();
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
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        })
        /*{

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", name);
                params.put("email", email);
                params.put("mobile", mobile);
                params.put("password", password);
                if (from == 1){
                    params.put("user_id", prefs.getString("user_id", ""));
                    params.put("newPassword", newPassword);
                }
                params.put("photo", photo);
                params.put("gender", gender);
                params.put("dob", dob);
                params.put("weight", weight);
                params.put("height", height);
                params.put("units", units);
                params.put("BMI", BMI);
                params.put("regID", regID);

                return params;
            }

        }*/;

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
        Log.d("strReqq", strReq.toString());
    }

    private void uploadPhoto(final String user_id, final String photo){
        // Tag used to cancel the request
        String tag_string_req = "req_upload";


        StringBuilder stringBuilder = new StringBuilder(AppConfig.URL_SERVER + "uploadPhoto.php?")
                .append("user_id=").append(user_id)
                .append("&photo=").append(photo);

        StringRequest strReq = new StringRequest(Request.Method.POST, stringBuilder.toString(), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Upload Response: " + response.toString());

                // Launch login activity
                Intent intent = new Intent(Register.this, Landing.class);
                startActivity(intent);
                finish();

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Launch login activity
                Intent intent = new Intent(Register.this, Landing.class);
                startActivity(intent);
                finish();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
        Log.d("strReqq", strReq.toString());
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