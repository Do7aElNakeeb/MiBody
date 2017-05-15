package ch.philopateer.mibody.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;

import ch.philopateer.mibody.R;
import ch.philopateer.mibody.activity.StatisticsActivity;
import ch.philopateer.mibody.activity.WorkoutPlay;
import ch.philopateer.mibody.adapter.MaterialCalendarAdapter;
import ch.philopateer.mibody.adapter.PlayedWorkoutsAdapter;
import ch.philopateer.mibody.adapter.PlayedWorkoutsOnDayAdapter;
import ch.philopateer.mibody.object.MaterialCalendar;
import ch.philopateer.mibody.object.WorkoutItem;

/**
 * Created by mamdouhelnakeeb on 3/24/17.
 */

public class ScheduleFragment extends Fragment implements View.OnClickListener, GridView.OnItemClickListener{

    // Variables
    //Views
    ImageView mPrevious;
    ImageView mNext;
    TextView mMonthName;
    GridView mCalendar;

    // Calendar Adapter
    private MaterialCalendarAdapter mMaterialCalendarAdapter;

    // Saved Events Adapter
    public PlayedWorkoutsOnDayAdapter playedWorkoutsAdapter;
    public RecyclerView mSavedEventsListView;

    /* <day, array> */
    public HashMap<Integer, ArrayList<WorkoutItem>> mSavedEventsPerDay;
    public static ArrayList<Integer> mSavedEventDays;

    public int mNumEventsOnDay = 0;


    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    MaterialCalendar materialCalendar;

