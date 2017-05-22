package com.donghee.launcher;

import com.donghee.server.MiniChatServer;

/**
 * Created by Administrator on 2017-02-19.
 */

public class MiniChatServerLauncher {
    public static void main(String[] args) {
        MiniChatServer server = new MiniChatServer();
        server.start();
    }
}
