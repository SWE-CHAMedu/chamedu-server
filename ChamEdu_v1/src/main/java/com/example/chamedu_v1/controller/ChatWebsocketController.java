package com.example.chamedu_v1.controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
public class ChatWebsocketController {

    @GetMapping("/chat")
    public String chatGET(){
        log.info("@ChatWebsocketController, chat GET()");
        return "chat";
    }
}
