package com.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final RestTemplate restTemplate;

    @GetMapping("/getUser")
    public String getUser() {
        return "user";
    }

    @GetMapping("/getUserOrder")
    public String getUserOrder(){
        String url = "http://order-app.longsan-namespace.svc.cluster.local:9004/getOrder";
        try {
            String forObject = restTemplate.getForObject(url, String.class);
            return getUser() + forObject;
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


}
