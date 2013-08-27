package com.llino.timelineandroid.entities;

import java.text.SimpleDateFormat;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.llino.timelineandroid.R;
import com.llino.timelineandroid.R.id;
import com.llino.timelineandroid.R.layout;

public class TimeCard extends TimeCardAbstract implements Comparable<TimeCard> {

	public TimeCard(String title) {
		this.setTitle(title);
	}

	
	public View getView(Context context) {
		View view = LayoutInflater.from(context).inflate(R.layout.time_card,
				null);

		TextView title = (TextView) view.findViewById(R.id.tc_title);
		title.setText(getTitle());

		TextView date = (TextView) view.findViewById(R.id.tc_date);

		SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");

		if (getDate() != null)
			date.setText(date_format.format(getDate().getTime()));

		return view;
	}

	@Override
	public int compareTo(TimeCard o) {

		if (this.getDate() == null || o.getDate() == null)
			return 0;

		return getDate().compareTo(o.getDate());
	}

}
