package com.veechand.financeapp;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.veechand.financeapp.db.DBHelper;
import com.veechand.financeapp.db.FinanceTransaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.*;

public class AddTransactionFragment extends Fragment implements View.OnClickListener {

    private RadioButton incomeRadioButton;
    private RadioButton expenseRadioButton;
    private EditText amountEditText;
    private Spinner transactionTypeSpinner;
    private Button addButton;
    private Button onDateButton;
    private RadioGroup sub_type_radio_group;
    private DatePickerDialog onDatePickerDialog;


    private DBHelper dbHelper = null;

    private String logTag;

    private int year = -1;
    private int monthOfYear = -1;
    private int dayOfMonth = -1;

    private final Calendar calendar = getInstance();

    private static SimpleDateFormat DATE_FORMAT;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_add_transaction);
        DATE_FORMAT = new SimpleDateFormat("dd/MM/yy");
        this.logTag = getResources().getString(R.string.finance_app_tag);

        View rootView = inflater.inflate(R.layout.activity_add_transaction, container, false);
        incomeRadioButton = (RadioButton) rootView.findViewById(R.id.incomeRadioButton);
        expenseRadioButton = (RadioButton) rootView.findViewById(R.id.expenseRadioButton);
        onDateButton = (Button) rootView.findViewById(R.id.onDateButton);
        amountEditText = (EditText) rootView.findViewById(R.id.amountEditText);
        transactionTypeSpinner = (Spinner) rootView.findViewById(R.id.transactionTypeSpinner);
        addButton = (Button) rootView.findViewById(R.id.addButton);
        sub_type_radio_group = (RadioGroup) rootView.findViewById(R.id.sub_type_radio_group);

        incomeRadioButton.setOnClickListener(this);
        expenseRadioButton.setOnClickListener(this);
        addButton.setOnClickListener(this);
        onDateButton.setOnClickListener(this);

        dbHelper = new DBHelper(getActivity());

        Date date = new Date();
        onDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int _year, int _monthOfYear, int _dayOfMonth) {
                calendar.set(_year, _monthOfYear, _dayOfMonth);
                onDateButton.setText(DATE_FORMAT.format(calendar.getTime()));
                year = _year;
                monthOfYear = _monthOfYear;
                dayOfMonth = _dayOfMonth;
            }
        }, calendar.get(YEAR), calendar.get(MONTH), calendar.get(DATE));

        onDateButton.setText(DATE_FORMAT.format(calendar.getTime()));
        String choosenDate = onDateButton.getText().toString();
        year = calendar.get(Calendar.YEAR);
        dayOfMonth = calendar.get(Calendar.DATE);
        monthOfYear = calendar.get(Calendar.MONTH);

        onDatePickerDialog.setTitle(getResources().getString(R.string.on_date));

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.incomeRadioButton:
                populateTransactionSubType(R.integer.income_true);
                break;
            case R.id.expenseRadioButton:
                populateTransactionSubType(R.integer.income_false);
                break;
            case R.id.onDateButton:
                onDatePickerDialog.show();
                break;
            case R.id.addButton:
                int checkedRadioButtonId = sub_type_radio_group.getCheckedRadioButtonId();
                String amount = amountEditText.getText().toString();
                if (checkedRadioButtonId == -1) {
                    Toast.makeText(getActivity(), R.string.error_no_sub_type_selected, Toast.LENGTH_LONG).show();
                    return;
                }
                if (amount.isEmpty() || Double.valueOf(amount).intValue() <= 0) {
                    Toast.makeText(getActivity(), R.string.error_no_amount_given, Toast.LENGTH_LONG).show();
                    return;
                }

                Cursor selectedCursor = (Cursor) transactionTypeSpinner.getSelectedItem();
                String selectedItem = selectedCursor.getString(selectedCursor.getColumnIndex(getResources().getString(R.string.sub_type_col_sub_type_name)));

                Log.i(logTag, String.valueOf(checkedRadioButtonId));
                Log.i(logTag, String.valueOf(R.id.incomeRadioButton));
                Log.i(logTag, amount);
                Log.i(logTag, selectedItem);
                int isIncome = (checkedRadioButtonId == R.id.incomeRadioButton) ? getResources().getInteger(R.integer.income_true) :
                        getResources().getInteger(R.integer.income_false);
                long transactionSubTypeId = dbHelper.getTransactionSubTypeID(selectedItem, isIncome);
                if (transactionSubTypeId == -1) {
                    Toast.makeText(getActivity(), R.string.error_sub_type_not_found, Toast.LENGTH_LONG).show();
                }
                //TODO(veechand): Update the userID with the loggedin user
                long userID = 1;
                long insertionResult = dbHelper.insertNewTransaction(new FinanceTransaction(amount, transactionSubTypeId, userID, isIncome, year, monthOfYear, dayOfMonth));
                if (insertionResult == -1) {
                    Toast.makeText(getActivity(), R.string.finance_transaction_update_failed, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), R.string.finance_transaction_update_successful, Toast.LENGTH_LONG).show();
                }
                break;


        }
    }

    private void populateTransactionSubType(int isIncome) {
        Cursor allTransactionSubtypeCursor = dbHelper.getAllTransactionSubTypes(
                getResources().getInteger(isIncome));
        if (allTransactionSubtypeCursor.getCount() <= 0) {
            return;
        }
        Log.i("AddTransactionActivity ", getResources().getString(R.string.sub_type_col_sub_type_name));
        String[] from = new String[]{
                getResources().getString(R.string.sub_type_col_sub_type_name)
        };
        int[] to = new int[]{android.R.id.text1};

        SimpleCursorAdapter transactionTypeSpinnerAdapter = new SimpleCursorAdapter(
                getActivity(), android.R.layout.simple_spinner_item, allTransactionSubtypeCursor, from, to, 0);
        transactionTypeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        transactionTypeSpinner.setAdapter(transactionTypeSpinnerAdapter);
        //allTransactionSubtypeCursor.close();
    }
}

/*
TODO:
  2. Add user to the sub transaction type table
  4. Register User and add it to DB
  5. State is not maintained
 */
