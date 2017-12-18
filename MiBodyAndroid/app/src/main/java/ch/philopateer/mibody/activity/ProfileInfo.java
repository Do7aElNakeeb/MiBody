package ch.philopateer.mibody.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import ch.philopateer.mibody.R;
import ch.philopateer.mibody.app.AppConfig;
import ch.philopateer.mibody.helper.Utils;
import ch.philopateer.mibody.object.UserData;

import static ch.philopateer.mibody.helper.Utils.cmToInch;
import static ch.philopateer.mibody.helper.Utils.kgToLbs;

/**
 * Created by mamdouhelnakeeb on 3/9/17.
 */

public class ProfileInfo extends AppCompatActivity {

    TextView name, email, gender, dob, weight, height;
    String photoUrl;
    ImageView userIV, profileEditIV;

    EditText nameET, emailET, weightET, heightET;
    Spinner genderSpinner;

    public Uri photoPath;
    private Bitmap bitmap;
    public String photo = "";

    DateFormat formatDate = DateFormat.getDateInstance();
    Calendar dateCalender = Calendar.getInstance();

    Boolean editMode = false;

    UserData userData;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    FirebaseAuth firebaseAuth;
    StorageReference photoReference;
    DatabaseReference databaseReference;

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

        profileEditIV = (ImageView) findViewById(R.id.profileEditIV);
        nameET = (EditText) findViewById(R.id.nameET);
        emailET = (EditText) findViewById(R.id.emailET);
        weightET = (EditText) findViewById(R.id.weightET);
        heightET = (EditText) findViewById(R.id.heightET);
        genderSpinner = (Spinner) findViewById(R.id.genderSpinner);

        prefs = getSharedPreferences("UserDetails", MODE_PRIVATE);
        editor = prefs.edit();


