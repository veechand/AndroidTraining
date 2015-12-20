package com.veechand.loginview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button submitButton;
    Button cancelButton;
    EditText usernameEditText;
    EditText passwordEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        submitButton = (Button) findViewById(R.id.buttonSubmit);
        cancelButton = (Button) findViewById(R.id.buttonCancel);
        usernameEditText = (EditText) findViewById(R.id.editTextUsername);
        passwordEditText = (EditText) findViewById(R.id.editTextPassword);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = String.valueOf(usernameEditText.getText());
                String password = String.valueOf(passwordEditText.getText());
                Log.i("MainActivity",userName + " " + password);
                if (userName.equals(password)){
                    Toast.makeText(MainActivity.this,R.string.login_successful,Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this,R.string.login_failed,Toast.LENGTH_LONG).show();
                }

            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernameEditText.setText("");
                passwordEditText.setText("");
            }
        });
    }
}
