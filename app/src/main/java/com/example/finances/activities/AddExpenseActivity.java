package com.example.finances.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.finances.R;
import com.example.finances.data.FinancesDatabaseHelper;

public class AddExpenseActivity extends AppCompatActivity {

    Spinner categorySelector;
    Button saveButton;
    EditText titleField;
    EditText amountField;
    FinancesDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        setuptoolbar();

        categorySelector = findViewById(R.id.categorySelector);
        saveButton = findViewById(R.id.saveButton);
        titleField = findViewById(R.id.titleField);
        amountField = findViewById(R.id.amountField);
        dbHelper = new FinancesDatabaseHelper(this);

        ArrayAdapter<CharSequence> categoryAdapter =
                ArrayAdapter.createFromResource(
                        this,
                        R.array.category_array,
                        android.R.layout.simple_spinner_dropdown_item
                );
        categorySelector.setAdapter(categoryAdapter);

        saveButton.setOnClickListener(v -> {
            String category= categorySelector.getSelectedItem().toString();
            String title = titleField.getText().toString();
            String amount = amountField.getText().toString();
            if (category.equals("Category") ||title.isEmpty()||amount.isEmpty()) {
                Toast.makeText(
                        getApplicationContext(),
                        "The above fields cannot be empty",
                        Toast.LENGTH_SHORT
                ).show();
            } else {
                boolean isInserted = dbHelper.insertExpenses(
                        "1/09/2022",
                        category,
                        title,
                        Double.parseDouble(amount)
                );

                if(isInserted){
                    Toast.makeText(getApplicationContext(),
                            "Your expenses have been saved",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),
                            "Unable to save expenses",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void setuptoolbar(){
        getSupportActionBar().setTitle("Add Expense");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}