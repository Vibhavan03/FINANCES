package com.example.finances.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finances.R;
import com.example.finances.data.Expense;

public class ExpenseViewHolder extends RecyclerView.ViewHolder {
    public ExpenseViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void setExpense(Expense expense){
        TextView expenseItemTextView = itemView.findViewById(R.id.expenseItemTextView);
        ImageView categoryIcon = itemView.findViewById(R.id.categoryIcon);
        TextView amountItemTextView = itemView.findViewById(R.id.amountItemTextView);
        TextView dateItemTextView = itemView.findViewById(R.id.dateItemTextView);
        expenseItemTextView.setText(expense.getExpense());
        amountItemTextView.setText(expense.getAmount());
        dateItemTextView.setText(expense.getDate());

        int drawableId = 0;

        switch (expense.getCategory()) {
            case "Bills" : {
                drawableId = R.drawable.ic_bills_2_24;
                break;
            }
            case "Entertainment":{
                drawableId = R.drawable.ic_entertainment_24;
                break;
            }
            case "Transport":{
                drawableId = R.drawable.ic_transport_24;
                break;
            }
            case "Groceries":{
                drawableId = R.drawable.ic_groceries_24;
                break;
            }
            case "Shopping":{
                drawableId = R.drawable.ic_shopping_24;
                break;
            }
            case "Food":{
                drawableId = R.drawable.ic_food_24;
                break;
            }
            case "Travel":{
                drawableId = R.drawable.ic_travel_24;
                break;
            }
            case "Others":{
                drawableId = R.drawable.ic_others_24;
                break;
            }
            case "Stationary":{
                drawableId = R.drawable.ic_stationary_24;
            }
        }
        categoryIcon.setImageDrawable(itemView.getResources()
                .getDrawable(drawableId));
    }
}
