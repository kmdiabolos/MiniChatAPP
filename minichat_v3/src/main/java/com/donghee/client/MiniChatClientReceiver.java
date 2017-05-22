package com.donghee.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by Administrator on 2017-02-19.
 */

public class MiniChatClientReceiver implements Runnable{

    private Socket socket;

    private BufferedReader br;

    private Disconnector dis;

    public MiniChatClientReceiver(Socket socket, Disconnector dis){
        this.socket = socket;
        this.dis = dis;
    }

    @Override
    public void run() {
        createStream();

        String message;
        try {
            for(;(message = br.readLine()) != null;){
                if(message.equals("disconnect")){
                    System.out.println("서버와 연결이 안전하게 종료되었습니다.");
                    dis.disconnect();
                    break;
                }else {
                    System.out.print(">>");
                    System.out.println(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        close();
        System.out.println("채팅 출력을 종료하였습니다.");
    }

    private void createStream(){
        try {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void close(){
        try {
            br.close();
            System.out.println("BufferedReader close 완료");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
