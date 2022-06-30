package com.example.finances.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finances.R;
import com.example.finances.adapters.ExpenseRecyclerViewAdapter;
import com.example.finances.data.FinancesDatabaseHelper;

public class MainActivity extends AppCompatActivity {

    Button setBudgetButton;
    EditText setBudgetField;
    TextView expenseTextView;
    TextView budgetTextView;
    ImageView editBudgetImageView;
    FloatingActionButton addExpenseButton;
    Double budget = 0d;
    Double expense = 0d;
    RecyclerView expenseList;
    FinancesDatabaseHelper dbHelper;
    ExpenseRecyclerViewAdapter expenseListAdapter;
    ProgressBar expenseProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setBudgetButton = findViewById(R.id.setBudgetButton);
        setBudgetField = findViewById(R.id.setBudgetField);
        expenseTextView = findViewById(R.id.expenseTextView);
        budgetTextView = findViewById(R.id.budgetTextView);
        editBudgetImageView = findViewById(R.id.editBudgetImageView);
        addExpenseButton = findViewById(R.id.addExpenseButton);
        expenseList = findViewById(R.id.expensesList);
        dbHelper = new FinancesDatabaseHelper(this);
        expenseProgressBar = findViewById(R.id.expenseProgressBar);

        setInitialState();
        setUpExpenseList();

        setBudgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEditMode();
            }
        });

        setBudgetField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (((event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER))
                        || (actionId == EditorInfo.IME_ACTION_DONE))
                        && !setBudgetField.getText().toString().isEmpty()) {
                    double budget = Double.parseDouble(setBudgetField.getText().toString());
                    if (budget < 1000) {
                        Toast.makeText(getApplicationContext(), "Budget cannot be less than 1000", Toast.LENGTH_SHORT).show();
                    } else {
                        if (dbHelper.storeBudget(budget)) {
                            Toast.makeText(getApplicationContext(), "Your budget has been set to "+budget, Toast.LENGTH_SHORT).show();
                            applyBudget(budget);
                            refreshExpenses();
                        } else {
                            Toast.makeText(MainActivity.this, "Unable to save budget", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                return false;
            }
        });

        editBudgetImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEditMode();
            }
        });

        addExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddExpenseActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshExpenses();

    }

    private void setInitialState() {
        budget = dbHelper.getBudget();
        if(budget>0){
            applyBudget(budget);
        }
        addExpenseButton.setEnabled(budget!=0);
    }

    public void setEditMode(){
        setBudgetButton.setVisibility(View.INVISIBLE);
        editBudgetImageView.setVisibility(View.INVISIBLE);
        setBudgetField.setVisibility(View.VISIBLE);
        budgetTextView.setVisibility(View.INVISIBLE);
        addExpenseButton.setEnabled(false);
    }

    public void applyBudget(Double budget){
        budgetTextView.setVisibility(View.VISIBLE);
        budgetTextView.setText("" + budget);
        setBudgetField.setVisibility(View.INVISIBLE);
        editBudgetImageView.setVisibility(View.VISIBLE);
        addExpenseButton.setEnabled(true);
        setBudgetButton.setVisibility(View.INVISIBLE);
        MainActivity.this.budget = budget;
    }

    public void setUpExpenseList(){
        expenseListAdapter = new ExpenseRecyclerViewAdapter(dbHelper.getExpenses());
        expenseList.setLayoutManager(new LinearLayoutManager(this));
        expenseList.setAdapter(expenseListAdapter);

    }

    public void refreshExpenses(){
        expenseListAdapter.setExpenses(dbHelper.getExpenses());
        expense = dbHelper.getTotalExpenses();
        expenseTextView.setText("₹"+expense);
        if(budget!=0) {
            int progressPercentage = (int) ((expense / budget) * 100);
            expenseProgressBar.setProgress(progressPercentage);
        }
    }
}