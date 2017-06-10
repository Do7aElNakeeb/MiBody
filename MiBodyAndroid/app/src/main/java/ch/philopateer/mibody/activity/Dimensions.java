package ch.philopateer.mibody.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import ch.philopateer.mibody.object.Muscles;
import ch.philopateer.mibody.helper.SQLiteHandler;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by mamdouhelnakeeb on 1/21/17.
 */

public class Dimensions extends AppCompatActivity {

    SQLiteHandler sqLiteHandler;
    ImageView bodyImgView, bodyP1, bodyP2, bodyP3, bodyP4, bodyP5, bodyP6;
    Bitmap bitmap;

    TextView muscleName;
    EditText muscleET;
    LinearLayout dimenLayoutToFlip, statisticsBackBtn, homeBtn;
    RelativeLayout.LayoutParams params;
    RelativeLayout profileDimen;

    int Waist = 0, Legs = 0, Chest = 0, Wrest = 0, Calf = 0, Arm = 0;
    HashMap<String, Float> muscle = new HashMap<>(6);

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(ch.philopateer.mibody.R.layout.profile_dimensions);

        sqLiteHandler = new SQLiteHandler(this);


        try {
            sqLiteHandler.open();

        } catch (Exception e) {
            Log.i("hello", "hello");
        }

        try {
            ArrayList<Muscles> musclesArrayList = sqLiteHandler.getMusclesUpdates(null);
            Wrest = musclesArrayList.get(musclesArrayList.size() - 1).Wrest;
            Legs = musclesArrayList.get(musclesArrayList.size() - 1).Legs;
            Chest = musclesArrayList.get(musclesArrayList.size() - 1).Chest;
            Waist = musclesArrayList.get(musclesArrayList.size() - 1).Waist;
            Calf = musclesArrayList.get(musclesArrayList.size() - 1).Calf;
            Arm = musclesArrayList.get(musclesArrayList.size() - 1).Arm;
        }
        catch (Exception e){
            Toast.makeText(Dimensions.this, "Update your muscles dimensions", Toast.LENGTH_SHORT).show();
        }


/*
        Waist = prefs.getFloat("Waist", 0);
        Legs = prefs.getFloat("Legs", 0);
        Chest = prefs.getFloat("Chest", 0);
        Wrest = prefs.getFloat("Wrest", 0);
        Calf = prefs.getFloat("Calf", 0);
        Arm = prefs.getFloat("Arm", 0);

        muscle.put("Wrest", (float) 0);
        muscle.put("Legs", (float) 0);
        muscle.put("Chest", (float) 0);
        muscle.put("Waist", (float) 0);
        muscle.put("Calf", (float) 0);
        muscle.put("Arm", (float) 0);
*/

        bodyImgView = (ImageView) findViewById(ch.philopateer.mibody.R.id.bodyImgView);
        bodyP1 = (ImageView) findViewById(ch.philopateer.mibody.R.id.body_dimen1);
        bodyP2 = (ImageView) findViewById(ch.philopateer.mibody.R.id.body_dimen2);
        bodyP3 = (ImageView) findViewById(ch.philopateer.mibody.R.id.body_dimen3);
        bodyP4 = (ImageView) findViewById(ch.philopateer.mibody.R.id.body_dimen4);
        bodyP5 = (ImageView) findViewById(ch.philopateer.mibody.R.id.body_dimen5);
        bodyP6 = (ImageView) findViewById(ch.philopateer.mibody.R.id.body_dimen6);
        muscleName = (TextView) findViewById(ch.philopateer.mibody.R.id.muscleName);
        muscleET = (EditText) findViewById(ch.philopateer.mibody.R.id.muscleET);

        profileDimen = (RelativeLayout) findViewById(ch.philopateer.mibody.R.id.profileDimen);

        dimenLayoutToFlip = (LinearLayout) findViewById(ch.philopateer.mibody.R.id.dimenLayoutToFlip);
        statisticsBackBtn = (LinearLayout) findViewById(ch.philopateer.mibody.R.id.statisticsBkBtn);
        homeBtn = (LinearLayout) findViewById(ch.philopateer.mibody.R.id.homeBtn);

        params = new RelativeLayout.LayoutParams((int) getResources().getDimension(ch.philopateer.mibody.R.dimen.body_dimen_width), (int) getResources().getDimension(ch.philopateer.mibody.R.dimen.body_dimen_height));

        bodyImgView.setDrawingCacheEnabled(true);
        bodyImgView.buildDrawingCache(true);


        muscleET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                switch (muscleName.getText().toString()){
                    case "Wrest":
                        if(!editable.toString().equals("")) {
                            Wrest = Integer.valueOf(editable.toString());
                        }
                        break;
                    case "Legs":
                        if(!editable.toString().equals("")) {
                            Legs = Integer.valueOf(editable.toString());
                        }
                        break;
                    case "Chest":
                        if(!editable.toString().equals("")) {
                            Chest = Integer.valueOf(editable.toString());
                        }
                        break;
                    case "Waist":
                        if(!editable.toString().equals("")) {
                            Waist = Integer.valueOf(editable.toString());
                        }
                        break;
                    case "Calf":
                        if(!editable.toString().equals("")) {
                            Calf = Integer.valueOf(editable.toString());
                        }
                        break;
                    case "Arm":
                        if(!editable.toString().equals("")) {
                            Arm = Integer.valueOf(editable.toString());
                        }
                        break;
                }

