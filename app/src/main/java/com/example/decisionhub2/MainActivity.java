package com.example.decisionhub2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;

public class MainActivity extends AppCompatActivity {
    EditText username,password;
    Button register,signin;

    DBhelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username=(EditText) findViewById(R.id.username);
        password=(EditText) findViewById(R.id.password);

        register=(Button) findViewById(R.id.btnregister);
        signin=(Button) findViewById(R.id.btneu);

        myDB=new DBhelper(this);
        SQLiteDatabase database=myDB.getReadableDatabase();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user=username.getText().toString();
                String pass=password.getText().toString();

                if(user.equals("")||pass.equals("")){
                    Toast.makeText(MainActivity.this,"Fill all the fields",Toast.LENGTH_SHORT).show();
                }
                else{
                    Boolean userCheckResult=myDB.checkUsername(user);
                    if(userCheckResult==false){
                        Boolean regResult=myDB.insertData(user,pass);
                        if(regResult==true){
                            Toast.makeText(MainActivity.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getApplicationContext(),Login.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(MainActivity.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(MainActivity.this,"User already exists\n Please Sign in",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
            }
        });
    }
}