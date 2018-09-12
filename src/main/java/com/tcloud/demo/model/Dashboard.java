package com.tcloud.demo.model;

public class Dashboard {
	private int dSource;
	private int dCollector;
	private int dStorageLength;
	private Long dStorageSize;
	private int dScheduleRunning;
	private int dSchedule;

	private int adapter;
	private int strategy;
	private int taskRunning;
	private int task;

	public int getdSource() {
		return dSource;
	}

	public void setdSource(int dSource) {
		this.dSource = dSource;
	}

	public int getdCollector() {
		return dCollector;
	}

	public void setdCollector(int dCollector) {
		this.dCollector = dCollector;
	}

	public int getdStorageLength() {
		return dStorageLength;
	}

	public void setdStorageLength(int dStorageLength) {
		this.dStorageLength = dStorageLength;
	}

	public Long getdStorageSize() {
		return dStorageSize;
	}

	public void setdStorageSize(Long dStorageSize) {
		this.dStorageSize = dStorageSize;
	}

	public int getdScheduleRunning() {
		return dScheduleRunning;
	}

	public void setdScheduleRunning(int dScheduleRunning) {
		this.dScheduleRunning = dScheduleRunning;
	}

	public int getdSchedule() {
		return dSchedule;
	}

	public void setdSchedule(int dSchedule) {
		this.dSchedule = dSchedule;
	}

	public int getAdapter() {
		return adapter;
	}

	public void setAdapter(int adapter) {
		this.adapter = adapter;
	}

	public int getStrategy() {
		return strategy;
	}

	public void setStrategy(int strategy) {
		this.strategy = strategy;
	}

	public int getTaskRunning() {
		return taskRunning;
	}

	public void setTaskRunning(int taskRunning) {
		this.taskRunning = taskRunning;
	}

	public int getTask() {
		return task;
	}

	public void setTask(int task) {
		this.task = task;
	}

	public Dashboard() {
		super();
	}

}
