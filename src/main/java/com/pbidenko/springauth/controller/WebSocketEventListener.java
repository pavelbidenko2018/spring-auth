package com.pbidenko.springauth.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.pbidenko.springauth.chatmodel.MessageStatus;
import com.pbidenko.springauth.entity.ChatMessage;

import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;


@Component
public class WebSocketEventListener {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketEventListener.class);
	
	@Autowired
	private SimpMessageSendingOperations sendingOperations;
	
	@EventListener
	public void handleWebSocketConnectListener(SessionConnectedEvent event) {
		LOGGER.info("We have a new connection");
	}
	
	@EventListener
	public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
		String username = (String)headerAccessor.getSessionAttributes().get("username");
		ChatMessage chatMessage = new ChatMessage();
		chatMessage.setStatus(MessageStatus.DELIVERED);
		chatMessage.setSenderName(username);
		
		sendingOperations.convertAndSend("/topic/public",chatMessage);
	}

}
