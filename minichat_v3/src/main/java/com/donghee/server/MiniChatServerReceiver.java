package com.donghee.server;

import com.donghee.protocol.Protocol;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Administrator on 2017-02-19.
 */

public class MiniChatServerReceiver implements Runnable {

    private Broadcast server;
    private Socket socket;

    private PrintWriter printWriter;
    private ObjectInputStream objectInputStream;

    public MiniChatServerReceiver(Broadcast server, Socket socket) {
        this.server = server;
        this.socket = socket;
        try {
            printWriter = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void println(String message) {
        printWriter.println(message);
    }

    @Override
    public void run() {
        String message;
        Object obj;
        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());

            for (; (obj = objectInputStream.readObject()) != null; ) {
                Protocol p = (Protocol) obj;
                message = p.message;
                if (message.equals("exit")) {
                    println("disconnect");
                    socket.close();
                    message = String.format("[%s]님이 나가셨습니다.", p.nickName);
                    server.broadcast(message);
                    System.out.println(message);
                    server.notiLeftUser(this);
                    break;
                } else {
                    message = String.format("[%s]: %s", p.nickName, p.message);
                    System.out.println(message);
                    server.broadcast(message);
                }
            }

            close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void close() throws IOException{
        printWriter.close();
        System.out.println("PrintWriter close 완료");
        objectInputStream.close();
        System.out.println("ObjectInputStream close 완료");
    }
}
