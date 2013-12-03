package com.example.braindrain;

public class Circle {
	private double engagement;
	private String tag;
	private double volatility;
	
	public Circle(String tag, double eng, double vol)
	{
		this.tag = tag;
		engagement = eng;
		volatility = vol;
	}
	
	public String getTag()
	{
		return tag;
	}
	public double getEng()
	{
		return engagement;
	}
	public double getVol()
	{
		return volatility;
	}
}
