package com.example.braindrain;

import java.util.ArrayList;

public class Circles {
	private ArrayList<Circle> theList;
	public Circles()
	{
		theList = new ArrayList<Circle>();
		
		Circle circle1 = new Circle("algorithms", 1.2, 2);
		Circle circle2 = new Circle("jogging", 3.1, 5);
		Circle circle3 = new Circle("group meet", 2.3, 2);
		Circle circle4 = new Circle("hci", 2.2, 1);
		Circle circle5 = new Circle("sleep", 0.8, 1);
		theList.add(circle1);
		theList.add(circle2);
		theList.add(circle3);
		theList.add(circle4);
		theList.add(circle5);
	}
	
	public ArrayList<Circle> returnList()
	{
		return theList;
	}
}
