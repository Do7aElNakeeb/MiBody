package ch.philopateer.mibody.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import ch.philopateer.mibody.R;
import ch.philopateer.mibody.adapter.LandingVideosAdapter;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;

/**
 * Created by mamdouhelnakeeb on 11/30/16.
 */

public class Landing extends AppCompatActivity {

    private SlidingUpPanelLayout slidingLayout;

    int currVidPosition = 0;
    RecyclerView landingVideosRV;
    Button previousBtn, nextBtn;

    CardView landingExerciseBtn, landingWorkoutsBtn, landingProfileBtn, landingSettingsBtn;
    ImageView landingFbBtn, landingInstaBtn, landingUTubeBtn, landingTwitterBtn, landingSiteBtn;

    String videoKeys[] = {"BPhvUwyNHaY"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_activity);


        //set layout slide listener
        slidingLayout = (SlidingUpPanelLayout)findViewById(R.id.sliding_layout);

        landingExerciseBtn = (CardView) slidingLayout.findViewById(R.id.landingExercisesBtn);
        landingWorkoutsBtn = (CardView) slidingLayout.findViewById(R.id.landingWorkoutsBtn);
        landingSiteBtn = (ImageView) slidingLayout.findViewById(R.id.landingSiteBtn);
        landingFbBtn = (ImageView) slidingLayout.findViewById(R.id.landingFbBtn);
        landingInstaBtn = (ImageView) slidingLayout.findViewById(R.id.landingInstaBtn);
        landingTwitterBtn = (ImageView) slidingLayout.findViewById(R.id.landingTwitterBtn);
        landingUTubeBtn = (ImageView) slidingLayout.findViewById(R.id.landingUTubeBtn);
        landingProfileBtn = (CardView) slidingLayout.findViewById(R.id.landingProfileBtn);
        landingSettingsBtn = (CardView)slidingLayout.findViewById(R.id.landingSettingsBtn);

        previousBtn = (Button) findViewById(R.id.previousVideo);
        nextBtn = (Button) findViewById(R.id.nextVideo);

        landingVideosRV = (RecyclerView) findViewById(R.id.landingVideosRV);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        landingVideosRV.setLayoutManager(linearLayoutManager);

        final ArrayList<String> videosArrayList = new ArrayList<String>();
        for (int i = 0; i < videoKeys.length; i++) {
            videosArrayList.add(videoKeys[i]);
        }

        landingProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        landingExerciseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ExercisesActivity.class);
                startActivity(intent);
            }
        });

        landingWorkoutsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), WorkoutsActivity.class);
                startActivity(intent);
            }
        });

        landingSettingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Settings.class);
                startActivity(intent);
            }
        });

        landingSiteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://www.mibody.ch";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        landingFbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url;
                PackageManager packageManager = getApplicationContext().getPackageManager();
                try {
                    int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
                    if (versionCode >= 3002850) { //newer versions of fb app
                        url =  "fb://facewebmodal/f?href=https://www.facebook.com/mibody.ch";
                    } else { //older versions of fb app
                        url =  "fb://page/mibody.ch";
                    }
                } catch (PackageManager.NameNotFoundException e) {
                    url =  "https://www.facebook.com/mibody.ch"; //normal web url
                }

                Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                String facebookUrl = url;
                facebookIntent.setData(Uri.parse(facebookUrl));
                startActivity(facebookIntent);
            }
        });

        landingInstaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("http://instagram.com/_u/mibody.ch");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://instagram.com/mibody.ch")));
                }
            }
        });

        landingTwitterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("twitter://user?screen_name=mibody1"));
                    startActivity(intent);

                }catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://twitter.com/#!/mibody1")));
                }
            }
        });

        landingUTubeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.youtube.com/channel/UC9Vt84rLqCM5VNS0KgEBVow"));
                startActivity(intent);
            }
        });


        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(landingVideosRV);

        LandingVideosAdapter landingVideosAdapter = new LandingVideosAdapter(this, videosArrayList);
        landingVideosRV.setAdapter(landingVideosAdapter);

        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currVidPosition != 0) {
                    currVidPosition--;
                    landingVideosRV.scrollToPosition(currVidPosition);
                }
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currVidPosition != videosArrayList.size()-1) {
                    currVidPosition++;
                    landingVideosRV.scrollToPosition(currVidPosition);
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (slidingLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
                slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
            else {
                finish();
            }
            return true;
        }

        // If it wasn't the Back key, bubble up to the default
        // system behavior
        return super.onKeyDown(keyCode, event);
    }

}