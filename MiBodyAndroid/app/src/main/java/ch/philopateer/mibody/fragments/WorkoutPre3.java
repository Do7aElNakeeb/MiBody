package ch.philopateer.mibody.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.philopateer.mibody.R;

/**
 * Created by mamdouhelnakeeb on 8/23/17.
 */

public class WorkoutPre3 extends Fragment {

    RecyclerView filteredExRV, featuredExRV;


    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.workouts_predefined_sel_exercises, container, false);

        this.view = view;

//        initFilteredExRV();
//        initFeaturedExRV();

        return view;
    }

    private void initFilteredExRV(){

        filteredExRV = (RecyclerView) view.findViewById(R.id.filteredExRV);
    }

    private void initFeaturedExRV(){

    }
}
