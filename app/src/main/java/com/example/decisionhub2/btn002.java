package com.example.decisionhub2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;

public class btn002 extends AppCompatActivity {
    EditText income,cardType;
    Button btnIncome,back;

    DBhelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btn002);

        income=(EditText) findViewById(R.id.editTextIncomeType);
        cardType=(EditText) findViewById(R.id.editTextCardIncome);
        btnIncome=(Button) findViewById(R.id.buttonIncomeType);
        back=(Button) findViewById(R.id.back2);
        myDB=new DBhelper(this);

        btnIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String incomeType = income.getText().toString().trim();
                String newCardType = cardType.getText().toString().trim();

                if (!incomeType.isEmpty() && !newCardType.isEmpty()) {
                    myDB.updateCardType(incomeType, newCardType);
                    Toast.makeText(btn002.this, "Card Type Updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(btn002.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}