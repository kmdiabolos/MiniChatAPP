package com.example.test2;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SendMessage extends AppCompatActivity implements disConnector {

    private static final boolean def = true;
    private Button send,exit;
    public EditText edittext,server,Port,Nick;
    private TextView chat;
    public String message,rmessage;
    public boolean isAlived;
    public Socket socket,testsocket;
   String ipAddress = "172.29.153.216";
    int port = 5000;
    String nick = "Devils";
    public BufferedReader br;
    public ObjectOutputStream oss;
    public DataOutputStream osss;
    private disConnector dis;
    Protocol p = new Protocol(nick);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_message);
        exit = (Button) findViewById(R.id.exit);
        send = (Button) findViewById(R.id.send);
        edittext = (EditText) findViewById(R.id.field);
        chat = (TextView) findViewById(R.id.chat);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        if (socket == null) {
            if (def) {
                ipAddress = "172.29.153.135";
                port = 5000;
                nick = "Devils";
            } else {
                ipAddress = "127.0.0.1";
                port = 5000;
            }

        }


        Output.start(); //쓰레드

        Toast.makeText(getBaseContext(), "채팅접속이 완료되었습니다. 나가시려면 'exit'", Toast.LENGTH_LONG).show();
        isAlived =true;

        exit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                isAlived = false;
                Toast.makeText(getBaseContext(), "채팅입력이 종료되었습니다.", Toast.LENGTH_LONG).show();
                finish();
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                if (isAlived ) {
                    message = edittext.getText().toString(); //메세지에 edittext에있는것을 넣어라

                    if (message.equals("")) {;} //메세지가 아무것도 없으면 아무것도 하지마라

                    else if (message.equals("exit")){
                        isAlived = false;
                        Toast.makeText(getBaseContext(), "채팅입력이 종료되었습니다.", Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else {
                        Input.start();
                        edittext.setText(""); //edittext 초기화
                        }
                    }

            }
        });
    }

    private void close() {
        try {
            oss.close();
            Toast.makeText(getBaseContext(), "ObjectOutputStream 완료", Toast.LENGTH_LONG).show();
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    Thread Input = new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                osss=new DataOutputStream(socket.getOutputStream());
                osss.writeUTF(nick+ " : " +message);
                osss.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });

       Thread Output = new Thread(new Runnable() {
        @Override
        public void run() {
            createStream();
            try {
                for(;(rmessage = br.readLine()) != null;){
                    if(rmessage.equals("disconnect")){
                        System.out.println("서버와 연결이 안전하게 종료되었습니다.");
                        dis.disconnect();
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
            System.out.println("채팅 출력을 종료하였습니다.");
        }

    });


}


