package ch.philopateer.mibody.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import ch.philopateer.mibody.R;
import ch.philopateer.mibody.activity.Register;
import ch.philopateer.mibody.app.AppConfig;
import ch.philopateer.mibody.helper.Utils;
import ch.philopateer.mibody.listener.OnBtnClickListener;

import static ch.philopateer.mibody.helper.Utils.cmToInch;
import static ch.philopateer.mibody.helper.Utils.kgToLbs;

/**
 * Created by mamdouhelnakeeb on 1/8/17.
 */

public class RegisterThree extends Fragment {

    TextView cmTxt, lbTxt, kgTxt, inchTxt, BMITxt;
    Button regThreeBtn;

    EditText weightET, heightET;
    LinearLayout bmiLL;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    OnBtnClickListener onBtnClickListener;
    public int units = 0;
    public String BMI = "";

    public float height = 0, weight = 0;

    public void initListener(OnBtnClickListener onBtnClickListener){
        this.onBtnClickListener = onBtnClickListener;
    }
/*
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser){
            if (!BMI.equals("")){
                BMITxt.setText(String.valueOf(BMIeqn(height, weight)));
                if (units == 0){
                    cmTxt.setTextColor(ContextCompat.getColor(getActivity(), R.color.MiBodyOrange));
                    inchTxt.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                    kgTxt.setTextColor(ContextCompat.getColor(getActivity(), R.color.MiBodyOrange));
                    lbTxt.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                }
                else {
                    inchTxt.setTextColor(ContextCompat.getColor(getActivity(), R.color.MiBodyOrange));
                    cmTxt.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                    lbTxt.setTextColor(ContextCompat.getColor(getActivity(), R.color.MiBodyOrange));
                    kgTxt.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                }
            }
        }
    }
*/
    public RegisterThree(){

    }

    public RegisterThree(int units, float weight, float height, String BMI){
        this.units = units;
        this.weight = weight;
        this.height = height;
        this.BMI = BMI;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.register_three, container, false);

        cmTxt = (TextView) view.findViewById(R.id.cmTxt);
        lbTxt = (TextView) view.findViewById(R.id.lbTxt);
        kgTxt = (TextView) view.findViewById(R.id.kgTxt);
        inchTxt = (TextView) view.findViewById(R.id.inchTxt);
        BMITxt = (TextView) view.findViewById(R.id.BMITxt);
        regThreeBtn = (Button) view.findViewById(R.id.regThreeBtn);

        bmiLL = (LinearLayout) view.findViewById(R.id.bmiLL);

        weightET = (EditText) view.findViewById(R.id.weightTxtField);
        heightET = (EditText) view.findViewById(R.id.heightTxtField);

        BMITxt.setText(String.valueOf(BMIeqn(height, weight)));

