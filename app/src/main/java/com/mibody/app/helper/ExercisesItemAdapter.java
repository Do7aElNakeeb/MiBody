package com.mibody.app.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mibody.app.R;
import com.mibody.app.app.ExerciseItem;
import com.mibody.app.app.ExercisesGroup;

import java.util.ArrayList;

/**
 * Created by NakeebMac on 10/1/16.
 */

public class ExercisesItemAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<ExercisesGroup> groups;

    public ExercisesItemAdapter(Context context, ArrayList<ExercisesGroup> groups) {
        this.context = context;
        this.groups = groups;
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<ExerciseItem> chList = groups.get(groupPosition).getItems();

        return chList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<ExerciseItem> chList = groups.get(groupPosition).getItems();
        return chList.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ExercisesGroup group = (ExercisesGroup) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.exercises_group, null);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.group_name);
        tv.setText(group.getName());
        // TODO Auto-generated method stub
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ExerciseItem child = (ExerciseItem) getChild(groupPosition,
                childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.exercises_item, null);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.expandedListItemTxt);
        ImageView iv = (ImageView) convertView.findViewById(R.id.expandedListItemImg);

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),child.getImage());
        tv.setText(child.getDescription().getText());
   //     iv.setImageDrawable(child.getImage());

        // tv.setText(child.getName().toString()+"::"+child.getTag());
        // tv.setTag(child.getTag());
        // TODO Auto-generated method stub
        return convertView;

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
