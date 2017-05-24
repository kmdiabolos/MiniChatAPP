package com.example.test2;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class SendMessage extends AppCompatActivity implements disConnector {

    private static final boolean def = true;
    private Button send,exit;
    public EditText edittext;
    private TextView chat;
    public String message,rmessage;
    public boolean isAlived;
    public Socket socket;
    public BufferedReader br;
    public DataOutputStream osss;
    private disConnector dis;
String nick,ipAddress;
    int port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_message);
        exit = (Button) findViewById(R.id.exit);
        send = (Button) findViewById(R.id.send);
        edittext = (EditText) findViewById(R.id.field);
        chat = (TextView) findViewById(R.id.chat);

        Intent intent = getIntent();
        nick = intent.getStringExtra("닉네임");
        ipAddress = intent.getStringExtra("아이피");
        port = intent.getExtras().getInt("포트");

//exception 처리하기위한 것
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        if (socket != null) {
                ipAddress = "127.0.0.1";
                port = 5000;
        }

        Output.start(); //데이터를 계속받아오는 쓰레드 시작

        Toast.makeText(getBaseContext(), "채팅접속이 완료되었습니다. 나가시려면 'exit'", Toast.LENGTH_LONG).show();
        isAlived =true;

        //우측상단의 닫기버튼이 눌러지면
        exit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                isAlived = false;
                Toast.makeText(getBaseContext(), "채팅입력이 종료되었습니다.", Toast.LENGTH_LONG).show();
                Exit.start();

                finish();
            }
        });

        //전송버튼이 눌러졌을때!!!
        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                if (isAlived ) {
                    message = edittext.getText().toString(); //메세지에 edittext에있는것을 넣어라
                    if (message.equals("")) {;} //메세지가 아무것도 없으면 아무것도 하지마라
                    else if (message.equals("exit")){
                        try {
                            osss.writeUTF("exit");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        isAlived = false;
                        close();
                        finish();
                        Toast.makeText(getBaseContext(), "채팅출력을 종료하였습니다.", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Input.start();
                        edittext.setText(""); //edittext 초기화
                        }
                    }

            }
        });
    }


    // 데이터를 소켓으로 내보내는 쓰레드 input
    Thread Input = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                osss.writeUTF(nick+ " : " +message); //닉네임 : 보내는 메세지
                osss.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });

    Thread Exit = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                osss.writeUTF("exit");
                osss.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });

    // 데이터를 읽어오는 쓰레드 output
       Thread Output = new Thread(new Runnable() {
        @Override
        public void run() {
            createStream();
            try {
                for(;(rmessage = br.readLine()) != null;){
                    if(rmessage.equals("disconnect")){
                        break;
                    }else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                chat.append(rmessage+"\n");
                            }
                        });
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            close();
        }
    });

    private void close() {
        try {
            osss.close();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void disconnect() {
        close();
    }
    private void createStream(){
        try {
            socket = new Socket(ipAddress, port);
            br = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
            osss=new DataOutputStream(socket.getOutputStream());
            osss.writeUTF(nick);
            osss.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


