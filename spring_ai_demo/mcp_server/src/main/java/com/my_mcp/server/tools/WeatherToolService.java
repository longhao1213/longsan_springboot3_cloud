package com.my_mcp.server.tools;

import java.util.function.Function;

public class WeatherToolService implements Function<WeatherRequest, WeatherResponse> {

    @Override
    public WeatherResponse apply(WeatherRequest request) {
        return new WeatherResponse(30.0, Unit.C);
    }
}