    public int selectedDay = -1;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.schedule_fragment, container, false);

        // init Firebase authentication and database
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(firebaseAuth.getCurrentUser().getUid()).child("statistics");


        if (rootView != null) {
            // Get Calendar info
            // Get Calendar info
            materialCalendar = new MaterialCalendar(this);
            materialCalendar.getInitialCalendarInfo();
            getSavedEventsForCurrentMonth(System.currentTimeMillis());

            // Previous ImageView
            mPrevious = (ImageView) rootView.findViewById(R.id.material_calendar_previous);
            if (mPrevious != null) {
                mPrevious.setOnClickListener(this);
            }

            // Month name TextView
            mMonthName = (TextView) rootView.findViewById(R.id.material_calendar_month_name);
            if (mMonthName != null) {
                Calendar cal = Calendar.getInstance();
                if (cal != null) {
                    mMonthName.setText(cal.getDisplayName(Calendar.MONTH, Calendar.LONG,
                            Locale.getDefault()) + " " + cal.get(Calendar.YEAR));
                }
            }

            // Next ImageView
            mNext = (ImageView) rootView.findViewById(R.id.material_calendar_next);
            if (mNext != null) {
                mNext.setOnClickListener(this);
            }

            // GridView for custom Calendar
            mCalendar = (GridView) rootView.findViewById(R.id.material_calendar_gridView);
            if (mCalendar != null) {
                mCalendar.setOnItemClickListener(this);
                mMaterialCalendarAdapter = new MaterialCalendarAdapter(getActivity(), materialCalendar);
                mCalendar.setAdapter(mMaterialCalendarAdapter);


                // Set current day to be auto selected when first opened
                if (materialCalendar.mCurrentDay != -1 && materialCalendar.mFirstDay != -1){
                    int startingPosition = 6 + materialCalendar.mFirstDay;
                    int currentDayPosition = startingPosition + materialCalendar.mCurrentDay;

                    Log.d("INITIALSELECTEDPOSITION", String.valueOf(currentDayPosition));
                    mCalendar.setItemChecked(currentDayPosition, true);

                    if (mMaterialCalendarAdapter != null) {
                        mMaterialCalendarAdapter.notifyDataSetChanged();
                    }
                }
            }

            // ListView for saved events in calendar
            mSavedEventsListView = (RecyclerView) rootView.findViewById(R.id.playedWorkoutsLV);
            mSavedEventsListView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        }

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (mSavedEventsListView != null) {
            playedWorkoutsAdapter = new PlayedWorkoutsOnDayAdapter(getActivity(), this, new PlayedWorkoutsOnDayAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    WorkoutItem workoutItem = mSavedEventsPerDay.get(selectedDay).get(position);
                    if (workoutItem != null) {

                        Intent intent = new Intent(getActivity().getBaseContext(), StatisticsActivity.class);
                        intent.putExtra("workoutItemStats", workoutItem);
                        Log.d("workoutItemExArSize", String.valueOf(workoutItem.exercisesList.size()));
                        startActivity(intent);
                    }
                }
            });

            mSavedEventsListView.setAdapter(playedWorkoutsAdapter);
            Log.d("EVENTS_ADAPTER", "set adapter");

            // Show current day saved events on load
            int today = materialCalendar.mCurrentDay + 6 + materialCalendar.mFirstDay;
            showSavedEventsListView(today);
        }
    }

    @Override
    public void onClick(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.material_calendar_previous:
                    materialCalendar.previousOnClick(mPrevious, mMonthName, mCalendar, mMaterialCalendarAdapter);
                    break;

                case R.id.material_calendar_next:
                    materialCalendar.nextOnClick(mNext, mMonthName, mCalendar, mMaterialCalendarAdapter);
                    break;

                default:
                    break;
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.material_calendar_gridView:
                materialCalendar.selectCalendarDay(mMaterialCalendarAdapter, position);

                // Reset event list
                mNumEventsOnDay = -1;

                showSavedEventsListView(position);
                break;

            default:
                break;
        }
    }

    // Saved Events
    public static void getSavedEventsForCurrentMonth() {
        /**
         *  -- IMPORTANT --
         *  This is where you get saved event info
         */

        // -- Ideas on what could be done here --
        // Probably pull from some database
        // cross check event dates with current calendar month and year

        // For loop adding each event date to ArrayList
        // Also get ArrayList<SavedEvents>

        //mSavedEventsPerDay = new ArrayList<HashMap<String, ArrayList<WorkoutItem>>>();

        /**
         * Make sure to use this variable name or update in CalendarAdapter 'setSavedEvent'
         */
        //mSavedEventDays = new ArrayList<Integer>();

        /*
        // This is just used for testing purposes to show saved events on the calendar
        Random random = new Random();
        int randomNumOfEvents = random.nextInt(10 - 1) + 1;

        for (int i = 0; i < randomNumOfEvents; i++) {
            int day = random.nextInt(MaterialCalendar.mNumDaysInMonth - 1) + 1;
            int eventPerDay = random.nextInt(5 - 1) + 3;

            HashMap<String, Integer> dayInfo = new HashMap<String, Integer>();
            dayInfo.put("day" + day, eventPerDay);

            mSavedEventDays.add(day);
            mSavedEventsPerDay.add(dayInfo);

            Log.d("EVENTS_PER DAY", String.valueOf(dayInfo));
        }

        Log.d("SAVED_EVENT_DATES", String.valueOf(mSavedEventDays));
        */
    }


    protected void showSavedEventsListView(int position) {
        Boolean savedEventsOnThisDay = false;
        int selectedDate = -1;

        if (materialCalendar.mFirstDay != -1 && mSavedEventDays != null && mSavedEventDays.size() > 0) {

            selectedDate = position - (6 + materialCalendar.mFirstDay);
            this.selectedDay = selectedDate;
            Log.d("SELECTED_SAVED_DATE", String.valueOf(selectedDate));

            for (int i = 0; i < mSavedEventDays.size(); i++) {
                if (selectedDate == mSavedEventDays.get(i)) {
                    savedEventsOnThisDay = true;
                }
            }
        }

        Log.d("SAVED_EVENTS_BOOL", String.valueOf(savedEventsOnThisDay));

        if (savedEventsOnThisDay) {
            Log.d("POSDAY", String.valueOf(selectedDate));
            if (mSavedEventsPerDay != null && mSavedEventsPerDay.size() > 0 && mSavedEventsPerDay.containsKey(selectedDate)) {

                mNumEventsOnDay = mSavedEventsPerDay.get(selectedDate).size();
                Log.d("NUM_EVENT_ON_DAY", String.valueOf(mNumEventsOnDay));

            }
        } else {
            mNumEventsOnDay = -1;
        }

        if (playedWorkoutsAdapter != null && mSavedEventsListView != null) {
            playedWorkoutsAdapter.notifyDataSetChanged();

            // Scrolls back to top of ListView before refresh
            mSavedEventsListView.smoothScrollToPosition(0);
        }
    }

    public void getSavedEventsForCurrentMonth(long timeInMilliSeconds){


        mSavedEventsPerDay = new HashMap<Integer, ArrayList<WorkoutItem>>();

        /**
         * Make sure to use this variable name or update in CalendarAdapter 'setSavedEvent'
         */
        mSavedEventDays = new ArrayList<Integer>();

        // init Firebase authentication and database
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(firebaseAuth.getCurrentUser().getUid()).child("statistics");

        long monthInMilliseconds = 0;

        final Calendar calendar = Calendar.getInstance();
        final SimpleDateFormat dayFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        dayFormatter.setTimeZone(TimeZone.getDefault());

        SimpleDateFormat monthFormatter = new SimpleDateFormat("MM/yyyy", Locale.US);
        monthFormatter.setTimeZone(TimeZone.getDefault());

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        calendar.setTimeInMillis(timeInMilliSeconds);
        String date = monthFormatter.format(calendar.getTime());

        try {
            Date mDate = monthFormatter.parse(date);
            monthInMilliseconds = mDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final String monthInMillsStr = String.valueOf(monthInMilliseconds);

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot workoutsPerMonthSnapShot : dataSnapshot.child(monthInMillsStr).getChildren()){

                    String monthInMillis = workoutsPerMonthSnapShot.getKey();

                    Log.d("SnapShotKeyMonth", monthInMillis);

                    calendar.setTimeInMillis(Long.parseLong(monthInMillis));
                    String date = dayFormatter.format(calendar.getTime());
                    String day = date.substring(0, date.indexOf('/'));

                    Log.d("dateIs:", date);
                    Log.d("dayIs:", day);

                    mSavedEventDays.add(Integer.parseInt(day));

                    //HashMap<Integer, ArrayList<WorkoutItem>> dayInfo = new HashMap<Integer, ArrayList<WorkoutItem>>();


                    GenericTypeIndicator<HashMap<String, WorkoutItem>> workoutItemsGTypeInd = new GenericTypeIndicator<HashMap<String, WorkoutItem>>() {};

                    Map<String, WorkoutItem> workoutItemHashMap = workoutsPerMonthSnapShot.getValue(workoutItemsGTypeInd); //(HashMap<String, WorkoutItem>) workoutsPerMonthSnapShot.getValue();
                    ArrayList<WorkoutItem> workoutItemArrayList = new ArrayList<WorkoutItem>(workoutItemHashMap.values());


                    Log.d("SnapShotKeyDayCount", String.valueOf(workoutItemArrayList.size()));

                    Log.d("SnapShotKeyDayName", String.valueOf(workoutItemArrayList.get(0).workoutID));

                    //dayInfo.put(Integer.parseInt(day), workoutItemArrayList);
                    Log.d("day" + day, String.valueOf(workoutItemArrayList.size()));

                    mSavedEventsPerDay.put(Integer.parseInt(day), workoutItemArrayList);

                }

                mMaterialCalendarAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        databaseReference.addListenerForSingleValueEvent(valueEventListener);
    }

}
