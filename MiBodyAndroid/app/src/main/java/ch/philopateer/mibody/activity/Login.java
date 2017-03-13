package ch.philopateer.mibody.activity;

/**
 * Created by El Nakeeb on 7/14/2016.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import ch.philopateer.mibody.R;
import ch.philopateer.mibody.app.AppConfig;
import ch.philopateer.mibody.app.AppController;
import ch.philopateer.mibody.helper.SessionManager;
import ch.philopateer.mibody.object.UserData;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    private static final String TAG = Register.class.getSimpleName();

    ProgressDialog prgDialog;
    RequestParams params = new RequestParams();
    Context applicationContext;

    LoginButton loginButton;
    EditText editTextEmail;
    EditText editTextPassword;
    RelativeLayout btnLinkToRegister;
    private Button buttonLogin;
    private SessionManager session;
    private ProgressDialog pDialog;

    CallbackManager callbackManager;
    private ProfileTracker profileTracker;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    StorageReference photoReferenece;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        photoReferenece = FirebaseStorage.getInstance().getReference();


        FacebookSdk.sdkInitialize(getApplicationContext());

        callbackManager = CallbackManager.Factory.create();

        loginButton = (LoginButton) findViewById(R.id.fbLoginBtn);
        editTextEmail = (EditText) findViewById(R.id.email);
        editTextPassword = (EditText) findViewById(R.id.password);
        buttonLogin = (Button) findViewById(R.id.btnLogin);
        btnLinkToRegister = (RelativeLayout) findViewById(R.id.emailSignUp);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        prefs = getSharedPreferences("UserDetails", MODE_PRIVATE);
        editor = prefs.edit();

        // Session manager
        session = new SessionManager(getApplicationContext());

        // Check if user is already logged in or not
        if (session.isLoggedIn().equals("1")) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(getApplicationContext(), Landing.class);
            startActivity(intent);
            finish();
        }
        else {
            fbLogout();
        }

        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday"));

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                fbLogin(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
            }
        });


        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, Register.class);
                intent.putExtra("from", 0);
                startActivity(intent);
            }
        });


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getting email and password from edit texts
                String email = editTextEmail.getText().toString().trim();
                String password  = editTextPassword.getText().toString().trim();

                //checking if email and passwords are empty
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"Please enter email",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(),"Please enter password",Toast.LENGTH_LONG).show();
                    return;
                }
                if (email.equals("guest") && password.equals("guest")){
                    //session.insertData("guest", email, "011", password, "50", null, null);
                    Toast.makeText(getApplicationContext(), "Welcome Guest!", Toast.LENGTH_LONG).show();
                    SharedPreferences prefs = getSharedPreferences("UserDetails", MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("user_id", "1");
                    editor.apply();
                    session.setLogin("1");
                    // Launch login activity
                    Intent intent = new Intent(Login.this, Landing.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    String regID = FirebaseInstanceId.getInstance().getToken();
                    //loginUser(email, password, regID);
                    loginWithMail(email, password);
                }
            }
        });
    }

    private void loginWithMail(final String email, final String password){
        pDialog.setMessage("Login with Email");
        showDialog();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                        hideDialog();
                        if (task.isSuccessful()) {
                            saveToSharedPrefs();
                            loginToHome();
                        } else {
                            //Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                            builder.setMessage(task.getException().getMessage())
                                    .setTitle("Error!")
                                    .setPositiveButton(android.R.string.ok, null);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                });
    }

    private void saveToSharedPrefs(){

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(firebaseAuth.getCurrentUser().getUid());

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UserData userData = dataSnapshot.getValue(UserData.class);

                session.insertData(firebaseAuth.getCurrentUser().getUid(), userData.name, firebaseAuth.getCurrentUser().getEmail(),
                        userData.gender, userData.dob, userData.weight, userData.height, userData.units, userData.BMI);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        databaseReference.addListenerForSingleValueEvent(valueEventListener);
    }

    private void loginToHome(){
        Intent intent = new Intent(getBaseContext(), Landing.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void loginUser(final String email, final String password, final String regID){

        // Tag used to cancel the request
        String tag_string_req = "req_register";

        //if the email and password are not empty
        //displaying a progress dialog

        pDialog.setMessage("Signing in please wait ...");
        showDialog();

        StringRequest strReq = new StringRequest(com.android.volley.Request.Method.POST, AppConfig.URL_SERVER + "login.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Register Response: " + response);
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
                        // User successfully stored in MySQL
                        // Now store the user in Shared Preferences
                        String message = jObj.getString("message");
                        String user_id = jObj.get("user_id").toString();
                        String name = jObj.get("name").toString();
                        String email = jObj.get("email").toString();
                        String mobile = jObj.get("mobile").toString();
                        String gender = jObj.get("gender").toString();
                        String dob = jObj.get("dob").toString();
                        String weight = jObj.get("weight").toString();
                        String height = jObj.get("height").toString();
                        String units = jObj.get("units").toString();
                        String BMI = jObj.get("BMI").toString();
                        String regID = jObj.get("regID").toString();

                        session.insertData(user_id, name, email, gender, dob, weight, height, units, BMI);

                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

                        // Launch login activity
                        Intent intent = new Intent(Login.this, Landing.class);
                        startActivity(intent);
                        finish();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
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
                params.put("email", email);
                params.put("password", password);
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

    private void fbLogin(final AccessToken token){
        pDialog.setMessage("Login with Facebook");
        showDialog();

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        else {
                            GraphRequest request = GraphRequest.newMeRequest(token, new GraphRequest.GraphJSONObjectCallback() {

                                @Override
                                public void onCompleted(JSONObject object, GraphResponse response) {
                                    Log.i("LoginActivity", response.toString());
                                    // Get facebook data from login
                                    try {

                                        if (object.has("id")) {
                                            editor.putString("fbID", object.getString("id"));
                                            Log.d("id", object.getString("id"));
                                        }

                                        if (object.has("name")) {
                                            editor.putString("name", object.getString("name"));
                                            Log.d("name", object.getString("name"));
                                        }

                                        if (object.has("email")) {
                                            editor.putString("email", object.getString("email"));
                                            Log.d("email", object.getString("email"));
                                        }

                                        if (object.has("gender")) {
                                            editor.putString("gender", object.getString("gender"));
                                            Log.d("gender", object.getString("gender"));
                                        }

                                        String tmpDoB = "", dob;
                                        if (object.has("birthday")) {
                                            dob = object.getString("birthday");

                                            tmpDoB = dob.substring(3, 5) +
                                                    "/" +
                                                    dob.substring(0, 2) +
                                                    "/" +
                                                    dob.substring(6, 10);

                                            editor.putString("bod", tmpDoB);
                                            Log.d("birthday", tmpDoB);
                                        }

                                        editor.putString("fbIDCon", "true");
                                        session.setLogin("1");
                                        editor.apply();

                                        FirebaseDatabase.getInstance().getReference().child("users")
                                                .child(firebaseAuth.getCurrentUser().getUid())
                                                .setValue(new UserData(object.getString("name"), object.getString("email"),
                                                        object.getString("gender"), tmpDoB, "", "", "0", ""));

                                        loginToHome();

                                    }
                                    catch(JSONException e) {
                                        Log.d(TAG,"Error parsing JSON");
                                    }
                                }
                            });
                            Bundle parameters = new Bundle();
                            parameters.putString("fields", "id,name,email,gender,birthday");
                            request.setParameters(parameters);
                            request.executeAsync();

                            Log.d("tsttt", "testt2");
                        }

                        hideDialog();
                    }
                });

    }

    public void fbLogout() {
        //mAuth.signOut();
        LoginManager.getInstance().logOut();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
