package com.veechand.financeapp;

import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.veechand.financeapp.db.DBHelper;

public class AddTransactionActivity extends AppCompatActivity implements View.OnClickListener {

    RadioButton incomeRadioButton;
    RadioButton expenseRadioButton;
    EditText amountEditText;
    Spinner transactionTypeSpinner;
    Button addButton;

    DBHelper dbHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        incomeRadioButton = (RadioButton) findViewById(R.id.incomeRadioButton);
        expenseRadioButton = (RadioButton) findViewById(R.id.expenseRadioButton);
        amountEditText = (EditText) findViewById(R.id.amountEditText);
        transactionTypeSpinner = (Spinner) findViewById(R.id.transactionTypeSpinner);
        addButton = (Button) findViewById(R.id.addButton);

        incomeRadioButton.setOnClickListener(this);
        expenseRadioButton.setOnClickListener(this);


        dbHelper = new DBHelper(AddTransactionActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.incomeRadioButton:
                populateTransactionSubType(R.integer.income_true);
                break;
            case R.id.expenseRadioButton:
                populateTransactionSubType(R.integer.income_false);
                break;


        }
    }

    private void populateTransactionSubType(int isIncome) {
        Cursor allTransactionSubtypeCursor = dbHelper.getAllTransactionSubTypes(
                getResources().getInteger(isIncome));
        if (allTransactionSubtypeCursor.getCount() <=0 ){
            return;
        }
        Log.i("AddTransactionActivity ", getResources().getString(R.string.sub_type_col_sub_type_name));
        String[] from = new String[]{
                getResources().getString(R.string.sub_type_col_sub_type_name)
        };
        int[] to = new int[]{android.R.id.text1};

        SimpleCursorAdapter transactionTypeSpinnerAdapter = new SimpleCursorAdapter(
                AddTransactionActivity.this, android.R.layout.simple_spinner_item, allTransactionSubtypeCursor, from, to, 0);
        transactionTypeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        transactionTypeSpinner.setAdapter(transactionTypeSpinnerAdapter);
    }
}

/*
TODO:
  1. Write the com.veechand.financeapp.com.veechand.finance.model.DBHelper
  2. Populate the TransactionSubType combo - Done
  3. On Click validate and add it to DB
  4. Register User and add it to DB
 */
