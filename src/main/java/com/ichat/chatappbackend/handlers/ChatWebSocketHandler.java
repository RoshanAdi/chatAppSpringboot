package com.ichat.chatappbackend.handlers;

import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final List<WebSocketSession> webSocketSessions = new ArrayList<>();
    private final Map<String, WebSocketSession> idToActiveSession = new HashMap<>();


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        webSocketSessions.add(session);

        System.out.println(session.getId());

        /*session.sendMessage();*/

       /* idToActiveSession.put(session.getId(), session);*/
        /*super.afterConnectionEstablished(session);*/
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        for(WebSocketSession webSocketSession : webSocketSessions){
            webSocketSession.sendMessage(message);
        }
        System.out.println("printing message = "+message);

        System.out.println(message.getPayload());

        TextMessage textMessage = new TextMessage(message.getPayload().replace("user","Id").replace("Joined", session.getId()));

        Thread.sleep(1000);
        session.sendMessage(textMessage);

      /*  String payload = message.getPayload();
        for (Map.Entry<String, WebSocketSession> otherSession : idToActiveSession.entrySet()) {
            if (otherSession.getKey().equals(session.getId())) continue;
            otherSession.getValue().sendMessage(new TextMessage(payload));
        }*/
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        webSocketSessions.remove(session);
        /*idToActiveSession.remove(session.getId());
        super.afterConnectionClosed(session, status);*/
    }
}
