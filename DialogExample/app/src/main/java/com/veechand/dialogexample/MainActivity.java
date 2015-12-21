package com.veechand.dialogexample;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
//import android.widget.ListView;
import android.widget.DatePicker;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button dialogButton;
    AlertDialog.Builder dialog;
    DatePickerDialog datePickerDialog;
    Button datePickerButton;
    String[] str;
    int currentSelected = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        str = getResources().getStringArray(R.array.company_name);
        datePickerButton = (Button) findViewById(R.id.datePickerButton);

        dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("Example Dialog");
        dialog.setSingleChoiceItems(str, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, " " + which, Toast.LENGTH_LONG).show();
                ;
                currentSelected = which;

            }
        });
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Dialog d = (AlertDialog) dialog;
                /*
                    ListView lw = ((AlertDialog)dialog).getListView();
                    Object checkedItem = lw.getAdapter().getItem(lw.getCheckedItemPosition());
                  */
                String checkedItem = str[currentSelected];
                Toast.makeText(MainActivity.this, "Cool !! " + String.valueOf(checkedItem), Toast.LENGTH_LONG).show();
                ;

            }
        });

        dialogButton = (Button) findViewById(R.id.dialogButton);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });

        datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Toast.makeText(MainActivity.this, String.valueOf(year) + String.valueOf(monthOfYear) + String.valueOf(dayOfMonth)  , Toast.LENGTH_LONG).show();
            }
        },2015,12,20);

        datePickerDialog.setTitle("Pick a date");


        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

    }
}