        weightET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (!editable.toString().equals("") && !heightET.getText().toString().equals("")) {

                    //weightET.setText(editable.toString());
                    weight = Float.parseFloat(weightET.getText().toString());
                    height = Float.parseFloat(heightET.getText().toString());
                    BMITxt.setText(BMIeqn(height, weight));
                    bmiLL.setVisibility(View.VISIBLE);
                }
                else {
                    bmiLL.setVisibility(View.GONE);
                }

            }
        });

        heightET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (!weightET.getText().toString().equals("") && !editable.toString().equals("")) {

                    //heightET.setText(editable.toString());
                    weight = Float.parseFloat(weightET.getText().toString());
                    height = Float.parseFloat(heightET.getText().toString());
                    BMITxt.setText(BMIeqn(height, weight));
                    bmiLL.setVisibility(View.VISIBLE);
                }
                else {
                    bmiLL.setVisibility(View.GONE);
                }
            }
        });

        regThreeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final String weight = weightET.getText().toString().trim();
                final String height = heightET.getText().toString().trim();

                //checking if email and passwords are empty

                if(TextUtils.isEmpty(weight)){
                    Toast.makeText(getActivity(),"Please enter weight",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(height)){
                    Toast.makeText(getActivity(),"Please enter height", Toast.LENGTH_LONG).show();
                    return;
                }

                if (units == 0){
                    if (Double.parseDouble(weight) > AppConfig.MAX_WEIGHT){
                        weightET.setText("");
                        Toast.makeText(getActivity(), "Your weight should be less than " + Double.toString(AppConfig.MAX_WEIGHT) +" kg", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if (Double.parseDouble(weight) < AppConfig.MIN_WEIGHT){
                        weightET.setText("");
                        Toast.makeText(getActivity(), "Your weight should be more than " + Double.toString(AppConfig.MIN_WEIGHT) +" kg", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                else if (units == 1){
                    if (Double.parseDouble(weight) > kgToLbs(AppConfig.MAX_WEIGHT)){
                        weightET.setText("");
                        Toast.makeText(getActivity(), "Your weight should be less than " + Double.toString(kgToLbs(AppConfig.MAX_WEIGHT)) +" lbs", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if (Double.parseDouble(weight) < kgToLbs(AppConfig.MIN_WEIGHT)){
                        weightET.setText("");
                        Toast.makeText(getActivity(), "Your weight should be more than " + Double.toString(kgToLbs(AppConfig.MIN_WEIGHT)) +" lbs", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                if (units == 0){
                    if (Double.parseDouble(height) > AppConfig.MAX_HEIGHT){
                        heightET.setText("");
                        Toast.makeText(getActivity(), "Your height should be less than " + Double.toString(AppConfig.MAX_HEIGHT) +" cm", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if (Double.parseDouble(height) < AppConfig.MIN_HEIGHT){
                        heightET.setText("");
                        Toast.makeText(getActivity(), "Your height should be more than " + Double.toString(AppConfig.MIN_WEIGHT) +" cm", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                else if (units == 1){
                    if (Double.parseDouble(height) > cmToInch(AppConfig.MAX_HEIGHT)){
                        heightET.setText("");
                        Toast.makeText(getActivity(), "Your height should be less than " + Double.toString(cmToInch(AppConfig.MAX_HEIGHT)) +" inch", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if (Double.parseDouble(height) < cmToInch(AppConfig.MIN_HEIGHT)){
                        heightET.setText("");
                        Toast.makeText(getActivity(), "Your height should be more than " + Double.toString(cmToInch(AppConfig.MIN_HEIGHT)) +" inch", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }


                ((Register)getActivity()).registerWithMail();
                //onBtnClickListener.onBtnClick();

            }
        });

        cmTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cmTxt.setTextColor(ContextCompat.getColor(getActivity(), R.color.MiBodyOrange));
                inchTxt.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                kgTxt.setTextColor(ContextCompat.getColor(getActivity(), R.color.MiBodyOrange));
                lbTxt.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                units = 0;
                BMITxt.setText(BMIeqn(height, weight));
            }
        });

        lbTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lbTxt.setTextColor(ContextCompat.getColor(getActivity(), R.color.MiBodyOrange));
                kgTxt.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                inchTxt.setTextColor(ContextCompat.getColor(getActivity(), R.color.MiBodyOrange));
                cmTxt.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                units = 1;
                BMITxt.setText(BMIeqn(height, weight));
            }
        });

        inchTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inchTxt.setTextColor(ContextCompat.getColor(getActivity(), R.color.MiBodyOrange));
                cmTxt.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                lbTxt.setTextColor(ContextCompat.getColor(getActivity(), R.color.MiBodyOrange));
                kgTxt.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                units = 1;
                BMITxt.setText(BMIeqn(height, weight));
            }
        });

        kgTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kgTxt.setTextColor(ContextCompat.getColor(getActivity(), R.color.MiBodyOrange));
                lbTxt.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                cmTxt.setTextColor(ContextCompat.getColor(getActivity(), R.color.MiBodyOrange));
                inchTxt.setTextColor(ContextCompat.getColor(getActivity(), R.color.black));
                units = 0;
                BMITxt.setText(String.valueOf(BMIeqn(height, weight)));
            }
        });

        return view;
    }

    public String BMIeqn(float height, float weight){
        Log.d("BMI", String.valueOf(weight) + " -- " + String.valueOf(height));

        if (units == 0){
            BMI = String.valueOf(weight / (height * height / 10000));
        }
        else {
            BMI = String.valueOf(703 * (weight / (height * height / 10000)));
        }
        return BMI.substring(0, BMI.indexOf('.') + 2);
    }


}