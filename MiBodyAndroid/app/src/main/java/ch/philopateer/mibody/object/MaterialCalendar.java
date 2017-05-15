package ch.philopateer.mibody.object;

import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import ch.philopateer.mibody.adapter.MaterialCalendarAdapter;
import ch.philopateer.mibody.fragments.ScheduleFragment;

/**
 * Created by mamdouhelnakeeb on 3/24/17.
 */

public class MaterialCalendar {

    // Variables
    public int mMonth = -1;
    public int mYear = -1;
    public int mCurrentDay = -1;
    public int mCurrentMonth = -1;
    public int mCurrentYear = -1;
    public int mFirstDay = -1;
    public int mNumDaysInMonth = -1;

    public ScheduleFragment scheduleFragment;


    public MaterialCalendar(ScheduleFragment scheduleFragment){
        this.scheduleFragment = scheduleFragment;
    }

    public void getInitialCalendarInfo() {
        Calendar cal = Calendar.getInstance();

        //scheduleFragment = new ScheduleFragment();

        if (cal != null) {
            Log.d("MONTH_NUMBER", String.valueOf(cal.getActualMaximum(Calendar.DAY_OF_MONTH)));
            mNumDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

            mMonth = cal.get(Calendar.MONTH);
            mYear = cal.get(Calendar.YEAR);

            mCurrentDay = cal.get(Calendar.DAY_OF_MONTH);
            mCurrentMonth = mMonth;
            mCurrentYear = mYear;

            getFirstDay(mMonth, mYear);

            Log.d("CURRENT_DAY", String.valueOf(mCurrentDay));
            Log.d("CURRENT_MONTH_INFO", String.valueOf(getMonthName(mMonth) + " " + mYear + " has " + mNumDaysInMonth
                    + " days " +
                    "and starts on " + mFirstDay));
        }
    }

    private void refreshCalendar(TextView monthTextView, GridView calendarGridView,
                                        MaterialCalendarAdapter materialCalendarAdapter, int month, int year) {

        checkCurrentDay(month, year);
        getNumDayInMonth(month, year);
        getFirstDay(month, year);

        if (monthTextView != null) {
            Log.d("REFRESH_MONTH", String.valueOf(month));
            monthTextView.setText(getMonthName(month) + " " + year);
        }

        // Clear Saved Events ListView count when changing calendars
        if (scheduleFragment.playedWorkoutsAdapter != null) {
            scheduleFragment.mNumEventsOnDay = -1;
            scheduleFragment.playedWorkoutsAdapter.notifyDataSetChanged();
            Log.d("EVENTS_ADAPTER", "refresh");
        }

        long monthInMilliseconds = 0;
        SimpleDateFormat formatter = new SimpleDateFormat("MM/yyyy", Locale.US);
        formatter.setTimeZone(TimeZone.getTimeZone("UTC+2"));


        String date = String.valueOf(month + 1) + "/" + String.valueOf(year);
        Log.d("dateIsC:", date);

        try {
            Date mDate = formatter.parse(date);
            monthInMilliseconds = mDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.d("monthInMills", String.valueOf(monthInMilliseconds));

        scheduleFragment.getSavedEventsForCurrentMonth(monthInMilliseconds);

        if (materialCalendarAdapter != null) {
            if (calendarGridView != null) {
                calendarGridView.setItemChecked(calendarGridView.getCheckedItemPosition(), false);
            }

            materialCalendarAdapter.notifyDataSetChanged();
        }
    }

    private String getMonthName(int month) {
        return new DateFormatSymbols().getMonths()[month];
    }

    private void checkCurrentDay(int month, int year) {
        if (month == mCurrentMonth && year == mCurrentYear) {
            Calendar cal = java.util.Calendar.getInstance();
            mCurrentDay = cal.get(Calendar.DAY_OF_MONTH);
        } else {
            mCurrentDay = -1;
        }
    }

    private void getNumDayInMonth(int month, int year) {
        Calendar cal = Calendar.getInstance();
        if (cal != null) {
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.YEAR, year);
            mNumDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

            Log.d("MONTH_NUMBER", String.valueOf(cal.getActualMaximum(Calendar.DAY_OF_MONTH)));
        }
    }

