package com.pbidenko.springauth.controller;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.pbidenko.springauth.entity.ChatMessage;

@Controller
public class ChatController {
	
//	@GetMapping("/chat")
//	public String chat() {
//		return "chat";
//	}

	
	@MessageMapping("/chat")
	@SendTo("/topic/public")
	public ChatMessage sendMessage(@Payload ChatMessage chatMessage, Principal principal) {
		return chatMessage;
	}	
	
	@MessageMapping("/chat.newUser/")
	@SendTo("/topic/public")
	public ChatMessage newUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {

		headerAccessor.getSessionAttributes().put("username", chatMessage.getSenderId());
		return chatMessage;
	}

}
