package ch.philopateer.mibody.helper;

import java.util.regex.Pattern;

/**
 * Created by mamdouhelnakeeb on 11/28/17.
 */

public class Utils {

    public static boolean isValidEmaill(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }

    public static boolean isValidPassword(String password) {

        /**
         * at least:
         * 1 Number
         * 1 small character
         * 8 characters minimum
         * */

        return Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{8,}$").matcher(password).matches();

    }

    public static boolean isValidName(String name){

        return Pattern.compile("^(?=.*[a-z])(?=\\S+$).{5,}$").matcher(name).matches();
    }

    public static double kgToLbs(double kgVal){

        return kgVal * 2.20462;
    }

    public static double cmToInch(double cmVal){
        return cmVal * 0.393701;
    }
}
