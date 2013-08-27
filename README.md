androidtimeline
===============
### Developed by Leonardo Lino
![Screenshot of Android Timeline](http://www.gomuseum.net/img/timeline_screenshot1.jpg "screenshot of android timeline")

Timeline library for android

This is still a work in progress so there are bugs.


### Usage
See example project to see how to use this timeline library

## Step by Step
1- Import timelineandroid into your workspace. Make sure in android properties it's set as a library

2- create a class MyTimecard that extends TimeCard. This forces you to implement a method called getView.

3- Create an xml file (in my example it's called my_timecard)

4- In your main.activity.xml, copy code from example xml

5- In main activity:

```android
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
		
		tbuild.addTimeCard(tc1);
		tbuild.addTimeCard(tc2);
		tbuild.build();
		

```

