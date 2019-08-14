package com.bridgelabz.fundoo.utility;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.text.format.DateFormat;

import java.util.Calendar;

public class TimePickerFragement extends DialogFragment
{
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState)
    {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(calendar.HOUR_OF_DAY);
        int min = calendar.get(calendar.MINUTE);
        return new TimePickerDialog(getActivity() ,(TimePickerDialog.OnTimeSetListener)getActivity()
                ,hour,min, DateFormat.is24HourFormat(getActivity()));
    }
}
