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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class DatePickerFragment extends DialogFragment implements OnDateSetListener {

	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Activity parent = getActivity();
		TextView txtDateMoved = (TextView) parent.findViewById(R.id.txtDateMoved);
		String string = txtDateMoved.getText().toString();
		Dialog dialog = null;
		try {
			Date date = SimpleDateFormat.getDateInstance().parse(string);
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);
			dialog = new DatePickerDialog(parent, this, year, month, day);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dialog;
	}

	@Override
	public void onDateSet(DatePicker view, int year, int month, int day) {
		Activity parent = getActivity();
		EditText txtDateMoved = (EditText) parent.findViewById(R.id.txtDateMoved);
		Calendar c = new GregorianCalendar(year, month, day);
		DateFormat formatter = SimpleDateFormat.getDateInstance();
		String formatted = formatter.format(c.getTime());
		txtDateMoved.setText(formatted);
	}

}
