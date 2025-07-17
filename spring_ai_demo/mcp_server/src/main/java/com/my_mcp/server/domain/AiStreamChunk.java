package com.my_mcp.server.domain;

import lombok.Data;

@Data
public class AiStreamChunk {
    private final String chunk;        // 当前流的文本片段
    private final boolean isComplete;  // 是否标记为流结束
    private final String fullMessage;  // 完整内容，仅最后一个 chunk 设置

}