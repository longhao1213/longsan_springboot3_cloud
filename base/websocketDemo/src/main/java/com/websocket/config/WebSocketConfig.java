package com.websocket.config;

import com.websocket.handler.ChatWebSocketHandler;
import com.websocket.handler.NoticeWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import java.util.HashMap;
import java.util.Map;

/**
 * WebFlux 的 WebSocket 配置类
 */
@Configuration
public class WebSocketConfig {

    /**
     * 配置 WebSocket 路径映射
     */
    @Bean
    public HandlerMapping webScokethandlerMapping(
            ChatWebSocketHandler chatWebSocketHandler,
            NoticeWebSocketHandler noticeWebSocketHandler
    ) {
        Map<String, WebSocketHandler> map = new HashMap<>();
        map.put("/ws/chat", chatWebSocketHandler);
        map.put("/ws/notice", noticeWebSocketHandler);
        // 映射路径到 handler
        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setOrder(1);
        mapping.setUrlMap(map);
        return mapping;
    }

    @Bean
    public WebSocketHandlerAdapter handlerAdapter() {
        return new WebSocketHandlerAdapter();
    }

}
