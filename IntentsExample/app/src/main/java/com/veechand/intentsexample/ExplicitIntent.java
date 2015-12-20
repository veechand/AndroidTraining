package com.veechand.intentsexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class ExplicitIntent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explicit_intent);

        Intent intent = getIntent();

        String phonenumber = intent.getStringExtra("phonenumber");

        Toast.makeText(ExplicitIntent.this,phonenumber,Toast.LENGTH_LONG).show();
    }
}
