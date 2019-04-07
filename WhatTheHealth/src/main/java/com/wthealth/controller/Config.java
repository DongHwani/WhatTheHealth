package com.wthealth.controller;

public final class Config {
	public static final String appid = "BIt_Project";
	public static final String apikey = "90df16b2fd3911e88f460cc47a1fcfae";
	public static String content;
	public static final String sender = "01092968532";
	private static String receiver;
	

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	@Override
	public String toString() {
		return "Config [appid=" + appid + ", apikey=" + apikey + ", content=" + content + ", sender=" + sender
				+ ", receiver=" + receiver + "]";
	}
	
	
}
