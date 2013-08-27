package com.llino.timelineandroid;

import java.util.ArrayList;
import java.util.Collections;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Months;
import org.joda.time.Years;
import org.joda.time.Duration;
import org.joda.time.Period;

import com.llino.timelineandroid.Navbar2.Timeitem;
import com.llino.timelineandroid.entities.TimeCard;

import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;

public class TBuilder {

	private static final int NUM_ROWS = 4;
	private static final int TIMECARD_WIDTH = 300;

	public static final int TIMESPAN_HOURS = 5;
	public static final int TIMESPAN_DAYS = 6;
	public static final int TIMESPAN_MONTHS = 7;
	public static final int TIMESPAN_YEARS = 8;
	public static final int TIMESPAN_CENTURIES = 10;

	private int timespan;

	private LinearLayout table;
	private LinearLayout tableNavbar;
	private Context context;
	private ArrayList<TimeCard> timecards = null;
	private ArrayList<LinearLayout> rows = new ArrayList<LinearLayout>();
	private LinearLayout llnavBar;
	private Navbar2 navbar;
	private int stepInline = 1;
	private int stepOther = 100;

	public TBuilder(Context context) {
		this.context = context;
	}

	public void build() {

		if (timecards == null) {
			Log.i("Error building Timeline", "No timecards have been added");
			return;
		}

		Collections.sort(timecards);

		initiateRows();

		calculateTimespan();


		navbar = new Navbar2(context, timespan);
		navbar.setTimecards(timecards);
		navbar.setStepInlineDate(stepInline);
		navbar.setStepOtherDate(stepOther);
		llnavBar = navbar.buildNavbar();

		positionCards();

		addRowsToTable();
	}

	private void positionCards() {
		int c = 0;
		int[] current = new int[NUM_ROWS];

		for (TimeCard tc : timecards) {
			current = placeTC(tc, current);
		}

	}

	private int[] placeTC(TimeCard tc, int[] current) {

		int offset = tcTimespan(tc);
		
		for (int i = 0; i < NUM_ROWS; i++) {
			
			if (offset >= current[i]){
				int diff = offset - current[i];
				addToRow(i, tc, diff);
				current[i] = offset + TIMECARD_WIDTH;
				break;
			}
		}

		return current;
	}

	private int tcTimespan(TimeCard tc) {
		
		if (timespan == TIMESPAN_CENTURIES){
			return offsetByDays(tc.getDatej(), 36500);
		} else if (timespan == TIMESPAN_YEARS) {
			return offsetByDays(tc.getDatej(), 365);
		} else if(timespan == TIMESPAN_MONTHS){
			return offsetByDays(tc.getDatej(), 30);
		} else if (timespan == TIMESPAN_DAYS){
			return offsetByDays(tc.getDatej(), 1);
		} else if (timespan == TIMESPAN_HOURS){
			//return offsetHours(tc.getDatej());
		}
		
		return 0;

	}
	
	
	private int offsetByDays (DateTime dt, int pDays){
		int res = 0;
		
		Days days = Days.daysBetween(timecards.get(0).getDatej(), dt);
		int daysBetween = days.getDays();
		
		long periodinPixels = (long) ((2*navbar.LINE_WIDTH+navbar.getIncrement()) * navbar.getDivider());
		
		int periodInDays = pDays* stepInline;
		
		/*
		 * periodInDays   -   pixels
		 * daysBetween    -  x
		 */
		
		res = (int) (daysBetween * periodinPixels / periodInDays);
		
		return res;
	}



	private void calculateTimespan() {

		DateTime start = timecards.get(0).getDatej();
		DateTime finish = timecards.get(timecards.size() - 1).getDatej();

		Years y = Years.yearsBetween(start, finish);
		int diff = y.getYears();

		if (diff > 90) {
			timespan = TIMESPAN_CENTURIES;
			return;
		} else if (diff >= 2 && diff <= 90) {
			timespan = TIMESPAN_YEARS;
			return;
		}

		Months m = Months.monthsBetween(start, finish);
		diff = m.getMonths();

		if (diff > 2) {
			timespan = TIMESPAN_MONTHS;
			return;
		}

		Days d = Days.daysBetween(start, finish);
		if (d.getDays() > 2) {
			timespan = TIMESPAN_DAYS;
			return;
		}

		Hours h = Hours.hoursBetween(start, finish);
		if (h.getHours() > 2) {
			timespan = TIMESPAN_HOURS;
			return;
		}

	}

	private void addToRow(int rowID, TimeCard timecard, int offset) {

		LayoutParams tlparams = new LinearLayout.LayoutParams(TIMECARD_WIDTH,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		tlparams.setMargins(offset, 0, 0, 0);
		rows.get(rowID).addView(timecard.getView(context), tlparams);

	}

	private void addRowsToTable() {

		LayoutParams layoutParams = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		for (LinearLayout row : rows) {
			layoutParams = new LinearLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			// ll.addView(row, layoutParams);
			table.addView(row, layoutParams);
		}
		//table.addView(llnavBar, layoutParams);
		tableNavbar.addView(llnavBar, layoutParams);

	}

	private void initiateRows() {
		for (int i = 0; i < NUM_ROWS; i++) {
			LinearLayout row = new LinearLayout(context);
			rows.add(row);
		}
	}
	
	

	public void setTable(LinearLayout table) {
		this.table = table;
	}

	public void addTimeCard(TimeCard tc) {
		if (timecards == null)
			timecards = new ArrayList<TimeCard>();

		timecards.add(tc);
	}

	public int getTimespan() {
		return timespan;
	}

	public void setTableNavbar(LinearLayout tableNavbar) {
		this.tableNavbar = tableNavbar;
	}
	
	

}
