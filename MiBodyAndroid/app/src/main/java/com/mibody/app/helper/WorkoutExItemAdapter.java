package com.mibody.app.helper;

import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mibody.app.R;
import com.mibody.app.app.AppConfig;
import com.mibody.app.app.ExerciseItem;
import com.mibody.app.app.WorkoutExItem;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


/**
 * Created by NakeebMac on 10/5/16.
 */

public class WorkoutExItemAdapter extends RecyclerView.Adapter<WorkoutExItemAdapter.ViewHolder> {

    private ArrayList<WorkoutExItem> workoutExItemArrayList;
    private Context context;

    private float screenWidth = 0;
    private int focusedItem = 2;
    private int selectedItem = -1;

    private static final int VIEW_TYPE_PADDING = 1;
    private static final int VIEW_TYPE_ITEM = 2;

    private static final int VIEW_TYPE_BEFORE_FOCUSED = 10;
    private static final int VIEW_TYPE_FOCUSED = 11;
    private static final int VIEW_TYPE_AFTER_FOCUSED = 12;

    public interface OnItemClickListener {
        void onItemClick(WorkoutExItem workoutExItem, int position, ViewHolder viewHolder);
    }

    public interface OnItemFocusListener {
        void onItemFocus(ViewHolder viewHolder, int position);
    }


    private final OnItemClickListener onItemClickListener;



    public WorkoutExItemAdapter(Context context, ArrayList<WorkoutExItem> workoutExItemArrayList, float screenWidth, OnItemClickListener onItemClickListener){
        this.workoutExItemArrayList = workoutExItemArrayList;
        this.context = context;
        this.onItemClickListener = onItemClickListener;
        this.screenWidth = screenWidth;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_workout_exercises_item, parent, false);
            return new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_workout_exercises_item, parent, false);
            view.setVisibility(View.INVISIBLE);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        if (getItemViewType(position) == VIEW_TYPE_ITEM) {

            final WorkoutExItem workoutExItem = workoutExItemArrayList.get(position -1);

            final LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.LLtoMove.getLayoutParams();
            //params.bottomMargin = 0;
            //holder.LLtoMove.setLayoutParams(params);

            RecyclerView.LayoutParams addWorkoutLLparams = (RecyclerView.LayoutParams) holder.addWorkoutLL.getLayoutParams();

            holder.exNumber.setText(String.valueOf(position));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
              //      params.bottomMargin = 0;
                //    holder.LLtoMove.setLayoutParams(params);
                    onItemClickListener.onItemClick(workoutExItem, holder.getAdapterPosition(), holder);
                }
            });

            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.MiBodyOrange));
            holder.itemView.setAlpha(1);

            if (position == focusedItem) {

                //addWorkoutLLparams.width = (int) screenWidth / 2;
                //holder.addWorkoutLL.setLayoutParams(addWorkoutLLparams);
                if (position == selectedItem){
                    holder.exName.setVisibility(View.INVISIBLE);
                    holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.MiBodyOrange));
                    holder.itemView.setAlpha(1);

                    //params.topMargin = 100;
                    holder.LLtoMove.setLayoutParams(params);

                    holder.LLtoMove.setPadding(0, 100, 0, 0);

                    //params.bottomMargin = 0;
                    holder.LLtoMove.setLayoutParams(params);
/*
                    Animation shake = AnimationUtils.loadAnimation(context, R.anim.trans_down);
                    holder.LLtoMove.startAnimation(shake);
                    */

                    holder.itemView.setScaleY(1);
                    holder.itemView.setScaleX(1);
                    holder.exDot1.setVisibility(View.VISIBLE);
                    holder.exDot2.setVisibility(View.VISIBLE);
                }
                else {
                    //addWorkoutLLparams.bottomMargin = 5;
                    //holder.addWorkoutLL.setLayoutParams(addWorkoutLLparams);

                    //params.topMargin = 0;
                    holder.LLtoMove.setLayoutParams(params);
                    holder.LLtoMove.setPadding(0, 0, 0, 0);


                    holder.exName.setVisibility(View.VISIBLE);
                    if (selectedItem != -1) {

                        //params.bottomMargin = 100;
                        //holder.LLtoMove.setLayoutParams(params);

                        //holder.LLtoMove.setAnimation(new TranslateAnimation(0, 0, 0, 0, Animation.ABSOLUTE, 100, Animation.ABSOLUTE, 0));
                    }
                }

                holder.exDot1.setVisibility(View.VISIBLE);
                holder.exDot2.setVisibility(View.VISIBLE);
