package com.mibody.app.fragments;

import android.content.Context;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.aigestudio.wheelpicker.widgets.WheelDatePicker;
import com.mibody.app.R;
import com.mibody.app.activity.Landing;
import com.mibody.app.listener.OnBtnClickListener;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mamdouhelnakeeb on 12/2/16.
 */

public class RegisterTwo extends Fragment {

    ToggleButton genderMale, genderFemale;
    EditText weightET, heightET;
    Button regTwoBtn;
    WheelDatePicker DoBPicker;

    OnBtnClickListener onBtnClickListener;

    public void initListener(OnBtnClickListener onBtnClickListener){
        this.onBtnClickListener = onBtnClickListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.register_two, container, false);

        genderMale = (ToggleButton) view.findViewById(R.id.genderMaleTB);
        genderFemale = (ToggleButton) view.findViewById(R.id.genderFemaleTB);
        weightET = (EditText) view.findViewById(R.id.weightTxtField);
        heightET = (EditText) view.findViewById(R.id.heightTxtField);

        DoBPicker = (WheelDatePicker) view.findViewById(R.id.DoB_picker);

        regTwoBtn = (Button) view.findViewById(R.id.regTwoBtn);

        genderFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genderFemale.setChecked(true);
                genderFemale.setBackgroundResource(R.drawable.female_on);
                genderMale.setChecked(false);
                genderMale.setBackgroundResource(R.drawable.male_off);
            }
        });

        genderMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genderFemale.setChecked(false);
                genderFemale.setBackgroundResource(R.drawable.female_off);
                genderMale.setChecked(true);
                genderMale.setBackgroundResource(R.drawable.male_on);
            }
        });

        DoBPicker.setSelectedDay(1);
        DoBPicker.setMonth(6);
        DoBPicker.setYear(1995);

        DoBPicker.setOnDateSelectedListener(new WheelDatePicker.OnDateSelectedListener() {
            @Override
            public void onDateSelected(WheelDatePicker picker, Date date) {

                Toast.makeText(getActivity(), String.valueOf(picker.getCurrentDay()) + " " + String.valueOf(picker.getCurrentMonth()) + " " + String.valueOf(picker.getCurrentYear()), Toast.LENGTH_LONG).show();
            }
        });

        regTwoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String weight = weightET.getText().toString().trim();
                String height = heightET.getText().toString().trim();

                //checking if email and passwords are empty
                if(TextUtils.isEmpty(weight)){
                    Toast.makeText(getActivity(),"Please enter weight",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(height)){
                    Toast.makeText(getActivity(),"Please enter height",Toast.LENGTH_LONG).show();
                    return;
                }

                SharedPreferences prefs = getActivity().getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("weight", weight);
                editor.putString("height", height);
                editor.apply();

                onBtnClickListener.onBtnClick();
            }
        });

        return view;
    }
}
