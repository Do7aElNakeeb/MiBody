package com.mibody.app.helper;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mibody.app.R;
import com.mibody.app.app.ExerciseItem;

import java.util.ArrayList;

/**
 * Created by NakeebMac on 10/6/16.
 */

public class WorkoutExsAdapter extends BaseAdapter {

    private Context context;
    private final String[] data ;

    private final int[] ExDrawables = new int[] {
            R.drawable.ex1, R.drawable.ex2, R.drawable.ex3, R.drawable.ex4, R.drawable.ex5, R.drawable.ex6,
            R.drawable.ex7, R.drawable.ex8, R.drawable.ex9, R.drawable.ex10, R.drawable.ex11 };

    public WorkoutExsAdapter(Context context, String[] data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View row ;

        if (convertView == null) {
            row = new View(context);
            row = inflater.inflate(R.layout.exercises_item, null);

            ImageView imageView = (ImageView) row.findViewById(R.id.expandedListItemImg);
            TextView textView = (TextView) row.findViewById(R.id.expandedListItemTxt);
            textView.setText(data[position]);
            imageView.setImageResource(ExDrawables[position]);

        } else {
            row = convertView;
        }


        return row;
    }

}
