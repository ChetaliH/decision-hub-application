package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class btn003 extends AppCompatActivity {
    EditText cardCat,cardInt;

    Button update3,back;

    DBhelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btn003);
        cardCat=(EditText) findViewById(R.id.editTextCardType);
        cardInt=(EditText) findViewById(R.id.editTextCardInterst);
        update3=(Button) findViewById(R.id.buttonCardType);
        back=(Button) findViewById(R.id.back3);
        myDb=new DBhelper(this);
        //myDb.insertInitialData();
        update3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateInterestRate();
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
    private void updateInterestRate() {
        String cardType = cardCat.getText().toString().trim();
        String newInterestRateStr = cardInt.getText().toString().trim();

        if (cardType.isEmpty() || newInterestRateStr.isEmpty()) {
            Toast.makeText(btn003.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double newInterestRate = Double.parseDouble(newInterestRateStr);
        myDb.updateInterestRate("CT002", cardType, newInterestRate);

        Toast.makeText(btn003.this, "Interest rate updated successfully", Toast.LENGTH_SHORT).show();
    }

}