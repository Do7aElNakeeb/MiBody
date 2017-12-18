package ch.philopateer.mibody.fragments;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import ch.philopateer.mibody.R;
import ch.philopateer.mibody.activity.Landing;
import ch.philopateer.mibody.activity.StatisticsActivity;
import ch.philopateer.mibody.app.AppConfig;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by mamdouhelnakeeb on 8/26/17.
 */

public class StatisticsResults extends Fragment {

    TextView caloriesTV, timeTypeTV, timeTV, resultQuoteTV;
    LinearLayout checkStatsLL, shareWorkoutLL;
    CardView mainMenuCV;

    int MET = 8;
    float totalTime = 0, wTime = 0;
    float weight = 0;

    int resultQuote = 0;

    SharedPreferences prefs;
    public StatisticsResults(int totalTime, int weight, int wTime, int resultQuote){
        this.totalTime = totalTime;
        this.weight = weight;
        this.wTime = wTime;
        this.resultQuote = resultQuote;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        prefs = getActivity().getSharedPreferences("UserDetails", MODE_PRIVATE);

        View view = inflater.inflate(R.layout.statistics_results, container, false);

        caloriesTV = (TextView) view.findViewById(R.id.caloriesTV);
        timeTypeTV = (TextView) view.findViewById(R.id.timeTypeTV);
        timeTV = (TextView) view.findViewById(R.id.timeTV);
        checkStatsLL = (LinearLayout) view.findViewById(R.id.checkStatsLL);
        shareWorkoutLL = (LinearLayout) view.findViewById(R.id.shareWorkoutLL);
        mainMenuCV = (CardView) view.findViewById(R.id.mainMenuCV);
        resultQuoteTV = (TextView) view.findViewById(R.id.resultQuoteTV);

        caloriesTV.setText(String.valueOf(calcCalories()));
        resultQuoteTV.setText(AppConfig.resultsQuote[resultQuote]);

        if ((int) wTime / 60 == 0) {
            timeTypeTV.setText("Seconds");
            timeTV.setText(String.valueOf((int) Math.ceil(wTime)));
        }
        else {

            timeTV.setText(String.valueOf((int) Math.ceil(wTime / 60)));
        }

        Log.d("wTime1", String.valueOf(wTime));

        checkStatsLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((StatisticsActivity)getActivity()).getViewPager().setCurrentItem(1);
            }
        });

        shareWorkoutLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);

                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                }
                else {
                    shareImage();

                }
            }
        });

        mainMenuCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), Landing.class));
            }
        });



        return view;
    }

    private int calcCalories(){

        String gender = prefs.getString("gender", "male");
        float age = calcAge();
        float heartRate = 0;

        if (age >= 20 && age < 30) {
            heartRate = AppConfig.heartRatesSA.get(20);
        }
        else if (age >= 30 && age < 35) {
            heartRate = AppConfig.heartRatesSA.get(30);
        }
        else if (age >= 35 && age < 40) {
            heartRate = AppConfig.heartRatesSA.get(35);
        }
        else if (age >= 40 && age < 45) {
            heartRate = AppConfig.heartRatesSA.get(40);
        }
        else if (age >= 45 && age < 50) {
            heartRate = AppConfig.heartRatesSA.get(45);
        }
        else if (age >= 50 && age < 55) {
            heartRate = AppConfig.heartRatesSA.get(50);
        }
        else if (age >= 55 && age < 60) {
            heartRate = AppConfig.heartRatesSA.get(55);
        }
        else if (age >= 60 && age < 65) {
            heartRate = AppConfig.heartRatesSA.get(60);
        }
        else if (age >= 65 && age < 70) {
            heartRate = AppConfig.heartRatesSA.get(65);
        }
        else if (age >= 70) {
            heartRate = AppConfig.heartRatesSA.get(70);
        }

        Log.d("caloriesParams", gender + " - " + String.valueOf(age) + " - " + String.valueOf(weight) + " - " + String.valueOf(heartRate) + " - " + String.valueOf(totalTime / 60));

        if (gender.equals("male") || gender.equals("Male")) {
            return (int) (((age * 0.2017) + (weight * 0.1988) + (heartRate * 0.6309) - 55.0969) * (totalTime / 60) / 4.184);
        }
        else {

            return (int) (((age * 0.074) - (weight * 0.1263) + (heartRate * 0.4472) - 20.4022) * (totalTime / 60) / 4.184);
        }

        //return (int) (0.0175 * MET * weight * totalTime);
    }

    public static Bitmap getScreenShot(View view) {
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        return bitmap;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100) {
            shareImage();
        }
        else {
            Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    public static void store(Bitmap bm, String fileName){

        final String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Screenshots";
        File dir = new File(dirPath);
        if(!dir.exists())
            dir.mkdirs();
        File file = new File(dirPath, fileName);
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 85, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void shareImage(){
        View rootView = getActivity().getWindow().getDecorView().findViewById(android.R.id.content);
        getScreenShot(rootView);

        String workName;
        if (((StatisticsActivity) getActivity()).workoutItem != null) {
            workName = ((StatisticsActivity) getActivity()).workoutItem.workoutName;
        } else {
            workName = "My Results";
        }

        String bitmapPath = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), getScreenShot(rootView), workName, null);
        Uri bitmapUri = Uri.parse(bitmapPath);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/png");
        intent.putExtra(Intent.EXTRA_STREAM, bitmapUri);

        try {
            startActivity(Intent.createChooser(intent, "Share Workout Result"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getActivity(), "No App Available", Toast.LENGTH_SHORT).show();
        }
    }

    private int calcAge(){
        int age = 0;
        Calendar calendar = Calendar.getInstance();
        String dob = getActivity().getSharedPreferences("UserDetails", Context.MODE_PRIVATE).getString("dob", "1/6/1995");
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
