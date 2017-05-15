package ch.philopateer.mibody.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import ch.philopateer.mibody.R;
import ch.philopateer.mibody.activity.StatisticsActivity;
import ch.philopateer.mibody.fragments.ScheduleFragment;
import ch.philopateer.mibody.helper.WorkoutExItemAdapter;
import ch.philopateer.mibody.object.WorkoutExItem;
import ch.philopateer.mibody.object.WorkoutItem;

/**
 * Created by mamdouhelnakeeb on 3/24/17.
 */

public class PlayedWorkoutsAdapter extends BaseAdapter {
    // Variables
    private Context mContext;
    private ViewHolder mHolder;
    private ScheduleFragment scheduleFragment;

    private WorkoutItem workoutItem = null;

    private static class ViewHolder {
        TextView mTitle;
        TextView mAbout;
        ImageView mImageView;
        View mDivider;
    }

    // Constructor
    public PlayedWorkoutsAdapter(Context context, ScheduleFragment scheduleFragment) {
        mContext = context;
        this.scheduleFragment = scheduleFragment;
    }

    @Override
    public int getCount() {
        Log.d("numOfEvOnDay", String.valueOf(scheduleFragment.mNumEventsOnDay));
        return scheduleFragment.mNumEventsOnDay;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (scheduleFragment.mSavedEventsPerDay.containsKey(scheduleFragment.selectedDay)) {
            workoutItem = scheduleFragment.mSavedEventsPerDay.get(scheduleFragment.selectedDay).get(position);
            workoutItem.JSONtoArray();
        }

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.schedule_played_workout_item, parent, false);

            mHolder = new ViewHolder();

            if (convertView != null) {
                // FindViewById
                mHolder.mTitle = (TextView) convertView.findViewById(R.id.saved_event_title_textView);
                mHolder.mAbout = (TextView) convertView.findViewById(R.id.saved_event_about_textView);
                mHolder.mImageView = (ImageView) convertView.findViewById(R.id.saved_event_imageView);
                convertView.setTag(mHolder);

            }
        } else {
            mHolder = (ViewHolder) convertView.getTag();

        }

        // Animates in each cell when added to the ListView
        Animation animateIn = AnimationUtils.loadAnimation(mContext, R.anim.listview_top_down);
        if (convertView != null && animateIn != null) {
            convertView.startAnimation(animateIn);
        }

        mHolder.mTitle.setText(workoutItem.workoutName);

        String workoutExercises = "";
        for (int i = 0; i < workoutItem.exercisesList.size(); i++) {
            if (i == workoutItem.exercisesList.size() - 1) {
                workoutExercises += workoutItem.exercisesList.get(i).name;
            } else {
                workoutExercises += workoutItem.exercisesList.get(i).name + ", ";
            }
        }
        mHolder.mAbout.setText(workoutExercises);


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (workoutItem != null) {


                    Intent intent = new Intent(mContext, StatisticsActivity.class);
                    intent.putExtra("workoutItemStats", workoutItem);
                    Log.d("workoutItemExArSize", String.valueOf(workoutItem.exercisesList.size()));
                    mContext.startActivity(intent);
                }
            }
        });

        return convertView;
    }

}
