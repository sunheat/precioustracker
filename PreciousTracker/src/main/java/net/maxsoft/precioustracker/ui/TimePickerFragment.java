package net.maxsoft.precioustracker.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import net.maxsoft.precioustracker.R;
import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * A time picker dialog to use with AddMoveActivity.
 * 
 * @author Max
 * 
 */
public class TimePickerFragment extends DialogFragment implements OnTimeSetListener {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Activity parent = getActivity();
		TextView txtTimeMoved = (TextView) parent.findViewById(R.id.txtTimeMoved);
		String string = txtTimeMoved.getText().toString();
		Dialog dialog = null;
		try {
			// parse the currently set time
			Date date = SimpleDateFormat.getTimeInstance().parse(string);
			// prepare the hour and minute to show in the time picker dialog
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);
			dialog = new TimePickerDialog(parent, this, hour, minute, DateFormat.is24HourFormat(parent));
		} catch (ParseException e) {
			Log.e("DatePickerDialog", e.getMessage());
			e.printStackTrace();
		}

		return dialog;
	}

	@Override
	public void onTimeSet(TimePicker view, int hour, int minute) {
		// get the current date Calendar object to hold the selected hour and
		// minute
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		// sets the hour and minute on the calendar object
		c = new GregorianCalendar(year, month, day, hour, minute);
		// formats the time and set it on the time field in the parent activity
		java.text.DateFormat formatter = SimpleDateFormat.getTimeInstance();
		String formatted = formatter.format(c.getTime());

		Activity parent = getActivity();
		EditText txtTimeMoved = (EditText) parent.findViewById(R.id.txtTimeMoved);
		txtTimeMoved.setText(formatted);
	}

}
