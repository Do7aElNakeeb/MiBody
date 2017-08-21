package ch.philopateer.mibody.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import ch.philopateer.mibody.R;
import ch.philopateer.mibody.activity.Register;
import ch.philopateer.mibody.app.AppConfig;
import ch.philopateer.mibody.listener.OnBtnClickListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by mamdouhelnakeeb on 1/8/17.
 */

public class RegisterOne extends Fragment {
    //defining view objects
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextNewPassword;
    //private EditText editTextMobile;
    private EditText editTextName;

    public Button buttonSignup;

    public String name = "";
    public String email;
    public String mobile;
    public String password;
    public String newPassword;
    public String photo = "";

    public Uri photoPath;

    CardView ppEditCV;
    TextView captureImage, selectImage, oldPassTV, newPassTV, emailTV;

    SharedPreferences prefs;

    LinearLayout btnLinkToLogin, changePhotoMenu;
    FrameLayout blackLayout;


    private Bitmap bitmap;
    ImageView userIV;


    public OnBtnClickListener onBtnClickListener;

    public void initListener(OnBtnClickListener onBtnClickListener){
        this.onBtnClickListener = onBtnClickListener;
    }

    public RegisterOne(){

    }

    public RegisterOne(String name, String email, String mobile, String password, String photo){
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.photo = photo;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.register_one, container, false);

        //initializing views
        editTextName = (EditText) view.findViewById(R.id.name);
        editTextEmail = (EditText) view.findViewById(R.id.email);
        //editTextMobile = (EditText) view.findViewById(R.id.mobile);
        editTextPassword = (EditText) view.findViewById(R.id.password);
        editTextNewPassword = (EditText) view.findViewById(R.id.newpassword);

        emailTV = (TextView) view.findViewById(R.id.emailTV);
        oldPassTV = (TextView) view.findViewById(R.id.oldPassTV);
        newPassTV = (TextView) view.findViewById(R.id.newPassTV);

        newPassTV.setVisibility(View.GONE);
        emailTV.setVisibility(View.VISIBLE);
        editTextNewPassword.setVisibility(View.GONE);

        oldPassTV.setText("Password");

        userIV = (ImageView) view.findViewById(R.id.userImage);
        buttonSignup = (Button) view.findViewById(R.id.btnRegister);
        btnLinkToLogin = (LinearLayout) view.findViewById(R.id.btnLinkToLoginScreen);
        blackLayout = (FrameLayout) view.findViewById(R.id.blackLayout);

        ppEditCV = (CardView) view.findViewById(R.id.ppEditCV);
        captureImage = (TextView) view.findViewById(R.id.captureImage);
        selectImage = (TextView) view.findViewById(R.id.selectImage);
        changePhotoMenu = (LinearLayout) view.findViewById(R.id.userImageChooseLayout);

        prefs = getActivity().getSharedPreferences("UserDetails", MODE_PRIVATE);

        if (prefs.getString("isLoggedIn", "").equals("1") || prefs.getString("fbTDCon", "").equals("true")){
            updateFields();
        }

        //attaching listener to button
        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //getting email and password from edit texts
                name = editTextName.getText().toString().trim();
                email = editTextEmail.getText().toString().trim();
                //mobile = editTextMobile.getText().toString().trim();
                password  = editTextPassword.getText().toString().trim();
                newPassword = editTextNewPassword.getText().toString().trim();

                //checking if email and passwords are empty
                if(TextUtils.isEmpty(name)){
                    Toast.makeText(getActivity(),"Please enter name",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getActivity(),"Please enter email",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getActivity(),"Please enter password",Toast.LENGTH_LONG).show();
                    return;
                }


                ((Register)getActivity()).getViewPager().setCurrentItem(1);

                //onBtnClickListener.onBtnClick();

            }
        });


        // Link to Register Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });

        ppEditCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getActivity(), "Select Image", Toast.LENGTH_SHORT).show();
                int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);

                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                }
                else {
                    showFileChooser();
                }
                /*
                if (changePhotoMenu.getVisibility() == View.VISIBLE){
                    blackLayout.setVisibility(View.GONE);
                    changePhotoMenu.setVisibility(View.GONE);
                }
                else {
                    blackLayout.setVisibility(View.VISIBLE);
                    changePhotoMenu.setVisibility(View.VISIBLE);

                }
                */
            }
        });

        blackLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                blackLayout.setVisibility(View.GONE);
                changePhotoMenu.setVisibility(View.GONE);
            }
        });

        captureImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "capture Image", Toast.LENGTH_SHORT).show();
            }
        });

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Select Image", Toast.LENGTH_SHORT).show();
                int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);

                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                }
                else {
                    showFileChooser();
                }
            }
        });

        return view;
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private void updateFields(){

        if (!prefs.getString("fbIDCon", "").equals("true")) {
            emailTV.setVisibility(View.GONE);
            editTextEmail.setVisibility(View.GONE);
            oldPassTV.setText("Old Password");
            newPassTV.setVisibility(View.VISIBLE);
            editTextNewPassword.setVisibility(View.VISIBLE);
            editTextPassword.setHint("Old Password");
            buttonSignup.setText("Update");
        }

        Target target = new Target() {
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

                photo = getStringImage(bitmap);

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };

        userIV.setTag(target);
        userIV.setDrawingCacheEnabled(true);


        String photoUrl;

        if (prefs.getString("fbIDCon", "").equals("true")){
            photoUrl = AppConfig.fbPhotoURL + prefs.getString("fbID", "") + AppConfig.fbPhotoConginf;
        }
        else {
            photoUrl = AppConfig.URL_SERVER + "userPhotos/" + prefs.getString("user_id", "") + ".png";
        }
        Log.d("photoUrl", photoUrl);

        new Picasso.Builder(getActivity()).downloader(new OkHttpDownloader(getActivity(), Integer.MAX_VALUE)).build().load(photoUrl).into(target);

        editTextName.setText(name);
        editTextEmail.setText(email);
        //editTextMobile.setText(mobile);
        editTextPassword.setText(password);
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
            Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            photoPath = data.getData();
            try {

                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), photoPath);

                photoCircled();

                blackLayout.setVisibility(View.GONE);
                changePhotoMenu.setVisibility(View.GONE);

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
}