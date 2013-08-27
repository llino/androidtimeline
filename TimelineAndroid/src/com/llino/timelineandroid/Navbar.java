package com.llino.timelineandroid;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.joda.time.Months;

import com.llino.timelineandroid.entities.TimeCard;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class Navbar {

	public static final int NAV_HEIGHT = 80;
	public static final int LINE_WIDTH = 2;
	public static final int INCREMENT = 18 + LINE_WIDTH;

	private Context context;
	private LinearLayout nav;
	private LinearLayout navTop;
	private LinearLayout navBottom;
	private LinearLayout navText;
	public int color = Color.rgb(245, 17, 21);
	private int temp = 0;
	private int tempYear=0;

	private int timespan;
	private ArrayList<TimeCard> timecards = null;
	private Months months;
	private DateTime start;
	private DateTime finish;


	public Navbar(Context context, int timespan) {
		this.context = context;
		this.timespan = timespan;
	}

	public LinearLayout buildNavbar() {
		initializeNavs();
		calculateLength();

		drawNav();

		return nav;
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

	private void calculateLength() {
		start = timecards.get(0).getDatej();
		finish = timecards.get(timecards.size() - 1).getDatej();
		months = Months.monthsBetween(start, finish);

	}

	private void drawNav() {

		int divide = 5;
		int total = (months.getMonths() + 2) * divide;
		DateTime tempMonth = null;
		int currYear = 0;

		View v = null;
		int c = 0;
		for (int i = 0, d = 1; i < total; i++, d++) {

			v = drawLine(INCREMENT);

			navTop.addView(v);

			c++;
			if (i % divide == 0) {
				int add;

				if (c == 1) {
					add = c * INCREMENT;
					navBottom.addView(drawLine(add));
					textSameLine(start.monthOfYear().getAsText());
					tempMonth = start;
					
				} else {
					add = c * INCREMENT + (LINE_WIDTH * (divide - 1)) - temp;
					navBottom.addView(drawLine(add));
					tempMonth = tempMonth.plusMonths(1);
					textSameLine(tempMonth.monthOfYear().getAsText());
				}
				
				
				if (currYear==0){
					currYear = tempMonth.getYear();
					addYear(Integer.toString(currYear), INCREMENT);
				} else {
					if (tempMonth.getYear() != currYear){
						currYear = tempMonth.getYear();
						add = d * INCREMENT;
						addYear(Integer.toString(currYear), add+tempYear);
						d=1;
					}
					
				}
				c = 0;
				
			}

		}

	}

	private void addYear(String text, int add) {
		TextView txt = new TextView(context);
		txt.setText(text);
		txt.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
		tempYear = txt.getMeasuredWidth();
		LayoutParams ll = new LayoutParams(LayoutParams.WRAP_CONTENT, 20);
		ll.setMargins(add, 0, 0, 0);
		txt.setLayoutParams(ll);
		
		navText.addView(txt);

	}

	private void textSameLine(String text) {
		TextView txt = new TextView(context);
		txt.setText(text);
		txt.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
		int width = txt.getMeasuredWidth();
		LayoutParams ll = new LayoutParams(width, 20);
		txt.setLayoutParams(ll);
		temp = width;
		navBottom.addView(txt);
	}

	private View drawLine(int offset) {
		View v = new View(context);
		LayoutParams ll = new LayoutParams(LINE_WIDTH, NAV_HEIGHT);
		ll.setMargins(offset, 0, 0, 0);
		v.setLayoutParams(new LayoutParams(ll));

		v.setBackgroundColor(color);

		return v;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public void setTimecards(ArrayList<TimeCard> timecards) {
		this.timecards = timecards;
	}

}
