package com.veechand.storageexample;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button fileSaveButton;
    Button fileReadButton;
    Button preferencesReadButton;
    Button preferencesSaveButton;

    Button buttonStore;
    Button buttonRead;
    Button buttonDelete;
    Button buttonUpdate;
    Button buttonListItem;
    Button buttonRecycleView;

    EditText textValue;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    com.veechand.storageexample.DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fileReadButton = (Button) findViewById(R.id.buttonFileRead);
        fileSaveButton = (Button) findViewById(R.id.buttonFileStore);
        preferencesReadButton = (Button) findViewById(R.id.buttonPreferencesRead);
        preferencesSaveButton = (Button) findViewById(R.id.buttonPreferencesStore);
        buttonStore = (Button) findViewById(R.id.buttonStore);
        buttonRead = (Button) findViewById(R.id.buttonRead);
        buttonDelete = (Button) findViewById(R.id.buttonDelete);
        buttonUpdate = (Button) findViewById(R.id.buttonUpdate);
        buttonListItem = (Button) findViewById(R.id.buttonListItem);
        buttonRecycleView = (Button) findViewById(R.id.buttonRecycleView);

        textValue = (EditText) findViewById(R.id.editText);
        pref = getSharedPreferences("sharedlinkedin", MODE_PRIVATE);
        editor = pref.edit();

        dbHelper = new DBHelper(MainActivity.this);


        fileReadButton.setOnClickListener(this);
        fileSaveButton.setOnClickListener(this);
        preferencesReadButton.setOnClickListener(this);
        preferencesSaveButton.setOnClickListener(this);
        buttonStore.setOnClickListener(this);
        buttonRead.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
        buttonUpdate.setOnClickListener(this);
        buttonListItem.setOnClickListener(this);
        buttonRecycleView.setOnClickListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        String textValue = this.textValue.getText().toString();
        String name = textValue.split(":")[0];
        String phoneNumber = textValue.split(":")[1];

        switch (v.getId()){
            case R.id.buttonFileStore:
                Log.d("Storage","Stored");
                try {
                    FileOutputStream fos = openFileOutput("veera", MODE_PRIVATE);
                    OutputStreamWriter ows = new OutputStreamWriter(fos);
                    BufferedWriter bw = new BufferedWriter(ows);
                    bw.write(textValue);
                    bw.flush();
                    bw.close();
                    fos.close();;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.buttonFileRead:
                Log.i("Storage","Read");
                try {
                    FileInputStream fis = openFileInput("veera");
                    BufferedReader bw = new BufferedReader(new InputStreamReader(fis));
                    Toast.makeText(MainActivity.this,bw.readLine(),Toast.LENGTH_LONG).show();
                    bw.close();
                    fis.close();;
                }  catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.buttonPreferencesRead:
                String value = pref.getString("textValue","");
                Toast.makeText(MainActivity.this,value,Toast.LENGTH_LONG).show();
                break;
            case R.id.buttonPreferencesStore:
                editor.putString("textValue",textValue);
                editor.commit();
                break;
            case R.id.buttonRead:
                Cursor cursor = dbHelper.readFromDB();
                while(cursor.moveToNext()){
                    String _name = cursor.getString(1);
                    String _phone = cursor.getString(2);
                    Toast.makeText(MainActivity.this,_name+"=="+_phone,Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.buttonDelete:
                long l1 = dbHelper.deleteDB(name, phoneNumber);
                if (l1 > 0){
                    Toast.makeText(MainActivity.this,"Delete Successful",Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.buttonStore:
                long l = dbHelper.insertInToDB(name, phoneNumber);
                if (l > 0){
                    Toast.makeText(MainActivity.this,"Successful",Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.buttonUpdate:
                dbHelper.updateDB(name,phoneNumber);
                break;
            case R.id.buttonListItem:
                Intent intent = new Intent(MainActivity.this,ListActivity.class);
                startActivity(intent);
                break;
            case R.id.buttonRecycleView:
                Intent recycle_intent = new Intent(MainActivity.this,RecyclerActivity.class);
                startActivity(recycle_intent);
                break;

        }
    }
}
