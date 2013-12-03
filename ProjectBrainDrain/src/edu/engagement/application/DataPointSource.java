package edu.engagement.application;

import java.util.ArrayList;
import java.util.List;

import edu.engagement.thrift.EegPower;
import edu.engagement.thrift.HeartRate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DataPointSource {

  // Database fields
  private SQLiteDatabase database;
  private DatabaseHelper dbHelper;
  private String[] allColumns = { DatabaseHelper.COLUMN_TIMESTAMP,
                                  DatabaseHelper.COLUMN_HEARTRATE,
                                  DatabaseHelper.COLUMN_ALPHA,
                                  DatabaseHelper.COLUMN_BETA,
                                  DatabaseHelper.COLUMN_THETA,};

  public DataPointSource(Context context) {
    dbHelper = new DatabaseHelper(context);
  }

  public void open() throws SQLException
  {
      database = dbHelper.getWritableDatabase();
  }

  public void close()
  {
      dbHelper.close();
  }

  public DataPoint createDataPointEEG(long timeStamp, double alpha, double beta, double theta)
  {
    ContentValues values = new ContentValues();
    values.put(DatabaseHelper.COLUMN_TIMESTAMP, timeStamp);
    values.put(DatabaseHelper.COLUMN_ALPHA, alpha);
    values.put(DatabaseHelper.COLUMN_BETA, beta);
    values.put(DatabaseHelper.COLUMN_THETA, theta);
    
    database.insert(DatabaseHelper.TABLE_EEG, null, values);
    
    return new DataPoint(timeStamp, 0, alpha, beta, theta);
  }
  
  public DataPoint createDataPointHR(long timeStamp, double hr)
  {
    ContentValues values = new ContentValues();
    values.put(DatabaseHelper.COLUMN_TIMESTAMP, timeStamp);
    values.put(DatabaseHelper.COLUMN_HEARTRATE, hr);
    
    database.insert(DatabaseHelper.TABLE_HR, null, values);
    
    return new DataPoint(timeStamp, hr, 0, 0, 0);
  }

  public void deleteDataPoint(DataPoint point)
  {
    long timestamp = point.getTimeStamp();
    if(point.getAlpha() != 0)
    {
    	database.delete(DatabaseHelper.TABLE_EEG, DatabaseHelper.COLUMN_TIMESTAMP
    	        + " = " + timestamp, null);
    	System.out.println("DataPoint deleted with timestamp: " + timestamp);
    }
    else if(point.getHeartRate() != 0)
    {
    	database.delete(DatabaseHelper.TABLE_HR, DatabaseHelper.COLUMN_TIMESTAMP
    	        + " = " + timestamp, null);
    	System.out.println("DataPoint deleted with timestamp: " + timestamp);
    }
    
  }

  public List<EegPower> getAllDataPointsEEG()
  {
    List<EegPower> points = new ArrayList<EegPower>();

    Cursor cursor = database.query(DatabaseHelper.TABLE_EEG,
        allColumns, null, null, null, null, null);

    cursor.moveToFirst();
    while (!cursor.isAfterLast())
    {
    	EegPower point = new EegPower();
    	point.setMillisecondTimeStamp(String.valueOf(cursor.getLong(0)));
    	point.setAlpha(cursor.getInt(1));
    	point.setBeta(cursor.getInt(2));
    	point.setTheta(cursor.getInt(3));
        points.add(point);
        cursor.moveToNext();
    }
    // make sure to close the cursor
    cursor.close();
    return points;
  }
  
  public List<HeartRate> getAllDataPointsHR()
  {
    List<HeartRate> points = new ArrayList<HeartRate>();

    Cursor cursor = database.query(DatabaseHelper.TABLE_HR,
        allColumns, null, null, null, null, null);

    cursor.moveToFirst();
    while (!cursor.isAfterLast())
    {
    	HeartRate point = new HeartRate();
    	point.setMillsecondTimeStamp(String.valueOf(cursor.getLong(0)));
    	point.setBpm(cursor.getInt(1));
        points.add(point);
        cursor.moveToNext();
    }
    // make sure to close the cursor
    cursor.close();
    return points;
  }
  
  public void clearDatabse()
  {
	  database.delete(DatabaseHelper.TABLE_EEG, null, null);
	  database.delete(DatabaseHelper.TABLE_HR, null, null);
  }
} 