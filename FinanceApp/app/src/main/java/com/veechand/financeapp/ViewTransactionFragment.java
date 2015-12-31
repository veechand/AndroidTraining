package com.veechand.financeapp;

import android.app.Fragment;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.veechand.financeapp.db.DBHelper;

import java.util.List;

public class ViewTransactionFragment extends Fragment {

    private RecyclerView transactionRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private DBHelper dbHelper;

    public ViewTransactionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
        return  view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


}
