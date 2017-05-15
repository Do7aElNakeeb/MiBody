package ch.philopateer.mibody.helper;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ch.philopateer.mibody.R;
import ch.philopateer.mibody.app.AppConfig;
import ch.philopateer.mibody.object.WorkoutExItem;

import java.util.ArrayList;

/**
 * Created by mamdouhelnakeeb on 2/7/17.
 */

public class StatisticsExAdapter extends RecyclerView.Adapter<StatisticsExAdapter.ViewHolder> {

    Context context;
    private int rvHeight, maxReps;
    private ArrayList<WorkoutExItem> workoutExItemArrayListObj;
   // private ArrayList<Integer> workoutExItemArrayListAch;

    public StatisticsExAdapter(Context context, int rvHeight, int maxReps, ArrayList<WorkoutExItem> workoutExItemArrayListObj){
        this.context = context;
        this.rvHeight = rvHeight;
        this.maxReps = maxReps;
        this.workoutExItemArrayListObj = workoutExItemArrayListObj;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.statistics_ex_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RelativeLayout.LayoutParams objParams = (RelativeLayout.LayoutParams) holder.objectiveFL.getLayoutParams();
        RelativeLayout.LayoutParams objParams2 = (RelativeLayout.LayoutParams) holder.FLred.getLayoutParams();
        RelativeLayout.LayoutParams achParams = (RelativeLayout.LayoutParams) holder.achievedFL.getLayoutParams();
        RelativeLayout.LayoutParams achParams2 = (RelativeLayout.LayoutParams) holder.FLgrey.getLayoutParams();


        int workoutExItemObj = workoutExItemArrayListObj.get(position).reps;
        int workoutExItemAch = workoutExItemArrayListObj.get(position).exReps;

        if (workoutExItemObj != 0){
            Log.d("ObjAch", String.valueOf(rvHeight) + " - " + String.valueOf(workoutExItemObj) + " - " + String.valueOf(workoutExItemAch) + " - " + String.valueOf(maxReps));
            if (maxReps > 0) {

                achParams.height = (int) ((float) rvHeight * ((float) workoutExItemAch / (float) maxReps) * (float)9 / (float)10);
                achParams2.height = achParams.height / 2;
                Log.d("objStats", String.valueOf(((float) workoutExItemAch / (float) maxReps) * (float)9 / (float)10));

                objParams.height = (int) ((float) rvHeight * ((float) workoutExItemObj / (float) maxReps) * (float)9 / (float)10);
                objParams2.height = objParams.height / 2;
                Log.d("objStats2", String.valueOf(((float) workoutExItemObj / (float) maxReps) * (float)9 / (float)10));
            }
            else {
                achParams.height = 0;
                achParams2.height = 0;
                objParams.height = 0;
                objParams2.height = 0;
            }
            holder.objectiveFL.setLayoutParams(objParams);
            holder.FLred.setLayoutParams(objParams2);
            holder.achievedFL.setLayoutParams(achParams);
            holder.FLgrey.setLayoutParams(achParams2);
            holder.statisticsExName.setText(workoutExItemArrayListObj.get(position).name);
            holder.itemView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return workoutExItemArrayListObj.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        FrameLayout objectiveFL, FLred, FLgrey, achievedFL;
        TextView statisticsExName, objText, achText;

        public ViewHolder(View itemView) {
            super(itemView);
            objectiveFL = (FrameLayout) itemView.findViewById(R.id.objectiveFL);
            FLred = (FrameLayout) itemView.findViewById(R.id.FLred2);
            FLgrey = (FrameLayout) itemView.findViewById(R.id.FLgrey4);
            achievedFL = (FrameLayout) itemView.findViewById(R.id.achievedFL);
            statisticsExName = (TextView) itemView.findViewById(R.id.statisticsExName);
            objText = (TextView) itemView.findViewById(R.id.objText);
            achText = (TextView) itemView.findViewById(R.id.achText);
        }
    }
}