                ContentValues values = new ContentValues();
                values.put(SQLiteHandler.MUSCLE_TRICEPS, Wrest);
                values.put(SQLiteHandler.MUSCLE_QUADRICEPS, Legs);
                values.put(SQLiteHandler.MUSCLE_CHEST, Chest);
                values.put(SQLiteHandler.MUSCLE_WAIST, Waist);
                values.put(SQLiteHandler.MUSCLE_CALF, Calf);
                values.put(SQLiteHandler.MUSCLE_BICEPS, Arm);
                sqLiteHandler.updateMuscle(values);
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dimensions.this, Landing.class);
                ContentValues values = new ContentValues();
                values.put(SQLiteHandler.MUSCLE_TRICEPS, Wrest);
                values.put(SQLiteHandler.MUSCLE_QUADRICEPS, Legs);
                values.put(SQLiteHandler.MUSCLE_CHEST, Chest);
                values.put(SQLiteHandler.MUSCLE_WAIST, Waist);
                values.put(SQLiteHandler.MUSCLE_CALF, Calf);
                values.put(SQLiteHandler.MUSCLE_BICEPS, Arm);
                sqLiteHandler.updateMuscle(values);
                sqLiteHandler.close();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        statisticsBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();
                values.put(SQLiteHandler.MUSCLE_TRICEPS, Wrest);
                values.put(SQLiteHandler.MUSCLE_QUADRICEPS, Legs);
                values.put(SQLiteHandler.MUSCLE_CHEST, Chest);
                values.put(SQLiteHandler.MUSCLE_WAIST, Waist);
                values.put(SQLiteHandler.MUSCLE_CALF, Calf);
                values.put(SQLiteHandler.MUSCLE_BICEPS, Arm);
                sqLiteHandler.updateMuscle(values);
                sqLiteHandler.close();
                finish();
            }
        });

        bodyImgView.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){


                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(muscleET.getWindowToken(), 0);

                dimenLayoutToFlip.setVisibility(View.GONE);

                bitmap = bodyImgView.getDrawingCache();

                int x = (int)event.getX();
                int y = (int)event.getY();
                int pixel = 0;
                if (x >= 0 && x <= bitmap.getWidth() && y >= 0 && y <= bitmap.getHeight()) {
                    pixel = bitmap.getPixel(x, y);
                }
                int flag = 0;

                if (pixel == Color.BLACK){
                    //Toast.makeText(Dimensions.this, "eshtaa311", Toast.LENGTH_SHORT).show();
                    flag = 1;
                }
                else {
                    for (int i = x - 20; i < x + 20 && i > 0 && i < bitmap.getWidth(); i++) {
                        for (int j = y - 20; j < y + 20 && j > 0 && j < bitmap.getHeight(); j++) {
                            pixel = bitmap.getPixel(i, j);
                            if (pixel == Color.BLACK) {
                                //Toast.makeText(Dimensions.this, "eshtaa3", Toast.LENGTH_SHORT).show();
                                Log.d("gotBlack", "yeah");
                                x = i;
                                y = j;
                                flag = 1;
                                break;
                            }
                        }
                        if(flag == 1){
                            break;
                        }
                    }
                }

                if (flag == 1) {
                    if (x <= bodyP1.getRight() && x >= bodyP1.getLeft()) {
                        muscleName.setText("Wrest");
                        muscleET.setText(String.valueOf(Wrest));
                        Log.d("gotBlack", "1");
                    } else if (x <= bodyP2.getRight() && x >= bodyP2.getLeft()) {
                        muscleName.setText("Legs");
                        muscleET.setText(String.valueOf(Legs));
                        Log.d("gotBlack", "2");
                    } else if (x <= bodyP3.getRight() && x >= bodyP3.getLeft()) {
                        muscleName.setText("Chest");
                        muscleET.setText(String.valueOf(Chest));
                        Log.d("gotBlack", "3");
                    } else if (x <= bodyP4.getRight() && x >= bodyP4.getLeft()) {
                        muscleName.setText("Waist");
                        muscleET.setText(String.valueOf(Waist));
                        Log.d("gotBlack", "4");
                    } else if (x <= bodyP5.getRight() && x >= bodyP5.getLeft()) {
                        muscleName.setText("Calf");
                        muscleET.setText(String.valueOf(Calf));
                        Log.d("gotBlack", "5");
                    } else if (x <= bodyP6.getRight() && x >= bodyP6.getLeft()) {
                        muscleName.setText("Arm");
                        muscleET.setText(String.valueOf(Arm));
                        Log.d("gotBlack", "6");
                    }
                    muscleET.selectAll();
                    dimenLayoutToFlip.setVisibility(View.VISIBLE);

                    params.topMargin = y + bodyImgView.getTop() - dimenLayoutToFlip.getHeight();

                    params.leftMargin = x - (dimenLayoutToFlip.getWidth() / 2 );
                    Log.d("margins", String.valueOf(params.leftMargin));

                    dimenLayoutToFlip.setLayoutParams(params);

                    InputMethodManager imm2 = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm2.showSoftInput(muscleET, InputMethodManager.SHOW_FORCED);

                }

                return true;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        try {
            sqLiteHandler.open();

        } catch (Exception e) {
            Log.i("hello", "hello");
        }

    }
}
