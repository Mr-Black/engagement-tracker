package edu.engagement.application;

public class DataPoint
{
	private long timeStamp;
	private double heartRate;
	private double alpha;
	private double beta;
	private double theta;
	
	public DataPoint(long timeStamp, double hr, double alpha, double beta, double theta)
	{
		this.timeStamp = timeStamp;
		this.heartRate = hr;
		this.alpha = alpha;
		this.beta = beta;
		this.theta = theta;
	}
	
	public double getAlpha() {
		return alpha;
	}
	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}
	public double getBeta() {
		return beta;
	}
	public void setBeta(double beta) {
		this.beta = beta;
	}
	public double getTheta() {
		return theta;
	}
	public void setTheta(double theta) {
		this.theta = theta;
	}
	
	
	public long getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
	public double getHeartRate() {
		return heartRate;
	}
	public void setHeartRate(double heartRate) {
		this.heartRate = heartRate;
	}

	
	
}
