package com.mibody.app.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mibody.app.R;
import com.mibody.app.activity.Landing;
import com.mibody.app.listener.OnBtnClickListener;

/**
 * Created by mamdouhelnakeeb on 1/8/17.
 */

public class RegisterThree extends Fragment {

    TextView cmTxt, lbTxt, kgTxt, inchTxt, BMITxt;
    Button regThreeBtn;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    OnBtnClickListener onBtnClickListener;
    int units = 0;

    float height = 0, weight = 0;

    public void initListener(OnBtnClickListener onBtnClickListener){
        this.onBtnClickListener = onBtnClickListener;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser){
            prefs = getActivity().getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
            weight = Float.valueOf(prefs.getString("weight", ""));
            height = Float.valueOf(prefs.getString("height", ""));
            BMITxt.setText(String.valueOf(BMIeqn(height, weight)));
        }
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

        BMITxt.setText(String.valueOf(BMIeqn(height, weight)));

        regThreeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBtnClickListener.onBtnClick();

                /*
                Intent intent = new Intent(getActivity(), Landing.class);
                startActivity(intent);
                */
            }
        });

        cmTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cmTxt.setTextColor(R.color.MiBodyOrange);
                inchTxt.setTextColor(R.color.black);
                kgTxt.setTextColor(R.color.MiBodyOrange);
                lbTxt.setTextColor(R.color.black);
                units = 0;
                BMITxt.setText(BMIeqn(height, weight));
            }
        });

        lbTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lbTxt.setTextColor(R.color.MiBodyOrange);
                kgTxt.setTextColor(R.color.black);
                inchTxt.setTextColor(R.color.MiBodyOrange);
                cmTxt.setTextColor(R.color.black);
                units = 1;
                BMITxt.setText(BMIeqn(height, weight));
            }
        });

        inchTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inchTxt.setTextColor(R.color.MiBodyOrange);
                cmTxt.setTextColor(R.color.black);
                lbTxt.setTextColor(R.color.MiBodyOrange);
                kgTxt.setTextColor(R.color.black);
                units = 1;
                BMITxt.setText(BMIeqn(height, weight));
            }
        });

        kgTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kgTxt.setTextColor(R.color.MiBodyOrange);
                lbTxt.setTextColor(R.color.black);
                cmTxt.setTextColor(R.color.MiBodyOrange);
                inchTxt.setTextColor(R.color.black);
                units = 0;
                BMITxt.setText(String.valueOf(BMIeqn(height, weight)));
            }
        });

        return view;
    }

    private String BMIeqn(float height, float weight){
        Log.d("BMI", String.valueOf(weight) + " -- " + String.valueOf(height));
        String BMI;
        if (units == 0){
            BMI = String.valueOf(weight / (height * height / 10000));
        }
        else {
            BMI = String.valueOf(703 * (weight / (height * height / 10000)));
        }
        return BMI.substring(0, BMI.indexOf('.') + 2);
    }
}