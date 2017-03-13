package ch.philopateer.mibody.object;

/**
 * Created by mamdouhelnakeeb on 3/6/17.
 */

public class UserData {

    public String name;
    public String email;
    public String gender;
    public String dob;
    public String weight;
    public String height;
    public String units;
    public String BMI;

    public UserData(String name, String email, String gender, String dob, String weight, String height, String units, String BMI){
        this.name = name;
        this.email = email;
        this.gender = gender;
        this.dob = dob;
        this.weight = weight;
        this.height = height;
        this.units = units;
        this.BMI = BMI;
    }

    public UserData(){

    }

}
