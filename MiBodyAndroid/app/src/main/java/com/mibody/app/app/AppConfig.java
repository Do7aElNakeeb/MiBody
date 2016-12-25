package com.mibody.app.app;

import com.mibody.app.R;

/**
 * Created by NakeebMac on 10/1/16.
 */

public class AppConfig {

    // Server promotions url
//    public static String URL_SERVER = "http://api.mibody.ch/";

    public static String URL_SERVER = "http://192.168.1.10/MiBodyServer/";

    // EXTRA string to send on to mainactivity
    public static String EXTRA_DEVICE_ADDRESS = "device_address";

    public static final String TAG = "BluIno";

    public static final boolean D = false;

    public static final int MESSAGE_STATE_CHANGE = 1, MESSAGE_READ = 2, MESSAGE_WRITE = 3, MESSAGE_DEVICE_NAME = 4, MESSAGE_TOAST = 5, SYNC_CONNECTION = 6,
    // Intent request codes
    REQUEST_CONNECT_DEVICE = 1, REQUEST_ENABLE_BT = 2;

    // Key names received from the BlueInterfaceService Handler
    public static final String DEVICE_NAME = "device_name", TOAST = "toast";

    public static String exercises_names[] = { "Exercise A", "Exercise B", "Exercise C", "Exercise D", "Exercise E",
            "Exercise F", "Exercise G", "Exercise H", "Exercise I", "Exercise J", "Exercise K" };


    public static int Images[] = { R.drawable.ex1, R.drawable.ex2,
            R.drawable.ex3, R.drawable.ex4, R.drawable.ex5,
            R.drawable.ex6, R.drawable.ex7, R.drawable.ex8,
            R.drawable.ex9, R.drawable.ex10, R.drawable.ex11 };


    // Video Thumbnail Url
    public static String VideoThumbnail = "http://img.youtube.com/vi/";

}
