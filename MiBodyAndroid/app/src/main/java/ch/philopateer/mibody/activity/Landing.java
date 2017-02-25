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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import ch.philopateer.mibody.R;
import ch.philopateer.mibody.helper.LandingVideosAdapter;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;

/**
 * Created by mamdouhelnakeeb on 11/30/16.
 */

public class Landing extends AppCompatActivity {

    private SlidingUpPanelLayout slidingLayout;
    private TextView textView;

    int currVidPosition = 0;
    RecyclerView landingVideosRV;
    Button previousBtn, nextBtn;

    CardView landingExerciseBtn, landingWorkoutsBtn, landingProfileBtn;
    ImageView landingFbBtn, landingInstaBtn, landingUTubeBtn, landingTwitterBtn;

    String videoKeys[] = {"BPhvUwyNHaY", "6fBZBntjEOA"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_activity);


        //set layout slide listener
        slidingLayout = (SlidingUpPanelLayout)findViewById(R.id.sliding_layout);

        //some "demo" event
        slidingLayout.setPanelSlideListener(onSlideListener());

        landingExerciseBtn = (CardView) slidingLayout.findViewById(R.id.landingExercisesBtn);
        landingWorkoutsBtn = (CardView) slidingLayout.findViewById(R.id.landingWorkoutsBtn);
        landingFbBtn = (ImageView) slidingLayout.findViewById(R.id.landingFbBtn);
        landingInstaBtn = (ImageView) slidingLayout.findViewById(R.id.landingInstaBtn);
        landingTwitterBtn = (ImageView) slidingLayout.findViewById(R.id.landingTwitterBtn);
        landingUTubeBtn = (ImageView) slidingLayout.findViewById(R.id.landingUTubeBtn);
        landingProfileBtn = (CardView) slidingLayout.findViewById(R.id.landingProfileBtn);

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
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });

        landingExerciseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ExercisesActivity.class);
                startActivity(intent);
            }
        });

        landingWorkoutsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WorkoutsActivity.class);
                startActivity(intent);
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

    /**
     * Request show sliding layout when clicked
     * @return
     */
    private View.OnClickListener onShowListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show sliding layout in bottom of screen (not expand it)
                slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        };
    }

    /**
     * Hide sliding layout when click button
     * @return
     */
    private View.OnClickListener onHideListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hide sliding layout
                slidingLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
            }
        };
    }

    private SlidingUpPanelLayout.PanelSlideListener onSlideListener() {
        return new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View view, float v) {
                //textView.setText("panel is sliding");
            }

            @Override
            public void onPanelCollapsed(View view) {
                //textView.setText("panel Collapse");
            }

            @Override
            public void onPanelExpanded(View view) {
                //textView.setText("panel expand");
            }

            @Override
            public void onPanelAnchored(View view) {
               // textView.setText("panel anchored");
            }

            @Override
            public void onPanelHidden(View view) {
                //textView.setText("panel is Hidden");
            }
        };
    }
}