        name.setText(prefs.getString("name", ""));
        email.setText(prefs.getString("email", ""));
        gender.setText(prefs.getString("gender", ""));
        dob.setText(prefs.getString("dob", ""));
        weight.setText(prefs.getString("weight", ""));
        height.setText(prefs.getString("height", ""));

        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(this, R.array.genderArray, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        genderSpinner.setAdapter(genderAdapter);


        findViewById(R.id.backBtnLL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        /*
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
*/
        userIV.setDrawingCacheEnabled(true);

        loadPhoto();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(firebaseAuth.getCurrentUser().getUid());

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                userData = dataSnapshot.getValue(UserData.class);

                name.setText(userData.name);
                email.setText(userData.email);
                gender.setText(userData.gender);
                dob.setText(userData.dob);
                weight.setText(userData.weight);
                height.setText(userData.height);

                editor.putString("name", userData.name);
                editor.putString("email", userData.email);
                editor.putString("gender", userData.gender);
                editor.putString("dob", userData.dob);
                editor.putString("weight", userData.weight);
                editor.putString("height", userData.height);

                editor.apply();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        databaseReference.addValueEventListener(valueEventListener);

        profileEditIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editMode) {

                    if (!Utils.isValidEmaill(emailET.getText().toString().trim())){
                        Toast.makeText(ProfileInfo.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (!Utils.isValidName(nameET.getText().toString().trim())){
                        Toast.makeText(ProfileInfo.this, "Please enter a valid name", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(TextUtils.isEmpty(weightET.getText().toString())){
                        Toast.makeText(ProfileInfo.this,"Please enter weight",Toast.LENGTH_LONG).show();
                        return;
                    }
                    if(TextUtils.isEmpty(heightET.getText().toString())){
                        Toast.makeText(ProfileInfo.this,"Please enter height", Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (getSharedPreferences("UserDetails", MODE_PRIVATE).getString("units", "0").equals("0")){
                        if (Double.parseDouble(weightET.getText().toString()) > AppConfig.MAX_WEIGHT){
                            weightET.setText("");
                            Toast.makeText(ProfileInfo.this, "Your weight should be less than " + Double.toString(AppConfig.MAX_WEIGHT) +" kg", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else if (Double.parseDouble(weightET.getText().toString()) < AppConfig.MIN_WEIGHT){
                            weightET.setText("");
                            Toast.makeText(ProfileInfo.this, "Your weight should be more than " + Double.toString(AppConfig.MIN_WEIGHT) +" kg", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    else if (getSharedPreferences("UserDetails", MODE_PRIVATE).getString("units", "0").equals("1")){
                        if (Double.parseDouble(weightET.getText().toString()) > kgToLbs(AppConfig.MAX_WEIGHT)){
                            weightET.setText("");
                            Toast.makeText(ProfileInfo.this, "Your weight should be less than " + Double.toString(kgToLbs(AppConfig.MAX_WEIGHT)) +" lbs", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else if (Double.parseDouble(weightET.getText().toString()) < kgToLbs(AppConfig.MIN_WEIGHT)){
                            weightET.setText("");
                            Toast.makeText(ProfileInfo.this, "Your weight should be more than " + Double.toString(kgToLbs(AppConfig.MIN_WEIGHT)) +" lbs", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    if (getSharedPreferences("UserDetails", MODE_PRIVATE).getString("units", "0").equals("0")){
                        if (Double.parseDouble(heightET.getText().toString()) > AppConfig.MAX_HEIGHT){
                            heightET.setText("");
                            Toast.makeText(ProfileInfo.this, "Your height should be less than " + Double.toString(AppConfig.MAX_HEIGHT) +" cm", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else if (Double.parseDouble(heightET.getText().toString()) < AppConfig.MIN_HEIGHT){
                            heightET.setText("");
                            Toast.makeText(ProfileInfo.this, "Your height should be more than " + Double.toString(AppConfig.MIN_WEIGHT) +" cm", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    else if (getSharedPreferences("UserDetails", MODE_PRIVATE).getString("units", "0").equals("1")){
                        if (Double.parseDouble(heightET.getText().toString()) > cmToInch(AppConfig.MAX_HEIGHT)){
                            heightET.setText("");
                            Toast.makeText(ProfileInfo.this, "Your height should be less than " + Double.toString(cmToInch(AppConfig.MAX_HEIGHT)) +" inch", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else if (Double.parseDouble(heightET.getText().toString()) < cmToInch(AppConfig.MIN_HEIGHT)){
                            heightET.setText("");
                            Toast.makeText(ProfileInfo.this, "Your height should be more than " + Double.toString(cmToInch(AppConfig.MIN_HEIGHT)) +" inch", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    
                    profileEditIV.setImageResource(R.drawable.pen_icon);
                    profileEditIV.setScaleX(-1);
                    profileEditIV.setScaleY(1);

                    name.setText(nameET.getText().toString().trim());
                    email.setText(emailET.getText().toString().trim());
                    weight.setText(weightET.getText().toString().trim());
                    height.setText(heightET.getText().toString().trim());
                    gender.setText(genderSpinner.getSelectedItem().toString().trim());

                    name.setVisibility(View.VISIBLE);
                    email.setVisibility(View.VISIBLE);
                    weight.setVisibility(View.VISIBLE);
                    height.setVisibility(View.VISIBLE);
                    gender.setVisibility(View.VISIBLE);

                    nameET.setVisibility(View.GONE);
                    emailET.setVisibility(View.GONE);
                    weightET.setVisibility(View.GONE);
                    heightET.setVisibility(View.GONE);
                    genderSpinner.setVisibility(View.GONE);

                    firebaseAuth.getCurrentUser().updateEmail(emailET.getText().toString().trim());

                    if (photoPath != null){
                        photoReference = FirebaseStorage.getInstance().getReference().child("userImages/" + firebaseAuth.getCurrentUser().getUid() + ".jpg");
                        photoReference.putFile(photoPath)
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileInfo.this);
                                        builder.setMessage(e.getMessage())
                                                .setTitle("Error!")
                                                .setPositiveButton(android.R.string.ok, null);
                                        AlertDialog dialog = builder.create();
                                        dialog.show();
                                    }
                                });
                    }

                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(nameET.getText().toString().trim())
                            .build();

                    firebaseAuth.getCurrentUser().updateProfile(profileUpdates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("name Updated", "User profile updated.");
                                    }
                                }
                            });

                    FirebaseDatabase.getInstance().getReference().child("users")
                            .child(firebaseAuth.getCurrentUser().getUid())
                            .setValue(new UserData(nameET.getText().toString().trim(),
                                    emailET.getText().toString().trim(),
                                    gender.getText().toString().trim(),
                                    dob.getText().toString().trim(),
                                    weight.getText().toString().trim(),
                                    height.getText().toString().trim()));

                    editMode = false;
                }
                else {

                    profileEditIV.setImageResource(R.drawable.true_mark_icon);
                    profileEditIV.setScaleX((float) 1.5);
                    profileEditIV.setScaleY((float) 1.5);

                    name.setVisibility(View.GONE);
                    email.setVisibility(View.GONE);
                    weight.setVisibility(View.GONE);
                    height.setVisibility(View.GONE);
                    gender.setVisibility(View.GONE);

                    nameET.setText(name.getText().toString().trim());
                    emailET.setText(email.getText().toString().trim());
                    weightET.setText(weight.getText().toString().trim());
                    heightET.setText(height.getText().toString().trim());

                    if (gender.getText().toString().trim().equals("Male")) {
                        genderSpinner.setSelection(0, true);
                    }
                    else if (gender.getText().toString().trim().equals("Female")){
                        genderSpinner.setSelection(1, true);
                    }

                    nameET.setVisibility(View.VISIBLE);
                    emailET.setVisibility(View.VISIBLE);
                    weightET.setVisibility(View.VISIBLE);
                    heightET.setVisibility(View.VISIBLE);
                    genderSpinner.setVisibility(View.VISIBLE);

                    editMode = true;
                }
            }
        });

        userIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editMode){
                    int permissionCheck = ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.READ_EXTERNAL_STORAGE);

                    if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(ProfileInfo.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                    }
                    else {
                        showFileChooser();
                    }
                }
            }
        });

        dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editMode) {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(ProfileInfo.this, d, dateCalender.get(Calendar.YEAR), dateCalender.get(Calendar.MONTH), dateCalender.get(Calendar.DAY_OF_MONTH));


                    datePickerDialog.getDatePicker().setMaxDate(978299999 * 1000L);
                    datePickerDialog.getDatePicker().setMinDate(0);
                    datePickerDialog.show();
                }
            }
        });
    }

    private void loadPhoto() {

        photoReference = FirebaseStorage.getInstance().getReference().child("userImages/" + firebaseAuth.getCurrentUser().getUid() + ".jpg");
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

        /*
        if (!prefs.getString("fbIDCon", "").equals("true")){
            //userIV.setTag(simpleTarget);


        }
        else {
            userIV.setTag(target);
            photoUrl = AppConfig.fbPhotoURL + prefs.getString("fbID", "") + AppConfig.fbPhotoConginf;
            new Picasso.Builder(this).downloader(new OkHttpDownloader(this, Integer.MAX_VALUE)).build().load(photoUrl).into(target);
        }
*/

    }


    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100) {
            showFileChooser();
        }
        else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            photoPath = data.getData();
            try {

                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoPath);

                photoCircled();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void photoCircled(){

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

        userIV.setDrawingCacheEnabled(true);

        Bitmap imageRounded = Bitmap
                .createBitmap(((BitmapDrawable)userIV.getDrawable()).getBitmap().getWidth(),
                        ((BitmapDrawable)userIV.getDrawable()).getBitmap().getHeight(),
                        ((BitmapDrawable)userIV.getDrawable()).getBitmap().getConfig());

        Canvas canvas = new Canvas(imageRounded);
        Paint mpaint = new Paint();
        final Rect rect = new Rect(0, 0, ((BitmapDrawable)userIV.getDrawable()).getBitmap().getWidth(), ((BitmapDrawable)userIV.getDrawable()).getBitmap().getHeight());
        mpaint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);

        canvas.drawCircle(((BitmapDrawable)userIV.getDrawable()).getBitmap().getWidth() / 2, ((BitmapDrawable)userIV.getDrawable()).getBitmap().getHeight() / 2, ((BitmapDrawable)userIV.getDrawable()).getBitmap().getWidth() / 2, mpaint);

        mpaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(((BitmapDrawable)userIV.getDrawable()).getBitmap(), rect, rect, mpaint);

        //Setting the Bitmap to ImageView
        userIV.setImageBitmap(imageRounded);
        photo = getStringImage(bitmap);
        Log.d("userPhoto", photo);
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateCalender.set(Calendar.YEAR, year);
            dateCalender.set(Calendar.MONTH, monthOfYear);
            dateCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            dob.setText(format.format(dateCalender.getTime()));
        }
    };
}