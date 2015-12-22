package com.veechand.storageexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vsubrama on 12/22/15.
 */
public class DBHelper {
    MySQLiteOpenHelper mySQLiteOpenHelper;
    public DBHelper(Context context){
        mySQLiteOpenHelper = new MySQLiteOpenHelper(context, "linkedin.db", null, 1);
    }
    public long insertInToDB(String name, String value){
        SQLiteDatabase wDB = mySQLiteOpenHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name",name);
        cv.put("phonenumber", value);
        long l = wDB.insert("linkedin", null, cv);
        wDB.close();
        return l;
    }

    public Cursor readFromDB() {
        SQLiteDatabase rDB = mySQLiteOpenHelper.getReadableDatabase();
        Cursor curs = rDB.query("linkedin", null, null, null, null, null, null);
        return curs;
    }

    public long updateDB(String name,String phoneNumber) {
        SQLiteDatabase wDB = mySQLiteOpenHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name",name);
        cv.put("phoneNumber",phoneNumber);
        String whereClause = "name = ?";
        String[] whereArgs = new String[]{name};
        int l = wDB.update("linkedin", cv, whereClause, whereArgs);
        wDB.close();
        return l;
    }

    public long deleteDB(String name, String phoneNumber){
        SQLiteDatabase wDB = mySQLiteOpenHelper.getWritableDatabase();
        String whereClause = "name = ? and phonenumber = ?";
        String[] whereArgs = new String[]{name,phoneNumber};
        int l = wDB.delete("linkedin", whereClause, whereArgs);
        wDB.close();
        return l;
    }
    class MySQLiteOpenHelper extends SQLiteOpenHelper {

        public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE linkedin (_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR2(256),phonenumber VARCHAR2(256))");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //backup
            //drop
            onCreate(db);
            //restore
            //delete backup

        }
    }
}
