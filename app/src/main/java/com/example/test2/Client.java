package com.example.test2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client extends AppCompatActivity{

    private EditText server,Port;
    private static final boolean def = true;
    private Socket socket;
    private ObjectOutputStream objectStream = null;
    private boolean isAlived;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Port = (EditText) findViewById(R.id.port);
        server = (EditText) findViewById(R.id.server);

    }

    public void start(){
        connect();
        if(socket!=null){
            runReceiver();
        }
    }

    private void runReceiver(){
        //new Thread(new ClientReceiver(socket, ));
    }

    private void connect(){
        final String ipAddress;
        final int port;
        if(def){
            ipAddress = server.getText().toString();
            port=5000;
            //System.out.println("접속할 서버의 포트번호를 입력해주세요.");
            //System.out.print(">>");
            //port = scanner.nextInt();
        }else{
            ipAddress = "127.0.0.1";
            port = 5000;
        }
        Toast.makeText(getBaseContext(), ipAddress + port + "서버로 접속합니다.", Toast.LENGTH_LONG).show();

        try {
            socket = new Socket(ipAddress, port);
            Toast.makeText(getBaseContext(), "서버 접속이 완료되었습니다.", Toast.LENGTH_LONG).show();


            isAlived = true;
            objectStream = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
