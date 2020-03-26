package com.rab3tech.vo;

public class EmailVO {
	private String name;
	private String to;
	private String from;
	private String subject;
	private String body;
	
	public EmailVO() {}

	public EmailVO(String to, String from, String subject, String body,String name) {
		this.to = to;
		this.from = from;
		this.subject = subject;
		this.name=name;
		this.body = body;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
