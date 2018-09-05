package com.tcloud.demo.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class AnalyzeTask {
	// basic
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable = false, unique = true)
	private String name;
	private Integer strategy_id;
	private String strategy_param;
	private String task_type;

	@Column(updatable = false)
	private Date create_date;
	private Date finish_date;
	private Integer status;
	private String user_name;
	private String description;

	// adv
	private Integer data_get;
	private Integer data_excute;
	private Integer host_max_cnt;
	private Integer thread_max_cnt;
	private Integer no_data_sleep;
	private Integer one_data_sleep;

	// do something
	private String result_path; // picture or data file path
	private String host_str; // 1;2;3;4
	private String host_all_str; // 1;2;3;4;5;6;7;8;9;10

	// set hosts and threads
	@Transient
	private List<Host> hosts;

	// my method
	public void selectHosts() {
		Integer host_cnt = 0;
		String[] host_list = host_all_str.split(";");
		
		if (thread_max_cnt < host_max_cnt) {
			host_cnt = thread_max_cnt;
		}else{
			host_cnt = host_max_cnt;
		}
		
		if(host_cnt > host_list.length){
			host_cnt = host_list.length;
		}
		
		this.host_str = String.join(";", getRandomFromArray(host_list, host_cnt));
//		System.out.println(this.host_str);
	}
	
	private String[] getRandomFromArray(String[] names, int cnt) {
		String[] a = names;
		String[] result = new String[cnt];
		boolean r[] = new boolean[a.length];
		Random random = new Random();
		int m = cnt; // 要随机取的元素个数
		if (m > a.length || m < 0) {
			return a;
		}

		int n = 0;
		while (true) {
			int temp = random.nextInt(a.length);
			if (!r[temp]) {
				if (n == m) // 取到足量随机数后退出循环
					break;
				n++;
				//System.out.println("得到的第" + n + "个随机数为：" + a[temp]);
				result[n - 1] = a[temp];
				r[temp] = true;
			}
		}
		return result;
	}

//	public static void main(String[] args) {
//		AnalyzeTask at = new AnalyzeTask();
//		at.setHost_max_cnt(3);
//		at.setThread_max_cnt(9);
//		at.setHost_all_str("1;2;3;4;5;6;7;8;9;10");
//		at.selectHosts();
//		at.fillHosts();
//	}
	
	public void fillHosts() {
		if(null == this.host_str){
			return;
		}
		List<Host> lists = new ArrayList<Host>();
		String[] host_list = this.host_str.split(";");
		int thread_num = 1;
		for (int i = 0; i < host_list.length; i++) {
			Host oneHost = new Host();
			oneHost.setId(host_list[i]);
//			System.out.println("Host-" + host_list[i]);
			int thread_cnt = this.thread_max_cnt / host_list.length;
			if((i+1)<=(this.thread_max_cnt % host_list.length)){
				thread_cnt++;
			}
					
			List<Thread> threads = new ArrayList<Thread>();
			for (int j = 0; j < thread_cnt; j++) {
				Thread th = new Thread();
				th.setId("Thread-" + thread_num);
				threads.add(th);
//				System.out.println("\tThread-" + thread_num);
				thread_num++;
			}

			oneHost.setThreads(threads);

			lists.add(oneHost);
		}
		this.setHosts(lists);
	}

	public List<Host> getHosts() {
		return hosts;
	}

	public void setHosts(List<Host> hosts) {
		this.hosts = hosts;
	}

	public String getHost_all_str() {
		return host_all_str;
	}

	public void setHost_all_str(String host_all_str) {
		this.host_all_str = host_all_str;
	}

	public String getResult_path() {
		return result_path;
	}

	public void setResult_path(String result_path) {
		this.result_path = result_path;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStrategy_id() {
		return strategy_id;
	}

	public void setStrategy_id(Integer strategy_id) {
		this.strategy_id = strategy_id;
	}

	public String getStrategy_param() {
		return strategy_param;
	}

	public void setStrategy_param(String strategy_param) {
		this.strategy_param = strategy_param;
	}

	public String getTask_type() {
		return task_type;
	}

	public void setTask_type(String task_type) {
		this.task_type = task_type;
	}

	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public Date getFinish_date() {
		return finish_date;
	}

	public void setFinish_date(Date finish_date) {
		this.finish_date = finish_date;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getData_get() {
		return data_get;
	}

	public void setData_get(Integer data_get) {
		this.data_get = data_get;
	}

	public Integer getData_excute() {
		return data_excute;
	}

	public void setData_excute(Integer data_excute) {
		this.data_excute = data_excute;
	}

	public Integer getHost_max_cnt() {
		return host_max_cnt;
	}

	public void setHost_max_cnt(Integer host_max_cnt) {
		this.host_max_cnt = host_max_cnt;
	}

	public Integer getThread_max_cnt() {
		return thread_max_cnt;
	}

	public void setThread_max_cnt(Integer thread_max_cnt) {
		this.thread_max_cnt = thread_max_cnt;
	}

	public Integer getNo_data_sleep() {
		return no_data_sleep;
	}

	public void setNo_data_sleep(Integer no_data_sleep) {
		this.no_data_sleep = no_data_sleep;
	}

	public Integer getOne_data_sleep() {
		return one_data_sleep;
	}

	public void setOne_data_sleep(Integer one_data_sleep) {
		this.one_data_sleep = one_data_sleep;
	}

	public String getHost_str() {
		return host_str;
	}

	public void setHost_str(String host_str) {
		this.host_str = host_str;
	}

	public AnalyzeTask() {
		super();
	}

}
