package com.veechand.financeapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.veechand.financeapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vsubrama on 12/24/15.
 */

public class DBHelper  {
    private final Context context;
    private String logTag;

    SQLiteHelper sqlHelper;
    public DBHelper(Context context){
        String db_name = context.getResources().getString(R.string.db_name);
        this.context = context;
        this.sqlHelper = new SQLiteHelper(context,db_name,null,1);
        this.logTag = context.getResources().getString(R.string.finance_app_tag);
    }

    public Cursor getAllTransactionSubTypes(int isIncome) {
/*
        String sub_type_query = "select * from sub_type where is_income="+isIncome;
*/
        SQLiteDatabase rDb = sqlHelper.getReadableDatabase();
        String tableName = context.getResources().getString(R.string.sub_type_table_name);
        String[] columns = new String[]{
                context.getResources().getString(R.string.sub_type_col_id),
                context.getResources().getString(R.string.sub_type_col_sub_type_name),
        };

        String selection;
        String[] selectionArgs;
        if (isIncome != -1) {
            selection = context.getResources().getString(R.string.sub_type_col_is_income) + "= ?";
            selectionArgs = new String[]{String.valueOf(isIncome)};
        } else {
            selection = null;
            selectionArgs = null;
        }
        Cursor resultCursor = rDb.query(tableName, columns, selection, selectionArgs, null, null, null);
        return resultCursor;
    }

    public long getTotalTransactionAmount(int isIncome){
        SQLiteDatabase rDb = sqlHelper.getReadableDatabase();
        String tableName = context.getResources().getString(R.string.finance_transaction_table_name);
        String col_amount = context.getResources().getString(R.string.finance_transaction_col_amount);

        String selection;

        if (isIncome != -1) {
            selection = context.getResources().getString(R.string.sub_type_col_is_income) + "=" + String.valueOf(isIncome);
        } else {
            selection = null;
        }
        String groupBy = context.getResources().getString(R.string.sub_type_col_is_income);
        String query = "select sum("+col_amount+") as "+ col_amount + " from "+ tableName + " where "+ selection + " group by " + groupBy;
        Log.i(logTag,query);
        Cursor resultCursor = rDb.rawQuery(query,null);
        long totalAmount = 0;
        if  ( resultCursor != null && resultCursor.moveToFirst()  ){
            totalAmount = resultCursor.getLong(resultCursor.getColumnIndex(context.getResources().getString(R.string.finance_transaction_col_amount)));
        }
        return totalAmount;
    }

