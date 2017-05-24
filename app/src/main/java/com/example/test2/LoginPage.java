package com.example.test2;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ObjectOutputStream;
import java.net.Socket;


/**
 * Created by 이규민 on 2017-05-18.
 */

public class LoginPage extends AppCompatActivity{

    private static final boolean def = true;
    private Button sign_in, sign_up, idFind, pwFind, connect;

    private EditText idField, pwField, server, Port, Nick;
    private LinearLayout server_page, login_page;
    public String idValue, pwValue, ipAddress, nick;
    public ObjectOutputStream objectStream = null;
    public boolean isAlived;
    public int port;
    private Socket socket;
    private TextView chat;
    public ObjectOutputStream oos=null;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Nick = (EditText) findViewById(R.id.Nick);
        Port = (EditText) findViewById(R.id.port);
        server = (EditText) findViewById(R.id.server);
        idField = (EditText) findViewById(R.id.idField);
        pwField = (EditText) findViewById(R.id.pwField);
        sign_in = (Button) findViewById(R.id.sign_in);
        sign_up = (Button) findViewById(R.id.sign_up);
        idFind = (Button) findViewById(R.id.idFind);
        pwFind = (Button) findViewById(R.id.pwFind);
        connect = (Button) findViewById(R.id.connect);


        server_page = (LinearLayout) findViewById(R.id.server_page);
        login_page = (LinearLayout) findViewById(R.id.login_page);
        /////////////////////////////////////////////////
        MediaPlayer player = MediaPlayer.create(this, R.raw.the_dope_steppa);   // red->raw 의 mp3 파일 실행. 종료 또는 조건은 아직 안넣.
        player.start();

        //////////////////////////////////////
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idValue = idField.getText().toString();
                pwValue = pwField.getText().toString();

                if(idValue.equals("Administrator")&& pwValue.equals("Password")){
                    login_page.setVisibility(View.GONE);
                    server_page.setVisibility(View.VISIBLE);
                }
                // 아이디와 비밀번호가 맞으면을 체크함. 현재 계정은 위의 1개뿐.
                else{
                    Toast.makeText(getBaseContext(),"올바르지않은 아이디/비밀번호입니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        connect.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           String nick = Nick.getText().toString();
                                           String ip = server.getText().toString();
                                           int port = Integer.parseInt(Port.getText().toString());
                                           if(ip.equals("172.29.153.216"))  //IP가 많아지면 함수 또는 배열로 비교. 지금은 적어서리.
                                           {
                                               Intent intent = new Intent(LoginPage.this, SendMessage.class);
                                               intent.putExtra("닉네임", nick);
                                               intent.putExtra("아이피", ip);
                                               intent.putExtra("포트", port);
                                               startActivity(intent);
                                           }
                                           else {
                                               Toast.makeText(getBaseContext(),"서버들의 IP중 하나가 아닙니다.", Toast.LENGTH_LONG).show();
                                           }
                                       }
                                   });

        idFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "아직 개발중입니다.", Toast.LENGTH_LONG).show();
            }
        });
        pwFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "아직 개발중입니다.", Toast.LENGTH_LONG).show();
            }
        });
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "아직 개발중입니다.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