    private void getFirstDay(int month, int year) {
        Calendar cal = Calendar.getInstance();
        if (cal != null) {
            cal.set(Calendar.MONTH, month);
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.DAY_OF_MONTH, 1);

            switch (cal.get(Calendar.DAY_OF_WEEK)) {
                case Calendar.SUNDAY:
                    Log.d("FIRST_DAY", "Sunday");
                    mFirstDay = 0;
                    break;

                case Calendar.MONDAY:
                    Log.d("FIRST_DAY", "Monday");
                    mFirstDay = 1;
                    break;

                case Calendar.TUESDAY:
                    Log.d("FIRST_DAY", "Tuesday");
                    mFirstDay = 2;
                    break;

                case Calendar.WEDNESDAY:
                    Log.d("FIRST_DAY", "Wednesday");
                    mFirstDay = 3;
                    break;

                case Calendar.THURSDAY:
                    Log.d("FIRST_DAY", "Thursday");
                    mFirstDay = 4;
                    break;

                case Calendar.FRIDAY:
                    Log.d("FIRST_DAY", "Friday");
                    mFirstDay = 5;
                    break;

                case Calendar.SATURDAY:
                    Log.d("FIRST_DAY", "Saturday");
                    mFirstDay = 6;
                    break;

                default:
                    break;
            }
        }
    }

    // Call in View.OnClickListener for Previous ImageView
    public void previousOnClick(ImageView previousImageView, TextView monthTextView, GridView calendarGridView, MaterialCalendarAdapter materialCalendarAdapter) {
        if (previousImageView != null && mMonth != -1 && mYear != -1) {
            previousMonth(monthTextView, calendarGridView, materialCalendarAdapter);
        }
    }

    // Call in View.OnClickListener for Next ImageView
    public void nextOnClick(ImageView nextImageView, TextView monthTextView, GridView calendarGridView, MaterialCalendarAdapter materialCalendarAdapter) {
        if (nextImageView != null && mMonth != -1 && mYear != -1) {
            nextMonth(monthTextView, calendarGridView, materialCalendarAdapter);
        }
    }

    private void previousMonth(TextView monthTextView, GridView calendarGridView, MaterialCalendarAdapter materialCalendarAdapter) {
        if (mMonth == 0) {
            mMonth = 11;
            mYear = mYear - 1;
        } else {
            mMonth = mMonth - 1;
        }

        refreshCalendar(monthTextView, calendarGridView, materialCalendarAdapter, mMonth, mYear);
    }

    private void nextMonth(TextView monthTextView, GridView calendarGridView, MaterialCalendarAdapter materialCalendarAdapter) {

        if (mMonth == 11) {
            if (mYear < Calendar.getInstance().get(Calendar.YEAR)) {
                mMonth = 0;
                mYear = mYear + 1;
                refreshCalendar(monthTextView, calendarGridView, materialCalendarAdapter, mMonth, mYear);
            }
        } else {
            if ( (mYear == Calendar.getInstance().get(Calendar.YEAR) && mMonth < Calendar.getInstance().get(Calendar.MONTH)) || (mYear < Calendar.getInstance().get(Calendar.YEAR)) ) {

                mMonth = mMonth + 1;
                refreshCalendar(monthTextView, calendarGridView, materialCalendarAdapter, mMonth, mYear);
            }
        }
    }

    // Call in GridView.OnItemClickListener for custom Calendar GirdView
    public void selectCalendarDay(MaterialCalendarAdapter materialCalendarAdapter, int position) {
        Log.d("SELECTED_POSITION", String.valueOf(position));
        int weekPositions = 6;
        int noneSelectablePositions = weekPositions + mFirstDay;

        if (position > noneSelectablePositions) {
            getSelectedDate(position, mMonth, mYear);

            if (materialCalendarAdapter != null) {
                materialCalendarAdapter.notifyDataSetChanged();
            }
        }
    }

    private void getSelectedDate(int selectedPosition, int month, int year) {
        int weekPositions = 6;
        int dateNumber = selectedPosition - weekPositions - mFirstDay;
        Log.d("DATE_NUMBER", String.valueOf(dateNumber));
        Log.d("SELECTED_DATE", String.valueOf(month + "/" + dateNumber + "/" + year));
    }

}
