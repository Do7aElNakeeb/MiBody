package com.mibody.app.app;

/**
 * Created by mamdouhelnakeeb on 1/23/17.
 */

public class Muscles {

    public int id;
    public float Triceps;
    public float Quadriceps;
    public float Chest;
    public float Waist;
    public float Calf;
    public float Biceps;
    public String updateTime;

    public Muscles(int id, float Triceps, float Quadriceps, float Chest, float Waist, float Calf, float Biceps, String updateTime){
        this.id = id;
        this.Triceps = Triceps;
        this.Quadriceps = Quadriceps;
        this.Chest = Chest;
        this.Waist = Waist;
        this.Calf = Calf;
        this.Biceps = Biceps;
        this.updateTime = updateTime;
    }
}
