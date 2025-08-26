package com.my_mcp.server.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

public class WeatherTools {

    @Tool(description = "获取指定城市指定日期的天气")
    String getWeather(
            @ToolParam(description = "城市名称")String city,
            @ToolParam(description = "日期,格式为YYYY-MM-DD,或者今天 明天")String date
    ){
        System.out.println("调用了天气工具");
        return city+"的天气是晴天";
    }
}
