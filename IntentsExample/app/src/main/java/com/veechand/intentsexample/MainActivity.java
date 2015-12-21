package com.veechand.intentsexample;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button callButton;
    EditText phoneNumberEditText;
    Button localCallButton;
    Button resultButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        callButton = (Button) findViewById(R.id.buttonCall);
        phoneNumberEditText = (EditText) findViewById(R.id.editTextPhoneNumber);
        localCallButton = (Button) findViewById(R.id.buttonLocalCall);
        resultButton = (Button) findViewById(R.id.buttonResultButton);

        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = String.valueOf(phoneNumberEditText.getText());

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                Uri uri = Uri.parse("tel:" + phoneNumber);
                callIntent.setData(uri);

                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(Intent.createChooser(callIntent,"Chooser"));
            }
        });

        localCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = String.valueOf(phoneNumberEditText.getText());
                Intent explicitIntent = new Intent(MainActivity.this,ExplicitIntent.class);
                explicitIntent.putExtra("phonenumber", phoneNumber);
                startActivity(explicitIntent);
            }
        });

        resultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = String.valueOf(phoneNumberEditText.getText());
                Intent explicitIntent = new Intent(MainActivity.this,ExplicitIntent.class);
                explicitIntent.putExtra("phonenumber", phoneNumber);
                int requestCode = getResources().getInteger(R.integer.request_code_1);
                startActivityForResult(explicitIntent,requestCode);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int _requestCode = getResources().getInteger(R.integer.request_code_1);

        if (requestCode == _requestCode && resultCode == RESULT_OK){
            String updatedPhoneNumber = data.getStringExtra(getResources().getString(R.string.updated_phone_number));
            Toast.makeText(MainActivity.this, updatedPhoneNumber, Toast.LENGTH_LONG).show();
        }

        if (requestCode == _requestCode && resultCode == RESULT_CANCELED){
            Toast.makeText(MainActivity.this, "Operation cancelled", Toast.LENGTH_LONG).show();
        }

        if (requestCode == _requestCode && resultCode == RESULT_FIRST_USER){
            Toast.makeText(MainActivity.this, "Some notorious happened !!", Toast.LENGTH_LONG).show();
        }

    }
}
