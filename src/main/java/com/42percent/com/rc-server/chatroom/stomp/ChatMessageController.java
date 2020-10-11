package com.parfait.study.simplewebsocket.chatroom.stomp;

import com.parfait.study.simplewebsocket.chatroom.model.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@Profile("stomp")
@RestController
public class ChatMessageController {

    private final SimpMessagingTemplate template;
    private static final Logger LOGGER = LoggerFactory.getLogger(ChatMessageController.class);


    @Autowired
    public ChatMessageController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/chat/join")
    public void join(ChatMessage message) {
        LOGGER.warn(message.toString());

        message.setMessage(message.getWriter() + "님이 입장하셨습니다.");
        template.convertAndSend("/subscribe/chat/room/" + message.getChatRoomId(), message);
    }

    @MessageMapping("/chat/message")
    public void message(ChatMessage message) {

        LOGGER.warn(message.toString());

        template.convertAndSend("/subscribe/chat/room/" + message.getChatRoomId(), message);
    }
}
