package com.rideread.rideread.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;
import com.rideread.rideread.activity.MineEditMessageActivity;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Jackbing on 2017/1/31.
 * 日期选择对话框
 */

public class DateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Log.e("date:","year="+year+",month="+month+",day="+dayOfMonth);
        MineEditMessageActivity activity=(MineEditMessageActivity)getActivity();
        activity.setBirthDate(year,month,dayOfMonth);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c=Calendar.getInstance(Locale.CHINESE);
        int year=c.get(Calendar.YEAR);
        int month=c.get(Calendar.MONTH);
        int day=c.get(Calendar.DAY_OF_MONTH);
        Log.e("date:","year="+year+",month="+month+",day="+day);
        return new DatePickerDialog(getActivity(),this,year,month,day);
    }




}
