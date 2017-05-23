package com.example.test2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import com.example.test2.Protocol;

public class Client extends AppCompatActivity implements disConnector{

    public Socket socket;
    private EditText server,Port,Nick,edittext;
    private static final boolean def = true;
    private ObjectOutputStream objectStream = null;
    private boolean isAlived;
    public String nick,message,ipAddress;
    private Button send,exit;
    private TextView chat;
   public int port;
    private Thread TT;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Port = (EditText) findViewById(R.id.port);
        server = (EditText) findViewById(R.id.server);
        Nick = (EditText) findViewById(R.id.Nick);
        exit = (Button) findViewById(R.id.exit);
        send = (Button) findViewById(R.id.send);
        edittext = (EditText) findViewById(R.id.field);
        chat = (TextView) findViewById(R.id.chat);
    }

    public void start(int pt, String ip, String nk) {
        this.port = pt;
        this.nick = nk;
        this.ipAddress = ip;

            if (socket == null) {
                connect();
                TT.start();
            } else if (socket != null) {
                runReceiver();
                try {
                    play();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    private void connect() {
        if (def) {
            ;
        } else {
            ipAddress = "127.0.0.1";
            port = 5000;
        }
        TT= new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket(ipAddress, port);
                    isAlived = true;
                    objectStream = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void runReceiver(){
        new Thread(new ClientReceiver(socket, this)).start();
    }

    //-------------------------------------
    private void play() throws IOException{

            setContentView(R.layout.send_message);
            Protocol p = new Protocol(nick);
            //Toast.makeText(getBaseContext(), "채팅접속이 완료되었습니다. 나가시려면 'exit'", Toast.LENGTH_LONG).show();

            for (; isAlived; ) {
                send.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        message = edittext.getText().toString(); //메세지에 edittext에있는것을 넣어라
                        //   p.setMessage(message);
                        if (message.equals("")) {
                            ;
                        } //메세지가 아무것도 없으면 아무것도 하지마라
                        else {
                            chat.append(message + "\n"); //텍스트뷰에 메세지 나타냄
                            edittext.setText(""); //edittext 초기화
                        }
                    }
                });

                exit.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        isAlived = false;
                        Toast.makeText(getBaseContext(), "채팅입력이 종료되었습니다.", Toast.LENGTH_LONG).show();
                        finish();
                    }
                });

                objectStream.writeObject(p);
                objectStream.reset();
                objectStream.flush();
                if (message.equals("exit")) {
                    isAlived = false;
                }
            }
            Toast.makeText(getBaseContext(), "채팅입력이 종료되었습니다.", Toast.LENGTH_LONG).show();
            finish();
        }
    }
//----------------------------------------
    private void close(){
        try {
            objectStream.close();
            Toast.makeText(getBaseContext(), "ObjectOutputStream 완료", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() { close(); }
}


