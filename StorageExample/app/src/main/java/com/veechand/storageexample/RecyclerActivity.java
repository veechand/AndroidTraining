package com.veechand.storageexample;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RecyclerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        ArrayList<Item> itemList = new ArrayList<Item>();
        DBHelper helper = new DBHelper(RecyclerActivity.this);
        Cursor cursor = helper.readFromDB();
        while(cursor.moveToNext()){
            Item item = new Item();
            item.setId(cursor.getLong(0));
            item.setName(cursor.getString(1));
            item.setPhone(cursor.getString(2));
            itemList.add(item);
        }
        MyAdapter adapter = new MyAdapter(itemList);
        RecyclerView viewById = (RecyclerView) findViewById(R.id.recyclerViewId);
        viewById.setAdapter(adapter);
        viewById.setLayoutManager(new GridLayoutManager(RecyclerActivity.this,2));

    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        private final ArrayList<Item> mitemList;

        public MyAdapter(ArrayList<Item> itemList){
            mitemList = itemList;
        }
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutService = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = layoutService.inflate(R.layout.list_item,parent,false);
            MyViewHolder viewHolder = new MyViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            Item item = mitemList.get(position);
            holder.id.setText(String.valueOf(item.getId()));
            holder.name.setText(item.getName());
            holder.phoneNumber.setText(item.getPhone());
        }

        @Override
        public int getItemCount() {
            return mitemList.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        EditText id;
        EditText name;
        EditText phoneNumber;

        public MyViewHolder(View view) {
            super(view);
            id = (EditText) view.findViewById(R.id.editTextId);
            name = (EditText) view.findViewById(R.id.editTextName);
            phoneNumber = (EditText) view.findViewById(R.id.editTextPhoneNumber);
        }

    }
}
