package com.donghee.server;

/**
 * Created by Administrator on 2017-02-19.
 */

public interface Broadcast {
    void broadcast(String message);
    void notiLeftUser(MiniChatServerReceiver receiver);
}
