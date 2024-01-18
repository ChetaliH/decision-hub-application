package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

public class btn001 extends AppCompatActivity {
    Button back,submit1;

    EditText rName, rDescription, rConditions;

    DBhelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btn001);

        back=(Button)findViewById(R.id.back1);
        submit1=(Button) findViewById(R.id.submit1);

        rName=(EditText) findViewById(R.id.crulename);
        rDescription=(EditText) findViewById(R.id.cruledes);
        rConditions=(EditText) findViewById(R.id.crulecon);

        dbHelper = new DBhelper(this);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });

        submit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newRuleName = rName.getText().toString();
                String newRuleDescription = rDescription.getText().toString();
                String newRuleConditions=rConditions.getText().toString();

                if(newRuleName.equals("")||newRuleDescription.equals("")||newRuleConditions.equals("")) {

                    Toast.makeText(btn001.this, "Enter values", Toast.LENGTH_SHORT).show();
                }
                else{
                    dbHelper.updateRule("IR001", newRuleName, newRuleDescription, newRuleConditions);
                    Toast.makeText(btn001.this, "Rule updated successfully!", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }
}