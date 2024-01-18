package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    EditText username,password;
    Button btnlogin,back;
    DBhelper myDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username=(EditText) findViewById(R.id.signinusername);
        password=(EditText) findViewById(R.id.signinpassword);
        btnlogin=(Button) findViewById(R.id.signinlogin);
        back=(Button) findViewById(R.id.signinback);
        myDB=new DBhelper(this);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user=username.getText().toString();
                String pass=password.getText().toString();

                if(user.equals("")||pass.equals("")){
                    Toast.makeText(Login.this, "Enter Credentials", Toast.LENGTH_SHORT).show();
                }
                else{
                   Boolean result= myDB.checkUsernamePassword(user,pass);
                   if(result==true){
                        Intent intent=new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                   }
                   else{
                       Toast.makeText(Login.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                   }
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