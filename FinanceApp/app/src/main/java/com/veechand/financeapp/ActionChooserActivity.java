package com.veechand.financeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ActionChooserActivity extends AppCompatActivity implements View.OnClickListener {

    Button viewTransaction;
    Button addTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_chooser);

        viewTransaction = (Button) findViewById(R.id.viewTransactionButton);
        addTransaction = (Button) findViewById(R.id.addTransactionButton);

        viewTransaction.setOnClickListener(this);
        addTransaction.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.viewTransactionButton:
                break;
            case R.id.addTransactionButton:
                Intent add_transaction_intent = new Intent(ActionChooserActivity.this,AddTransactionActivity.class);
                startActivity(add_transaction_intent);
                break;
        }
    }
}
