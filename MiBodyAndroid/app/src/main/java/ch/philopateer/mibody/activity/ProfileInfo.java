package ch.philopateer.mibody.activity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.concurrent.ExecutionException;

import ch.philopateer.mibody.R;
import ch.philopateer.mibody.app.AppConfig;

/**
 * Created by mamdouhelnakeeb on 3/9/17.
 */

public class ProfileInfo extends AppCompatActivity {

    TextView name, email, gender, dob, weight, height;
    String photoUrl;
    ImageView userIV;

    SharedPreferences prefs;
    Target target;

    FirebaseAuth firebaseAuth;
    StorageReference photoReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_info_activity);

        firebaseAuth = FirebaseAuth.getInstance();
        photoReference = FirebaseStorage.getInstance().getReference();

        name = (TextView) findViewById(R.id.name);
        email = (TextView) findViewById(R.id.email);
        gender = (TextView) findViewById(R.id.gender);
        dob = (TextView) findViewById(R.id.dob);
        weight = (TextView) findViewById(R.id.weight);
        height = (TextView) findViewById(R.id.height);
        userIV = (ImageView) findViewById(R.id.userImage);

        prefs = getSharedPreferences("UserDetails", MODE_PRIVATE);

        target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                userIV.setScaleType(ImageView.ScaleType.CENTER_CROP);
                userIV.setImageBitmap(bitmap);

                Matrix matrix = userIV.getImageMatrix();
                float scale;

                if (userIV.getDrawable().getIntrinsicWidth() * userIV.getHeight() > userIV.getDrawable().getIntrinsicHeight() * userIV.getWidth()) {
                    scale = (float) userIV.getHeight() / (float) userIV.getDrawable().getIntrinsicHeight();
                } else {
                    scale = (float) userIV.getWidth() / (float) userIV.getDrawable().getIntrinsicWidth();
                }

                matrix.setScale(scale, scale);
                userIV.setImageMatrix(matrix);

                Bitmap imageRounded = Bitmap.createBitmap(userIV.getDrawingCache().getWidth(), userIV.getDrawingCache().getHeight(), userIV.getDrawingCache().getConfig());
                Canvas canvas = new Canvas(imageRounded);
                Paint mpaint = new Paint();
                final Rect rect = new Rect(0, 0, userIV.getDrawingCache().getWidth(), userIV.getDrawingCache().getHeight());
                mpaint.setAntiAlias(true);
                canvas.drawARGB(0, 0, 0, 0);

                canvas.drawCircle(userIV.getDrawingCache().getWidth() / 2, userIV.getDrawingCache().getHeight() / 2, userIV.getDrawingCache().getWidth() / 2, mpaint);

                mpaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                canvas.drawBitmap(userIV.getDrawingCache(), rect, rect, mpaint);

                //Setting the Bitmap to ImageView
                userIV.setImageBitmap(imageRounded);

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };


        SimpleTarget<Bitmap> simpleTarget = new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                userIV.setScaleType(ImageView.ScaleType.CENTER_CROP);
                userIV.setImageBitmap(resource);

                userIV.buildDrawingCache();
                Matrix matrix = userIV.getImageMatrix();
                float scale;

                if (userIV.getDrawable().getIntrinsicWidth() * userIV.getHeight() > userIV.getDrawable().getIntrinsicHeight() * userIV.getWidth()) {
                    scale = (float) userIV.getHeight() / (float) userIV.getDrawable().getIntrinsicHeight();
                } else {
                    scale = (float) userIV.getWidth() / (float) userIV.getDrawable().getIntrinsicWidth();
                }

                matrix.setScale(scale, scale);
                userIV.setImageMatrix(matrix);

                Bitmap imageRounded = Bitmap.createBitmap(userIV.getDrawingCache().getWidth(), userIV.getDrawingCache().getHeight(), userIV.getDrawingCache().getConfig());
                Canvas canvas = new Canvas(imageRounded);
                Paint mpaint = new Paint();
                final Rect rect = new Rect(0, 0, userIV.getDrawingCache().getWidth(), userIV.getDrawingCache().getHeight());
                mpaint.setAntiAlias(true);
                canvas.drawARGB(0, 0, 0, 0);

                canvas.drawCircle(userIV.getDrawingCache().getWidth() / 2, userIV.getDrawingCache().getHeight() / 2, userIV.getDrawingCache().getWidth() / 2, mpaint);

                mpaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                canvas.drawBitmap(userIV.getDrawingCache(), rect, rect, mpaint);

                //Setting the Bitmap to ImageView
                userIV.setImageBitmap(imageRounded);
            }
        };


        userIV.setDrawingCacheEnabled(true);



        name.setText(prefs.getString("name", ""));
        email.setText(prefs.getString("email", ""));
        gender.setText(prefs.getString("gender", ""));
        dob.setText(prefs.getString("dob", ""));
        weight.setText(prefs.getString("weight", ""));
        height.setText(prefs.getString("height", ""));
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!prefs.getString("fbIDCon", "").equals("true")){
            //userIV.setTag(simpleTarget);
            photoReference = photoReference.child("userImages/" + firebaseAuth.getCurrentUser().getUid() + ".jpg");
            Log.d("photoRef", photoReference.getPath());

            Glide.with(this).using(new FirebaseImageLoader()).load(photoReference).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    userIV.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    userIV.setImageBitmap(resource);

                    userIV.buildDrawingCache();
                    Matrix matrix = userIV.getImageMatrix();
                    float scale;

                    if (userIV.getDrawable().getIntrinsicWidth() * userIV.getHeight() > userIV.getDrawable().getIntrinsicHeight() * userIV.getWidth()) {
                        scale = (float) userIV.getHeight() / (float) userIV.getDrawable().getIntrinsicHeight();
                    } else {
                        scale = (float) userIV.getWidth() / (float) userIV.getDrawable().getIntrinsicWidth();
                    }

                    matrix.setScale(scale, scale);
                    userIV.setImageMatrix(matrix);

                    try {
                        Bitmap imageRounded = Bitmap.createBitmap(userIV.getDrawingCache().getWidth(), userIV.getDrawingCache().getHeight(), userIV.getDrawingCache().getConfig());
                        Canvas canvas = new Canvas(imageRounded);
                        Paint mpaint = new Paint();
                        final Rect rect = new Rect(0, 0, userIV.getDrawingCache().getWidth(), userIV.getDrawingCache().getHeight());
                        mpaint.setAntiAlias(true);
                        canvas.drawARGB(0, 0, 0, 0);

                        canvas.drawCircle(userIV.getDrawingCache().getWidth() / 2, userIV.getDrawingCache().getHeight() / 2, userIV.getDrawingCache().getWidth() / 2, mpaint);

                        mpaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
                        canvas.drawBitmap(userIV.getDrawingCache(), rect, rect, mpaint);

                        //Setting the Bitmap to ImageView
                        userIV.setImageBitmap(imageRounded);
                    }
                    catch (Exception e){

                    }
                }
            });

        }
        else {
            userIV.setTag(target);
            photoUrl = AppConfig.fbPhotoURL + prefs.getString("fbID", "") + AppConfig.fbPhotoConginf;
            new Picasso.Builder(this).downloader(new OkHttpDownloader(this, Integer.MAX_VALUE)).build().load(photoUrl).into(target);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}