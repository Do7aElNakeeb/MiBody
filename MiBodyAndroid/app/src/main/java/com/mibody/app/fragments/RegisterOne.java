package com.mibody.app.fragments;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mibody.app.R;
import com.mibody.app.activity.Login;
import com.mibody.app.activity.MainActivity;
import com.mibody.app.activity.Register;
import com.mibody.app.app.WorkoutExItem;
import com.mibody.app.helper.SessionManager;
import com.mibody.app.helper.WorkoutExItemAdapter;
import com.mibody.app.listener.OnBtnClickListener;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by mamdouhelnakeeb on 1/8/17.
 */

public class RegisterOne extends Fragment {
    //defining view objects
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextMobile;
    private EditText editTextName;
    private EditText editTextWeight;
    public Button buttonSignup;
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

    public OnBtnClickListener onBtnClickListener;

    public void initListener(OnBtnClickListener onBtnClickListener){
        this.onBtnClickListener = onBtnClickListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.register_one, container, false);



        //initializing views
        editTextName = (EditText) view.findViewById(R.id.name);
        editTextEmail = (EditText) view.findViewById(R.id.email);
        editTextMobile = (EditText) view.findViewById(R.id.mobile);
        editTextPassword = (EditText) view.findViewById(R.id.password);
        //   editTextWeight = (EditText) findViewById(R.id.weight);

        buttonSignup = (Button) view.findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) view.findViewById(R.id.btnLinkToLoginScreen);
        //       weightBtn = (Button) findViewById(R.id.weightBtn);

        // Progress dialog
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);

        prefs = getActivity().getSharedPreferences("UserDetails", MODE_PRIVATE);

        // Session manager
        session = new SessionManager(getActivity());

        // Check if user is already logged in or not
        if (session.isLoggedIn().equals("1")) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }

/*
        weightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), WorkoutPlayActivity.class);
                intent.putExtra("weight", "weight");
                startActivity(intent);

            }
        });
        */

        //attaching listener to button
        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //getting email and password from edit texts
                name = editTextName.getText().toString().trim();
                email = editTextEmail.getText().toString().trim();
                mobile = editTextMobile.getText().toString().trim();
                password  = editTextPassword.getText().toString().trim();
                //weight  = editTextWeight.getText().toString().trim();

                //checking if email and passwords are empty
                if(TextUtils.isEmpty(name)){
                    Toast.makeText(getActivity(),"Please enter name",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getActivity(),"Please enter email",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(mobile)){
                    Toast.makeText(getActivity(),"Please enter mobile",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getActivity(),"Please enter password",Toast.LENGTH_LONG).show();
                    return;
                }

                SharedPreferences.Editor editor = prefs.edit();

                editor.putString("name", name);
                editor.putString("email", email);
                editor.putString("mobile", mobile);
                editor.apply();

                onBtnClickListener.onBtnClick();

/*
                if (name.equals("guest") && password.equals("guest")){
                    session.insertData(name, email, mobile, password, weight, null, null);
                    Toast.makeText(getActivity(), "Welcome Guest!", Toast.LENGTH_LONG).show();
                    // Launch login activity
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    //startActivity(intent);
                }
                else {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    RegisterTwo registerTwo = new RegisterTwo();

                    fragmentTransaction.add(R.id.exercisesFragment, registerTwo);
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    fragmentTransaction.commit();

                    //String regID = FirebaseInstanceId.getInstance().getToken();

                    //registerUser(name, email, mobile, password, weight, regID);
                }
*/
            }
        });


        // Link to Register Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), Login.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        return view;
    }

}
