package com.my_mcp.server.tools;

import cn.hutool.http.HttpUtil;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class WeatherService {

    @Tool(description = "Get weather forecast for a specific latitude/longitude")
    public String getWeatherForecastByLocation(double latitude, double longitude) {
        // Implementation using weather.gov API
        return "本地天气预报 " + latitude + ", " + longitude;
    }

    @Tool(description = "Get weather alerts for a US state. Input is Two-letter US state code (e.g., CA, NY)")
    public String getAlerts(String state) {
        // Implementation using weather.gov API
        return state + " 2 mcp";
    }

    public record WeatherResponse(Current current) {
        public record Current(LocalDateTime time, int interval, double temperature_2m) {
        }
    }

    @Tool(description = "Get the temperature (in celsius) for a specific location")
    public String getTemperature(@ToolParam(description = "The location latitude") double latitude,
                                 @ToolParam(description = "The location longitude") double longitude,
                                 ToolContext toolContext) {

        return HttpUtil.get("https://api.open-meteo.com/v1/forecast?latitude=" + latitude + "&longitude=" + longitude + "&current=temperature_2m");

    }
}
