package com.example.finances.data;

public class Expense {
    String category;
    String expense;
    String amount;
    String date;

    public Expense(String category, String expense, String amount, String date) {
        this.category = category;
        this.expense = expense;
        this.amount = amount;
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getExpense() {
        return expense;
    }

    public void setExpense(String expense) {
        this.expense = expense;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
