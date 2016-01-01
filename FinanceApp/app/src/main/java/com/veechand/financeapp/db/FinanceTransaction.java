package com.veechand.financeapp.db;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static java.util.Calendar.getInstance;

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
    private int year;
    private int month;
    private int date;
    private String cDate;

    private final Calendar calendar = getInstance();

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yy");

    public FinanceTransaction(String amount, long transactionSubTypeId, long userId, int isIncome, int year, int month, int date) {
        this.amount = amount;
        this.transactionSubTypeId = transactionSubTypeId;
        this.userId = userId;
        this.isIncome = isIncome;
        this.year = year;
        this.month = month;
        this.date = date;
        calendar.set(year,month,date);
        cDate = DATE_FORMAT.format(calendar.getTime());
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
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getcDate() {
        return cDate;
    }

    public void setcDate(String cDate) {
        this.cDate = cDate;
    }
}
