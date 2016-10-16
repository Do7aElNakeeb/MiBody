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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mibody.app.R;
import com.mibody.app.app.ExerciseItem;
import com.mibody.app.app.WorkoutExItem;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by NakeebMac on 10/13/16.
 */

public class AddWorkoutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ItemTouchHelperAdapter {

    private ArrayList<ExerciseItem> exerciseItems;
    private ArrayList<WorkoutExItem> workoutExItemArrayList;
    private Context context;
    private final OnStartDragListener mDragStartListener;

    // Constructor
    public AddWorkoutAdapter(Context context, ArrayList<ExerciseItem> exerciseItems, ArrayList<WorkoutExItem> workoutExItemArrayList, OnStartDragListener dragStartListener) {
        this.mDragStartListener = dragStartListener;
        this.context = context;
        this.exerciseItems = exerciseItems;
        this.workoutExItemArrayList = workoutExItemArrayList;
    }



    @Override
    public int getItemViewType(int position) {
       // return super.getItemViewType(position);
        return position % 2 * 2;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder = null;
        ViewGroup AddExGroup = (ViewGroup) mInflater.inflate(
                R.layout.exercises_item, parent, false);

        ViewGroup AddWorkoutGroup = (ViewGroup) mInflater.inflate(
                R.layout.exercises_set_grid_item, parent, false);

        switch (viewType){
            case 0: return viewHolder = new ExercisesViewHolder(AddExGroup);
            case 2: return viewHolder = new AddExerciseViewHolder(AddWorkoutGroup);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int pos = getItemViewType(position);
        switch (pos){
            case 0: {
                final ExerciseItem model = exerciseItems.get(position);

                final ExercisesViewHolder mainHolder = (ExercisesViewHolder) holder;// holder

                Bitmap image = BitmapFactory.decodeResource(context.getResources(), model.getImage());// This will convert drawbale image into
                // bitmap

                // setting title
                mainHolder.title.setText(model.getName());

                mainHolder.imageView.setImageBitmap(image);

                mainHolder.imageView.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                            mDragStartListener.onStartDrag(mainHolder);
                        }
                        return false;
                    }
                });
                break;
            }
            case 2:{
                final WorkoutExItem model = workoutExItemArrayList.get(position);

                final AddExerciseViewHolder mainHolder2 = (AddExerciseViewHolder) holder;// holder

                mainHolder2.RestMinus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (model.getRestT() > 0) {
                            model.setRestT(model.getRestT() - 1);
                            mainHolder2.RestTime.setText(String.valueOf(model.getRestT()));
                        }
                    }
                });

                mainHolder2.RestPlus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (model.getRestT() < 15) {
                            model.setRestT(model.getRestT() + 1);
                            mainHolder2.RestTime.setText(String.valueOf(model.getRestT()));
                        }
                    }
                });
            }
            break;

        }

    }


    @Override
    public int getItemCount() {
        return workoutExItemArrayList.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(exerciseItems, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        exerciseItems.remove(position);
        notifyItemRemoved(position);
    }

    class ExercisesViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder{

        public TextView title;
        public ImageView imageView;

        public ExercisesViewHolder(View itemView) {
            super(itemView);
            this.title = (TextView) itemView.findViewById(R.id.expandedListItemTxt);
            this.imageView = (ImageView) itemView.findViewById(R.id.expandedListItemImg);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }

    class AddExerciseViewHolder extends RecyclerView.ViewHolder{

        public ImageView ExerciseImg;
        public Button RGB;
        public Button RestPlus;
        public Button RestMinus;
        public Button AddEx;
        public EditText RestTime;
        public EditText Reps;
        public Button AddExercise;
        public Button RemoveExercise;

        public AddExerciseViewHolder(View itemView) {
            super(itemView);
            this.ExerciseImg = (ImageView) itemView.findViewById(R.id.exercise1_img);
            this.RGB = (Button) itemView.findViewById(R.id.rgb_btn);
            this.RestPlus = (Button) itemView.findViewById(R.id.rest1_plus);
            this.RestMinus = (Button) itemView.findViewById(R.id.rest1_minus);
//        this.AddEx = (Button) itemView.findViewById(R.id.add_exercise2);
            this.RestTime = (EditText) itemView.findViewById(R.id.rest1_count);
            this.Reps = (EditText) itemView.findViewById(R.id.reps1_count);
            this.AddExercise = (Button) itemView.findViewById(R.id.add_exercise);
            this.RemoveExercise = (Button) itemView.findViewById(R.id.remove_exercise);
        }
    }
}
