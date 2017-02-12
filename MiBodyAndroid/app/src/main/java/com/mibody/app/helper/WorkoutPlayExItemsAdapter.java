package com.mibody.app.helper;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mibody.app.R;
import com.mibody.app.app.AppConfig;
import com.mibody.app.app.WorkoutExItem;
import com.mibody.app.app.WorkoutItem;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

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
    private int focusedItem = 2;


    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private final OnItemClickListener onItemClickListener;



    public WorkoutPlayExItemsAdapter(Context context, int scrSize, ArrayList<WorkoutExItem> workoutExItemArrayList, ArrayList<Integer> achWorkoutExItemArrayList, OnItemClickListener onItemClickListener){
        this.context = context;
        this.scrSize = scrSize;
        this.workoutExItemArrayList = workoutExItemArrayList;
        this.achWorkoutExItemArrayList = achWorkoutExItemArrayList;
        this.onItemClickListener = onItemClickListener;
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

            holder.exIcon.setColorFilter(ContextCompat.getColor(context, R.color.MiBodyWhite));
            new Picasso.Builder(context)
                    .downloader(new OkHttpDownloader(context, Integer.MAX_VALUE))
                    .build()
                    .load(AppConfig.URL_SERVER + "ExIcon/" + workoutExItem.exerciseImage)
                    .into(holder.exIcon);

            Log.d("exImageHere", String.valueOf(workoutExItem.reps));
            holder.exReps.setText(String.valueOf(workoutExItem.reps));
            holder.exProgressBar.setMax(workoutExItem.reps);
            //holder.exProgressBar.setProgress(Integer.parseInt(holder.exRepsCounter.getText().toString()));

            if (position == focusedItem) {

                holder.exDot1.setVisibility(View.VISIBLE);
                holder.exDot2.setVisibility(View.VISIBLE);
                holder.exDot11.setVisibility(View.VISIBLE);
                holder.exDot22.setVisibility(View.VISIBLE);
                holder.itemView.setAlpha(1);

                holder.exRepsCounter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onItemClickListener.onItemClick(holder.getAdapterPosition());
                        holder.workoutExRepsCountET.setText(holder.exRepsCounter.getText().toString());
                        holder.workoutExRepsCountET.setVisibility(View.VISIBLE);
                        holder.exRepsCounter.setVisibility(View.GONE);
                        holder.trueMarkCV.setVisibility(View.VISIBLE);

                        /*
                        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(holder.workoutExRepsCountET, InputMethodManager.SHOW_IMPLICIT);
                        */
                    }
                });


                holder.trueMarkCV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (holder.trueMarkCV.getVisibility() == View.VISIBLE){
                            if(holder.workoutExRepsCountET.getText().toString().equals("")){
                                Toast.makeText(context, "Enter Reps Value", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                achWorkoutExItemArrayList.set(holder.getAdapterPosition() - 1, Integer.parseInt(holder.workoutExRepsCountET.getText().toString()) + achWorkoutExItemArrayList.get(holder.getAdapterPosition() - 1));
                                //achWorkoutExItemArrayList.get(holder.getAdapterPosition() - 1) = ;
                                Log.d("achExRepsAdapter", String.valueOf(achWorkoutExItemArrayList.get(holder.getAdapterPosition() - 1)));

                                holder.exRepsCounter.setText(holder.workoutExRepsCountET.getText().toString());
                                holder.exProgressBar.setProgress(Integer.parseInt(holder.workoutExRepsCountET.getText().toString()));

                                holder.exRepsCounter.setVisibility(View.VISIBLE);
                                holder.workoutExRepsCountET.setVisibility(View.GONE);
                                holder.trueMarkCV.setVisibility(View.INVISIBLE);

                                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(holder.workoutExRepsCountET.getWindowToken(), 0);
                            }
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

        CardView exDot1, exDot11, exDot2, exDot22, trueMarkCV;
        TextView exRepsCounter, exReps;
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
            trueMarkCV = (CardView) itemView.findViewById(R.id.trueMarkCV);

            workoutExRepsCountET = (EditText) itemView.findViewById(R.id.workoutExRepsCountET);
            exProgressBar = (ProgressBar) itemView.findViewById(R.id.exProgressBar);
            exRepsCounter = (TextView) itemView.findViewById(R.id.workoutExRepsCount);
            exReps = (TextView) itemView.findViewById(R.id.workoutExerciseReps);
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
}