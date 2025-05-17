package com.websocket.handler;


import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

@Component
public class ChatWebSocketHandler implements WebSocketHandler {
    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return session.send(
                // 接收客户端发送的消息
                session.receive()
                        // 提取文本
                        .map(WebSocketMessage::getPayloadAsText)
                        // 拼接返回消息
                        .map(msg -> "你发送了：" + msg)
                        // 转换为WebSocketMessage
                        .map(session::textMessage)
        );
    }
}
