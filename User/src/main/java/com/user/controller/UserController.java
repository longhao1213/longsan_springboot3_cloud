package com.user.controller;

import com.user.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final RestTemplate restTemplate;

    @GetMapping("/getUser")
    @Operation(summary = "获取用户")
    public String getUser(@RequestParam() @Valid @NotBlank(message = "faewjiofwea") String  userName) {
        return userName;
    }

    @GetMapping("/getUserOrder")
    public String getUserOrder(){
        String url = "http://order-app.longsan-namespace.svc.cluster.local:9004/getOrder";
        try {
            String forObject = restTemplate.getForObject(url, String.class);
            return getUser("test") + forObject;
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    @GetMapping("/testArthas")
    public String testArthas(String name, int age) {
        String param = age + 10 + "";
        log.info("name:{},age:{}", name, age);
        return param;
    }

    @PostMapping("/addUser")
    public String addUser(@RequestBody @Valid User user) {
        return user.toString();
    }


}
