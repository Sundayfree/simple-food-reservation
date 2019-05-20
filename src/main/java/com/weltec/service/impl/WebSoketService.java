package com.weltec.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Description:
 * Author: Liu
 * Date: 2019-05-11 21:55
 */
@Component
@ServerEndpoint("/webSocket")
public class WebSoketService {
    private Session session;

    private static CopyOnWriteArraySet<WebSoketService> webSocketSet = new CopyOnWriteArraySet<> ();

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);
    }

    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
    }


    public void sendMessage(String message) {
        for (WebSoketService webSocket: webSocketSet) {
            try {
                webSocket.session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
