package com.example.test2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by 이규민 on 2017-05-18.
 */

public class LoginPage extends AppCompatActivity {

    private Button sign_in,sign_up,idFind,pwFind;
    private EditText idField,pwField;
    String idValue,pwValue;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        idField = (EditText) findViewById(R.id.idField);
        pwField = (EditText) findViewById(R.id.pwField);
        sign_in = (Button) findViewById(R.id.sign_in);
        sign_up = (Button) findViewById(R.id.sign_up);
        idFind = (Button) findViewById(R.id.idFind);
        pwFind = (Button) findViewById(R.id.pwFind);

        idValue = idField.getText().toString();
        pwValue = pwField.getText().toString();

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        idFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

        pwFind.setOnClickListener();


    }
}
