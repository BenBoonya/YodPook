package com.yodpook.android.models;

/**
 * Created by Boonya Kitpitak on 4/8/17.
 */

public class SavingAmount {
    private String amount;
    private String userDisplay;
    private String paymentType;

    public SavingAmount() {

    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUserDisplay() {
        return userDisplay;
    }

    public void setUserDisplay(String userDisplay) {
        this.userDisplay = userDisplay;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
}
