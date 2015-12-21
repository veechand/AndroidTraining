package com.veechand.intentsexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ExplicitIntent extends AppCompatActivity {

    Button doneButton;
    EditText updatedPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_explicit_intent);

        doneButton = (Button) findViewById(R.id.buttonDone);
        updatedPhoneNumber = (EditText) findViewById(R.id.editTextUpdatedPhone);

        final Intent intent = getIntent();
        String phonenumber = intent.getStringExtra("phonenumber");
        if (phonenumber != null &&  !phonenumber.isEmpty()){
            updatedPhoneNumber.setText(phonenumber);
        }
        //Toast.makeText(ExplicitIntent.this, phonenumber, Toast.LENGTH_LONG).show();
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = String.valueOf(updatedPhoneNumber.getText());
                if (phoneNumber == null || phoneNumber.isEmpty()){
                    Toast.makeText(ExplicitIntent.this, "Not a valid number", Toast.LENGTH_LONG).show();
                } else {
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(getResources().getString(R.string.updated_phone_number), phoneNumber);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            }
        });
    }
}
