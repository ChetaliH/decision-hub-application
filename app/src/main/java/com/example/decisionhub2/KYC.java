package com.example.decisionhub2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;

public class KYC extends AppCompatActivity {
    EditText age,pan,cibil,national;
    Button update,back;
    DBhelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kyc);

        age=(EditText) findViewById(R.id.editTextAge);
        pan=(EditText) findViewById(R.id.editTextPan);
        cibil=(EditText) findViewById(R.id.editTextCibil);
        national=(EditText) findViewById(R.id.editTextNationality);
        update=(Button) findViewById(R.id.buttonUpdateKYC);
        back=(Button) findViewById(R.id.back6);
        myDB=new DBhelper(this);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertKYCData();
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

    private void insertKYCData() {
        String strAge = age.getText().toString().trim();
        String strPan = pan.getText().toString().trim();
        String strCibil = cibil.getText().toString().trim();
        String strNational = national.getText().toString().trim();

        if (strAge.isEmpty() || strPan.isEmpty() || strCibil.isEmpty() || strNational.isEmpty()) {
            Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int intAge = Integer.parseInt(strAge);
        long longPan = Long.parseLong(strPan);
        int intCibil = Integer.parseInt(strCibil);


        boolean isInserted = myDB.insertKYCData(intAge, longPan, intCibil, strNational);

        if (isInserted) {
            Toast.makeText(this, "KYC data inserted successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to insert KYC data", Toast.LENGTH_SHORT).show();
        }
    }
}


