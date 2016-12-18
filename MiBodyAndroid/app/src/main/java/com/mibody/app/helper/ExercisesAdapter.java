package com.mibody.app.helper;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mibody.app.R;
import com.mibody.app.app.AppConfig;
import com.mibody.app.app.ExerciseItem;
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

    ViewHolder tempVH;

    public ExercisesAdapter(Context context, ArrayList<ExerciseItem> exerciseItemArrayList, int type){
        this.exerciseItemArrayList = exerciseItemArrayList;
        this.context = context;
        this.type = type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());

        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(R.layout.add_workout_exercises_item, parent, false);

        tempVH = new ViewHolder(mainGroup);
        return new ViewHolder(mainGroup);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        ExerciseItem exerciseItem = exerciseItemArrayList.get(position);

        //Picasso.with(context).load(AppConfig.URL_SERVER + "/ExIcon/" + exerciseItem.getIcon()).into(holder.icon);
        new Picasso.Builder(context).downloader(new OkHttpDownloader(context, Integer.MAX_VALUE)).build().load(AppConfig.URL_SERVER + "/ExIcon/" + exerciseItem.getIcon()).into(holder.icon);

        if(type == 0){

            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.MiBodyOrange));
            holder.icon.setColorFilter(ContextCompat.getColor(context, R.color.MiBodyWhite));
            holder.name.setText(exerciseItem.getName());
            holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    holder.cardView.setOnTouchListener(new MyTouchListener());
                    tempVH = holder;
                    return true;
                }
            });

        }
        else if (type == 1){
            holder.name1.setText(String.valueOf(position + 1));
            if (position == 0){
                holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.MiBodyOrange));
                holder.icon.setColorFilter(ContextCompat.getColor(context, R.color.MiBodyWhite));
                //holder.exDot.setVisibility(View.VISIBLE);
            }
            if (position == exerciseItemArrayList.size()-1){
                //holder.exDot.setVisibility(View.GONE);
            }
            else {
                //holder.exDot.setVisibility(View.VISIBLE);
                holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.black));
                holder.icon.setColorFilter(ContextCompat.getColor(context, R.color.MiBodyWhite));
                holder.cardView.setAlpha((float) 0.5);
                holder.icon.setAlpha((float) 0.5);
            }
            holder.cardView.setSelected(true);
            //holder.cardView.setRadius(115);
            holder.icon.setOnDragListener(new OnDragListener() {
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
                            holder.icon = tempVH.icon;
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

/*
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // run scale animation and make it bigger
                    Animation anim = AnimationUtils.loadAnimation(context, R.anim.scale_in_ex);
                    Log.d("animation", "hhasFocus");
                    holder.itemView.setScaleX((float) 1.5);
                    holder.itemView.setScaleY((float) 1.5);
                    holder.itemView.setPaddingRelative(2, 3, 2, 3);
                    //itemView.startAnimation(anim);
                    //anim.setFillAfter(true);
                }
            });


            holder.cardView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        // run scale animation and make it bigger
                        Animation anim = AnimationUtils.loadAnimation(context, R.anim.scale_in_ex);
                        Log.d("animation", "hasFocus");
                        holder.cardView.startAnimation(anim);
                        anim.setFillAfter(true);
                    } else {
                        // run scale animation and make it smaller
                        Animation anim = AnimationUtils.loadAnimation(context, R.anim.scale_out_ex);
                        Log.d("animation", "dontHasFocus");
                        holder.cardView.startAnimation(anim);
                        anim.setFillAfter(true);
                    }
                }
            });
            */

        }
        else if (type == 2){
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.MiBodyWhite));
            holder.icon.setColorFilter(ContextCompat.getColor(context, R.color.MiBodyOrange));
            holder.name.setText(exerciseItem.getName());
        }

    }


    @Override
    public int getItemCount() {
        return (null != exerciseItemArrayList ? exerciseItemArrayList.size() : 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView, exDot;
        ImageView icon;
        TextView name, name1;

        public ViewHolder(final View itemView) {
            super(itemView);

            cardView = (CardView) itemView.findViewById(R.id.ExerciseCV);
            icon = (ImageView) itemView.findViewById(R.id.ExerciseImage);
            if(type == 0){
                name = (TextView) itemView.findViewById(R.id.ExerciseTxt1);
                name.setVisibility(View.VISIBLE);
            }
            else if (type == 1){

                name = (TextView) itemView.findViewById(R.id.ExerciseTxt2);
                name.setVisibility(View.VISIBLE);
                name1 = (TextView) itemView.findViewById(R.id.ExerciseTxt1);
                name1.setVisibility(View.VISIBLE);
                exDot = (CardView) itemView.findViewById(R.id.exDot);
                if (getAdapterPosition() != exerciseItemArrayList.size() - 1){
                    exDot.setVisibility(View.VISIBLE);
                }
            }
            else if (type == 2){
                name = (TextView) itemView.findViewById(R.id.ExerciseTxt2);
                name.setVisibility(View.VISIBLE);
            }


            itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        // run scale animation and make it bigger
                        itemView.setScaleX((float) 1.5);
                        itemView.setScaleY((float) 1.5);
                        itemView.setPaddingRelative(2, 3, 2, 3);
                    } else {
                        // run scale animation and make it smaller
                        itemView.setScaleX((float) 1);
                        itemView.setScaleY((float) 1);
                        //itemView.setPaddingRelative(2, 3, 2, 3);
                    }
                }
            });

        }
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
