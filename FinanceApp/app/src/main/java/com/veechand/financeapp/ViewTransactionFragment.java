package com.veechand.financeapp;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.veechand.financeapp.db.DBHelper;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ViewTransactionFragment extends Fragment {

    private RecyclerView transactionRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private TextView totalIncomeTextView;
    private TextView totalExpenseTextView;
    private DBHelper dbHelper;
    /*private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;
    private Button toDateButton;
    private Button fromDateButton;*/


    public ViewTransactionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //TODO (vsubrama): Not everything is needed during onCreateView, move to appropirate lifecyle methods. Got this when doing to datePicker
        Log.i("Cases","CAlled onCreateView");
        View view =  inflater.inflate(R.layout.activity_view_transaction, container, false);
        this.transactionRecyclerView = (RecyclerView) view.findViewById(R.id.transaction_recycler_view);
        transactionRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        transactionRecyclerView.setLayoutManager(mLayoutManager);
        this.dbHelper = new DBHelper(this.getActivity());
        List myDataset = dbHelper.getAllTransactions();
        mAdapter = new TransactionAdapter(myDataset);
        transactionRecyclerView.setAdapter(mAdapter);

        totalIncomeTextView = (TextView) view.findViewById(R.id.incomeTextView);
        totalExpenseTextView = (TextView) view.findViewById(R.id.expenseTextView);
        /*toDateButton = (Button) view.findViewById(R.id.toDateButton);
        fromDateButton = (Button) view.findViewById(R.id.fromDateButton);
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Toast.makeText(getActivity(), String.valueOf(year) + String.valueOf(monthOfYear) + String.valueOf(dayOfMonth), Toast.LENGTH_SHORT).show();
                fromDateButton.setText( String.valueOf(year) + "/" + String.valueOf(monthOfYear+1)  + "/" + String.valueOf(dayOfMonth));
            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE));

        fromDatePickerDialog.setTitle(getResources().getString(R.string.from_date));

        toDatePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Toast.makeText(getActivity(), String.valueOf(year) + String.valueOf(monthOfYear) + String.valueOf(dayOfMonth), Toast.LENGTH_SHORT).show();
                toDateButton.setText(String.valueOf(year) + "/" + String.valueOf(monthOfYear+1) + "/" + String.valueOf(dayOfMonth));
            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE));

        toDatePickerDialog.setTitle(getResources().getString(R.string.to_date));

        toDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDatePickerDialog.show();
            }
        });

        fromDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDatePickerDialog.show();
            }
        });

*/
        long totalIncome = dbHelper.getTotalTransactionAmount(getResources().getInteger(R.integer.income_true));
        long totalExpense = dbHelper.getTotalTransactionAmount(getResources().getInteger(R.integer.income_false));

        totalIncomeTextView.setText(String.valueOf(totalIncome));
        totalIncomeTextView.setTextColor(Color.GREEN);

        totalExpenseTextView.setText(String.valueOf(totalExpense));
        totalExpenseTextView.setTextColor(Color.MAGENTA);

        return  view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


}
