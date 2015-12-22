package com.veechand.storageexample;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.SimpleExpandableListAdapter;

public class ListActivity extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        listView = (ListView) findViewById(R.id.listView);
        DBHelper dbHelper = new DBHelper(ListActivity.this);
        Cursor c = dbHelper.readFromDB();
        /*String[] from = {"name","phonenumber"};
        int[] to = {android.R.id.text1,android.R.id.text2};*/
        //SimpleCursorAdapter sca = new SimpleCursorAdapter(ListActivity.this, android.R.layout.simple_list_item_2, c, from, to, 0);
        String[] from = {"_id","name","phonenumber"};
        int[] to = {R.id.editTextId,R.id.editTextName,R.id.editTextPhoneNumber};
        //SimpleCursorAdapter sca = new SimpleCursorAdapter(ListActivity.this, R.layout.list_item, c, from, to, 0);
        MyAdapter adapater = new MyAdapter(ListActivity.this,c,0);
        listView.setAdapter(adapater);
    }
    class MyAdapter extends CursorAdapter {
        public MyAdapter(Context context, Cursor c, int flags) {
            super(context, c, flags);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            LayoutInflater li = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = li.inflate(R.layout.list_item, parent, false);
            return view;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            EditText id = (EditText) view.findViewById(R.id.editTextId);
            EditText name = (EditText) view.findViewById(R.id.editTextName);
            EditText phoneNumber = (EditText) view.findViewById(R.id.editTextPhoneNumber);

            id.setText(cursor.getString(0));
            name.setText(cursor.getString(1));
            name.setTextColor(Color.BLUE);
            phoneNumber.setText(cursor.getString(2));
        }
    }
}
