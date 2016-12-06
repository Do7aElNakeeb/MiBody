package com.mibody.app.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import java.util.Date;

/**
 * Created by mamdouhelnakeeb on 12/2/16.
 */

public class RegisterTwo extends Fragment {

    ToggleButton genderMale, genderFemale;
    EditText weightET, heightET;
    Button regTwoBtn;
    WheelDatePicker DoBPicker;


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
                Toast.makeText(getActivity(), date.toString(), Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }
}
