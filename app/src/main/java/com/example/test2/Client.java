package com.example.test2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client extends AppCompatActivity implements Disconnector{

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

    @Override
    public void disconnect() { close(); }

    private void close(){
        try {
            objectStream.close();
            System.out.println("ObjectOutputStream close 완료");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(){
        connect();
        if(socket != null) {
            runReceiver();
            try {
                play();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    private void runReceiver(){
        new Thread(new ClientReceiver(socket, this)).start();
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
            Toast.makeText(getBaseContext(), ;"서버 접속이 완료되었습니다.", Toast.LENGTH_LONG).show()


            isAlived = true;
            objectStream = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void play() throws IOException{

        System.out.println("닉네임 설정해주세요.");
        System.out.print(">>");
        String nick = scanner.next();
        Protocol p = new Protocol(nick);

        System.out.println("채팅을 시작합니다. 종료하시려면 \"exit\"를 입력해주세요.");
        for(;isAlived;){
            String message = scanner.nextLine();

            p.setMessage(message);

            objectStream.writeObject(p);
            objectStream.reset();
            objectStream.flush();

            if(message.equals("exit")){
                isAlived = false;
            }
        }
        System.out.println("채팅 입력을 종료하였습니다.");
    }

}
