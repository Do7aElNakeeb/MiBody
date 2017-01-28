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
import android.widget.Button;
import android.widget.EditText;
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

    private int paddingWidth = 0;
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



    public WorkoutExItemAdapter(Context context, ArrayList<WorkoutExItem> workoutExItemArrayList, OnItemClickListener onItemClickListener){
        this.workoutExItemArrayList = workoutExItemArrayList;
        this.context = context;
        this.onItemClickListener = onItemClickListener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_workout_exercises_item, parent, false);
            return new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_workout_exercises_item, parent, false);
            view.setVisibility(View.VISIBLE);
            return new ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        if (getItemViewType(position) == VIEW_TYPE_ITEM) {

            final WorkoutExItem workoutExItem = workoutExItemArrayList.get(position -1);

            final LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) holder.LLtoMove.getLayoutParams();

            holder.exNumber.setText(String.valueOf(position));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    params.topMargin = 0;
                    holder.LLtoMove.setLayoutParams(params);
                    onItemClickListener.onItemClick(workoutExItem, holder.getAdapterPosition(), holder);
                }
            });


            if (position == focusedItem) {

                if (position == selectedItem){
                    holder.exName.setVisibility(View.GONE);
                }
                else {
                    params.topMargin = (int) context.getResources().getDimension(R.dimen.focused_item);
                    holder.exName.setVisibility(View.VISIBLE);
                    holder.LLtoMove.setLayoutParams(params);
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
                params.topMargin = (int) context.getResources().getDimension(R.dimen.focused_item);
                holder.LLtoMove.setLayoutParams(params);
                holder.exDot1.setVisibility(View.GONE);
                holder.exDot2.setVisibility(View.GONE);
                holder.itemView.setScaleY((float) 0.75);
                holder.itemView.setScaleX((float) 0.75);
                holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.MiBodyOrange));
                holder.icon.setColorFilter(ContextCompat.getColor(context, R.color.MiBodyWhite));
            }
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
                            //CardView view = (CardView) event.getLocalState();
                            //ViewGroup owner = (ViewGroup) view.getParent();
                            //owner.removeView(view);
                            // CardView container = (CardView) v;
                            //container = tempVH.cardView;
                            //view.setCardBackgroundColor(ContextCompat.getColor(context, R.color.black));
                            //view.setVisibility(View.VISIBLE);
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
            holder.itemView.setVisibility(View.INVISIBLE);
            holder.exDot1.setVisibility(View.INVISIBLE);
            holder.exDot2.setVisibility(View.INVISIBLE);
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

        public LinearLayout LLtoMove;

        public ViewHolder(final View itemView) {
            super(itemView);

            LLtoMove = (LinearLayout) itemView.findViewById(R.id.LLtoMove);

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
