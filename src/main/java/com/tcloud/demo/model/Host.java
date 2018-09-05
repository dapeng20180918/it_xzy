package com.tcloud.demo.model;

import java.util.List;

public class Host {
	private String id;
	private List<Thread> threads;

	public Host() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Thread> getThreads() {
		return threads;
	}

	public void setThreads(List<Thread> threads) {
		this.threads = threads;
	}

}
