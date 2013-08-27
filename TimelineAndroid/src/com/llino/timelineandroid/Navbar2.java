package com.llino.timelineandroid;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Months;
import org.joda.time.Period;
import org.joda.time.Years;

import com.llino.timelineandroid.entities.TimeCard;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class Navbar2 {

	public static final int NAV_HEIGHT = 80;
	public static final int LINE_WIDTH = 2;
	public static final int DEFAULT_INCREMENT = 18;

	private Context context;
	private LinearLayout nav;
	private LinearLayout navTop;
	private LinearLayout navBottom;
	private LinearLayout navText;
	public int color = Color.rgb(245, 17, 21);

	private ArrayList<TimeCard> timecards = null;
	private Hours hours;
	private Days days;
	private Months months;
	private Years years;
	private DateTime start;
	private DateTime finish;

	private int increment;
	private int timespan;
	private int divider = 5;
	private int width = 0;
	private int stepInlineDate=1;
	private int stepOtherDate=10;
	private ArrayList<Timeitem> cycleResult;


	public Navbar2(Context context, int timespan) {
		this.context = context;
		this.timespan = timespan;
		increment = DEFAULT_INCREMENT;
	}

	public LinearLayout buildNavbar() {
		initializeNavs();
		cycleResult = calculateValsForNav();

		drawNav(cycleResult);

		return nav;
	}

	private void drawNav(ArrayList<Timeitem> cycleResult) {
		int total = cycleResult.size() * divider;

		TextView txt = null;
		int txtSize = 0;
		String minorCurrent = null;

		for (int i = 0, c = 0, d = 0, e = 0; i < total; i++, c++, e++) {

			if (i == 0)
				navTop.addView(drawLine(0));
			else
				navTop.addView(drawLine(increment + LINE_WIDTH));

			if (i % divider == 0) {
				int add;
				if (c == 0) {
					add = c * (increment + LINE_WIDTH);
					navBottom.addView(drawLine(add));
				} else {
					add = c * (increment + LINE_WIDTH)
							+ (LINE_WIDTH * (divider - 1)) - txtSize;
					navBottom.addView(drawLine(add));
				}

				txt = addText(cycleResult.get(d).major, 0);
				txtSize = txt.getMeasuredWidth();
				navBottom.addView(txt);

				if (i == 0) {
					txt = addText(cycleResult.get(d).minor, 0);
					navText.addView(txt);
					width = txt.getMeasuredWidth();
					minorCurrent = cycleResult.get(d).minor;
					e = 1;

				} else if (!cycleResult.get(d).minor.equals(minorCurrent)) {
					add = LINE_WIDTH * e + increment * e - width;
					txt = addText(cycleResult.get(d).minor, add);
					navText.addView(txt);
					width = txt.getMeasuredWidth();
					minorCurrent = cycleResult.get(d).minor;

					e = divider;
				}

				c = 0;
				d++;
			}
		}
	}

	private TextView addText(String str, int left) {
		TextView txt = new TextView(context);
		txt.setText(str);
		txt.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);

		width = txt.getMeasuredWidth();

		LayoutParams ll = new LayoutParams(width, NAV_HEIGHT / 3);
		ll.setMargins(left, 0, 0, 0);
		txt.setLayoutParams(ll);
		return txt;
	}

	private ArrayList<Timeitem> calculateValsForNav() {

		start = timecards.get(0).getDatej();
		finish = timecards.get(timecards.size() - 1).getDatej();
		ArrayList<String> minor = new ArrayList<String>();
		ArrayList<String> major = new ArrayList<String>();

		cycleResult = null;

		if (timespan == TBuilder.TIMESPAN_CENTURIES) {
			cycleResult = cycleCenturies(start, finish, stepInlineDate, stepOtherDate);
		} else if (timespan == TBuilder.TIMESPAN_YEARS) {
			cycleResult = cycleYears(start, finish, stepInlineDate, stepOtherDate);

		} else if (timespan == TBuilder.TIMESPAN_MONTHS) {
			cycleResult = cycleMonths(start, finish);

		} else if (timespan == TBuilder.TIMESPAN_DAYS) {
			cycleResult = cycleDays(start, finish);
		}

		return cycleResult;
	}
	
	private ArrayList<Timeitem> cycleCenturies(DateTime start, DateTime finish, int stepInlineDate, int stepOtherDate) {
		ArrayList<Timeitem> items = new ArrayList<Navbar2.Timeitem>();

		DateTime temp = start;
		int minor = start.getYear();

		do {

			if (temp.getCenturyOfEra() % stepOtherDate == 0) {
				minor = temp.getCenturyOfEra();
			}

			items.add(new Timeitem(temp.centuryOfEra().getAsText(), Integer.toString(minor), temp));
			temp = temp.plusYears(stepInlineDate*100);
		} while (temp.getCenturyOfEra() <= finish.getCenturyOfEra());

		return items;

	}
	
	private ArrayList<Timeitem> cycleYears(DateTime start, DateTime finish, int stepInlineDate, int stepOtherDate) {
		ArrayList<Timeitem> items = new ArrayList<Navbar2.Timeitem>();

		DateTime temp = start;
		int minor = start.getYear();

		do {

			if (temp.getYearOfCentury() % stepOtherDate == 0) {
				minor = temp.getYear();
			}

			items.add(new Timeitem(temp.year().getAsText(), Integer.toString(minor), temp));
			temp = temp.plusYears(stepInlineDate);
		} while (temp.getYear() <= finish.getYear());

		return items;

	}

	private ArrayList<Timeitem> cycleMonths(DateTime start, DateTime finish) {

		ArrayList<Timeitem> items = new ArrayList<Navbar2.Timeitem>();

		DateTime temp = start;

		do {
			items.add(new Timeitem(temp.monthOfYear().getAsText(), temp.year()
					.getAsText(), temp));
			temp = temp.plusMonths(1);
		} while (temp.getMonthOfYear() != finish.getMonthOfYear()
				|| temp.getYear() != finish.getYear());
		
		items.add(new Timeitem(temp.monthOfYear().getAsText(), temp.year()
				.getAsText(), temp));
		

		return items;
	}

	private ArrayList<Timeitem> cycleDays(DateTime start, DateTime finish) {

		ArrayList<Timeitem> items = new ArrayList<Navbar2.Timeitem>();

		DateTime temp = start;

		do {
			items.add(new Timeitem(temp.dayOfMonth().getAsString(), temp
					.dayOfMonth().getAsString(), temp));
			temp = temp.plusDays(1);
		} while (temp.getMonthOfYear() != finish.getMonthOfYear()
				|| temp.getDayOfMonth() != finish.getDayOfMonth());

		return items;
	}

	private View drawLine(int offset) {
		View v = new View(context);
		LayoutParams ll = new LayoutParams(LINE_WIDTH, NAV_HEIGHT);
		ll.setMargins(offset, 0, 0, 0);
		v.setLayoutParams(new LayoutParams(ll));

		v.setBackgroundColor(color);

		return v;
	}

	private void initializeNavs() {
		nav = new LinearLayout(context);
		nav.setOrientation(LinearLayout.VERTICAL);
		nav.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				NAV_HEIGHT));

		navTop = new LinearLayout(context);
		navTop.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				NAV_HEIGHT / 3));

		navBottom = new LinearLayout(context);
		navBottom.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				NAV_HEIGHT / 3));

		navText = new LinearLayout(context);
		navText.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				NAV_HEIGHT / 3));

		nav.addView(navTop);
		nav.addView(navBottom);
		nav.addView(navText);
	}

	public void setTimecards(ArrayList<TimeCard> timecards) {
		this.timecards = timecards;
	}


	public class Timeitem {
		public String major;
		public String minor;
		public DateTime date;


		public Timeitem(String major, String minor, DateTime date) {
			this.major = major;
			this.minor = minor;
			this.date = date;
		}
		

	}


	public int getIncrement() {
		return increment;
	}

	public int getDivider() {
		return divider;
	}

	public ArrayList<Timeitem> getCycleResult() {
		return cycleResult;
	}
	

	public void setStepInlineDate(int stepInlineDate) {
		this.stepInlineDate = stepInlineDate;
	}
	

	public void setStepOtherDate(int stepOtherDate) {
		this.stepOtherDate = stepOtherDate;
	}
	
	
	

}
