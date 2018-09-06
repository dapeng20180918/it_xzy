'use strict';
myApp.service('auth', ['$q', '$rootScope', '$http', '$resource', function ($q, $rootScope, $http, $resource) {
	var self = this;
	//Setting--start
	var ifLogging = true;
	var restHost = "";
	//Setting--end
    var statusMap = {};
    var statusArray = [];
    self.storageTypeArray = [];
    self.treeData = [];
    self.scheduleStatusArray = [];
    self.bdStatusArray = [];
    self.hostArray = [];
    self.hostMap = {};
    self.hostListString = "";
    self.elementsArray = [];
    self.restHost = restHost;
    
    //user
    self.userResource = $resource(restHost + "/rest/user/:id", {id: '@id'}, {});
    self.roleResource = $resource(restHost + "/rest/role/:id", {id: '@id'}, {});
    self.ruleResource = $resource(restHost + "/rest/rule/:id", {id: '@id'}, {});
    self.eventResource = $resource(restHost + "/rest/event/:id", {id: '@id'}, {});
    self.feedbackResource = $resource(restHost + "/rest/feedback/:id", {id: '@id'}, {});
    
    //service
    self.dataSourceResource = $resource(restHost + "/rest/datasource/:id", 
    		{id: '@id'}, {});
    self.dataCollectorResource = $resource(restHost + "/rest/datacollector/:id", 
    		{id: '@id'}, {});
    self.dataStorageInfoResource = $resource(restHost + "/rest/datastorageinfo/:id", 
    		{id: '@id'}, {});
    self.dataCollectScheduleResource = $resource(restHost + "/rest/datacollectschedule/:id", 
    		{id: '@id'}, {});
    
    self.AnalyzeAdapterResource = $resource(restHost + "/rest/analyzeadapter/:id", 
    		{id: '@id'}, {});
    self.AnalyzeStrategyResource = $resource(restHost + "/rest/analyzestrategy/:id", 
    		{id: '@id'}, {});
    self.AnalyzeTaskResource = $resource(restHost + "/rest/analyzetask/:id", 
    		{id: '@id'}, {});
    self.AnalyzeSettingResource = $resource(restHost + "/rest/analyzesetting/:id", 
    		{id: '@id'}, {});
    
    
    this.info = function(data){
    	if(ifLogging)
    		console.info(data);
    }
    
    this.error = function(data){
		console.error(data);
    }
    
    $http.get('data/storagetree.json').success(function (data) {
        if(!data){
            return;
        }
        self.treeData = data;
    })
    
    $http.get('data/storagetype.json').success(function (data) {
        if(!data){
            return;
        }
        self.storageTypeArray = data;
    })

    $http.get('data/elements.json').success(function (data) {
        if(!data){
            return;
        }
        self.elementsArray = data;
    })

    $http.get('data/hosts.json').success(function (data) {
        if(!data){
            return;
        }
        self.hostArray = data;
        var tempArray = [];
        for (var i = 0; i < data.length; i++){
            var one = data[i];
            self.hostMap[one.id] = one.name;
            tempArray[i] = one.id;
        }
        self.hostListString = tempArray.join(";");
    })

    $http.get('data/servicestatus.json').success(function (data) {
        if(!data){
            return;
        }
        self.scheduleStatusArray = data.schedule;
        self.bdStatusArray = data.bd;

        statusArray = data.user;
        for (var i = 0; i < statusArray.length; i++){
            var one = statusArray[i];
            statusMap[one.value] = one.key;
        }
    })

    this.getStatusArray = function(){
        return statusArray;
    }

    this.getStatusString = function(status){
    	if(!status){
    		return "";
    	}
    	var ret = statusMap[status];
    	return ret?ret:"异常";
    };


    

}]);
