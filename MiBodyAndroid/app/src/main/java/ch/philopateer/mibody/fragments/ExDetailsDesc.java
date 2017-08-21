package ch.philopateer.mibody.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ch.philopateer.mibody.R;

/**
 * Created by mamdouhelnakeeb on 8/1/17.
 */

public class ExDetailsDesc extends Fragment {

    public String exDesc;

    TextView exDescTV;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.exercise_details_desc_fragment, null);

        exDescTV = (TextView) view.findViewById(R.id.exDescription);

        exDescTV.setText(exDesc);

        return view;
    }
}
