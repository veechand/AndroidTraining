package com.veechand.financeapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.veechand.financeapp.db.DBHelper;
import com.veechand.financeapp.db.FinanceTransaction;

import java.util.List;

public class ViewTransactionActivity extends AppCompatActivity {

    private RecyclerView transactionRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_transaction);
        this.transactionRecyclerView = (RecyclerView) findViewById(R.id.transaction_recycler_view);
        transactionRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        transactionRecyclerView.setLayoutManager(mLayoutManager);
        this.dbHelper = new DBHelper(ViewTransactionActivity.this);
        List myDataset = dbHelper.getAllTransactions();
        mAdapter = new TransactionAdapter(myDataset);
        transactionRecyclerView.setAdapter(mAdapter);
    }

}
