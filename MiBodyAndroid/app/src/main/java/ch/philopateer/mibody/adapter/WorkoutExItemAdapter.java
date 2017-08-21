package ch.philopateer.mibody.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ch.philopateer.mibody.R;
import ch.philopateer.mibody.app.AppConfig;
import ch.philopateer.mibody.object.ExerciseItem;
import ch.philopateer.mibody.object.WorkoutExItem;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by NakeebMac on 10/5/16.
 */

public class WorkoutExItemAdapter extends RecyclerView.Adapter<WorkoutExItemAdapter.ViewHolder> {

    public ArrayList<WorkoutExItem> workoutExItemArrayList;
    private Context context;

    private float screenWidth = 0;
    private int focusedItem = 2;
    private int selectedItem = -1;

    private static final int VIEW_TYPE_PADDING = 1;
    private static final int VIEW_TYPE_ITEM = 2;

    public interface OnItemClickListener {
        void onItemClick(WorkoutExItem workoutExItem, int position);
    }

    private final OnItemClickListener onItemClickListener;

    public WorkoutExItemAdapter(Context context, ArrayList<WorkoutExItem> workoutExItemArrayList, float screenWidth, OnItemClickListener onItemClickListener){
        this.workoutExItemArrayList = workoutExItemArrayList;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
        this.screenWidth = screenWidth;

        Log.d("workoutAdapter", String.valueOf(workoutExItemArrayList.get(0).name));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_workout_exercises_item, parent, false);
        if (viewType != VIEW_TYPE_ITEM) {

            view.setVisibility(View.INVISIBLE);
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        if (getItemViewType(position) == VIEW_TYPE_ITEM) {

            final WorkoutExItem workoutExItem = workoutExItemArrayList.get(position -1);

            if (workoutExItem.exReps > 1) {
                holder.exRepsTV.setText(String.valueOf(workoutExItem.exReps));
                holder.exRepsCV.setVisibility(View.VISIBLE);
            }
            else {
                holder.exRepsCV.setVisibility(View.GONE);
            }

            holder.exName.setText(workoutExItem.name);

            holder.exNumber.setText(String.valueOf(position));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    onItemClickListener.onItemClick(workoutExItem, holder.getAdapterPosition());
                }
            });

            if (holder.exName.getText().toString().equals("Drop here")){
                holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.MiBodyGrey3));
                holder.itemView.setAlpha((float) 0.6);
                workoutExItem.name = holder.exName.getText().toString();
                holder.icon.setImageResource(R.drawable.drop_icon);
            }
            else {
                holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.MiBodyOrange));
                holder.itemView.setAlpha(1);
                new Picasso.Builder(context)
                        .downloader(new OkHttpDownloader(context, Integer.MAX_VALUE))
                        .build()
                        .load(AppConfig.URL_SERVER + "ExIcon/" + workoutExItem.exerciseImage)
                        .placeholder(AppConfig.exIcons.get(workoutExItem.name))
                        .into(holder.icon);

            }

            if (position == focusedItem) {

                if (position == selectedItem){
                    holder.exName.setVisibility(View.INVISIBLE);

                    holder.itemView.setScaleY(1);
                    holder.itemView.setScaleX(1);
                    holder.exDot1.setVisibility(View.VISIBLE);
                    holder.exDot2.setVisibility(View.VISIBLE);
                    workoutExItem.name = holder.exName.getText().toString();
                }
                else {
                    holder.exName.setVisibility(View.VISIBLE);
                }

                holder.icon.setAlpha((float) 1);
                holder.cardView.setAlpha(1);
                holder.exDot1.setVisibility(View.VISIBLE);
                holder.exDot2.setVisibility(View.VISIBLE);

                holder.itemView.setScaleY(1);
                holder.itemView.setScaleX(1);

            }
            else {

                holder.exDot1.setVisibility(View.GONE);
                holder.exDot2.setVisibility(View.GONE);

                holder.itemView.setScaleY((float) 0.75);
                holder.itemView.setScaleX((float) 0.75);

                if (selectedItem != -1) {
                    holder.exName.setVisibility(View.INVISIBLE);
                }
                else {
                    holder.exName.setVisibility(View.VISIBLE);
                }

            }

            holder.cardView.setOnDragListener(new View.OnDragListener() {
                @Override
                public boolean onDrag(View v, DragEvent event) {

                    switch (event.getAction()) {
                        case DragEvent.ACTION_DROP:
                            // Dropped, reassign View to ViewGroup
                            CardView view = (CardView) event.getLocalState();
                            ExerciseItem exerciseItem = (ExerciseItem) view.getTag(R.id.exDot2);
                            if (holder.exName.getText().toString().equals("Drop here")){
                                Log.d("tsss", "drop here text");
                                workoutExItemArrayList.add(new WorkoutExItem());
                                notifyItemInserted(workoutExItemArrayList.size());
                                setFocusedItem(holder.getAdapterPosition());
                            }

                            Log.d("tsss111", "drop here text");
                            new Picasso.Builder(context)
                                    .downloader(new OkHttpDownloader(context, Integer.MAX_VALUE))
                                    .build()
                                    .load(AppConfig.URL_SERVER + "ExIcon/" + exerciseItem.getIcon())
                                    .placeholder(AppConfig.exercises_icons[(int) view.getTag(R.id.exDot1)])
                                    .into(holder.icon);

                            holder.exName.setText(exerciseItem.getName());
                            holder.exName.setVisibility(View.VISIBLE);
                            workoutExItem.exerciseImage = exerciseItem.getIcon();
                            workoutExItem.name = exerciseItem.getName();

                            //view.setCardBackgroundColor(ContextCompat.getColor(context, R.color.black));
                            view.setVisibility(View.VISIBLE);
                            notifyDataSetChanged();
                            break;

                        default:
                            break;
                    }
                    return true;
                }
            });
        }
        else {
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
            params.width = (int) screenWidth / 2;
            holder.itemView.setLayoutParams(params);
        }
    }

    public void setFocusedItem(int focusedItem) {
        this.focusedItem = focusedItem;
        notifyDataSetChanged();
    }

    public void setSelectedItem(int selectedItem){
        this.selectedItem = selectedItem;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return workoutExItemArrayList.size() + 2;
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0 || position == getItemCount()-1) {
            return VIEW_TYPE_PADDING;
        }
        return VIEW_TYPE_ITEM;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView, exDot1, exDot2, exRepsCV;
        ImageView icon;
        TextView exName, exNumber, exRepsTV;

        public LinearLayout LLtoMove, addWorkoutLL;

        public ViewHolder(final View itemView) {
            super(itemView);

            LLtoMove = (LinearLayout) itemView.findViewById(R.id.LLtoMove);
            addWorkoutLL = (LinearLayout) itemView.findViewById(R.id.addWorkoutLL);

            cardView = (CardView) itemView.findViewById(R.id.ExerciseCV);
            icon = (ImageView) itemView.findViewById(R.id.ExerciseIcon);
            exNumber = (TextView) itemView.findViewById(R.id.exNumber);
            exName = (TextView) itemView.findViewById(R.id.exName);
            exDot1 = (CardView) itemView.findViewById(R.id.exDot1);
            exDot2 = (CardView) itemView.findViewById(R.id.exDot2);

            exRepsCV = (CardView) itemView.findViewById(R.id.exRepsCV);
            exRepsTV = (TextView) itemView.findViewById(R.id.exRepsTV);


        }

    }
}