    public long insertNewTransaction(FinanceTransaction financeTransaction){
        SQLiteDatabase wDb = sqlHelper.getWritableDatabase();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            wDb.setForeignKeyConstraintsEnabled(true);
        }
        String tableName = context.getResources().getString(R.string.finance_transaction_table_name);
        ContentValues cv = new ContentValues();
        cv.put(context.getResources().getString(R.string.finance_transaction_col_is_income),financeTransaction.getIsIncome());
        cv.put(context.getResources().getString(R.string.finance_transaction_col_user_id),financeTransaction.getUserId());
        cv.put(context.getResources().getString(R.string.finance_transaction_col_sub_type_id),financeTransaction.getTransactionSubTypeId());
        cv.put(context.getResources().getString(R.string.finance_transaction_col_amount), financeTransaction.getAmount());
        cv.put(context.getResources().getString(R.string.finance_transaction_col_year), financeTransaction.getYear());
        cv.put(context.getResources().getString(R.string.finance_transaction_col_date), financeTransaction.getDate());
        cv.put(context.getResources().getString(R.string.finance_transaction_col_month), financeTransaction.getMonth());
        long result = wDb.insert(tableName, null, cv);
        wDb.close();
        return result;
    }

    public long getTransactionSubTypeID(String transaction_sub_type, int isIncome) {
        SQLiteDatabase rDb = sqlHelper.getReadableDatabase();
        String tableName = context.getResources().getString(R.string.sub_type_table_name);
        String[] columns = new String[]{
                context.getResources().getString(R.string.sub_type_col_id)
        };
       // String selection = new StringBuilder(context.getResources().getString(R.string.sub_type_col_sub_type_name)).append("= ? and ").append(context.getResources().getString(R.string.sub_type_col_is_income)).append("= ?").toString();
        String selection = new StringBuilder(context.getResources().getString(R.string.sub_type_col_sub_type_name)).append("= ? ").toString();
        Log.i(logTag,"Selection query "+selection);
        Log.i(logTag,"Selection sub_type "+transaction_sub_type);
        //String[] selectionArgs = new String[]{transaction_sub_type,String.valueOf(isIncome)};
        String[] selectionArgs = new String[]{transaction_sub_type};
        Cursor resultCursor = rDb.query(tableName, columns, selection, selectionArgs, null, null, null);
        Log.i(logTag, "Number of query results" + String.valueOf(resultCursor.getCount()));
        Log.i(logTag, "Columns[0] " + columns[0]);
        //TODO:vsubrama while updating check there is only one sub type
        long transactionSubTypeID = -1;
        if( resultCursor != null && resultCursor.moveToFirst() ) {
            int columnIndex = resultCursor.getColumnIndex(context.getResources().getString(R.string.sub_type_col_id));
            transactionSubTypeID = resultCursor.getLong(columnIndex);
            Log.i(logTag,"Column Index"+columnIndex);
        }
        resultCursor.close();
        return transactionSubTypeID;
    }

    public long insertNewSubType(String subTypeName, int isIncome, long userID) {
        SQLiteDatabase wDb = sqlHelper.getWritableDatabase();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            wDb.setForeignKeyConstraintsEnabled(true);
        }
        String tableName = context.getResources().getString(R.string.sub_type_table_name);
        ContentValues cv = new ContentValues();
        cv.put(context.getResources().getString(R.string.sub_type_col_sub_type_name), subTypeName);
        cv.put(context.getResources().getString(R.string.sub_type_col_is_income), isIncome);
        //cv.put(context.getResources().getString(R.string.sub_type_user_id),userID);
        long l = wDb.insert(tableName, null, cv);
        wDb.close();
        return l;
    }

    public List<FinanceTransaction> getAllTransactions() {

        List<FinanceTransaction> allTransactions = new ArrayList<FinanceTransaction>();
        Map<Integer,String> subTypeMapping = new HashMap<Integer,String>();
        SQLiteDatabase rDb = sqlHelper.getReadableDatabase();

        Cursor allTransactionSubTypes = getAllTransactionSubTypes(-1);
        while(allTransactionSubTypes != null && allTransactionSubTypes.moveToNext()){
            int id = allTransactionSubTypes.getInt(allTransactionSubTypes.getColumnIndex(context.getResources().getString(R.string.sub_type_col_id)));
            String name = allTransactionSubTypes.getString(allTransactionSubTypes.getColumnIndex(context.getResources().getString(R.string.sub_type_col_sub_type_name)));
            subTypeMapping.put(id,name);
        }
        Cursor result = rDb.query(context.getResources().getString(R.string.finance_transaction_table_name), null, null, null, null, null, null);
        while (result != null && result.moveToNext()){
            String amount = result.getString(result.getColumnIndex(context.getResources().getString(R.string.finance_transaction_col_amount)));
            int isIncome = result.getInt(result.getColumnIndex(context.getResources().getString(R.string.finance_transaction_col_is_income)));
            long userId = result.getLong(result.getColumnIndex(context.getResources().getString(R.string.finance_transaction_col_user_id)));
            long transactionSubtypeID = result.getLong(result.getColumnIndex(context.getResources().getString(R.string.finance_transaction_col_sub_type_id)));
            int month = result.getInt(result.getColumnIndex(context.getResources().getString(R.string.finance_transaction_col_month)));
            int year = result.getInt(result.getColumnIndex(context.getResources().getString(R.string.finance_transaction_col_year)));
            int date = result.getInt(result.getColumnIndex(context.getResources().getString(R.string.finance_transaction_col_date)));

            FinanceTransaction financeTransaction = new FinanceTransaction(amount, transactionSubtypeID, userId, isIncome,year,month,date);
            financeTransaction.setTransactionSubType(subTypeMapping.get((int)transactionSubtypeID));
            allTransactions.add(financeTransaction);
        }
        result.close();
        return allTransactions;

    }

    class SQLiteHelper extends SQLiteOpenHelper {

        public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            /*
             * Create table for user _id, username and password
             *     create table user (_id INTEGER PRIMARY KEY AUTOINCREMENT, email VARCHAR2(256),password VARCHAR2(256));
             * Create table for sub_type : _id, sub_type_name, is_income
             *    create table sub_type (_id INTEGER PRIMARY KEY AUTOINCREMENT, sub_type_name VARCHAR2(256), is_income INTEGER);
             * Create table for transaction: _id, is_income, sub_type_id, amount, user_id
             *   create table finance_transaction (_id INTEGER PRIMARY KEY AUTOINCREMENT,
             *     is_income INTEGER, sub_type_id INTEGER, user_id INTEGER, amount INTEGER,
             *    FOREIGN KEY(sub_type_id) REFERENCES sub_type(_id),
             *    FOREIGN KEY(user_id) REFERENCES user(_id)
             *    );
             *
             *  other query references:
             *   insert into user (email,password) values ("veera@veera.com","veera123");
             *   insert into sub_type (sub_type_name,is_income) values ("veg",0); // 0 means expense
             *   insert into sub_type (sub_type_name,is_income) values ("li_pay",1) //1 means income
             *
             *   insert into finance_transaction (is_income,sub_type_id,user_id,amount) values ();
             */
            db.execSQL("create table user (_id INTEGER PRIMARY KEY AUTOINCREMENT, email VARCHAR2(256),password VARCHAR2(256))");
            db.execSQL("create table sub_type (_id INTEGER PRIMARY KEY AUTOINCREMENT, sub_type_name VARCHAR2(256), is_income INTEGER);");
            String financeTransaction = "create table finance_transaction (_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "is_income INTEGER, sub_type_id INTEGER, user_id INTEGER, amount INTEGER,"
                    + "year INTEGER, month INTEGER, cdate INTEGER,"
                    + "FOREIGN KEY(sub_type_id) REFERENCES sub_type(_id),"
                    + "FOREIGN KEY(user_id) REFERENCES user(_id))";
            db.execSQL(financeTransaction);

            //TODO(veechand): Test queries remove later
            db.execSQL("insert into sub_type (sub_type_name,is_income) values (\"veg\",0)");
            db.execSQL("insert into sub_type (sub_type_name,is_income) values (\"li_pay\",1)");
            db.execSQL("insert into user (email,password) values  (\"ss@s.com\",0)");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
