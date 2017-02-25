package ch.philopateer.mibody.app;

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
            "Calfraise",
            "Chinup",
            "Dip",
            "Inclinedpushup",
            "Legraise",
            "Lunge",
            "Pullup",
            "Pushup",
            "Situp",
            "Squat"};

    public static String exercises_discreptions[] = {
            "Standup, your back facing the machine. Raise the heel as far as possible. The exercise can be made harder by performing the exercise on one leg.",
            "Grap the narrow hand grip and hang from it with your arms extended and palms facing toward you. Pull up your body until the elbows are bent and the head is higher than the hands.",
            "Hang from the dip bar with your arms straight and your shoulders positioned above the hands. Lower your body until the arms are bent at a 90 degrees angle.",
            "Lie in a prone inclined position, put your palms on the floor  and  your feet put on the elevated cross bar. Use your arms to raise and lower your body while your back remains straight.",
            "Stand and keep the back in contact with the A1:E11 support and place hands on the arm support. \n Lift legs upward as far as possible. Lower down to starting position slowly and with control. \n Make sure the back stays flat on floor and that the abdominal muscles are tight.",
            "Stand with one leg on the floor and the other hanged on the elevated cross bar. Step forward with one leg and bends down until the front knee is at ninety degrees angle and the back knee almost touches the floor while keeping the upper body straight.",
            "Grap the wide hand grip and hang from it with your arms extended and palms facing away from you. Pull up your body until the elbows are bent and the head is higher than the hands.",
            "Lie in a prone position, grap your palms around the wide position hand grip and your toes remain on the floor. Use your arms to raise and lower your body while your back remains straight.",
            "Lie with the back on the floor, squeeze your foot into the footstrap, bent  your knees to reduce stress on the back muscles and spine.\n Elevate both the upper and lower vertebrae from the floor until everything superior to the buttocks is not touching the ground.",
            "Stand up, bend your legs at the knees and hips, lowering the torso between the legs. Lean the torso forward to maintain balance."};

    public static int exercises_icons[] = {
            ch.philopateer.mibody.R.drawable.calfraise,
            ch.philopateer.mibody.R.drawable.chinup,
            ch.philopateer.mibody.R.drawable.dip,
            ch.philopateer.mibody.R.drawable.inclinedpushup,
            ch.philopateer.mibody.R.drawable.legraise,
            ch.philopateer.mibody.R.drawable.lunge,
            ch.philopateer.mibody.R.drawable.pullup,
            ch.philopateer.mibody.R.drawable.pushup,
            ch.philopateer.mibody.R.drawable.situp,
            ch.philopateer.mibody.R.drawable.squat};

    public static int exercises_imgs[] = {
            ch.philopateer.mibody.R.drawable.calfraises_img,
            ch.philopateer.mibody.R.drawable.chinup_img,
            ch.philopateer.mibody.R.drawable.dip_img,
            ch.philopateer.mibody.R.drawable.inclinedpushup_img,
            ch.philopateer.mibody.R.drawable.legraises_img,
            ch.philopateer.mibody.R.drawable.lunges_img,
            ch.philopateer.mibody.R.drawable.pullup_img,
            ch.philopateer.mibody.R.drawable.pushup_img,
            ch.philopateer.mibody.R.drawable.situp_img,
            ch.philopateer.mibody.R.drawable.squat_img};

    public static int exercises_gifs[] = {
            ch.philopateer.mibody.R.raw.calfraises_gif,
            ch.philopateer.mibody.R.raw.chinup_gif,
            ch.philopateer.mibody.R.raw.dip_gif,
            ch.philopateer.mibody.R.raw.inclinedpushup_gif,
            ch.philopateer.mibody.R.raw.legraise_gif,
            ch.philopateer.mibody.R.raw.lunge_gif,
            ch.philopateer.mibody.R.raw.pullup_gif,
            ch.philopateer.mibody.R.raw.pushup_gif,
            ch.philopateer.mibody.R.raw.situp_gif,
            ch.philopateer.mibody.R.raw.squat_gif};



    // Video Thumbnail Url
    public static String VideoThumbnail = "http://img.youtube.com/vi/";

    // Facebook Photo Url
    public static String fbPhotoURL = "https://graph.facebook.com/" ;
    public static String fbPhotoConginf = "/picture?width=512&height=512";

}