package com.example.debts.repository;

import com.example.debts.model.Debt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DebtsDbAdapter 
{
	public static final String KEY_ROWID = "_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_SUM = "sum";
	public static final String KEY_TYPE = "type";
	 
	private static final String TAG = "DebtsDbAdapter";
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	 
	private static final String DATABASE_NAME = "Debts";
	private static final String SQLITE_TABLE = "Debt";
	private static final int DATABASE_VERSION = 1;
	 
	private final Context mCtx;
	 
	private static final String DATABASE_CREATE =
	  "CREATE TABLE if not exists " + SQLITE_TABLE + " (" +
	  KEY_ROWID + " integer PRIMARY KEY autoincrement," +
	  KEY_NAME + "," +
	  KEY_SUM + "," +
	  KEY_TYPE + ");";
	 
	 private static class DatabaseHelper extends SQLiteOpenHelper 
	 {
		 DatabaseHelper(Context context)
		 {
		  	 super(context, DATABASE_NAME, null, DATABASE_VERSION);
		 }
	  
		 @Override
		 public void onCreate(SQLiteDatabase db)
		 {
			 Log.w(TAG, DATABASE_CREATE);
			 db.execSQL(DATABASE_CREATE);
		 }
	 
		 @Override
		 public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
		 {
			 Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
			 		+ newVersion + ", which will destroy all old data");
		 	 db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE);
		 	 onCreate(db);
		 }
	 }
	 
	 public DebtsDbAdapter(Context ctx)
	 {
		 this.mCtx = ctx;
	 }
	 
	 public DebtsDbAdapter open() throws SQLException
	 {
		 mDbHelper = new DatabaseHelper(mCtx);
		 mDb = mDbHelper.getWritableDatabase();
		 return this;
	 }
	 
	 public void close()
	 {
		 if (mDbHelper != null)
		 {
			 mDbHelper.close();
		 }
	 }
	 
	 public long createDebt(String name, double sum, boolean type)
	 {
		 ContentValues initialValues = new ContentValues();
		 initialValues.put(KEY_NAME, name);
		 initialValues.put(KEY_SUM, sum);
		 initialValues.put(KEY_TYPE, type);
	 
		 return mDb.insert(SQLITE_TABLE, null, initialValues);
	 }
	 
	 public long createDebt(Debt newDebt)
	 {
		 ContentValues initialValues = new ContentValues();
		 initialValues.put(KEY_NAME, newDebt.getName());
		 initialValues.put(KEY_SUM, newDebt.getSum());
		 initialValues.put(KEY_TYPE, newDebt.isMyDebt());
	 
		 return mDb.insert(SQLITE_TABLE, null, initialValues);
	 }
	 
	 public boolean deleteAllDebts()
	 {
		 int doneDelete = 0;
		 doneDelete = mDb.delete(SQLITE_TABLE, null , null);
		 Log.w(TAG, Integer.toString(doneDelete));
		 
		 return doneDelete > 0;
	 }
	 
	 public Cursor fetchDebtsByName(String inputText) throws SQLException
	 {
		 Log.w(TAG, inputText);
		 Cursor mCursor = null;
		 if (inputText == null  ||  inputText.length () == 0)
		 {
			 mCursor = mDb.query(SQLITE_TABLE, new String[] {KEY_ROWID,
					 KEY_NAME, KEY_SUM, KEY_TYPE}, 
					 null, null, null, null, null);
		 }
		 else
		 {
			 mCursor = mDb.query(true, SQLITE_TABLE, new String[] {KEY_ROWID,
					 KEY_NAME, KEY_SUM, KEY_TYPE}, 
					 KEY_NAME + " like '%" + inputText + "%'", null,
					 null, null, null, null);
		 }
		 if (mCursor != null)
		 {
			 mCursor.moveToFirst();
		 }
		 
		 return mCursor;
	 }
	 
	 public Cursor fetchAllDebts()
	 {
		 Cursor mCursor = mDb.query(SQLITE_TABLE, new String[] {KEY_ROWID,
				 KEY_NAME, KEY_SUM, KEY_TYPE}, 
				 null, null, null, null, null);
	 
		 if (mCursor != null)
		 {
			 mCursor.moveToFirst();
		 }
		 return mCursor;
	 }
	 
	 public void insertSomeDebts()
	 {
		 for (int i = 0; i < 20; i++)
		 {
			 Debt newDebt = new Debt();
			 createDebt(newDebt.getName(), newDebt.getSum(), newDebt.isMyDebt());
		 }
	 }
}
