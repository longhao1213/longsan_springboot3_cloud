package com.websocket.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

@Component
public class NoticeWebSocketHandler implements WebSocketHandler {
    @Override
    public Mono<Void> handle(WebSocketSession session) {
        System.out.println("客户端连接: " + session.getId());
        // 先处理接收到的消息
        Mono<Void> input = session.receive()
                .doOnNext(msg -> System.out.println("收到客户端消息: " + msg.getPayloadAsText()))
                .then();

        // 向客户端推送欢迎消息
        Mono<Void> output = session.send(
                Mono.just(session.textMessage("欢迎连接通知服务"))
        );

        return Mono.when(input, output);
    }
}
