package com.llino.timelineandroid.entities;

import java.util.Calendar;
import java.util.Locale;

import org.joda.time.DateTime;

import android.content.Context;
import android.view.View;

public abstract class TimeCardAbstract {
	
	protected String title;
	protected String desc;
	
	//I've provided this date format since it's used more often
	protected Calendar date;
	
	//Used Internally for TimelineAndroid
	protected DateTime datej;
	
	
	public abstract View getView(Context context);

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
		this.datej = new DateTime(date);
	}

	public DateTime getDatej() {
		return datej;
	}

	public void setDatej(DateTime datej) {
		this.datej = datej;
		date = datej.toCalendar(Locale.ENGLISH);
	}
	
	


}
