package com.example.braindrain;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.GridLayout.LayoutParams;
import android.widget.Space;

public class OverallActivity extends Activity{

	private ArrayList<Circle> theList;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.overall);
		
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int widthofScreen = size.x;
		int heightofScreen = size.y;
		
		Circles test = new Circles();
		theList = test.returnList();
		
		GridLayout gridLayout = (GridLayout) this.findViewById(R.id.gridlayout);
		
		ImageView btn1 = new ImageView(this);
		btn1.setImageResource(R.drawable.green_btn);
		GridLayout.LayoutParams param = new GridLayout.LayoutParams();
		param.height = (int) (widthofScreen * .1);
		param.width = (int) (widthofScreen * .1);
		param.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1);
		param.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1);
		btn1.setLayoutParams(param);
		
		ImageView btn2 = new ImageView(this);
		btn2.setImageResource(R.drawable.green_btn);
		GridLayout.LayoutParams param2 = new GridLayout.LayoutParams();
		param2.height = (int) (widthofScreen * .3);
		param2.width = (int) (widthofScreen *.3);
		param2.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 3);
		param2.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 3);
		btn2.setLayoutParams(param2);
		
		ImageView btn3 = new ImageView(this);
		btn3.setImageResource(R.drawable.green_btn);
		GridLayout.LayoutParams param3 = new GridLayout.LayoutParams();
		param3.height = (int) (widthofScreen *.6);
		param3.width = (int) (widthofScreen * .6);
		param3.columnSpec = GridLayout.spec(GridLayout.UNDEFINED,6);
		param3.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 6);
		btn3.setLayoutParams(param3);
		
		ImageView btn4 = new ImageView(this);
		btn4.setImageResource(R.drawable.green_btn);
		GridLayout.LayoutParams param4 = new GridLayout.LayoutParams();
		param4.height = (int) (widthofScreen *.2);
		param4.width = (int) (widthofScreen * .2);
		param4.columnSpec = GridLayout.spec(GridLayout.UNDEFINED,2);
		param4.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 2);
		btn4.setLayoutParams(param4);
		
		Random ran = new Random();
		int x= 1 + ran.nextInt(4);
		int y = 1 + ran.nextInt(4);
		
		ImageView spc = new ImageView(this);
		GridLayout.LayoutParams param5 = new GridLayout.LayoutParams();
		int percent_height = y/10;
		int percent_width = x/10;
		param5.height = (int) widthofScreen*percent_height;
		param5.width = (int) widthofScreen*percent_width;
		param5.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, x);
		param5.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, y);
		spc.setLayoutParams(param5);
		
		gridLayout.addView(btn1);
		gridLayout.addView(btn2);
		gridLayout.addView(spc);
		gridLayout.addView(btn3);
		gridLayout.addView(btn4);
	}
}
