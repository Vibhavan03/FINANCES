package com.example.finances.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

public class FinancesDatabaseHelper extends SQLiteOpenHelper {

    public FinancesDatabaseHelper(@Nullable Context context) {
        super(context, "Finances.db",null , 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Expenses(" +
                "SrNo integer PRIMARY KEY AUTOINCREMENT, " +
                "Date Date, " +
                "Category Text," +
                " Expense Text, " +
                "Amount Double)");

        db.execSQL("CREATE TABLE Budgets(" +
                "Month Text, " +
                "Year integer, " +
                "Budget Double)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertExpenses(String date, String category, String expense, Double amount){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Date", date);
        contentValues.put("Category", category);
        contentValues.put("Expense",expense);
        contentValues.put("Amount",amount);
        long result = db.insert("Expenses",null,contentValues);
        return result!=-1;
    }

    @SuppressLint("Range")
    public ArrayList<Expense> getExpenses(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Expenses", null);
        ArrayList<Expense> expenseArrayList = new ArrayList<Expense>();
        while (cursor.moveToNext()){
            String expense = cursor.getString(cursor.getColumnIndex("Expense"));
            String date = cursor.getString(cursor.getColumnIndex("Date"));
            Double amount = cursor.getDouble(cursor.getColumnIndex("Amount"));
            String category = cursor.getString(cursor.getColumnIndex("Category"));
            Expense expenseData = new Expense(category,expense,amount.toString(),date);
            expenseArrayList.add(expenseData);
        }
        cursor.close();
        return expenseArrayList;
    }

    public double getTotalExpenses(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT sum(Amount)  FROM Expenses",null);
        if(!cursor.moveToFirst()) {
            return 0;
        }
        double totalExpenses = cursor.getDouble(0);
        cursor.close();
        return totalExpenses;
    }

    public double getBudget(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT Budget FROM Budgets",null);
        if(!cursor.moveToFirst()){
            Log.e("FinancesDatabaseHelper","Unable to fetch budget.");
            return 0;
        }
        double budget = cursor.getDouble(0);
        Log.e("FinancesDatabaseHelper","budget is" + budget);
        cursor.close();
        return budget;
    }

    public boolean storeBudget(double budget){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Budget",budget);
        contentValues.put("Month","June");
        contentValues.put("Year",2022);
        Cursor cursor = db.rawQuery("SELECT * FROM Budgets WHERE Month = 'June' AND Year = 2022", null);

        long result;
        if(cursor.getCount()>0){
            result = db.update("Budgets",contentValues,"Month = 'June' AND Year = 2022",null);
        } else{
            result = db.insert("Budgets",null,contentValues);
        }
        cursor.close();
        return result!=-1;
    }

}
