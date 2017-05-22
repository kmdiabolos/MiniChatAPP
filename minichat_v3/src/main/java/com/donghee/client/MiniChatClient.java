package com.donghee.client;

import com.donghee.protocol.Protocol;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Administrator on 2017-02-19.
 */

public class MiniChatClient implements Disconnector {

    private static final boolean def = false;

    private Socket socket;
    private ObjectOutputStream objectStream = null;
    private Scanner scanner;

    private boolean isAlived;
    public MiniChatClient(){
        scanner = new Scanner(System.in);
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
        new Thread(new MiniChatClientReceiver(socket, this)).start();
    }

    private void connect(){
        final String ipAddress;
        final int port;
        if(def){
            System.out.println("접속할 서버 주소를 입력해주세요.");
            System.out.print(">>");
            ipAddress = scanner.next();
            System.out.println("접속할 서버의 포트번호를 입력해주세요.");
            System.out.print(">>");
            port = scanner.nextInt();
        }else{
            ipAddress = "127.0.0.1";
            port = 5000;
        }

        System.out.println(String.format("%s:%d 서버로 접속합니다.", ipAddress, port));

        try {
            socket = new Socket(ipAddress, port);
            System.out.println("서버 접속이 완료되었습니다.");

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

    private void close(){
        try {
            objectStream.close();
            System.out.println("ObjectOutputStream close 완료");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() { close(); }
}
