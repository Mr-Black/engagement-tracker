package edu.engagement.application;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper
{

  public static final String TABLE_HR = "table_hr";
  public static final String TABLE_EEG = "table_eeg";
  public static final String COLUMN_TIMESTAMP = "Timestamp";
  public static final String COLUMN_ALPHA = "Alpha";
  public static final String COLUMN_BETA = "Beta";
  public static final String COLUMN_THETA = "Theta";
  public static final String COLUMN_HEARTRATE = "Heartrate";

  private static final String DATABASE_NAME = "commments.db";
  private static final int DATABASE_VERSION = 1;

  // Database creation sql statement
  private static final String DATABASE_CREATE_EEG = "CREATE TABLE IF NOT EXISTS "
      + TABLE_EEG + "(" + COLUMN_TIMESTAMP + " INTEGER, "
	  + COLUMN_ALPHA + " REAL, "
	  + COLUMN_BETA + " REAL, "
	  + COLUMN_THETA + " REAL"
      + " );";
  
  private static final String DATABASE_CREATE_HR = "CREATE TABLE IF NOT EXISTS "
	  + TABLE_HR + "(" + COLUMN_TIMESTAMP + " INTEGER, "
	  + COLUMN_HEARTRATE + " REAL"
      + " );";

  public DatabaseHelper(Context context)
  {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase database)
  {
    database.execSQL(DATABASE_CREATE_EEG);
    database.execSQL(DATABASE_CREATE_HR);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
  {
    Log.w(DatabaseHelper.class.getName(),
        "Upgrading database from version " + oldVersion + " to "
            + newVersion + ", which will destroy all old data");
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_HR);
    db.execSQL("DROP TABLE IF EXISTS " + TABLE_EEG);
    onCreate(db);
  }

} 