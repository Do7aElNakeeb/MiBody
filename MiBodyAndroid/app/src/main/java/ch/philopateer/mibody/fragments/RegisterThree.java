package ch.philopateer.mibody.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import ch.philopateer.mibody.R;
import ch.philopateer.mibody.listener.OnBtnClickListener;

/**
 * Created by mamdouhelnakeeb on 1/8/17.
 */

public class RegisterThree extends Fragment {

    TextView cmTxt, lbTxt, kgTxt, inchTxt, BMITxt;
    Button regThreeBtn;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    OnBtnClickListener onBtnClickListener;
    public int units = 0;
    public String BMI = "";

    public float height = 0, weight = 0;

    public void initListener(OnBtnClickListener onBtnClickListener){
        this.onBtnClickListener = onBtnClickListener;
    }

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

        BMITxt.setText(String.valueOf(BMIeqn(height, weight)));

        regThreeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBtnClickListener.onBtnClick();

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