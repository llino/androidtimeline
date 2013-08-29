package com.llino.timelineexample;


import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.widget.LinearLayout;

import com.llino.timelineandroid.TBuilder;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		LinearLayout tabela = (LinearLayout)findViewById(R.id.tabela);
		LinearLayout tabelaNavbar = (LinearLayout) findViewById(R.id.navbar);
		
		TBuilder tbuild = new TBuilder(getApplicationContext());
		tbuild.setTable(tabela);
		tbuild.setTableNavbar(tabelaNavbar);
		
		Calendar cal = new GregorianCalendar();
		MyTimecard tc1 = new MyTimecard("Example 1");
		tc1.setDate(cal);
		Bitmap bm = getBitmapFromAsset("media_29441.jpg");
		tc1.setImgBitmap(bm);
		
		
		Calendar cal2 = new GregorianCalendar();
		MyTimecard tc2 = new MyTimecard("New Test");
		cal2.set(Calendar.MONTH, Calendar.MAY);
		cal2.set(Calendar.DAY_OF_MONTH, 17);
		cal2.set(Calendar.YEAR, 2002);
		tc2.setDate(cal2);
		Bitmap bm2 = getBitmapFromAsset("salvador_dali1.jpg");
		tc2.setImgBitmap(bm2);
		
		Calendar cal3 = new GregorianCalendar();
		MyTimecard tc3 = new MyTimecard("Another");
		cal3.set(Calendar.MONTH, Calendar.JANUARY);
		cal3.set(Calendar.DAY_OF_MONTH, 4);
		tc3.setDate(cal3);
		Bitmap bm3 = getBitmapFromAsset("salvador_dali2.jpg");
		tc3.setImgBitmap(bm3);
		
		
		Calendar cal4 = new GregorianCalendar();
		MyTimecard tc4 = new MyTimecard("Hello World");
		cal4.set(Calendar.YEAR, 2004);
		tc4.setDate(cal4);
		
		Calendar cal5 = new GregorianCalendar();
		MyTimecard tc5 = new MyTimecard("World New");
		cal5.set(Calendar.YEAR, 1983);
		cal5.set(Calendar.MONTH, Calendar.FEBRUARY);
		cal5.set(Calendar.DAY_OF_MONTH, 11);
		tc5.setDate(cal5);
		Bitmap bm5 = getBitmapFromAsset("salvador_dali3.jpg");
		tc5.setImgBitmap(bm5);
		
		Calendar cal6 = new GregorianCalendar();
		MyTimecard tc6 = new MyTimecard("Yet Another");
		cal6.set(Calendar.YEAR, 1987);
		cal6.set(Calendar.MONTH, Calendar.FEBRUARY);
		cal6.set(Calendar.DAY_OF_MONTH, 21);
		tc6.setDate(cal6);
		
		
		tbuild.addTimeCard(tc1);
		tbuild.addTimeCard(tc2);
		tbuild.addTimeCard(tc3);
		tbuild.addTimeCard(tc4);
		tbuild.addTimeCard(tc5);
		tbuild.addTimeCard(tc6);
		tbuild.build();
		

		
	}
	
	private Bitmap getBitmapFromAsset(String strName)
	{
	    AssetManager assetManager = getAssets();
	    InputStream istr=null;
		try {
			istr = assetManager.open(strName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    Bitmap bitmap = BitmapFactory.decodeStream(istr);
	    return bitmap;
	 }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
