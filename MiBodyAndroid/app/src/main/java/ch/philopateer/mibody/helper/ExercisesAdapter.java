package ch.philopateer.mibody.helper;

import android.content.ClipData;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ch.philopateer.mibody.R;
import ch.philopateer.mibody.app.AppConfig;
import ch.philopateer.mibody.object.ExerciseItem;

import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;

import android.view.View.DragShadowBuilder;

/**
 * Created by mamdouhelnakeeb on 12/16/16.
 */

public class ExercisesAdapter extends RecyclerView.Adapter<ExercisesAdapter.ViewHolder> {

    private ArrayList<ExerciseItem> exerciseItemArrayList;
    private Context context;
    private int type;
    int rvHeight;


    public interface OnItemClickListener {
        void onItemClick(ExerciseItem exerciseItem, int position);
    }


    private final OnItemClickListener onItemClickListener;

    public ExercisesAdapter(Context context, int rvHeight, ArrayList<ExerciseItem> exerciseItemArrayList, int type, OnItemClickListener onItemClickListener){
        this.exerciseItemArrayList = exerciseItemArrayList;
        this.context = context;
        this.type = type;
        this.rvHeight = rvHeight;
        this.onItemClickListener = onItemClickListener;
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

        int pos = position;
        if (getItemCount() < 9){
            pos = 9;
        }

        new Picasso.Builder(context)
                .downloader(new OkHttpDownloader(context, Integer.MAX_VALUE))
                .build()
                .load(AppConfig.URL_SERVER + "ExIcon/" + exerciseItem.getIcon())
                .placeholder(AppConfig.exercises_icons[Arrays.asList(AppConfig.exercises_names).indexOf(exerciseItem.getName())])
                .into(holder.icon);
        holder.name.setVisibility(View.VISIBLE);

        if(type == 0){

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.ExItemLL.getLayoutParams();
            params.rightMargin = (int) context.getResources().getDimension(ch.philopateer.mibody.R.dimen.exercise_item_margin);
            holder.ExItemLL.setLayoutParams(params);

            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.MiBodyOrange));
            holder.icon.setColorFilter(ContextCompat.getColor(context, R.color.MiBodyWhite));
            holder.name.setText(exerciseItem.getName());
            holder.cardView.setTag(R.id.exDot1, holder.getAdapterPosition());
            holder.cardView.setTag(R.id.exDot2, exerciseItem);
            holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ClipData data = ClipData.newPlainText("", "");
                    DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                    v.startDrag(data, shadowBuilder, v, 0);
                    v.setVisibility(View.VISIBLE);

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
                    onItemClickListener.onItemClick(exerciseItem, holder.getAdapterPosition());
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

    public class ViewHolder extends RecyclerView.ViewHolder{

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

    }
}
