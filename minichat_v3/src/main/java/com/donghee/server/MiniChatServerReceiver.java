package com.donghee.server;

import com.donghee.protocol.Protocol;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.io.DataInputStream;
import java.net.Socket;

/**
 * Created by Administrator on 2017-02-19.
 */

public class MiniChatServerReceiver implements Runnable {

    private Broadcast server;
    private Socket socket;

    private PrintWriter printWriter;
    private ObjectInputStream objectInputStream;
    public String nickname;

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
        DataInputStream input;
        
        int i=1;
        try {
    		input = new DataInputStream(socket.getInputStream());
        	while(true){
        		message = input.readUTF();
        		if(i==1){
        			nickname=message;
        			String connect = new String(nickname+"���� �����ϼ̽��ϴ�.");
        			server.broadcast(connect);
        			i++;
        		}
        		else{
                if (message.equals("exit")) {
                    println("disconnect");
                    socket.close();
                    message = new String(nickname+"���� �����ϼ̽��ϴ�.");
                    System.out.println(message);
                    server.notiLeftUser(this);
                    break;
                } else {
                	
                	System.out.println(message);
                	server.broadcast(message);
                }
        		}
        	}
          }   
         catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    private void close() throws IOException{
        printWriter.close();
        System.out.println("PrintWriter close �Ϸ�");
        objectInputStream.close();
        System.out.println("ObjectInputStream close �Ϸ�");
    }
}
