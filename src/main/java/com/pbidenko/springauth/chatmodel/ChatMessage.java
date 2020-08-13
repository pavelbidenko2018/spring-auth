package com.pbidenko.springauth.chatmodel;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
public class ChatMessage {
	
	@Getter
	private MessageType type;
	
	@Getter
	private String content;
	
	@Getter
	private String sender;
	
	@Getter
	private String time;
	
	public MessageType getType() {
		return type;
	}

	public String getContent() {
		return content;
	}

	public String getSender() {
		return sender;
	}

	public String getTime() {
		return time;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	
}
