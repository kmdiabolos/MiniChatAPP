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

    private static final boolean def = true;

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
            System.out.println("������ ���� �ּҸ� �Է����ּ���.");
            System.out.print(">>");
            ipAddress = scanner.next();
            System.out.println("������ ������ ��Ʈ��ȣ�� �Է����ּ���.");
            System.out.print(">>");
            port = scanner.nextInt();
        }else{
            ipAddress = "127.0.0.1";
            port = 5000;
        }

        System.out.println(String.format("%s:%d ������ �����մϴ�.", ipAddress, port));

        try {
            socket = new Socket(ipAddress, port);
            System.out.println("���� ������ �Ϸ�Ǿ����ϴ�.");

            isAlived = true;
            objectStream = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void play() throws IOException{

        System.out.println("�г��� �������ּ���.");
        System.out.print(">>");
        String nick = scanner.next();
        Protocol p = new Protocol(nick);

        System.out.println("ä���� �����մϴ�. �����Ͻ÷��� \"exit\"�� �Է����ּ���.");
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
        System.out.println("ä�� �Է��� �����Ͽ����ϴ�.");
    }

    private void close(){
        try {
            objectStream.close();
            System.out.println("ObjectOutputStream close �Ϸ�");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() { close(); }
}
