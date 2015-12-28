package com.veechand.financeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
                Intent viewTransaction = new Intent(ActionChooserActivity.this,ViewTransactionActivity.class);
                startActivity(viewTransaction);
                break;
            case R.id.addTransactionButton:
                Intent addTransactionIntent = new Intent(ActionChooserActivity.this,AddTransactionActivity.class);
                startActivity(addTransactionIntent);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //TODO: Check do we need to show this on all the toolbar
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_transaction_subtype_add:
                Intent intent = new Intent(ActionChooserActivity.this, TransactionSubTypeAdd.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }
}
