package com.example.decisionhub2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.R;

public class btn004 extends AppCompatActivity {
    EditText fullAmt,no_of_days_1,no_of_days_2,outstandingAmount;

    TextView resultTextView;
    Button totalInterest,back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btn004);
        fullAmt = (EditText) findViewById(R.id.editTextNumber2);
        no_of_days_1 = (EditText) findViewById(R.id.editTextNumber);
        no_of_days_2 = (EditText) findViewById(R.id.editTextNumber3);
        outstandingAmount = (EditText) findViewById(R.id.editTextNumber4);
        resultTextView = (TextView) findViewById(R.id.textView5);
        totalInterest=(Button) findViewById(R.id.buttonInterest);
        back=(Button) findViewById(R.id.back4);

        totalInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateInterestRate();
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

    public void calculateInterestRate(){
            try {
                double fullAmount = Double.parseDouble(fullAmt.getText().toString());
                int numberOfDays1 = Integer.parseInt(no_of_days_1.getText().toString());
                int numberOfDays2 = Integer.parseInt(no_of_days_2.getText().toString());
                double outstandingAmt = Double.parseDouble(outstandingAmount.getText().toString());

                double interestRate = ((outstandingAmt - fullAmount) / fullAmount) * (365.0 / (numberOfDays1 + numberOfDays2));


                resultTextView.setText(String.format("Interest Rate: %.2f%%", interestRate * 100));
            }catch (NumberFormatException e) {

                resultTextView.setText("Invalid input. Please enter numeric values.");
            }
    }

}