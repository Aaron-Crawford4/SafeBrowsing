package com.safe_browsing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView username =(TextView) findViewById(R.id.username);
        TextView password =(TextView) findViewById(R.id.password);
        TextView ip_number =(TextView) findViewById(R.id.ip_number);

        MaterialButton login_btn = (MaterialButton) findViewById(R.id.login_btn);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(username.getText().toString().equals("") || password.getText().toString().equals("") || ip_number.getText().toString().equals("")){
                    //empty text box, don't continue
                    Toast.makeText(MainActivity.this, "Fill In All Information Boxes", Toast.LENGTH_SHORT).show();
                }
                else {
                    //no empty text box, continue
                    //Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    sign_in();
                }
            }
        });
    }
    public void sign_in (){
        Intent intent = new Intent(this, Activity2.class);
        startActivity(intent);
    }
}