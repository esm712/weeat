package com.websocket.chat.handler;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.websocket.chat.dto.ChatMessage;
import com.websocket.chat.dto.ChatRoom;
import com.websocket.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@RequiredArgsConstructor
@Component
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class WebSocketChatHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final ChatService chatService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 메시지 내용 가져오기
        String payload = message.getPayload();
        log.info("payload {}", payload);
        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
        // 메시지에 적힌 채팅방 아이디 확인
        int chat_id= chatMessage.getChat_id();
        // 아이디로 채팅방 객체 획득
        ChatRoom room = chatService.findRoomById(chat_id);
        // 채팅방 존재 -> 채팅 서비스 (입장, 대화, 퇴장)
        if(room!=null){
            room.handleActions(session, chatMessage, chatService);
        }
        else
            System.out.println(chatMessage.getUser_name()+"님이 존재하지 않은 채팅방에 접근 중입니다!!");
    }
}