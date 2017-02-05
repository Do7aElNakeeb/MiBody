package com.mibody.app.helper;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

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
    ArrayList<WorkoutExItem> workoutExItemArrayList;
    private int focusedItem = 2;


    public WorkoutPlayExItemsAdapter(Context context, ArrayList<WorkoutExItem> workoutExItemArrayList){
        this.context = context;
        this.workoutExItemArrayList = workoutExItemArrayList;
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

            final WorkoutExItem workoutExItem = workoutExItemArrayList.get(position -1);
            new Picasso.Builder(context).downloader(new OkHttpDownloader(context, Integer.MAX_VALUE)).build().load(AppConfig.URL_SERVER + "ExIcon/" + workoutExItem.exerciseImage).into(holder.exIcon);
            holder.exReps.setText(String.valueOf(workoutExItem.exReps));
            holder.exProgressBar.setMax(workoutExItem.exReps);
            holder.exProgressBar.setProgress(0);
            holder.exRepsCounter.setText(String.valueOf(0));
            holder.exIcon.setColorFilter(ContextCompat.getColor(context, R.color.MiBodyWhite));

            if (position == focusedItem) {

                holder.exDot1.setVisibility(View.VISIBLE);
                holder.exDot2.setVisibility(View.VISIBLE);
                holder.exDot11.setVisibility(View.VISIBLE);
                holder.exDot22.setVisibility(View.VISIBLE);
                holder.itemView.setAlpha(1);

                holder.exRepsCounter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holder.workoutExRepsCountET.setVisibility(View.VISIBLE);
                        holder.exRepsCounter.setVisibility(View.GONE);
                    }
                });

                holder.workoutExRepsCountET.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        holder.exRepsCounter.setText(editable.toString());
                        holder.exRepsCounter.setVisibility(View.VISIBLE);
                        holder.workoutExRepsCountET.setVisibility(View.GONE);
                        holder.exProgressBar.setProgress(Integer.valueOf(holder.exRepsCounter.getText().toString()));
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
        }
    }

    @Override
    public int getItemCount() {
        return workoutExItemArrayList.size() + 2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView exDot1, exDot11, exDot2, exDot22;
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

            workoutExRepsCountET = (EditText) itemView.findViewById(R.id.workoutExRepsCountET);
            exProgressBar = (ProgressBar) itemView.findViewById(R.id.exProgressBar);
            exRepsCounter = (TextView) itemView.findViewById(R.id.exNumber);
            exReps = (TextView) itemView.findViewById(R.id.exName);
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
