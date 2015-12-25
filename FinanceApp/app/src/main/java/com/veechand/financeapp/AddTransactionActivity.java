package com.veechand.financeapp;

import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.veechand.financeapp.db.DBHelper;
import com.veechand.financeapp.db.FinanceTransaction;

public class AddTransactionActivity extends AppCompatActivity implements View.OnClickListener {

    private RadioButton incomeRadioButton;
    private RadioButton expenseRadioButton;
    private EditText amountEditText;
    private Spinner transactionTypeSpinner;
    private Button addButton;
    private RadioGroup sub_type_radio_group;


    private DBHelper dbHelper = null;

    private String logTag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        incomeRadioButton = (RadioButton) findViewById(R.id.incomeRadioButton);
        expenseRadioButton = (RadioButton) findViewById(R.id.expenseRadioButton);
        amountEditText = (EditText) findViewById(R.id.amountEditText);
        transactionTypeSpinner = (Spinner) findViewById(R.id.transactionTypeSpinner);
        addButton = (Button) findViewById(R.id.addButton);
        sub_type_radio_group = (RadioGroup) findViewById(R.id.sub_type_radio_group);

        this.logTag = getResources().getString(R.string.finance_app_tag);


        incomeRadioButton.setOnClickListener(this);
        expenseRadioButton.setOnClickListener(this);
        addButton.setOnClickListener(this);

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
            case R.id.addButton:
                int checkedRadioButtonId = sub_type_radio_group.getCheckedRadioButtonId();
                String amount = amountEditText.getText().toString();
                if (checkedRadioButtonId == -1){
                    Toast.makeText(AddTransactionActivity.this,R.string.error_no_sub_type_selected,Toast.LENGTH_LONG).show();
                    return;
                }
                if (amount.isEmpty() || Double.valueOf(amount).intValue() <= 0 ){
                    Toast.makeText(AddTransactionActivity.this,R.string.error_no_amount_given,Toast.LENGTH_LONG).show();
                    return;
                }
                Cursor selectedCursor = (Cursor) transactionTypeSpinner.getSelectedItem();
                String selectedItem = selectedCursor.getString(selectedCursor.getColumnIndex(getResources().getString(R.string.sub_type_col_sub_type_name)));

                Log.i(logTag, String.valueOf(checkedRadioButtonId));
                Log.i(logTag, String.valueOf(R.id.incomeRadioButton));
                Log.i(logTag, amount);
                Log.i(logTag, selectedItem);
                int isIncome = (checkedRadioButtonId == R.id.incomeRadioButton)?getResources().getInteger(R.integer.income_true):
                        getResources().getInteger(R.integer.income_false);
                long transactionSubTypeId = dbHelper.getTransactionSubTypeID(selectedItem,isIncome);
                if (transactionSubTypeId == -1){
                    Toast.makeText(AddTransactionActivity.this,R.string.error_sub_type_not_found,Toast.LENGTH_LONG).show();
                }
                //TODO(veechand): Update the userID with the loggedin user
                long userID = 1;
                long insertionResult = dbHelper.insertNewTransaction(new FinanceTransaction(amount, transactionSubTypeId, userID, isIncome));
                if (insertionResult == -1){
                    Toast.makeText(AddTransactionActivity.this,R.string.finance_transaction_update_failed,Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AddTransactionActivity.this,R.string.finance_transaction_update_successful,Toast.LENGTH_LONG).show();
                }
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
        //allTransactionSubtypeCursor.close();
    }
}

/*
TODO:
  1. Showing transaction on recycler card view
  2. Changing the view. Always show the transaction and the master detail view to select action
  2. Add user to the sub transaction type table
  4. Register User and add it to DB

 */