//                holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.MiBodyWhite));
//                holder.icon.setColorFilter(ContextCompat.getColor(context, R.color.MiBodyOrange));

                holder.itemView.setScaleY(1);
                holder.itemView.setScaleX(1);

                //holder.itemView.setPadding(0, 0, 0, 0);
                // run scale animation and make it bigger
                //Animation anim = AnimationUtils.loadAnimation(context, R.anim.scale_in_ex);
                //Log.d("animation", "hasFocus");
                //holder.itemView.startAnimation(anim);
                //anim.setFillAfter(true);
            }
            else {
                //params.bottomMargin = (int) context.getResources().getDimension(R.dimen.focused_item);
                //holder.LLtoMove.setLayoutParams(params);
                holder.exDot1.setVisibility(View.GONE);
                holder.exDot2.setVisibility(View.GONE);

                holder.itemView.setScaleY((float) 0.75);
                holder.itemView.setScaleX((float) 0.75);

                //addWorkoutLLparams.width = (int) screenWidth / 4;
                //holder.addWorkoutLL.setLayoutParams(addWorkoutLLparams);
                holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.black));
                holder.itemView.setAlpha((float) 0.3);
                holder.icon.setColorFilter(ContextCompat.getColor(context, R.color.MiBodyWhite));
            }
            //holder.addWorkoutLL.setLayoutParams(addWorkoutLLparams);

            /*
            else if (position < focusedItem){
                holder.exDot1.setVisibility(View.VISIBLE);
                holder.exDot2.setVisibility(View.GONE);
                holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.MiBodyOrange));
                holder.icon.setColorFilter(ContextCompat.getColor(context, R.color.MiBodyWhite));

                holder.exName.setVisibility(View.VISIBLE);
                //holder.itemView.setPadding(0, 0, 0, 0);

                // run scale animation and make it bigger
                //Animation anim = AnimationUtils.loadAnimation(context, R.anim.scale_out_ex);
                //Log.d("animation", "smallerFocus");
                //holder.itemView.startAnimation(anim);
                //anim.setFillAfter(true);
            }
            else if (position > focusedItem){
                holder.exDot1.setVisibility(View.VISIBLE);
                holder.exDot2.setVisibility(View.VISIBLE);
                holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.MiBodyOrange));
                holder.icon.setColorFilter(ContextCompat.getColor(context, R.color.MiBodyWhite));

                holder.exName.setVisibility(View.VISIBLE);
                //holder.itemView.setPadding(0, 0, 0, 0);

                // run scale animation and make it bigger
                //Animation anim = AnimationUtils.loadAnimation(context, R.anim.scale_out_ex);
                //Log.d("animation", "biggerFocus");
                //holder.itemView.startAnimation(anim);
                //anim.setFillAfter(true);
            }
*/
            holder.cardView.setOnDragListener(new View.OnDragListener() {
                @Override
                public boolean onDrag(View v, DragEvent event) {
                    int action = event.getAction();
                    switch (event.getAction()) {
                        case DragEvent.ACTION_DRAG_STARTED:
                            // do nothing
                            break;
                        case DragEvent.ACTION_DRAG_ENTERED:
//                    v.setBackgroundDrawable(enterShape);
                            break;
                        case DragEvent.ACTION_DRAG_EXITED:
//                    v.setBackgroundDrawable(normalShape);
                            break;
                        case DragEvent.ACTION_DROP:
                            // Dropped, reassign View to ViewGroup
                            CardView view = (CardView) event.getLocalState();
                            ExerciseItem exerciseItem = (ExerciseItem) view.getTag();
                            new Picasso.Builder(context).downloader(new OkHttpDownloader(context, Integer.MAX_VALUE)).build().load(AppConfig.URL_SERVER + "ExIcon/" + exerciseItem.getIcon()).into(holder.icon);
                            holder.exName.setText(exerciseItem.getName());
                            holder.exName.setVisibility(View.VISIBLE);

                            //view.setCardBackgroundColor(ContextCompat.getColor(context, R.color.black));
                            view.setVisibility(View.VISIBLE);
                            notifyDataSetChanged();
                            break;
                        case DragEvent.ACTION_DRAG_ENDED:
//                    v.setBackgroundDrawable(normalShape);
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
    //    notifyItemChanged(focusedItem);
  //      notifyItemChanged(focusedItem-1);
//        notifyItemChanged(focusedItem+1);
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

        CardView cardView, exDot1, exDot2;
        ImageView icon;
        TextView exName, exNumber;

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


        }

    }

    private class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.VISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }

    private class MyDragListener implements View.OnDragListener {
        /*
        Drawable enterShape = getResources().getDrawable(
                R.drawable.gym1);
        Drawable normalShape = getResources().getDrawable(R.drawable.gym1);
*/
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
//                    v.setBackgroundDrawable(enterShape);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
//                    v.setBackgroundDrawable(normalShape);
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    CardView view = (CardView) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    //owner.removeView(view);
                    CardView container = (CardView) v;
                    container.addView(view);
                    //view.setCardBackgroundColor(ContextCompat.getColor(context, R.color.black));
                    view.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
//                    v.setBackgroundDrawable(normalShape);
                default:
                    break;
            }
            return true;
        }

    }
}
