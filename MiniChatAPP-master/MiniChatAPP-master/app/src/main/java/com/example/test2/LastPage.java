package com.example.test2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by 이규민 on 2017-05-25.
 */

public class LastPage extends AppCompatActivity {

    private Button home, send;
    private EditText require;
    String test;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lastpage);


        home = (Button) findViewById(R.id.last_homebt);
        send = (Button) findViewById(R.id.last_sendbtn);
        require = (EditText) findViewById(R.id.last_require);

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LastPage.this, LoginPage.class);
                startActivity(intent);
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                test = require.getText().toString();
                if (test == null) {

                }
                else{
                    require.setText("");
                    Toast.makeText(getBaseContext(), "감사합니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}