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
    private int focusedItem = -1;
    private int selectedItem = -1;

    private static final int VIEW_TYPE_PADDING = 1;
    private static final int VIEW_TYPE_ITEM = 2;


    public WorkoutExItemAdapter(Context context, ArrayList<WorkoutExItem> workoutExItemArrayList, int paddingWidth){
        this.workoutExItemArrayList = workoutExItemArrayList;
        this.context = context;
        this.paddingWidth = paddingWidth;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_workout_exercises_item, parent, false);
            return new ViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_workout_exercises_item, parent, false);

            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
            layoutParams.width = paddingWidth;
            view.setLayoutParams(layoutParams);

            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {


        if (getItemViewType(position) == VIEW_TYPE_ITEM) {

            final WorkoutExItem workoutExItem = workoutExItemArrayList.get(position -1);

            holder.exNumber.setText(String.valueOf(position));

            if (position == focusedItem) {
                holder.exDot1.setVisibility(View.VISIBLE);
                holder.exDot2.setVisibility(View.VISIBLE);

                // run scale animation and make it bigger
                Animation anim = AnimationUtils.loadAnimation(context, R.anim.scale_in_ex);
                Log.d("animation", "hhasFocus");
                //holder.cardView.startAnimation(anim);
                anim.setFillAfter(true);
            }
            else if (position < focusedItem){
                holder.exDot1.setVisibility(View.VISIBLE);
                holder.exDot2.setVisibility(View.GONE);

                // run scale animation and make it bigger
                Animation anim = AnimationUtils.loadAnimation(context, R.anim.scale_out_ex);
                Log.d("animation", "hhasFocus");
                //holder.cardView.startAnimation(anim);
                anim.setFillAfter(true);
            }
            else if (position > focusedItem){
                holder.exDot1.setVisibility(View.GONE);
                holder.exDot2.setVisibility(View.VISIBLE);

                // run scale animation and make it bigger
                Animation anim = AnimationUtils.loadAnimation(context, R.anim.scale_out_ex);
                Log.d("animation", "hhasFocus");
                //holder.cardView.startAnimation(anim);
                anim.setFillAfter(true);
            }


            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (workoutExItem.name == null){
                        holder.workoutExDetailsLayout.setVisibility(View.GONE);
                        Toast.makeText(context, "Drag Exercise and Drop Here Firstly", Toast.LENGTH_LONG).show();
                    }
                    else {
                        holder.workoutExDetailsLayout.setVisibility(View.VISIBLE);
                    }
                }
            });

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
        return workoutExItemArrayList.size();
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

        LinearLayout workoutExDetailsLayout;
        ImageView b1, b2, b3, r1, r2, r3, y1, y2, y3;
        TextView repsTxtView, restTxtView, exRepsTxtView;
        EditText repsEdtTxt, restEdtTxt;
        Button repsMinusBtn, repsPlusBtn, restMinusBtn, restPlusBtn, exRepsPlusBtn;


        public ViewHolder(final View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.ExerciseCV);
            icon = (ImageView) itemView.findViewById(R.id.ExerciseIcon);
            exNumber = (TextView) itemView.findViewById(R.id.exNumber);
            exName = (TextView) itemView.findViewById(R.id.exName);
            exDot1 = (CardView) itemView.findViewById(R.id.exDot1);
            exDot2 = (CardView) itemView.findViewById(R.id.exDot2);

            workoutExDetailsLayout = (LinearLayout) itemView.findViewById(R.id.workoutExerciseDetails);
//             workoutExDetailsLayout.setVisibility(View.GONE);

            // Ropes
            b1 = (ImageView) itemView.findViewById(R.id.ropes_black1);
            b2 = (ImageView) itemView.findViewById(R.id.ropes_black2);
            b3 = (ImageView) itemView.findViewById(R.id.ropes_black3);
            r1 = (ImageView) itemView.findViewById(R.id.ropes_red1);
            r2 = (ImageView) itemView.findViewById(R.id.ropes_red2);
            r3 = (ImageView) itemView.findViewById(R.id.ropes_red3);
            y1 = (ImageView) itemView.findViewById(R.id.ropes_yellow1);
            y2 = (ImageView) itemView.findViewById(R.id.ropes_yellow2);
            y3 = (ImageView) itemView.findViewById(R.id.ropes_yellow3);

            // Reps
            repsTxtView = (TextView) itemView.findViewById(R.id.reps_txtview);
            repsEdtTxt = (EditText) itemView.findViewById(R.id.reps_edttxt);
            repsMinusBtn = (Button) itemView.findViewById(R.id.reps_minus_btn);
            repsPlusBtn = (Button) itemView.findViewById(R.id.reps_plus_btn);

            // Rest
            restTxtView = (TextView) itemView.findViewById(R.id.rest_time_txtview);
            restEdtTxt = (EditText) itemView.findViewById(R.id.rest_time_edttxt);
            restMinusBtn = (Button) itemView.findViewById(R.id.rest_minus_btn);
            restPlusBtn = (Button) itemView.findViewById(R.id.rest_plus_btn);

            // Exercise Reps
            exRepsTxtView = (TextView) itemView.findViewById(R.id.exercise_reps_txtview);
            exRepsPlusBtn = (Button) itemView.findViewById(R.id.exercise_reps_circle_btn);

        }
    }

    private class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                        view);
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
