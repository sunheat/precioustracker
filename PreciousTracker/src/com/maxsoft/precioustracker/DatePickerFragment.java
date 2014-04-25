package com.maxsoft.precioustracker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A date picker dialog to use with AddMoveActivity.
 * 
 * @author Max
 * 
 */
public class DatePickerFragment extends DialogFragment implements OnDateSetListener {

	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Activity parent = getActivity();
		TextView txtDateMoved = (TextView) parent.findViewById(R.id.txtDateMoved);
		String string = txtDateMoved.getText().toString();
		Dialog dialog = null;
		try {
			// parse the currently set date
			Date date = SimpleDateFormat.getDateInstance().parse(string);
			// from the parsed date, get the year, month, day to show in the
			// picker
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);
			dialog = new DatePickerDialog(parent, this, year, month, day);
		} catch (ParseException e) {
			Log.e("DatePickerDialog", e.getMessage());
			e.printStackTrace();
		}
		return dialog;
	}

	@Override
	public void onDateSet(DatePicker view, int year, int month, int day) {
		Activity parent = getActivity();
		EditText txtDateMoved = (EditText) parent.findViewById(R.id.txtDateMoved);
		// format the selected date and set it on the date field of the parent
		// activity
		Calendar c = new GregorianCalendar(year, month, day);
		DateFormat formatter = SimpleDateFormat.getDateInstance();
		String formatted = formatter.format(c.getTime());
		txtDateMoved.setText(formatted);
	}

}
