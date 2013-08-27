package com.llino.timelineexample;

import java.text.SimpleDateFormat;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.llino.timelineandroid.entities.TimeCard;

public class MyTimecard extends TimeCard {
	
	private Bitmap imgBitmap = null;

	public MyTimecard(String title) {
		super(title);
	}

	@Override
	public View getView(Context context) {
		super.getView(context);
		
		
		View view = LayoutInflater.from(context).inflate(R.layout.my_timecard,
				null);

		TextView title = (TextView) view.findViewById(R.id.tc_title);
		title.setText(getTitle());

		TextView date = (TextView) view.findViewById(R.id.tc_date);

		SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");

		if (getDate() != null)
			date.setText(date_format.format(getDate().getTime()));
		
		
		
		ImageView iv = (ImageView) view.findViewById(R.id.tc_img);

		if (imgBitmap != null){
			iv.setImageBitmap(imgBitmap);
		}


		return view;
	}

	public Bitmap getImgBitmap() {
		return imgBitmap;
	}

	public void setImgBitmap(Bitmap imgBitmap) {
		this.imgBitmap = imgBitmap;
	}



}
