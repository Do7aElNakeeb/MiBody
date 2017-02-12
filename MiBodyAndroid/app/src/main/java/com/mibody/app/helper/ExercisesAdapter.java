package com.mibody.app.helper;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mibody.app.R;
import com.mibody.app.activity.AddWorkout;
import com.mibody.app.app.AppConfig;
import com.mibody.app.app.ExerciseItem;
import com.mibody.app.app.WorkoutExItem;
import com.mibody.app.fragments.ExerciseDetails;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


import android.content.ClipData;
import android.graphics.drawable.Drawable;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;

/**
 * Created by mamdouhelnakeeb on 12/16/16.
 */

public class ExercisesAdapter extends RecyclerView.Adapter<ExercisesAdapter.ViewHolder> {

    private ArrayList<ExerciseItem> exerciseItemArrayList;
    private Context context;
    private int type;
    int rvHeight;

    private ItemClickListener clickListener;

    public interface OnItemClickListener {
        void onItemClick(ExerciseItem exerciseItem, int position);
    }
    //private final OnItemClickListener onItemClickListener;

    public ExercisesAdapter(Context context, int rvHeight, ArrayList<ExerciseItem> exerciseItemArrayList, int type){
        this.exerciseItemArrayList = exerciseItemArrayList;
        this.context = context;
        this.type = type;
        this.rvHeight = rvHeight;
        //this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(R.layout.exercises_item, parent, false);

        return new ViewHolder(mainGroup);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        final ExerciseItem exerciseItem = exerciseItemArrayList.get(position);

        new Picasso.Builder(context).downloader(new OkHttpDownloader(context, Integer.MAX_VALUE)).build().load(AppConfig.URL_SERVER + "ExIcon/" + exerciseItem.getIcon()).into(holder.icon);
        holder.name.setVisibility(View.VISIBLE);

        if(type == 0){

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.ExItemLL.getLayoutParams();
            params.rightMargin = (int) context.getResources().getDimension(R.dimen.exercise_item_margin);
            holder.ExItemLL.setLayoutParams(params);

            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.MiBodyOrange));
            holder.icon.setColorFilter(ContextCompat.getColor(context, R.color.MiBodyWhite));
            holder.name.setText(exerciseItem.getName());
            holder.cardView.setTag(exerciseItem);
            holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ClipData data = ClipData.newPlainText("", "");
                    DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                    v.startDrag(data, shadowBuilder, v, 0);
                    v.setVisibility(View.VISIBLE);
                    /*
                    holder.cardView.setOnTouchListener(new OnTouchListener() {
                        @Override
                        public boolean onTouch(View view, MotionEvent motionEvent) {
                            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                                ClipData data = ClipData.newPlainText("", "");
                                DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                                view.startDrag(data, shadowBuilder, view, 0);
                                view.setVisibility(View.VISIBLE);
                                return true;
                            } else {
                                return false;
                            }
                        }
                    });
                    */
                    return true;
                }
            });

        }
        else if (type == 1){

            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.MiBodyWhite));
            holder.icon.setColorFilter(ContextCompat.getColor(context, R.color.MiBodyOrange));
            holder.name.setText(exerciseItem.getName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ExerciseDetails exerciseDetails = new ExerciseDetails();
                    exerciseDetails.setExercise(exerciseItem);
                    FragmentTransaction ft = ((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                    ft.add(R.id.exercisesFragment, exerciseDetails, "Exercise Details Fragment");
                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            });

            if (position == 3 || position == 4 || position == 5){
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.ExItemLL.getLayoutParams();
                params.topMargin = (rvHeight - (holder.itemView.getHeight() * 3)) / 3;
                //params.bottomMargin = 0; //(rvHeight - (holder.ExItemLL.getHeight() * 3)) / 2;
                holder.ExItemLL.setLayoutParams(params);
            }
            //onItemClickListener.onItemClick(exerciseItem, holder.getAdapterPosition());

        }

    }

    @Override
    public int getItemCount() {
        return (null != exerciseItemArrayList ? exerciseItemArrayList.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        CardView cardView;
        ImageView icon;
        TextView name;
        LinearLayout ExItemLL;

        public ViewHolder(final View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.exerciseCV);
            icon = (ImageView) itemView.findViewById(R.id.exerciseIcon);
            ExItemLL = (LinearLayout) itemView.findViewById(R.id.ExerciseItemLL);
            if (type == 0) {
                name = (TextView) itemView.findViewById(R.id.exerciseName1);
            } else if (type == 1) {
                name = (TextView) itemView.findViewById(R.id.exerciseName2);
            }

        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    private class MyTouchListener implements OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                        view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.VISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }

    private class MyDragListener implements OnDragListener {
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
