package com.veechand.financeapp;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.veechand.financeapp.db.FinanceTransaction;

import java.util.List;

/**
 * Created by vsubrama on 12/28/15.
 */

class TransactionAdapter extends  RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private final List myDataset;
    private int isIncome;

    static class TransactionViewHolder extends RecyclerView.ViewHolder {
        TextView amountDescription ;
        TextView amount;
        public TransactionViewHolder(View itemView) {
            super(itemView);
            this.amount = (TextView) itemView.findViewById(R.id.transactionAmountTextView);
            this.amountDescription = (TextView) itemView.findViewById(R.id.transactionDescTextView);
        }

    }

    public TransactionAdapter(List myDataset) {
        this.myDataset = myDataset;
    }

    @Override
    public TransactionAdapter.TransactionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.isIncome = parent.getResources().getInteger(R.integer.income_true);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_content, parent, false);
        TransactionAdapter.TransactionViewHolder viewHolder = new TransactionViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(TransactionAdapter.TransactionViewHolder holder, int position) {
        FinanceTransaction financeTransaction = (FinanceTransaction) myDataset.get(position);
        int isIncome = financeTransaction.getIsIncome();
        holder.amountDescription.setText(financeTransaction.getTransactionSubType());
        if (isIncome == this.isIncome) {
            holder.amountDescription.setTextColor(Color.GREEN);
        } else {
            holder.amountDescription.setTextColor(Color.MAGENTA);
        }
        holder.amount.setText(financeTransaction.getAmount());
    }

    @Override
    public int getItemCount() {
        return myDataset.size();
    }
}