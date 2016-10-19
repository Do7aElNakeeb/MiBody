package com.mibody.app.helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mibody.app.R;
import com.mibody.app.app.ExerciseItem;
import com.mibody.app.app.WorkoutItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NakeebMac on 10/6/16.
 */

public class WorkoutsAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private List<WorkoutItem> arrayList;
    private Context context;

    private ItemClickListener clickListener;


    public WorkoutsAdapter(Context context, List<WorkoutItem> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // This method will inflate the custom layout and return as viewholder
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());

        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(
                R.layout.exercises_item, parent, false);
        final RecyclerViewHolder listHolder = new RecyclerViewHolder(mainGroup);
        return listHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        final WorkoutItem model = arrayList.get(position);

        Bitmap image = BitmapFactory.decodeResource(context.getResources(), model.Image);// This will convert drawbale image into

        // setting title
        holder.title.setText(model.workoutName);

        holder.imageView.setImageBitmap(image);
    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }
}
