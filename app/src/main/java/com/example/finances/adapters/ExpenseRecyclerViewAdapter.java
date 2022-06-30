package com.example.finances.adapters;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finances.R;
import com.example.finances.data.Expense;

import java.util.ArrayList;

public class ExpenseRecyclerViewAdapter extends RecyclerView.Adapter<ExpenseViewHolder>{

    private ArrayList<Expense> expenses;

    public ExpenseRecyclerViewAdapter(ArrayList<Expense> expenses) {
        this.expenses = expenses;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setExpenses(ArrayList<Expense> expenses){
        this.expenses = expenses;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View expenseView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.expense_list_item,viewGroup,false);
        return new ExpenseViewHolder(expenseView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseViewHolder expenseViewHolder, int i) {
        expenseViewHolder.setExpense(expenses.get(i));
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }
}
