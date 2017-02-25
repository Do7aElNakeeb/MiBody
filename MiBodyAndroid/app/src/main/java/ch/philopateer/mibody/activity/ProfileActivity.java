package ch.philopateer.mibody.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import ch.philopateer.mibody.R;

/**
 * Created by mamdouhelnakeeb on 2/17/17.
 */

public class ProfileActivity extends AppCompatActivity {

    CardView dimensionsBtnCV, statisticsBtnCV, profileInfoBtnCV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        dimensionsBtnCV = (CardView) findViewById(R.id.dimensionsBtnCV);
        statisticsBtnCV = (CardView) findViewById(R.id.statisticsBtnCV);
        profileInfoBtnCV = (CardView) findViewById(R.id.profileBtnCV);

        dimensionsBtnCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, Dimensions.class));
            }
        });

        statisticsBtnCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, StatisticsActivity.class));
            }
        });

        profileInfoBtnCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, Register.class);
                intent.putExtra("from", 1);
                startActivity(intent);
            }
        });
    }
}
