'use strict';

myApp.controller('CollectCtrl', ['$scope', '$http', 'auth', function ($scope, $http, auth) {
	$scope.cityArray = [];
	$scope.elementsArray = auth.elementsArray;
	$scope.datestart = "";
	$scope.dateend = "";
	$scope.citys = 0;
	$scope.elements = 0;
	$scope.dataSize = 0;//default:MB 1month*1city*1elem -->  10M data   1M=3600*24*31
	$scope.step = 1;
	
	$scope.users = [];
	$scope.searchKey = "";
	$scope.strategyArray = [];
	$scope.strategyMap = {};
	$scope.taskStatusArray = auth.bdStatusArray;
	$scope.taskStatusMap = {};
	$scope.treeData = [];
	$scope.expandedNodes = [];
	$scope.hostMap = auth.hostMap;
	
	auth.reSizeTable();
	
	function getDataSize(){//MB
		var start = $scope.datestart;
		var end = $scope.dateend;
		if(start=="" || end==""){
			return 0;
		}
		var timeDiff = (new Date(end).getTime() - new Date(start).getTime())/1000; //s
		if(timeDiff<=0){
			return 0;
		}
		var out =  $scope.citys*$scope.elements*timeDiff*10/(3600*24*30);
		if (out<1){
			return 1;
		}
		return Math.round(out);

	}

	$("#siteId").on("click", function(){
		var arr = $("[name = \'station_ids[]\']:checkbox");
		if($(this).is(":checked")){
	        arr.prop("checked", true)
	    }else {
	        arr.prop("checked", false)
	    }
	});
	
	$("#selectAll").on("click", function(){
		var arr = $("[name = \'elements[]\']:checkbox");
		if($(this).is(":checked")){
	        arr.prop("checked", true)
	    }else {
	        arr.prop("checked", false)
	    }
	});
	

	$scope.search = function(){
		$scope.datestart = $("#dateS").val();
		$scope.dateend = $("#dateE").val();
		$scope.citys = $("[name = \'station_ids[]\']:checked").length;
		$scope.elements = $("[name = \'elements[]\']:checked").length;
		$scope.dataSize = getDataSize();

		auth.info("start: " + $scope.datestart);
		auth.info("end: " + $scope.dateend);
		auth.info("cityLength: " + $scope.citys);
		auth.info("elementLength: " + $scope.elements);
		auth.info("dataSize(MB): " + $scope.dataSize);
		
		$scope.step = 3;

	}

	$scope.getStatusPic = function(status){
		return auth.getStatusPic(status);
	}
	
	$scope.choose = function(name){
		var filename = "data/citys/" + name + ".json";
	    $http.get(filename).success(function (data) {
	    	$scope.cityArray = data.stations;
	    	$("#siteId").prop("checked", false);
	    })
	}
	$scope.choose('beijing');
	
	$scope.treeOptions = {  
			nodeChildren: "threads",  
			dirSelectable: true,
			isLeaf: function(node) {
				return !node.threads;
			},
			injectClasses: {  
				ul: "a1",  
				li: "a2",  
				liSelected: "a7",  
				iExpanded: "a3",  
				iCollapsed: "a4",  
				iLeaf: "a5",  
				label: "a6",  
				labelSelected: "a8"  
			}  
		};

	$scope.showSelected = function(sel) {
		$scope.selectedNode = sel;
	};
	
	for (var i = 0; i < $scope.taskStatusArray.length; i++){
		var one = $scope.taskStatusArray[i];
		$scope.taskStatusMap[one.value] = one.key;
	}

	query();

	function reset(){
		$scope.selectedUser = {};
		$scope.step = 1;
		$scope.createUser = {
			data_get : 50,
			data_excute : 1,
			host_max_cnt : 5,
			thread_max_cnt : 10,
			no_data_sleep : 5,
			one_data_sleep : 5,
			task_type : "数据加工",
			host_all_str : auth.hostListString
		};
		$scope.ifSelectedUser = false;
		$scope.ifFinishedTask = false;
		$scope.errorMsg = "";
		$("tr[value]").removeClass("success");
		$("input[name=myRadio]").attr("checked",false); 
	}

	$scope.clickRadio = function(user){
		$("tr[value]").removeClass("success");
		$("tr[value=\'" + user.id + "\']").addClass("success");
		$scope.selectedUser = angular.copy(user);
		$scope.ifSelectedUser = true;
		if($scope.selectedUser.status==2){
			$scope.ifFinishedTask = true;
		}else{
			$scope.ifFinishedTask = false;
		}
		auth.info("selected:");
		auth.info($scope.selectedUser);

		if($scope.selectedUser.hosts){
			$scope.treeData = $scope.selectedUser.hosts;
		}else{
			$scope.treeData = [];	
		}

		$scope.expandedNodes = [];
		for(var i in $scope.treeData) {
			var node1 = $scope.treeData[i];
			$scope.expandedNodes.push(node1);
		}
		
		$scope.resultPicture = "/images/result/" + $scope.selectedUser.id%10 + ".jpg";
	}

	$scope.setCreatePanel = function(id){
		$("div.tab-pane[type=create]").removeClass("active");
		$("#"+id).addClass("active");
	}

	$scope.setInfoPanel = function(id){
		$("div.tab-pane[type=info]").removeClass("active");
		$("#"+id).addClass("active");
	}

	function query(){
		//reset
		reset();
		//query user
		auth.AnalyzeTaskResource.query({type:'数据加工'}, function (resp) {
			auth.info(resp);
			$scope.users = resp;
		}, function (err) {
			auth.error(err);
			$scope.users = [];
		});

		//query data 
		$scope.strategyArray = [];
		$scope.strategyMap = {};
		auth.AnalyzeStrategyResource.query({type:'数据加工'}, function (resp) {
			auth.info(resp);
			$scope.strategyArray = resp;
			
			for (var i = 0; i < resp.length; i++){
				var one = resp[i];
				$scope.strategyMap[one.id] = one.name;
			}
			//reset default create role selected
			if($scope.strategyArray.length>0)
				$scope.createUser.strategy_id = $scope.strategyArray[0].id;
		}, function (err) {
			auth.error(err);
			$scope.dataSourceArray = [];
		});
	}

	$scope.createConfirm = function(){
		//do something
		auth.info("createConfirm:");
		auth.info($scope.createUser);

		//create data
		auth.dataStorageInfoResource.save({id:'create'}, {
				data_size:$scope.dataSize,
				parent:502,
				storage_type:"关系数据库表" }, function (resp) {
			
			auth.info(resp);
			$scope.createUser.result_path = resp.storage_location;
			//create task
			auth.AnalyzeTaskResource.save({id:'create'}, $scope.createUser, function (resp) {
				auth.info(resp);
				//query
				query();
				$scope.$broadcast("enableSubmitBtn");
				$('#myCreateModal').modal('hide');
			}, function (err) {
				$scope.$broadcast("enableSubmitBtn");
				auth.error(err);
			});

		}, function (err) {
			$scope.$broadcast("enableSubmitBtn");
			auth.error(err);
		});
		
	}

}]);