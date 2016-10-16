package com.mibody.app.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mibody.app.app.ExerciseItem;
import com.mibody.app.app.WorkoutExItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NakeebMac on 10/13/16.
 */

public class SQLiteHandler extends SQLiteOpenHelper {

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    SQLiteDatabase db;

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "mibody.db";

    // Login table name
    public static final String TABLE_NAME = "workouts";

    // Login Table Columns names
    public static final String NAME = "name";
    public static final String RESTT = "RestT";
    public static final String REPS = "reps";
    public static final String EXERCISES = "exercises";
    public static final String RGB = "rgb";
    /*
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String MOG80 = "MOG80";
    public static final String MOG92 = "MOG92";
    public static final String MOG95 = "MOG95";
    public static final String Diesel = "diesel";
    public static final String MOBILMART = "MobilMart";
    public static final String ONTHERUN = "OnTheRun";
    public static final String THEWAYTOGO = "TheWayToGo";
    public static final String CARWASH = "CarWash";
    public static final String Lubricants = "lubricants";
    public static final String PHONE = "phone";
*/

    String[] cols = {NAME, RESTT, REPS, EXERCISES, RGB};

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_STATIONS_TABLE = "CREATE TABLE " + TABLE_NAME + "( id integer primary key autoincrement, "
                + NAME + " TEXT," + RESTT + " TEXT,"
                + REPS + " TEXT," + EXERCISES + " TEXT" + ")";
        db.execSQL(CREATE_STATIONS_TABLE);

        Log.d(TAG, "Database tables created");
    }

    public void open() throws SQLException {
        db = this.getWritableDatabase();
    }

    public void close(){
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */

    public void addWorkout(WorkoutExItem workoutExItem) {

        ContentValues values = new ContentValues();
        values.put(NAME, workoutExItem.name);
        values.put(RESTT, workoutExItem.RestT);
        values.put(REPS, workoutExItem.RepsT);
        values.put(EXERCISES, workoutExItem.exercise);
        values.put(RGB, workoutExItem.rgb);

        Log.d("TAG", "New station inserted into sqlite: ");

        // Inserting Row
        db.insert(SQLiteHandler.TABLE_NAME, null, values);

        Log.d("TAG", "New station inserted into sqlite: ");
    }

    public List<WorkoutExItem> getWorkoutsExercises(String args){
        List<WorkoutExItem> markers = new ArrayList<WorkoutExItem>();

        Cursor cursor = db.query(SQLiteHandler.TABLE_NAME, cols, args, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            WorkoutExItem m = cursorToMarker(cursor);
            markers.add(m);
            cursor.moveToNext();
        }
        cursor.close();


        return markers;
    }

    private WorkoutExItem cursorToMarker(Cursor cursor) {
        return new WorkoutExItem(cursor.getString(0), cursor.getInt(1), cursor.getInt(2), cursor.getString(3), cursor.getString(4));
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_NAME, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }
}
