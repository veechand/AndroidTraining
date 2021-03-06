package com.veechand.financeapp.db;

/**
 * Created by vsubrama on 12/25/15.
 */
/*
 * TODO(veechand) Need to add date to the transaction
 * Need a transaction description
 */
public class FinanceTransaction {

    private String amount;
    private long transactionSubTypeId;
    private long userId;
    private int isIncome;
    private String transactionSubType;

    public FinanceTransaction(String amount, long transactionSubTypeId, long userId, int isIncome) {
        this.amount = amount;
        this.transactionSubTypeId = transactionSubTypeId;
        this.userId = userId;
        this.isIncome = isIncome;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public long getTransactionSubTypeId() {
        return transactionSubTypeId;
    }

    public void setTransactionSubTypeId(int transactionSubTypeId) {
        this.transactionSubTypeId = transactionSubTypeId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getIsIncome() {
        return isIncome;
    }

    public void setIsIncome(int isIncome) {
        this.isIncome = isIncome;
    }


    public String getTransactionSubType() {
        return transactionSubType;
    }

    public void setTransactionSubType(String transactionSubType) {
        this.transactionSubType = transactionSubType;
    }


}
