package ch.philopateer.mibody.object;

/**
 * Created by mamdouhelnakeeb on 1/23/17.
 */

public class Muscles {

    public int id;
    public int Wrest;
    public int Legs;
    public int Chest;
    public int Waist;
    public int Calf;
    public int Arm;
    public String updateTime;

    public Muscles(int id, int Wrest, int Legs, int Chest, int Waist, int Calf, int Arm, String updateTime){
        this.id = id;
        this.Wrest = Wrest;
        this.Legs = Legs;
        this.Chest = Chest;
        this.Waist = Waist;
        this.Calf = Calf;
        this.Arm = Arm;
        this.updateTime = updateTime;
    }
}
