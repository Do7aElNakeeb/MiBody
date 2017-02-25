package ch.philopateer.mibody.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import ch.philopateer.mibody.R;
import ch.philopateer.mibody.app.AppConfig;
import ch.philopateer.mibody.app.WorkoutExItem;
import ch.philopateer.mibody.helper.StatisticsExAdapter;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by mamdouhelnakeeb on 2/7/17.
 */

public class StatisticsActivity extends AppCompatActivity{

    private ProgressDialog pDialog;

    RelativeLayout dimensionsBtn, homeBtn;
    ImageView filterBtn, userImage;
    TextView nameTV, heightTV, heightUnitTV, weightTv, weightUnitTV, ageTV, imcTV;
    RecyclerView exercisesStatisticsRV, performStatisticsRV;
    FrameLayout yAxisFL;

    StatisticsExAdapter statisticsExAdapter;

    FrameLayout blackFL;
    LinearLayout filterMenuLL;
    TextView filterExTxt, filterMusTxt;

    SharedPreferences prefs;

    int maxReps = 0;
    ArrayList<WorkoutExItem> workoutExItemArrayListObj;
    ArrayList<Integer> workoutExItemArrayListAch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_activity);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        prefs = getSharedPreferences("UserDetails", MODE_PRIVATE);

        dimensionsBtn = (RelativeLayout) findViewById(R.id.dimensionBtn);
        homeBtn = (RelativeLayout) findViewById(R.id.homeBtn);
        filterBtn = (ImageView) findViewById(R.id.statisticsFilterBtn);
        userImage = (ImageView) findViewById(R.id.userIV);
        nameTV = (TextView) findViewById(R.id.userNameTxtView);
        heightTV = (TextView) findViewById(R.id.userHeightTxtView);
        heightUnitTV = (TextView) findViewById(R.id.userHeightUnitTxtView);
        weightTv = (TextView) findViewById(R.id.userWeightTxtView);
        weightUnitTV = (TextView) findViewById(R.id.userWeightUnitTxtView);
        ageTV = (TextView) findViewById(R.id.ageTxtView);
        imcTV = (TextView) findViewById(R.id.imcTxtView);
        yAxisFL = (FrameLayout) findViewById(R.id.yAxisFL);

        blackFL = (FrameLayout) findViewById(R.id.blackLayout);
        filterMenuLL = (LinearLayout) findViewById(R.id.statisticsFilterMenuLL);
        filterExTxt = (TextView) findViewById(R.id.statisticsFbyEx);
        filterMusTxt = (TextView) findViewById(R.id.statisticsFbyMuscle);

        exercisesStatisticsRV = (RecyclerView) findViewById(R.id.exercisesStatisticsRV);
        performStatisticsRV = (RecyclerView) findViewById(R.id.performStatisticsRV);

        updateUserDetails();
        initExStatisticsRV();

        dimensionsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StatisticsActivity.this, Dimensions.class);
                //startActivity(intent);
                finish();
            }
        });

        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (filterMenuLL.getVisibility() == View.VISIBLE){
                    blackFL.setVisibility(View.GONE);
                    filterMenuLL.setVisibility(View.GONE);
                    filterBtn.setImageResource(R.drawable.filter_icon);
                }
                else {
                    blackFL.setVisibility(View.VISIBLE);
                    filterMenuLL.setVisibility(View.VISIBLE);
                    filterBtn.setImageResource(R.drawable.filter_icon_white);
                }
            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StatisticsActivity.this, Landing.class);
                startActivity(intent);
                finish();
            }
        });

        blackFL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                blackFL.setVisibility(View.GONE);
                filterMenuLL.setVisibility(View.GONE);
                filterBtn.setImageResource(R.drawable.filter_icon);
            }
        });

        filterExTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(StatisticsActivity.this, "Exercise Statistics", Toast.LENGTH_SHORT).show();
            }
        });

        filterMusTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(StatisticsActivity.this, "Muscle Statistics", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void initExStatisticsRV(){

        pDialog.setMessage("Loading ...");
        showDialog();
        workoutExItemArrayListAch = new ArrayList<Integer>();
        workoutExItemArrayListObj = new ArrayList<WorkoutExItem>();
        SharedPreferences objPrefs = getSharedPreferences("ExObjStatistics", Context.MODE_PRIVATE);

        SharedPreferences achPrefs = getSharedPreferences("ExAchStatistics", Context.MODE_PRIVATE);


        for (int i = 0; i < AppConfig.exercises_names.length; i++){

            if(objPrefs.getInt(AppConfig.exercises_names[i], 0) != 0) {
                workoutExItemArrayListObj.add(new WorkoutExItem(AppConfig.exercises_names[i], "", "", "", "", objPrefs.getInt(AppConfig.exercises_names[i], 0), 0, 0));
                workoutExItemArrayListAch.add(achPrefs.getInt(AppConfig.exercises_names[i], 0));
            /*
            workoutExItemArrayListObj.get(i).name = AppConfig.exercises_names[i];
            workoutExItemArrayListObj.get(i).reps = objPrefs.getInt(AppConfig.exercises_names[i], 0);
            workoutExItemArrayListAch.set(i, achPrefs.getInt(AppConfig.exercises_names[i], 0));
            */
                if (workoutExItemArrayListObj.get(workoutExItemArrayListObj.size()-1).reps >= maxReps) {
                    maxReps = workoutExItemArrayListObj.get(workoutExItemArrayListObj.size()-1).reps;
                }
                if (workoutExItemArrayListAch.get(workoutExItemArrayListAch.size()-1) >= maxReps) {
                    maxReps = workoutExItemArrayListAch.get(workoutExItemArrayListAch.size()-1);
                }

            }

        }
/*
        for (int i=0; i< workoutExItemArrayListAch.size(); i++){
            workoutExItemArrayListAch.set(i, workoutExItemArrayListAch.get(i));
            if (workoutExItemArrayListAch.get(i) >= maxReps){
                maxReps = workoutExItemArrayListAch.get(i);
            }
        }
*/
        yAxisFL.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                yAxisFL.getViewTreeObserver().removeOnPreDrawListener(this);

                statisticsExAdapter = new StatisticsExAdapter(StatisticsActivity.this, yAxisFL.getHeight(), maxReps, workoutExItemArrayListObj, workoutExItemArrayListAch);
                exercisesStatisticsRV.setLayoutManager(new LinearLayoutManager(StatisticsActivity.this, LinearLayoutManager.HORIZONTAL, false));
                exercisesStatisticsRV.setAdapter(statisticsExAdapter);
                hideDialog();
                return true;
            }
        });

    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private void updateUserDetails(){

        photoCircled();
        nameTV.setText(prefs.getString("name", "Guest"));
        weightTv.setText(prefs.getString("weight", "75"));
        heightTV.setText(prefs.getString("height", "160"));
        String BMI = prefs.getString("BMI", "20");
        imcTV.setText(BMI.substring(0, BMI.indexOf('.') + 2));

        ageTV.setText(String.valueOf(calcAge()));

        if (prefs.getString("units", "0").equals("0")){
            weightUnitTV.setText("Kg");
            heightUnitTV.setText("Cm");
        }
        else {
            weightUnitTV.setText("Lb");
            heightUnitTV.setText("Inch");
        }
    }

    private void photoCircled(){

        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                userImage.setImageBitmap(bitmap);

                Matrix matrix = userImage.getImageMatrix();
                float scale;

                if (userImage.getDrawable().getIntrinsicWidth() * userImage.getHeight() > userImage.getDrawable().getIntrinsicHeight() * userImage.getWidth()) {
                    scale = (float) userImage.getHeight() / (float) userImage.getDrawable().getIntrinsicHeight();
                } else {
                    scale = (float) userImage.getWidth() / (float) userImage.getDrawable().getIntrinsicWidth();
                }

                matrix.setScale(scale, scale);
                userImage.setImageMatrix(matrix);

                Bitmap imageRounded = Bitmap.createBitmap(userImage.getDrawingCache().getWidth(), userImage.getDrawingCache().getHeight(), userImage.getDrawingCache().getConfig());
                Canvas canvas = new Canvas(imageRounded);
                Paint mpaint = new Paint();
                mpaint.setAntiAlias(true);
                mpaint.setShader(new BitmapShader(userImage.getDrawingCache(), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
                canvas.drawRoundRect((new RectF(0, 0, userImage.getDrawingCache().getWidth(), userImage.getDrawingCache().getHeight())), 50, 50, mpaint);// Round Image Corner 100 100 100 100
                userImage.setImageBitmap(imageRounded);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };

        userImage.setTag(target);
        userImage.setDrawingCacheEnabled(true);

        new Picasso.Builder(this).downloader(new OkHttpDownloader(this, Integer.MAX_VALUE)).build().load(AppConfig.URL_SERVER + "userPhotos/" + prefs.getString("user_id", "") + ".png")
                .into(target);

    }

    private int calcAge(){
        int age = 0;
        Calendar calendar = Calendar.getInstance();
        String dob = prefs.getString("dob", "");
        int dobDay = Integer.parseInt(dob.substring(0, dob.indexOf('/')));
        dob  = dob.replace(dob.substring(0, dob.indexOf('/') + 1), "");
        int dobMonth = Integer.parseInt(dob.substring(0, dob.indexOf('/')));
        dob  = dob.replace(dob.substring(0, dob.indexOf('/') + 1), "");
        int dobYear = Integer.parseInt(dob.substring(0, 4));

        int curDay = calendar.get(Calendar.DAY_OF_MONTH);
        int curMonth = calendar.get(Calendar.MONTH);
        int curYear = calendar.get(Calendar.YEAR);

        if (curMonth > dobMonth){
            age = curYear - dobYear;
        }
        else if (curMonth < dobMonth){
            age = curYear - dobYear - 1;
        }
        else if (curMonth == dobMonth){
            if (curDay >= dobDay){
                age = curYear - dobYear;
            }
            else {
                age = curYear - dobYear - 1;
            }
        }

        return age;
    }
}