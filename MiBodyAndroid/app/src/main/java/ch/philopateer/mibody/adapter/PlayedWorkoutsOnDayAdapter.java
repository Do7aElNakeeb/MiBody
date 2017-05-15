package ch.philopateer.mibody.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ch.philopateer.mibody.R;
import ch.philopateer.mibody.fragments.ScheduleFragment;
import ch.philopateer.mibody.helper.OnItemClickListener;
import ch.philopateer.mibody.helper.WorkoutExItemAdapter;
import ch.philopateer.mibody.object.WorkoutItem;

/**
 * Created by mamdouhelnakeeb on 5/15/17.
 */

public class PlayedWorkoutsOnDayAdapter extends RecyclerView.Adapter<PlayedWorkoutsOnDayAdapter.ViewHolder> {

    private Context context;
    private ScheduleFragment scheduleFragment;
    private OnItemClickListener onItemClickListener;

    private WorkoutItem workoutItem = null;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public PlayedWorkoutsOnDayAdapter(Context context, ScheduleFragment scheduleFragment, OnItemClickListener onItemClickListener){
        this.context = context;
        this.scheduleFragment = scheduleFragment;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_played_workout_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        if (scheduleFragment.mSavedEventsPerDay.containsKey(scheduleFragment.selectedDay)) {
            workoutItem = scheduleFragment.mSavedEventsPerDay.get(scheduleFragment.selectedDay).get(position);
            workoutItem.JSONtoArray();
        }


        holder.workoutName.setText(workoutItem.workoutName);

        String workoutExercises = "";
        for (int i = 0; i < workoutItem.exercisesList.size(); i++) {
            if (i == workoutItem.exercisesList.size() - 1) {
                workoutExercises += workoutItem.exercisesList.get(i).name;
            } else {
                workoutExercises += workoutItem.exercisesList.get(i).name + ", ";
            }
        }
        holder.workoutExercises.setText(workoutExercises);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return scheduleFragment.mNumEventsOnDay;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView workoutName, workoutExercises;

        public ViewHolder(View itemView) {
            super(itemView);

            workoutName = (TextView) itemView.findViewById(R.id.saved_event_title_textView);
            workoutExercises = (TextView) itemView.findViewById(R.id.saved_event_about_textView);
        }
    }
}
