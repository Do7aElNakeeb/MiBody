package com.mibody.app.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> { // Recyclerview will extend to
    // recyclerview adapter
    private ArrayList<ExerciseItem> arrayList;
    private Context context;
    //Here is current selection position
    private int mSelectedPosition = 0;
    private OnMyListItemClick mOnMainMenuClickListener = OnMyListItemClick.NULL;

    private ItemClickListener clickListener;


    public RecyclerViewAdapter(Context context, ArrayList<ExerciseItem> arrayList) {
        this.context = context;
        this.arrayList = arrayList;

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);

    }
    
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final ExerciseItem model = arrayList.get(position);

    //    final RecyclerViewHolder mainHolder = (RecyclerViewHolder) holder;// holder

        Bitmap image = BitmapFactory.decodeResource(context.getResources(),model.getImage());// This will convert drawbale image into
        // bitmap

        // setting title
        holder.title.setText(model.getName());

        holder.imageView.setImageBitmap(image);
/*
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mSelectedPosition == position)
                    holder.itemView.setSelected(true);

                else
                    holder.itemView.setBackgroundColor(Color.RED);
            }
        });
*/
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        // This method will inflate the custom layout and return as viewholder
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());

        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(
                R.layout.exercises_item, viewGroup, false);
        final ViewHolder listHolder = new ViewHolder(mainGroup);
        /*
        mainGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //here you set your current position from holder of clicked view
                mSelectedPosition = listHolder.getAdapterPosition();

                //here you pass object from your list - item value which you clicked
                mOnMainMenuClickListener.onMyListItemClick(arrayList.get(mSelectedPosition));

                //here you inform view that something was change - view will be invalidated
                notifyDataSetChanged();
            }
        });
        */
        return listHolder;

    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        public ImageView imageView;


        public ViewHolder(View itemView) {
            super(itemView);

            this.title = (TextView) itemView.findViewById(R.id.expandedListItemTxt);
            this.imageView = (ImageView) itemView.findViewById(R.id.expandedListItemImg);

            itemView.setOnClickListener(this);
            itemView.setClickable(true);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
        }
    }

    public interface OnMyListItemClick {
        OnMyListItemClick NULL = new OnMyListItemClick() {
            @Override
            public void onMyListItemClick(ExerciseItem item) {
                item.getName();
            }
        };

        void onMyListItemClick(ExerciseItem item);
    }

}
