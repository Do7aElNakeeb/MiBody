package com.mibody.app.helper;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mibody.app.R;
import com.mibody.app.app.AppConfig;
import com.mibody.app.app.ExerciseItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by mamdouhelnakeeb on 12/16/16.
 */

public class ExercisesAdapter extends RecyclerView.Adapter<ExercisesAdapter.ViewHolder> {

    private ArrayList<ExerciseItem> exerciseItemArrayList;
    private Context context;
    private int type;

    public ExercisesAdapter(Context context, ArrayList<ExerciseItem> exerciseItemArrayList, int type){
        this.exerciseItemArrayList = exerciseItemArrayList;
        this.context = context;
        this.type = type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());

        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(R.layout.add_workout_exercises_item, parent, false);

        return new ViewHolder(mainGroup);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        ExerciseItem exerciseItem = exerciseItemArrayList.get(position);

        Picasso.with(context).load(AppConfig.URL_SERVER + "/ExIcon/" + exerciseItem.getIcon()).into(holder.icon);

        if(type == 0){

            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.MiBodyOrange));
            holder.icon.setColorFilter(ContextCompat.getColor(context, R.color.MiBodyWhite));
            holder.name.setText(exerciseItem.getName());
        }
        else if (type == 1){
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.MiBodyOrange));
            holder.icon.setColorFilter(ContextCompat.getColor(context, R.color.MiBodyWhite));
            holder.cardView.setRadius(130);
        }
        else {
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

        CardView cardView;
        ImageView icon;
        TextView name;

        public ViewHolder(View itemView) {
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
            }
            else {
                name.setVisibility(View.GONE);
            }


        }
    }
}
