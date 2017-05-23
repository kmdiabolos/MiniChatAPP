package com.example.test2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;



/**
 * Created by 이규민 on 2017-05-18.
 */

public class LoginPage extends AppCompatActivity {

    private Button sign_in,sign_up,idFind,pwFind,connect;
    private EditText idField,pwField,server,Port,Nick;
    private LinearLayout server_page,login_page;
    String idValue,pwValue,ip,nk;
    int pt;
    Client client = new Client();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Nick = (EditText) findViewById(R.id.Nick);
        Port = (EditText)findViewById(R.id.port);
        server=(EditText)findViewById(R.id.server);
        idField = (EditText) findViewById(R.id.idField);
        pwField = (EditText) findViewById(R.id.pwField);
        sign_in = (Button) findViewById(R.id.sign_in);
        sign_up = (Button) findViewById(R.id.sign_up);
        idFind = (Button) findViewById(R.id.idFind);
        pwFind = (Button) findViewById(R.id.pwFind);
        connect = (Button) findViewById(R.id.connect);

        idValue = idField.getText().toString();
        pwValue = pwField.getText().toString();

        server_page = (LinearLayout) findViewById(R.id.server_page);
        login_page = (LinearLayout) findViewById(R.id.login_page);

        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 아이디와 비밀번호가 맞으면을 체크해야되지만 구현하지못하여 바로 넘어간다.
                login_page.setVisibility(View.GONE);
                server_page.setVisibility(View.VISIBLE);








            }
        });
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nk = Nick.getText().toString();
                ip = server.getText().toString();
                pt = Integer.parseInt(""+Port.getText());

                client.start(pt,ip,nk);

//                Intent intent = new Intent(LoginPage.this, MainActivity.class);
  //              startActivity(intent);
            }
        });



        idFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "아직 개발중입니다.", Toast.LENGTH_LONG).show();
            }
        });
        pwFind.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(getBaseContext(), "아직 개발중입니다.", Toast.LENGTH_LONG).show();
            }
        });
        sign_up.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Toast.makeText(getBaseContext(), "아직 개발중입니다.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
