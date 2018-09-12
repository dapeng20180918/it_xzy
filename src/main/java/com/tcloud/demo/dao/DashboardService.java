package com.tcloud.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcloud.demo.dao.impl.*;
import com.tcloud.demo.model.AnalyzeAdapter;
import com.tcloud.demo.model.AnalyzeStrategy;
import com.tcloud.demo.model.AnalyzeTask;
import com.tcloud.demo.model.Dashboard;
import com.tcloud.demo.model.DataCollectSchedule;
import com.tcloud.demo.model.DataCollector;
import com.tcloud.demo.model.DataSource;
import com.tcloud.demo.model.DataStorageInfo;

@Service
public class DashboardService {
	@Autowired
	DataSourceDao dsd;
	
	@Autowired
	DataCollectorDao dcd;

	@Autowired
	DataCollectScheduleDao dcsd;
	
	@Autowired
	DataStorageInfoDao dsid;
	
	@Autowired
	AnalyzeAdapterDao aad;
	
	@Autowired
	AnalyzeStrategyDao asd;
	
	@Autowired
	AnalyzeTaskDao atd;
	
	public Dashboard getDashboard() {
		Dashboard db = new Dashboard();
		List<DataSource> _dsd = dsd.getAll();
		List<DataCollector> _dcd = dcd.getAll();
		List<DataCollectSchedule> _dcsd = dcsd.getAll();
		List<DataStorageInfo> _dsid = dsid.getAll();
		
		List<AnalyzeAdapter> _aad = aad.getAll();
		List<AnalyzeStrategy> _asd = asd.getAll();
		List<AnalyzeTask> _atd = atd.getAll();
		
		db.setdSource(_dsd.size());
		db.setdCollector(_dcd.size());
		
		Long dStorageSize = 0L;
		for(DataStorageInfo one: _dsid){
			if(one.getData_size() != null){
				dStorageSize += one.getData_size();
			}
		}
		db.setdStorageSize(dStorageSize);
		db.setdStorageLength(_dsid.size());
		
		int dScheduleRunning = 0;
		for(DataCollectSchedule one: _dcsd){
			if(one.getStatus() != null && one.getStatus() == 1){// running
				dScheduleRunning++;
			}
		}
		db.setdSchedule(_dcsd.size());
		db.setdScheduleRunning(dScheduleRunning);
		
		db.setAdapter(_aad.size());
		db.setStrategy(_asd.size());
		
		int taskRunning = 0;
		for(AnalyzeTask one: _atd){
			if(one.getStatus() != null && one.getStatus() == 2){// finished
				taskRunning++;
			}
		}
		db.setTask(_atd.size());
		db.setTaskRunning(taskRunning);
		
		return db;
	}
}
