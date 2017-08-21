package ch.philopateer.mibody.helper;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import ch.philopateer.mibody.R;
import ch.philopateer.mibody.app.AppConfig;
import ch.philopateer.mibody.object.WorkoutExItem;

import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by mamdouhelnakeeb on 1/28/17.
 */

public class WorkoutPlayExItemsAdapter extends RecyclerView.Adapter<WorkoutPlayExItemsAdapter.ViewHolder> {


    private static final int VIEW_TYPE_PADDING = 1;
    private static final int VIEW_TYPE_ITEM = 2;
    Context context;
    int scrSize;
    public ArrayList<WorkoutExItem> workoutExItemArrayList;
    public ArrayList<Integer> achWorkoutExItemArrayList;
    int achExReps;
    private int focusedItem = -1;

    private int itemAction = 0;


    public WorkoutPlayExItemsAdapter(Context context, int scrSize, ArrayList<WorkoutExItem> workoutExItemArrayList, ArrayList<Integer> achWorkoutExItemArrayList){
        this.context = context;
        this.scrSize = scrSize;
        this.workoutExItemArrayList = workoutExItemArrayList;
        this.achWorkoutExItemArrayList = achWorkoutExItemArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_play_ex_item, parent, false);
            return new WorkoutPlayExItemsAdapter.ViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_play_ex_item, parent, false);
            view.setVisibility(View.INVISIBLE);
            return new WorkoutPlayExItemsAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (getItemViewType(position) == VIEW_TYPE_ITEM) {

            final WorkoutExItem workoutExItem = workoutExItemArrayList.get(position - 1);
            achExReps = achWorkoutExItemArrayList.get(position-1);

            /*
            holder.exIcon.setColorFilter(ContextCompat.getColor(context, R.color.MiBodyWhite));
            new Picasso.Builder(context)
                    .downloader(new OkHttpDownloader(context, Integer.MAX_VALUE))
                    .build()
                    .load(AppConfig.URL_SERVER + "ExIcon/" + workoutExItem.exerciseImage)
                    .placeholder(AppConfig.exercises_icons[Arrays.asList(AppConfig.exercises_names).indexOf(workoutExItem.name)])
                    .into(holder.exIcon);
*/
            Log.d("exImageHere", String.valueOf(workoutExItem.name));

            holder.exReps.setText(String.valueOf(workoutExItem.reps));
            holder.exRepsCounter.setText(String.valueOf(achExReps));
            holder.exProgressBar.setMax(workoutExItem.reps);
            holder.exProgressBar.setProgress(achExReps);

            if (workoutExItem.exReps < 1){
                holder.exRepsCV.setVisibility(View.GONE);
            }
            else {
                holder.exRepsTV.setText(String.valueOf(workoutExItem.exReps));
                holder.exRepsCV.setVisibility(View.VISIBLE);
            }


            if(itemAction == 0) {
                holder.counterMinusCV.setVisibility(View.GONE);
                holder.counterPlusCV.setVisibility(View.GONE);
            }

            if (position == focusedItem) {

                if(itemAction == 1){
                    holder.counterMinusCV.setVisibility(View.VISIBLE);
                    holder.counterPlusCV.setVisibility(View.VISIBLE);
                }

                /*
                holder.exDot1.setVisibility(View.VISIBLE);
                holder.exDot2.setVisibility(View.VISIBLE);
                holder.exDot11.setVisibility(View.VISIBLE);
                holder.exDot22.setVisibility(View.VISIBLE);
                */
                holder.itemView.setAlpha(1);

                /*
                holder.exRepsCounter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemClickListener.onItemClick(holder.getAdapterPosition());
                        holder.workoutExRepsCountET.setText(holder.exRepsCounter.getText().toString());
                        holder.workoutExRepsCountET.setVisibility(View.VISIBLE);
                        holder.exRepsCounter.setVisibility(View.GONE);
                        holder.trueMarkCV.setVisibility(View.VISIBLE);


                        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(holder.workoutExRepsCountET, InputMethodManager.SHOW_FORCED);

                    }
                });
*/

                holder.counterPlusCV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (holder.counterPlusCV.getVisibility() == View.VISIBLE){
                            holder.exRepsCounter.setText(String.valueOf(Integer.parseInt(holder.exRepsCounter.getText().toString()) + 1));
                            holder.exProgressBar.setProgress(Integer.parseInt(holder.exRepsCounter.getText().toString()));

                            achWorkoutExItemArrayList.set(holder.getAdapterPosition() - 1,
                                    Integer.parseInt(holder.exRepsCounter.getText().toString()));

                            Log.d("achExRepsAdapter", String.valueOf(achWorkoutExItemArrayList.get(holder.getAdapterPosition() - 1)));
                        }
                    }
                });

                holder.counterMinusCV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (holder.counterMinusCV.getVisibility() == View.VISIBLE && Integer.parseInt(holder.exRepsCounter.getText().toString()) > 0){
                            holder.exRepsCounter.setText(String.valueOf(Integer.parseInt(holder.exRepsCounter.getText().toString()) - 1));
                            holder.exProgressBar.setProgress(Integer.parseInt(holder.exRepsCounter.getText().toString()));

                            achWorkoutExItemArrayList.set(holder.getAdapterPosition() - 1,
                                    Integer.parseInt(holder.exRepsCounter.getText().toString()));

                            Log.d("achExRepsAdapter", String.valueOf(achWorkoutExItemArrayList.get(holder.getAdapterPosition() - 1)));
                        }
                    }
                });

            }
            else {
                holder.exDot1.setVisibility(View.GONE);
                holder.exDot2.setVisibility(View.GONE);
                holder.exDot11.setVisibility(View.GONE);
                holder.exDot22.setVisibility(View.GONE);
                holder.itemView.setAlpha((float) 0.5);
            }

        }
        else {
            holder.itemView.setVisibility(View.INVISIBLE);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
            params.width = scrSize / 2;
            holder.itemView.setLayoutParams(params);
        }
    }

    @Override
    public int getItemCount() {
        return workoutExItemArrayList.size() + 2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView exDot1, exDot11, exDot2, exDot22, counterMinusCV, counterPlusCV, exRepsCV;
        TextView exRepsCounter, exReps, exRepsTV;
        EditText workoutExRepsCountET;
        ImageView exIcon;
        ProgressBar exProgressBar;

        public ViewHolder(View itemView) {
            super(itemView);

            exIcon = (ImageView) itemView.findViewById(R.id.workoutExItemIcon);
            exDot1 = (CardView) itemView.findViewById(R.id.exDot1);
            exDot2 = (CardView) itemView.findViewById(R.id.exDot2);
            exDot11 = (CardView) itemView.findViewById(R.id.exDot11);
            exDot22 = (CardView) itemView.findViewById(R.id.exDot22);
            counterMinusCV = (CardView) itemView.findViewById(R.id.counterMinusCV);
            counterPlusCV = (CardView) itemView.findViewById(R.id.counterPlusCV);
            exRepsCV = (CardView) itemView.findViewById(R.id.exRepsCV);

            workoutExRepsCountET = (EditText) itemView.findViewById(R.id.workoutExRepsCountET);
            exProgressBar = (ProgressBar) itemView.findViewById(R.id.exProgressBar);
            exRepsCounter = (TextView) itemView.findViewById(R.id.workoutExRepsCount);
            exReps = (TextView) itemView.findViewById(R.id.workoutExerciseReps);
            exRepsTV = (TextView) itemView.findViewById(R.id.exRepsTV);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 || position == getItemCount()-1) {
            return VIEW_TYPE_PADDING;
        }
        else {
            return VIEW_TYPE_ITEM;
        }
    }

    public void setFocusedItem(int focusedItem) {
        this.focusedItem = focusedItem;
        notifyDataSetChanged();
    }

    public void setItemAction(int itemAction){
        this.itemAction = itemAction;
        notifyDataSetChanged();
    }
}