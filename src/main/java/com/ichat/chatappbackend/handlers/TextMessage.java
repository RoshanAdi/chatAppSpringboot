package com.ichat.chatappbackend.handlers;
import org.springframework.web.socket.WebSocketMessage;

public final class TextMessage implements WebSocketMessage<String> {


    public TextMessage(CharSequence payload){}



    @Override
    public String getPayload() {
        return null;
    }

    @Override
    public int getPayloadLength() {
        return 0;
    }

    @Override
    public boolean isLast() {
        return false;
    }
}
