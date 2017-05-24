package com.donghee.launcher;

import com.donghee.client.MiniChatClient;

/**
 * Created by Administrator on 2017-02-19.
 */

public class MiniChatClientLauncher {
    public static void main(String[] args) {
        MiniChatClient client = new MiniChatClient();
        client.start();
    }
}
