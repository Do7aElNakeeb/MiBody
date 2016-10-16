package com.mibody.app.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.innodroid.expandablerecycler.ExpandableRecyclerAdapter;
import com.mibody.app.R;
import com.mibody.app.app.ExerciseItem;
import com.mibody.app.app.ExercisesGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NakeebMac on 10/1/16.
 */

public class ExercisesItemAdapter extends ExpandableRecyclerAdapter<ExercisesItemAdapter.ExerciseListItem> {

    public static final int HEADER = 0;
    public static final int CHILD = 1;
    String exercises_names[] = { "Exercise A", "Exercise B", "Exercise C", "Exercise D",
            "Exercise E", "Exercise F", "Exercise G", "Exercise H", "Exercise I", "Exercise J", "Exercise K" };

    String country_names[] = { "Description A", "Description B", "Description C", "Description D",
            "Description E", "Description F", "Description G", "Description H", "Description I",
            "Description J", "Description K", "Japan", "Costa Rica", "Uruguay",
            "Italy", "England", "France", "Switzerland", "Ecuador",
            "Honduras", "Agrentina", "Nigeria", "Bosnia and Herzegovina",
            "Iran", "Germany", "United States", "Portugal", "Ghana",
            "Belgium", "Algeria", "Russia", "Korea Republic" };

    int Images[] = { R.drawable.gym1, R.drawable.gym2,
            R.drawable.gym1, R.drawable.gym2, R.drawable.gym1,
            R.drawable.gym2, R.drawable.gym1, R.drawable.gym2,
            R.drawable.gym1, R.drawable.gym2, R.drawable.gym1 };

    public static final int TYPE_PERSON = 1001;

    public ExercisesItemAdapter(Context context) {
        super(context);

        setItems(getSampleItems());
    }

    public static class ExerciseListItem extends ExpandableRecyclerAdapter.ListItem {
        public String Text;
        public int Image;

        public ExerciseListItem(String group) {
            super(TYPE_HEADER);

            Text = group;
        }

        public ExerciseListItem(String text, int image) {
            super(TYPE_PERSON);

            Text = text;
            Image = image;
        }
    }

    public class HeaderViewHolder extends ExpandableRecyclerAdapter.HeaderViewHolder {
        TextView name;

        public HeaderViewHolder(View view) {
            super(view, (ImageView) view.findViewById(R.id.btn_expand_toggle));

            name = (TextView) view.findViewById(R.id.group_name);
        }

        public void bind(int position) {
            super.bind(position);

            name.setText(visibleItems.get(position).Text);
        }
    }

    public class PersonViewHolder extends ExpandableRecyclerAdapter.ViewHolder {
        TextView name;
        ImageView image;
        public PersonViewHolder(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.expandedListItemTxt);
            image = (ImageView) view.findViewById(R.id.expandedListItemImg);

        }

        public void bind(int position) {
            name.setText(visibleItems.get(position).Text);
            image.setImageResource(visibleItems.get(position).Image);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_HEADER:
                return new HeaderViewHolder(inflate(R.layout.exercises_group, parent));
            case TYPE_PERSON:
            default:
                return new PersonViewHolder(inflate(R.layout.exercises_item, parent));
        }
    }

    @Override
    public void onBindViewHolder(ExpandableRecyclerAdapter.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_HEADER:
                ((HeaderViewHolder) holder).bind(position);
                break;
            case TYPE_PERSON:
            default:
                ((PersonViewHolder) holder).bind(position);
                break;
        }
    }

    private List<ExerciseListItem> getSampleItems() {
        List<ExerciseListItem> items = new ArrayList<>();
        int j =0;
        for (String exercise_name : exercises_names){
            items.add(new ExerciseListItem(exercise_name));
            items.add((new ExerciseListItem(exercise_name, Images[j])));
            j++;
        }

        return items;
    }
}
   /*
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
*/