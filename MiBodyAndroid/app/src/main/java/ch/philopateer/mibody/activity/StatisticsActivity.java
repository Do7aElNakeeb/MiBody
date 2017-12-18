package ch.philopateer.mibody.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import ch.philopateer.mibody.R;
import ch.philopateer.mibody.app.AppConfig;
import ch.philopateer.mibody.fragments.StatisticsGraph;
import ch.philopateer.mibody.fragments.StatisticsResults;
import ch.philopateer.mibody.adapter.ViewPagerAdapter;
import ch.philopateer.mibody.object.WorkoutExItem;
import ch.philopateer.mibody.adapter.StatisticsExAdapter;
import ch.philopateer.mibody.object.WorkoutItem;
import me.relex.circleindicator.CircleIndicator;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by mamdouhelnakeeb on 2/7/17.
 */

public class StatisticsActivity extends AppCompatActivity{

    // TODO time graph

    private ProgressDialog pDialog;

    RelativeLayout homeBtn;
    LinearLayout homeBtnLL;
    ImageView filterBtn, userImage;
    TextView nameTV, heightTV, heightUnitTV, weightTv, weightUnitTV, ageTV, imcTV;
    RecyclerView exercisesStatisticsRV, performStatisticsRV;
    FrameLayout yAxisFL;

    StatisticsExAdapter statisticsExAdapter;

    FrameLayout blackFL;
    LinearLayout filterMenuLL;

    SharedPreferences prefs;

    int maxReps = 0, maxTIme = 0;
    ArrayList<WorkoutExItem> workoutExItemArrayListObj;
    ArrayList<Integer> workoutExItemArrayListAch;
    
    FirebaseAuth firebaseAuth;
    StorageReference photoReference;

    public WorkoutItem workoutItem = null;

    long actTotalTime = 0;
    int wTime = 0;

    int resultQuote = 0;

    ViewPager statisticsVP;
    CircleIndicator circleIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_activity);

        prefs = getSharedPreferences("UserDetails", MODE_PRIVATE);

        Bundle bundle = getIntent().getExtras();
        if (bundle.containsKey("workoutItemStats")) {
            workoutItem = (WorkoutItem) getIntent().getParcelableExtra("workoutItemStats");
            workoutItem.JSONtoArray();
            Log.d("workoutItemExArSize2", String.valueOf(workoutItem.exercisesList.size()));
        }

        initExStatisticsRV();

        statisticsVP = (ViewPager) findViewById(R.id.statisticsVP);
        circleIndicator = (CircleIndicator) findViewById(R.id.page_indicator);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        Log.d("wTime0", String.valueOf(wTime));

        viewPagerAdapter.addFragment(new StatisticsResults((int) actTotalTime, Integer.parseInt(prefs.getString("weight", "75")), workoutItem.wTime, resultQuote), "1");
        viewPagerAdapter.addFragment(new StatisticsGraph(workoutItem, maxReps, maxTIme), "2");
        statisticsVP.setAdapter(viewPagerAdapter);
        circleIndicator.setViewPager(statisticsVP);


    }

    private void initExStatisticsRV(){

        int actTotReps = 0, exTotReps = 0, actTotTime = 0, exTotTime = 0;

        for (int i = 0; i < workoutItem.exercisesList.size(); i++){


            actTotalTime += workoutItem.exercisesList.get(i).actualExTime;

            if (workoutItem.exercisesList.get(i).repsTimeBool) {
                actTotTime += workoutItem.exercisesList.get(i).actualExTime;
                exTotTime += workoutItem.exercisesList.get(i).exTime;
            }
            else {
                actTotReps += workoutItem.exercisesList.get(i).actualReps;
                exTotReps += workoutItem.exercisesList.get(i).reps;
            }

            if (workoutItem.exercisesList.get(i).reps >= maxReps) {
                maxReps = workoutItem.exercisesList.get(i).reps;
            }
            if (workoutItem.exercisesList.get(i).actualReps >= maxReps) {
                maxReps = workoutItem.exercisesList.get(i).actualReps;
            }

            if (workoutItem.exercisesList.get(i).actualExTime >= maxTIme) {
                maxTIme = (int) workoutItem.exercisesList.get(i).actualExTime;
            }

        }

        if (actTotReps > exTotReps && actTotTime == exTotTime){
            resultQuote = 0;
        }
        else if (actTotReps == exTotReps && actTotTime == exTotTime){
            resultQuote = 1;
        }
        else {
            resultQuote = 2;
        }

    }

    public ViewPager getViewPager() {
        if (null == statisticsVP) {
            statisticsVP = (ViewPager) findViewById(R.id.viewpager);
        }
        return statisticsVP;
    }

    private void updateUserDetails(){

        photoCircled();
        nameTV.setText(prefs.getString("name", "Guest"));
        weightTv.setText(prefs.getString("weight", "75"));
        heightTV.setText(prefs.getString("height", "160"));
        String BMI = prefs.getString("BMI", "20.34");
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

        if (!prefs.getString("fbIDCon", "").equals("true")){
            photoReference = photoReference.child("userImages/" + firebaseAuth.getCurrentUser().getUid() + ".jpg");
            Log.d("photoRef", photoReference.getPath());
            Glide.with(this).using(new FirebaseImageLoader()).load(photoReference).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    userImage.setImageBitmap(resource);

                    Matrix matrix = userImage.getImageMatrix();
                    float scale;

                    if (userImage.getDrawable().getIntrinsicWidth() * userImage.getHeight() > userImage.getDrawable().getIntrinsicHeight() * userImage.getWidth()) {
                        scale = (float) userImage.getHeight() / (float) userImage.getDrawable().getIntrinsicHeight();
                    } else {
                        scale = (float) userImage.getWidth() / (float) userImage.getDrawable().getIntrinsicWidth();
                    }

                    matrix.setScale(scale, scale);
                    userImage.setImageMatrix(matrix);

                    try {

                        Bitmap imageRounded = Bitmap.createBitmap(userImage.getDrawingCache().getWidth(), userImage.getDrawingCache().getHeight(), userImage.getDrawingCache().getConfig());
                        Canvas canvas = new Canvas(imageRounded);
                        Paint mpaint = new Paint();
                        mpaint.setAntiAlias(true);
                        mpaint.setShader(new BitmapShader(userImage.getDrawingCache(), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
                        canvas.drawRoundRect((new RectF(0, 0, userImage.getDrawingCache().getWidth(), userImage.getDrawingCache().getHeight())), 50, 50, mpaint);// Round Image Corner 100 100 100 100
                        userImage.setImageBitmap(imageRounded);
                    }
                    catch (Exception e){

                    }
                }
            });

        }
        else {
            new Picasso.Builder(this)
                    .downloader(new OkHttpDownloader(this, Integer.MAX_VALUE))
                    .build()
                    .load(AppConfig.fbPhotoURL + prefs.getString("fbID", "") + AppConfig.fbPhotoConginf)
                    .into(target);
        }


    }

    private int calcAge(){
        int age = 0;
        Calendar calendar = Calendar.getInstance();
        String dob = prefs.getString("dob", "1/6/1995");
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