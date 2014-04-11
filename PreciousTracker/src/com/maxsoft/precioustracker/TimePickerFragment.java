package com.maxsoft.precioustracker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class TimePickerFragment extends DialogFragment implements OnTimeSetListener {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Activity parent = getActivity();
		TextView txtTimeMoved = (TextView) parent.findViewById(R.id.txtTimeMoved);
		String string = txtTimeMoved.getText().toString();
		Dialog dialog = null;
		try {
			Date date = SimpleDateFormat.getTimeInstance().parse(string);
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);
			dialog = new TimePickerDialog(parent, this, hour, minute, DateFormat.is24HourFormat(parent));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return dialog;
	}

	@Override
	public void onTimeSet(TimePicker view, int hour, int minute) {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		c = new GregorianCalendar(year, month, day, hour, minute);
		java.text.DateFormat formatter = SimpleDateFormat.getTimeInstance();
		String formatted = formatter.format(c.getTime());

		Activity parent = getActivity();
		EditText txtTimeMoved = (EditText) parent.findViewById(R.id.txtTimeMoved);
		txtTimeMoved.setText(formatted);
	}

}
