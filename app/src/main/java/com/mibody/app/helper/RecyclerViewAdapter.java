package com.mibody.app.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.mibody.app.app.ExerciseItem;

import java.util.ArrayList;
import java.util.Collections;

import com.mibody.app.R;

import com.mibody.app.helper.ItemTouchHelperAdapter;
import com.mibody.app.helper.ItemTouchHelperViewHolder;
import com.mibody.app.helper.OnStartDragListener;

/**
 * Created by NakeebMac on 10/11/16.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {// Recyclerview will extend to
    // recyclerview adapter
    private ArrayList<ExerciseItem> arrayList;
    private Context context;


    public RecyclerViewAdapter(Context context, ArrayList<ExerciseItem> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);

    }
    
    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        final ExerciseItem model = arrayList.get(position);

        final RecyclerViewHolder mainHolder = (RecyclerViewHolder) holder;// holder

        Bitmap image = BitmapFactory.decodeResource(context.getResources(),model.getImage());// This will convert drawbale image into
        // bitmap

        // setting title
        mainHolder.title.setText(model.getName());

        mainHolder.imageView.setImageBitmap(image);

        mainHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        // This method will inflate the custom layout and return as viewholder
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());

        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(
                R.layout.exercises_item, viewGroup, false);
        RecyclerViewHolder listHolder = new RecyclerViewHolder(mainGroup);
        return listHolder;

    }

}
