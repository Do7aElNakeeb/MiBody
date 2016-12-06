package com.mibody.app.helper;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.mibody.app.R;
import com.mibody.app.app.ExerciseItem;
import com.mibody.app.app.WorkoutExItem;

import java.util.ArrayList;


/**
 * Created by NakeebMac on 10/5/16.
 */

public class WorkoutExItemAdapter extends RecyclerView.Adapter<AddWorkoutRecyclerViewHolder> {

/*   Context context;
    private int ExercisesNo = 1;
    private static LayoutInflater inflater = null;
*/
    private ArrayList<WorkoutExItem> arrayList;
    private Context context;
    private int ExercisesNo;

    String exercises_names[] = { "Exercise A", "Exercise B", "Exercise C", "Exercise D", "Exercise E",
            "Exercise F", "Exercise G", "Exercise H", "Exercise I", "Exercise J", "Exercise K" };


    int Images[] = { R.drawable.ex1, R.drawable.ex2,
            R.drawable.ex3, R.drawable.ex4, R.drawable.ex5,
            R.drawable.ex6, R.drawable.ex7, R.drawable.ex8,
            R.drawable.ex9, R.drawable.ex10, R.drawable.ex11 };

    public WorkoutExItemAdapter(Context context, ArrayList<WorkoutExItem> arrayList) {
        // TODO Auto-generated constructor stub

        /*
        //      result=prgmNameList;
        context = addWorkout;
//        imageId=prgmImages;
        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        */

        this.context = context;
        this.arrayList = arrayList;
        this.ExercisesNo = arrayList.size();

    }
    @Override
    public AddWorkoutRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // This method will inflate the custom layout and return as viewholder
        LayoutInflater mInflater = LayoutInflater.from(parent.getContext());

        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(
                R.layout.exercises_set_grid_item, parent, false);
        AddWorkoutRecyclerViewHolder listHolder = new AddWorkoutRecyclerViewHolder(mainGroup);
        return listHolder;

    }

    @Override
    public void onBindViewHolder(AddWorkoutRecyclerViewHolder holder, final int position) {
        final WorkoutExItem model = arrayList.get(position);

        final AddWorkoutRecyclerViewHolder mainHolder = (AddWorkoutRecyclerViewHolder) holder;// holder

//        SharedPreferences prefs = context.getSharedPreferences("WorkoutFragment", Context.MODE_PRIVATE);
//      ExercisesNo = Integer.valueOf(prefs.getString("WorkoutExNo", ""));


        mainHolder.ExerciseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    showExerciseSelector(mainHolder.ExerciseImg);
             //   model.name = String.valueOf(mainHolder.ExerciseImg.getId());

                /////////////////
                final Dialog dialog = new Dialog(context);
                dialog.setTitle("Select Exercise");
                dialog.setContentView(R.layout.exercises_recycle_horizontal);

                final RecyclerView ExercisesRV = (RecyclerView) dialog.findViewById(R.id.exercises_grid);
                ExercisesRV.setHasFixedSize(true);
                ExercisesRV.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

                ExercisesRV.setItemAnimator(new DefaultItemAnimator());
                ArrayList<ExerciseItem> arrayList = new ArrayList<>();
                for (int i = 0; i < exercises_names.length; i++) {
                    arrayList.add(new ExerciseItem(Images[i], exercises_names[i]));
                }

                RecyclerViewAdapter adapter = new RecyclerViewAdapter(context, arrayList);
                ExercisesRV.setAdapter(adapter);// set adapter on recyclerview
                //  adapter.notifyDataSetChanged();// Notify the adapter


                adapter.setClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        mainHolder.ExerciseImg.setImageResource(Images[position]);
                        mainHolder.ExerciseImg.setTag(Images[position]);
                        model.name = String.valueOf(position);
                        dialog.dismiss();
                    }
                });

                dialog.show();


            //    arrayList.get(position).exercise = "Ex1";
            }
        });


        mainHolder.RGB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRGBDialog(mainHolder.RGB);
                mainHolder.RGB.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        model.setRgb(mainHolder.RGB.getText().toString());
                    }
                });

            }
        });

        mainHolder.RestMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.getRestT() > 0) {
                    model.setRestT(model.getRestT() - 1);
                    mainHolder.RestTime.setText(String.valueOf(model.getRestT()));
                }
            }
        });

        mainHolder.RestPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.getRestT() < 15) {
                    model.setRestT(model.getRestT() + 1);
                    mainHolder.RestTime.setText(String.valueOf(model.getRestT()));
                }
            }
        });

        mainHolder.Reps.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                model.RepsT = Integer.valueOf(s.toString());
            }
        });

        mainHolder.RemoveExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,arrayList.size());
            }
        });

        mainHolder.setRepeatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.setReps++;
                mainHolder.setRepeat.setText(String.valueOf(model.setReps));
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != arrayList ? arrayList.size() : 0);
    }


    private void showRGBDialog(final Button RGB){
        final Dialog dialog = new Dialog(context);
        //dialog.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        dialog.setTitle("Select the load");
        dialog.setContentView(R.layout.rgb);

        final String[] rgb = new String[3];
        final RadioGroup rgb1 = (RadioGroup) dialog.findViewById(R.id.rgb1);
        final RadioGroup rgb2 = (RadioGroup) dialog.findViewById(R.id.rgb2);
        final RadioGroup rgb3 = (RadioGroup) dialog.findViewById(R.id.rgb3);

        rgb1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rgb_btn = (RadioButton) group.findViewById(checkedId);
                rgb[0] = rgb_btn.getText().toString();
            }
        });

        rgb2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rgb_btn = (RadioButton) group.findViewById(checkedId);
                rgb[1] = rgb_btn.getText().toString();
            }
        });

        rgb3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rgb_btn = (RadioButton) group.findViewById(checkedId);
                rgb[2] = rgb_btn.getText().toString();
            }
        });


        Button rgbSet = (Button) dialog.findViewById(R.id.rgb_btn);

        rgbSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RGB.setText(rgb[0] + rgb[1] + rgb[2]);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showExerciseSelector(final ImageView imageView){

        final Dialog dialog = new Dialog(context);
        dialog.setTitle("Select Exercise");
        dialog.setContentView(R.layout.exercises_recycle_horizontal);

        final RecyclerView ExercisesRV = (RecyclerView) dialog.findViewById(R.id.exercises_grid);
        ExercisesRV.setHasFixedSize(true);
        ExercisesRV.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));

        ExercisesRV.setItemAnimator(new DefaultItemAnimator());
        ArrayList<ExerciseItem> arrayList = new ArrayList<>();
        for (int i = 0; i < exercises_names.length; i++) {
            arrayList.add(new ExerciseItem(Images[i], exercises_names[i]));
        }

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(context, arrayList);
        ExercisesRV.setAdapter(adapter);// set adapter on recyclerview
      //  adapter.notifyDataSetChanged();// Notify the adapter


       adapter.setClickListener(new ItemClickListener() {
           @Override
           public void onClick(View view, int position) {
               imageView.setImageResource(Images[position]);
               imageView.setTag(Images[position]);
               Log.d("TagOfImageView", String.valueOf(Images[position]));
               dialog.dismiss();
           }
       });


    /*    exerciseSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
*/
        dialog.show();

    }

    public void addItem(){
        ExercisesNo++;
    }

    /*


    @Override
    public int getCount() {
        return ExercisesNo;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final WorkoutExItem workoutExItem = new WorkoutExItem();
        View rowView;

        rowView = inflater.inflate(R.layout.exercises_set_grid_item, null);
        workoutExItem.setExerciseImg((ImageView) rowView.findViewById(R.id.exercise1_img));
        workoutExItem.setAddEx((Button) rowView.findViewById(R.id.add_exercise2));
        workoutExItem.setRestMinus((Button) rowView.findViewById(R.id.rest1_minus));
        workoutExItem.setRestPlus((Button) rowView.findViewById(R.id.rest1_plus));
        workoutExItem.setRGB((Button) rowView.findViewById(R.id.rgb_btn));
        workoutExItem.setReps((EditText) rowView.findViewById(R.id.reps1_count));
        workoutExItem.setRestTime((EditText) rowView.findViewById(R.id.rest1_count));
//        workoutExItem.setRestT(0);

        workoutExItem.getAddEx().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workoutExItem.getAddEx().setVisibility(View.GONE);
                ExercisesNo++;

            }
        });

        workoutExItem.getRestPlus().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workoutExItem.getRestTime().setText(workoutExItem.getRestT() + 1);
            }
        });

        workoutExItem.getRestMinus().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (workoutExItem.getRestT() != 0){
                    workoutExItem.getRestTime().setText(workoutExItem.getRestT() - 1);
                }
            }
        });

        
        return rowView;
    }
    */
}
