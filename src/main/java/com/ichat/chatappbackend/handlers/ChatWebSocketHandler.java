package com.ichat.chatappbackend.handlers;


import org.json.JSONObject;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;

public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> idToActiveSession = new HashMap<>();   // stores active sessions and ids
    private final Map<String, String> idAndNick = new HashMap<>();  //stores nick name and the id for updating list of users
    private TextMessage message1;


    public ChatWebSocketHandler() {

    }
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        idToActiveSession.put(session.getId(), session);
    }
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        JSONObject obj = new JSONObject(message.getPayload());


        if(Objects.equals(obj.get("senderId").toString(), "l4231rfd2384if9")){ // check for the first msg receive when joining someone(the nick name)
            this.message1 = message;
            session.sendMessage(new TextMessage(message.getPayload().replace("l4231rfd2384if9",session.getId()))); //user "joined" msg to the new user

            idAndNick.put(session.getId(),obj.get("user").toString());
            JSONObject obj2 = new JSONObject(idAndNick);
            for (Map.Entry<String, WebSocketSession> otherSession : idToActiveSession.entrySet()) { //sending public msgs to all
                otherSession.getValue().sendMessage(new TextMessage(obj2.toString())); //updating user list
                if (otherSession.getKey().equals(session.getId())) continue;  //loop skips to avoid sending "joined" msg again to the new user
                otherSession.getValue().sendMessage(new TextMessage(message.getPayload()));
            }}
        else if (!(Objects.equals(obj.get("recieverId").toString(), ""))) {  //catching private msgs

            session.sendMessage(new TextMessage(message.getPayload()));
idToActiveSession.get(obj.get("recieverId")).sendMessage(new TextMessage(message.getPayload()));
        }

        else {  //public chat msgs
        for (Map.Entry<String, WebSocketSession> otherSession : idToActiveSession.entrySet()) {
            otherSession.getValue().sendMessage(new TextMessage(message.getPayload()));
        }}

        }
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        var name = idAndNick.get(session.getId());
        idToActiveSession.remove(session.getId());
        super.afterConnectionClosed(session, status);
        idAndNick.remove(session.getId());

        for (Map.Entry<String, WebSocketSession> otherSession : idToActiveSession.entrySet()) { //update userlist upon leaving a user
            JSONObject obj2 = new JSONObject(idAndNick);
            otherSession.getValue().sendMessage(new TextMessage(obj2.toString()));
            otherSession.getValue().sendMessage(new TextMessage(message1.getPayload().replace("Joined","left")));

        }
    }


}
