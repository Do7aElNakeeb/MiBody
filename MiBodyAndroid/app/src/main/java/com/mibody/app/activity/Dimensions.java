package com.mibody.app.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mibody.app.R;
import com.mibody.app.app.Muscles;
import com.mibody.app.helper.SQLiteHandler;

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

    float Waist = 0, Quadriceps = 0, Chest = 0, Triceps = 0, Calf = 0, Biceps = 0;
    HashMap<String, Float> muscle = new HashMap<>(6);

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_dimensions);

        sqLiteHandler = new SQLiteHandler(this);


        try {
            sqLiteHandler.open();

        } catch (Exception e) {
            Log.i("hello", "hello");
        }

        try {
            ArrayList<Muscles> musclesArrayList = sqLiteHandler.getMusclesUpdates(null);
            Triceps = musclesArrayList.get(musclesArrayList.size() - 1).Triceps;
            Quadriceps = musclesArrayList.get(musclesArrayList.size() - 1).Quadriceps;
            Chest = musclesArrayList.get(musclesArrayList.size() - 1).Chest;
            Waist = musclesArrayList.get(musclesArrayList.size() - 1).Waist;
            Calf = musclesArrayList.get(musclesArrayList.size() - 1).Calf;
            Biceps = musclesArrayList.get(musclesArrayList.size() - 1).Biceps;
        }
        catch (Exception e){
            Toast.makeText(Dimensions.this, "Update your muscles dimensions", Toast.LENGTH_SHORT).show();
        }


/*
        Waist = prefs.getFloat("Waist", 0);
        Quadriceps = prefs.getFloat("Quadriceps", 0);
        Chest = prefs.getFloat("Chest", 0);
        Triceps = prefs.getFloat("Triceps", 0);
        Calf = prefs.getFloat("Calf", 0);
        Biceps = prefs.getFloat("Biceps", 0);

        muscle.put("Triceps", (float) 0);
        muscle.put("Quadriceps", (float) 0);
        muscle.put("Chest", (float) 0);
        muscle.put("Waist", (float) 0);
        muscle.put("Calf", (float) 0);
        muscle.put("Biceps", (float) 0);
*/

        bodyImgView = (ImageView) findViewById(R.id.bodyImgView);
        bodyP1 = (ImageView) findViewById(R.id.body_dimen1);
        bodyP2 = (ImageView) findViewById(R.id.body_dimen2);
        bodyP3 = (ImageView) findViewById(R.id.body_dimen3);
        bodyP4 = (ImageView) findViewById(R.id.body_dimen4);
        bodyP5 = (ImageView) findViewById(R.id.body_dimen5);
        bodyP6 = (ImageView) findViewById(R.id.body_dimen6);
        muscleName = (TextView) findViewById(R.id.muscleName);
        muscleET = (EditText) findViewById(R.id.muscleET);

        profileDimen = (RelativeLayout) findViewById(R.id.profileDimen);

        dimenLayoutToFlip = (LinearLayout) findViewById(R.id.dimenLayoutToFlip);
        statisticsBackBtn = (LinearLayout) findViewById(R.id.statisticsBkBtn);
        homeBtn = (LinearLayout) findViewById(R.id.homeBtn);

        params = new RelativeLayout.LayoutParams((int) getResources().getDimension(R.dimen.body_dimen_width), (int) getResources().getDimension(R.dimen.body_dimen_height));

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
                    case "Triceps":
                        if(!editable.toString().equals("")) {
                            Triceps = Float.valueOf(editable.toString());
                        }
                        break;
                    case "Quadriceps":
                        if(!editable.toString().equals("")) {
                            Quadriceps = Float.valueOf(editable.toString());
                        }
                        break;
                    case "Chest":
                        if(!editable.toString().equals("")) {
                            Chest = Float.valueOf(editable.toString());
                        }
                        break;
                    case "Waist":
                        if(!editable.toString().equals("")) {
                            Waist = Float.valueOf(editable.toString());
                        }
                        break;
                    case "Calf":
                        if(!editable.toString().equals("")) {
                            Calf = Float.valueOf(editable.toString());
                        }
                        break;
                    case "Biceps":
                        if(!editable.toString().equals("")) {
                            Biceps = Float.valueOf(editable.toString());
                        }
                        break;
                }
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dimensions.this, Landing.class);
                ContentValues values = new ContentValues();
                values.put(SQLiteHandler.MUSCLE_TRICEPS, Triceps);
                values.put(SQLiteHandler.MUSCLE_QUADRICEPS, Quadriceps);
                values.put(SQLiteHandler.MUSCLE_CHEST, Chest);
                values.put(SQLiteHandler.MUSCLE_WAIST, Waist);
                values.put(SQLiteHandler.MUSCLE_CALF, Calf);
                values.put(SQLiteHandler.MUSCLE_BICEPS, Biceps);
                sqLiteHandler.updateMuscle(values);
                sqLiteHandler.close();
                startActivity(intent);
            }
        });

        statisticsBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dimensions.this, ExercisesActivity.class);
                ContentValues values = new ContentValues();
                values.put(SQLiteHandler.MUSCLE_TRICEPS, Triceps);
                values.put(SQLiteHandler.MUSCLE_QUADRICEPS, Quadriceps);
                values.put(SQLiteHandler.MUSCLE_CHEST, Chest);
                values.put(SQLiteHandler.MUSCLE_WAIST, Waist);
                values.put(SQLiteHandler.MUSCLE_CALF, Calf);
                values.put(SQLiteHandler.MUSCLE_BICEPS, Biceps);
                sqLiteHandler.updateMuscle(values);
                sqLiteHandler.close();
                startActivity(intent);
            }
        });

        bodyImgView.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){


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
                        muscleName.setText("Triceps");
                        muscleET.setText(String.valueOf(Triceps));
                        Log.d("gotBlack", "1");
                    } else if (x <= bodyP2.getRight() && x >= bodyP2.getLeft()) {
                        muscleName.setText("Quadriceps");
                        muscleET.setText(String.valueOf(Quadriceps));
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
                        muscleName.setText("Biceps");
                        muscleET.setText(String.valueOf(Biceps));
                        Log.d("gotBlack", "6");
                    }
                    dimenLayoutToFlip.setVisibility(View.VISIBLE);

                    params.topMargin = y + bodyImgView.getTop() - dimenLayoutToFlip.getHeight();

                    params.leftMargin = x - (dimenLayoutToFlip.getWidth() / 2 );
                    Log.d("margins", String.valueOf(params.leftMargin));

                    dimenLayoutToFlip.setLayoutParams(params);

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
