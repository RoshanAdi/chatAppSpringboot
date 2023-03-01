package com.ichat.chatappbackend.handlers;


import com.ichat.chatappbackend.Dtos.PayloadDto;
import org.json.JSONObject;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;

public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> idToActiveSession = new HashMap<>();
    private final Map<String, String> idAndNick = new HashMap<>();


    public ChatWebSocketHandler() {

    }
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println(session.getId());
        idToActiveSession.put(session.getId(), session);
    }
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        JSONObject obj = new JSONObject(message.getPayload());

        if(Objects.equals(obj.get("senderId").toString(), "l4231rfd2384if9")){
          session.sendMessage(new TextMessage(message.getPayload().replace("l4231rfd2384if9",session.getId())));

            idAndNick.put(session.getId(),obj.get("user").toString());
            JSONObject obj2 = new JSONObject(idAndNick);
            for (Map.Entry<String, WebSocketSession> otherSession : idToActiveSession.entrySet()) {
                otherSession.getValue().sendMessage(new TextMessage(obj2.toString()));
                if (otherSession.getKey().equals(session.getId())) continue;
                otherSession.getValue().sendMessage(new TextMessage(message.getPayload()));

            }

        }
        else {
        for (Map.Entry<String, WebSocketSession> otherSession : idToActiveSession.entrySet()) {

            otherSession.getValue().sendMessage(new TextMessage(message.getPayload()));

        }}
        System.out.println("printing message = "+message);
        System.out.println(message.getPayload());


                    System.out.println("printing nickname array = "+idAndNick);

    }
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        idToActiveSession.remove(session.getId());
        System.out.println("session = "+session+" "+status);
        super.afterConnectionClosed(session, status);
        idAndNick.remove(session.getId());

        for (Map.Entry<String, WebSocketSession> otherSession : idToActiveSession.entrySet()) {
            JSONObject obj2 = new JSONObject(idAndNick);
            otherSession.getValue().sendMessage(new TextMessage(obj2.toString()));;

        }
    }


}
