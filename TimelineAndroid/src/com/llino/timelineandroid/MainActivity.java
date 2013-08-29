package com.llino.timelineandroid;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.llino.timelineandroid.entities.TimeCard;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.TableLayout;

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
		TimeCard tc1 = new TimeCard("ola");
		tc1.setDate(cal);
		
		Calendar cal2 = new GregorianCalendar();
		TimeCard tc2 = new TimeCard("bola");
		cal2.set(Calendar.MONTH, Calendar.MAY);
		cal2.set(Calendar.DAY_OF_MONTH, 17);
		cal2.set(Calendar.YEAR, 2002);
		tc2.setDate(cal2);
		
		Calendar cal3 = new GregorianCalendar();
		TimeCard tc3 = new TimeCard("fee");
		cal3.set(Calendar.MONTH, Calendar.JANUARY);
		cal3.set(Calendar.DAY_OF_MONTH, 4);
		tc3.setDate(cal3);
		
		
		Calendar cal4 = new GregorianCalendar();
		TimeCard tc4 = new TimeCard("uyuyu");
		cal4.set(Calendar.YEAR, 2004);
		tc4.setDate(cal4);
		
		Calendar cal5 = new GregorianCalendar();
		TimeCard tc5 = new TimeCard("iiuu");
		cal5.set(Calendar.YEAR, 1983);
		cal5.set(Calendar.MONTH, Calendar.FEBRUARY);
		cal5.set(Calendar.DAY_OF_MONTH, 11);
		tc5.setDate(cal5);
		
		Calendar cal6 = new GregorianCalendar();
		TimeCard tc6 = new TimeCard("iiuu");
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
