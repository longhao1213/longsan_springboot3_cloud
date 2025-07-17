package com.my_mcp.server.contorller;

import cn.hutool.json.JSONUtil;
import com.my_mcp.server.domain.ActorsFilms;
import com.my_mcp.server.domain.Subject;
import com.my_mcp.server.tools.DateTimeTools;
import com.my_mcp.server.tools.WeatherRequest;
import com.my_mcp.server.tools.WeatherService;
import com.my_mcp.server.tools.WeatherToolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.model.tool.ToolCallingChatOptions;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.ai.template.NoOpTemplateRenderer;
import org.springframework.ai.template.st.StTemplateRenderer;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.function.FunctionToolCallback;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Map;

@RestController
//@RequiredArgsConstructor
@Slf4j
public class TestController {

    private final ChatClient chatClient;
    private final OllamaChatModel ollamaChatModel;
    private final ChatMemory chatMemory;

    public TestController(ChatClient.Builder chatClientBuilder, OllamaChatModel ollamaChatModel, ChatMemory chatMemory) {
        this.chatClient = chatClientBuilder.build();
        this.ollamaChatModel = ollamaChatModel;
        this.chatMemory = chatMemory;
    }

    @GetMapping(value = "/ai",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> generation(@RequestParam("input") String input) {
        return this.chatClient.prompt()
                .user(input)
                .stream()
                .content();
    }

    @GetMapping(value = "/aiByTools",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public String aiByTools(@RequestParam("input") String input) {
//        return ChatClient.create(ollamaChatModel)
//                .prompt(new Prompt(input,
//                        OllamaOptions.builder()
//                                .model("llama3.1:8b")
//                                .temperature(0.4)
//                                .build()))
//                .tools(new DateTimeTools())
//                .call().content();

        ToolCallback toolCallback = FunctionToolCallback
                .builder("currentWeather", new WeatherToolService())
                .description("Get the weather in location")
                .inputType(WeatherRequest.class)
                .build();
        return ChatClient.create(ollamaChatModel)
                .prompt(new Prompt(input,
                        OllamaOptions.builder()
                                .model("llama3.1:8b")
                                .temperature(0.4)
                                .toolCallbacks(toolCallback)
                                .build()))
//                .tools(toolCallback)
                .call()
                .content();

    }

    @GetMapping(value = "/aiByMemory",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ChatResponse> aiByMemory(@RequestParam("input") String input,@RequestParam("conversationId")String conversationId) {
        UserMessage userMessage = new UserMessage(input);
        chatMemory.add(conversationId, userMessage);
        // 拼接完整AI回复内容，结束时写入记忆
        StringBuilder builder = new StringBuilder();

        return chatClient.prompt(new Prompt(chatMemory.get(conversationId)))
                .stream()
                .chatResponse()
                .doOnNext(response -> {
                    String content = response.getResult().getOutput().getText();
                    if (content != null) {
                        builder.append(content);
                    }
                })
                .doOnComplete(() -> {
                    String fullResponse = builder.toString();
                    chatMemory.add(conversationId, new org.springframework.ai.chat.messages.AssistantMessage(fullResponse));
                });

    }

    @GetMapping(value = "/aiByPrompts",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ChatResponse> aiByPrompts(@RequestParam(value = "adjective", defaultValue = "轻松") String adjective,
                                    @RequestParam(value = "topic", defaultValue = "汽车") String topic) {
        PromptTemplate promptTemplate = new PromptTemplate("说一个 {adjective} 笑话 有关于 {topic}");

        Prompt prompt = promptTemplate.create(Map.of("adjective", adjective, "topic", topic));

        return chatClient.prompt(prompt).stream().chatResponse();
    }

    @GetMapping("/aiByFormat")
    public Subject aiByFormat() {
        Subject subject = ChatClient.create(ollamaChatModel)
                .prompt(new Prompt("",
                        OllamaOptions.builder()
                                .model("gemma3:27b")
                                .temperature(0.4)
                                .build()))
            .user(u -> u.text("根据{type}告诉我高考有哪些科目要考.")
                    .param("type", "理科"))
            .call()
            .entity(Subject.class);
        System.out.println(JSONUtil.toJsonStr(subject));
        return subject;
    }

    @GetMapping(value = "/aiByOllama",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ChatResponse> generation2(@RequestParam("input") String input) {
        Flux<ChatResponse> stream = ollamaChatModel.stream(
                new Prompt(input,
                        OllamaOptions.builder()
                                .model("deepseek-r1:8b")
                                .temperature(0.4)
                                .build())
        );
        return stream;
    }
}
