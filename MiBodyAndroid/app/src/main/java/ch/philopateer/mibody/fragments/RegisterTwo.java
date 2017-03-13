package ch.philopateer.mibody.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.aigestudio.wheelpicker.widgets.WheelDatePicker;

import ch.philopateer.mibody.R;
import ch.philopateer.mibody.activity.Register;
import ch.philopateer.mibody.listener.OnBtnClickListener;

import java.util.Date;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by mamdouhelnakeeb on 12/2/16.
 */

public class RegisterTwo extends Fragment {

    ToggleButton genderMale, genderFemale;
    //EditText weightET, heightET;
    Button regTwoBtn;
    WheelDatePicker DoBPicker;

    public String gender = "male", dob = ""; //, weight, height;


    OnBtnClickListener onBtnClickListener;

    public void initListener(OnBtnClickListener onBtnClickListener){
        this.onBtnClickListener = onBtnClickListener;
    }

    public RegisterTwo(){

    }

    public RegisterTwo(String gender, String dob){
        this.gender = gender;
        this.dob = dob;
        /*
        this.weight = weight;
        this.height = height;
        */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.register_two, container, false);

        genderMale = (ToggleButton) view.findViewById(R.id.genderMaleTB);
        genderFemale = (ToggleButton) view.findViewById(R.id.genderFemaleTB);
        /*
        weightET = (EditText) view.findViewById(R.id.weightTxtField);
        heightET = (EditText) view.findViewById(R.id.heightTxtField);
*/
        DoBPicker = (WheelDatePicker) view.findViewById(R.id.DoB_picker);

        regTwoBtn = (Button) view.findViewById(R.id.regTwoBtn);

        genderFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genderFemale.setChecked(true);
                genderFemale.setBackgroundResource(R.drawable.female_on);
                genderMale.setChecked(false);
                genderMale.setBackgroundResource(R.drawable.male_off);
                gender = "female";
            }
        });

        genderMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genderFemale.setChecked(false);
                genderFemale.setBackgroundResource(R.drawable.female_off);
                genderMale.setChecked(true);
                genderMale.setBackgroundResource(R.drawable.male_on);
                gender = "male";
            }
        });

        DoBPicker.setSelectedDay(1);
        DoBPicker.setMonth(6);
        DoBPicker.setYear(1995);

        DoBPicker.setYearStart(1970);
        DoBPicker.setYearEnd(2000);

        if (!dob.equals("")) {
            updateFields();
        }
        else {
            dob = "1/6/1995";
        }

        DoBPicker.setOnDateSelectedListener(new WheelDatePicker.OnDateSelectedListener() {
            @Override
            public void onDateSelected(WheelDatePicker picker, Date date) {
                dob = String.valueOf(picker.getCurrentDay()) + "/" + String.valueOf(picker.getCurrentMonth()) + "/" + String.valueOf(picker.getCurrentYear());
            }
        });

        regTwoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*
                weight = weightET.getText().toString().trim();
                height = heightET.getText().toString().trim();
*/
                //checking if email and passwords are empty
                /*
                if(TextUtils.isEmpty(weight)){
                    Toast.makeText(getActivity(),"Please enter weight",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(height)){
                    Toast.makeText(getActivity(),"Please enter height",Toast.LENGTH_LONG).show();
                    return;
                }
*/

                ((Register)getActivity()).getViewPager().setCurrentItem(2);
                //onBtnClickListener.onBtnClick();
            }
        });

        return view;
    }

    private void updateFields(){
        if (gender.equals("male")) {
            genderFemale.setChecked(true);
            genderFemale.setBackgroundResource(R.drawable.female_off);
            genderMale.setChecked(false);
            genderMale.setBackgroundResource(R.drawable.male_on);
        }
        else {
            genderFemale.setChecked(true);
            genderFemale.setBackgroundResource(R.drawable.female_on);
            genderMale.setChecked(false);
            genderMale.setBackgroundResource(R.drawable.male_off);
        }

        String tmpDoB = dob;

        DoBPicker.setSelectedDay(Integer.parseInt(tmpDoB.substring(0, tmpDoB.indexOf('/'))));
        tmpDoB  = tmpDoB.replace(tmpDoB.substring(0, tmpDoB.indexOf('/') + 1), "");

        DoBPicker.setMonth(Integer.parseInt(tmpDoB.substring(0, tmpDoB.indexOf('/'))));
        tmpDoB  = tmpDoB.replace(tmpDoB.substring(0, tmpDoB.indexOf('/') + 1), "");

        DoBPicker.setYear(Integer.parseInt(tmpDoB.substring(0, 4)));
/*
        weightET.setText(weight);
        heightET.setText(height);
        */
    }
}
