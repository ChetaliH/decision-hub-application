package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Testing extends AppCompatActivity {
    EditText ruleid;
    Button test,back;

    TextView conditionsTextView;

    DBhelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);

        test=(Button) findViewById(R.id.btntest);
        back=(Button) findViewById(R.id.btntestback);
        ruleid=(EditText) findViewById(R.id.ruleid);
        myDB=new DBhelper(this);
        conditionsTextView=(TextView) findViewById(R.id.conditionsTextView);

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String RuleId = ruleid.getText().toString().trim();


                if (RuleId.equals("IR001")) {
                    ArrayList<RulesModal> rulesList = myDB.fetchRules(RuleId);
                    if(!rulesList.isEmpty()) {
                        StringBuilder conditionsStringBuilder = new StringBuilder();
                        for (RulesModal rule : rulesList) {
                            conditionsStringBuilder.append(rule.conditions).append("\n");
                        }
                        conditionsTextView.setText(conditionsStringBuilder.toString());
                        Log.d("TestingDebug", "Fetched Conditions:\n" + conditionsStringBuilder.toString());
                    }else {
                        conditionsTextView.setText("No conditions found for the entered rule ID");
                    }

                }
                else if (RuleId.equals("CT002")) {
                    double interestRate1= myDB.fetchInterestRate("CT002", "Regular");
                    double interestRate2= myDB.fetchInterestRate("CT002", "Super premium");
                    double interestRate3= myDB.fetchInterestRate("CT002", "Premium");
                    double interestRate4= myDB.fetchInterestRate("CT002", "Business");
                    // Display interest rate in the EditText
                    conditionsTextView.setText("Regular: "+interestRate1+"\n Super premium: "+interestRate2+"\nPremium: "+interestRate3+"\nBusiness: "+interestRate4);

                }
                else if(RuleId.equals("CT001")){
                    conditionsTextView.setText("\n35000: "+ myDB.fetchCardTypeForIncome(35000)+"\n60000: "+myDB.fetchCardTypeForIncome(60000)+"\n100000: "+myDB.fetchCardTypeForIncome(100000)+"\n200000: "+myDB.fetchCardTypeForIncome(200000));
                }
                else{
                    Toast.makeText(Testing.this, "Please enter a rule ID", Toast.LENGTH_SHORT).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

    }
}