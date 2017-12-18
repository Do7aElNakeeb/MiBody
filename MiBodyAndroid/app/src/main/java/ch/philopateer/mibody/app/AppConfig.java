package ch.philopateer.mibody.app;

import android.util.SparseIntArray;

import java.util.HashMap;
import java.util.Map;

import ch.philopateer.mibody.R;

/**
 * Created by NakeebMac on 10/1/16.
 */

public class AppConfig {

    // Server promotions url
//    public static String URL_SERVER = "http://api.mibody.ch/";

    public static String URL_SERVER = "http://79.170.40.33/do7aelnakeeb.cf/MiBodyServer/";

    // EXTRA string to send on to mainactivity
    public static String EXTRA_DEVICE_ADDRESS = "device_address";

    public static final String TAG = "BluIno";

    public static final boolean D = false;

    public static final int MESSAGE_STATE_CHANGE = 1, MESSAGE_READ = 2, MESSAGE_WRITE = 3, MESSAGE_DEVICE_NAME = 4, MESSAGE_TOAST = 5, SYNC_CONNECTION = 6,
    // Intent request codes
    REQUEST_CONNECT_DEVICE = 1, REQUEST_ENABLE_BT = 2;

    // Key names received from the BlueInterfaceService Handler
    public static final String DEVICE_NAME = "device_name", TOAST = "toast";

    public static String exercises_names[] = {
            "Chinup",
            "Pullup",
            "Pushup",
            "Inclined pushup",
            "Situp",
            "Leg raise",
            "Lunge",
            "Squat",
            "Dip",
            "Calf raise"
    };

    public static String exercises_discreptions[] = {
            "Grap the narrow hand grip and hang from it with your arms extended and palms facing toward you. Pull up your body until the elbows are bent and the head is higher than the hands.",
            "Grap the wide hand grip and hang from it with your arms extended and palms facing away from you. Pull up your body until the elbows are bent and the head is higher than the hands.",
            "Lie in a prone position, grap your palms around the wide position hand grip and your toes remain on the floor. Use your arms to raise and lower your body while your back remains straight.",
            "Lie in a prone inclined position, put your palms on the floor  and  your feet put on the elevated cross bar. Use your arms to raise and lower your body while your back remains straight.",
            "Lie with the back on the floor, squeeze your foot into the footstrap, bent  your knees to reduce stress on the back muscles and spine.\n Elevate both the upper and lower vertebrae from the floor until everything superior to the buttocks is not touching the ground.",
            "Stand and keep the back in contact with the support and place hands on the arm support. \n Lift legs upward as far as possible. Lower down to starting position slowly and with control. \n Make sure the back stays flat on floor and that the abdominal muscles are tight.",
            "Stand with one leg on the floor and the other hanged on the elevated cross bar. Step forward with one leg and bends down until the front knee is at ninety degrees angle and the back knee almost touches the floor while keeping the upper body straight.",
            "Stand up, bend your legs at the knees and hips, lowering the torso between the legs. Lean the torso forward to maintain balance.",
            "Hang from the dip bar with your arms straight and your shoulders positioned above the hands. Lower your body until the arms are bent at a 90 degrees angle.",
            "Standup, your back facing the machine. Raise the heel as far as possible. The exercise can be made harder by performing the exercise on one leg."
    };

    public static int exercises_icons[] = {
            R.drawable.chinup,
            R.drawable.pullup,
            R.drawable.pushup,
            R.drawable.inclinedpushup,
            R.drawable.situp,
            R.drawable.legraise,
            R.drawable.lunge,
            R.drawable.squat,
            R.drawable.dip,
            R.drawable.calfraise
    };

    public static final Map<String, Integer> exIcons;
    static
    {
        exIcons = new HashMap<String, Integer>();
        exIcons.put("Calf raise", R.drawable.calfraise);
        exIcons.put("Chinup", R.drawable.chinup);
        exIcons.put("Dip", R.drawable.dip);
        exIcons.put("Inclined pushup", R.drawable.inclinedpushup);
        exIcons.put("Leg raise", R.drawable.legraise);
        exIcons.put("Lunge", R.drawable.lunge);
        exIcons.put("Pullup", R.drawable.pullup);
        exIcons.put("Pushup", R.drawable.pushup);
        exIcons.put("Situp", R.drawable.situp);
        exIcons.put("Squat", R.drawable.squat);
    }

    public static int exercises_imgs[] = {
            R.drawable.chinup_img,
            R.drawable.pullup_img,
            R.drawable.pushup_img,
            R.drawable.inclinedpushup_img,
            R.drawable.situp_img,
            R.drawable.legraises_img,
            R.drawable.lunges_img,
            R.drawable.squat_img,
            R.drawable.dip_img,
            R.drawable.calfraises_img
    };

    public static int exercises_gifs[] = {
            R.raw.chinup_gif,
            R.raw.pullup_gif,
            R.raw.pushup_gif,
            R.raw.inclinedpushup_gif,
            R.raw.situp_gif,
            R.raw.legraise_gif,
            R.raw.lunge_gif,
            R.raw.squat_gif,
            R.raw.dip_gif,
            R.raw.calfraises_gif
    };

    public static final Map<Integer, Integer> heartRates;
    static
    {
        
        heartRates = new HashMap<Integer, Integer>();
        heartRates.put(20, 170);
        heartRates.put(30, 162);
        heartRates.put(35, 157);
        heartRates.put(40, 153);
        heartRates.put(45, 149);
        heartRates.put(50, 145);
        heartRates.put(55, 140);
        heartRates.put(60, 136);
        heartRates.put(65, 132);
        heartRates.put(70, 128);
    }

    public static final SparseIntArray heartRatesSA;
    static
    {

        heartRatesSA = new SparseIntArray();
        heartRatesSA.put(20, 170);
        heartRatesSA.put(30, 162);
        heartRatesSA.put(35, 157);
        heartRatesSA.put(40, 153);
        heartRatesSA.put(45, 149);
        heartRatesSA.put(50, 145);
        heartRatesSA.put(55, 140);
        heartRatesSA.put(60, 136);
        heartRatesSA.put(65, 132);
        heartRatesSA.put(70, 128);
    }

    public static String resultsQuote[] = {
      "WOW, This’s beyond your goal",
            "Congrats, you’ve done it",
            "Hard luck next time"
    };

    // Video Thumbnail Url
    public static String VideoThumbnail = "http://img.youtube.com/vi/";

    // Facebook Photo Url
    public static String fbPhotoURL = "https://graph.facebook.com/" ;
    public static String fbPhotoConginf = "/picture?width=512&height=512";

    // Google App API Key
    public static String Google_API_KEY = "AIzaSyCn0E7XmM87YpnHPZq6TMaQ5NBcnKPsWLU";


    public static double MIN_WEIGHT = 30;
    public static double MAX_WEIGHT = 300;

    public static double MIN_HEIGHT = 50;
    public static double MAX_HEIGHT = 400;


    public static double MAX_REST = 5;
    public static double MAX_REPS = 1;

}