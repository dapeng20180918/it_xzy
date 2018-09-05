'use strict';

myApp.controller('BDScheduleCtrl', ['$scope', 'auth',  function ($scope, auth) {

$scope.users = [];
	$scope.searchKey = "";
	$scope.strategyArray = [];
	$scope.strategyMap = {};
	$scope.taskStatusArray = auth.bdStatusArray;
	$scope.taskStatusMap = {};
	$scope.treeData = [];
	$scope.expandedNodes = [];
	$scope.hostMap = auth.hostMap;
	$scope.resultPicture = "/images/result/1.jpg";

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

	$scope.setInfoPanel = function(id){
		$("div.tab-pane[type=info]").removeClass("active");
		$("#"+id).addClass("active");
	}

	function query(){
		//reset
		reset();
		//query user
		auth.AnalyzeTaskResource.query({}, function (resp) {
			auth.info(resp);
			$scope.users = resp;
		}, function (err) {
			auth.error(err);
			$scope.users = [];
		});

		//query data 
		$scope.strategyArray = [];
		$scope.strategyMap = {};
		auth.AnalyzeStrategyResource.query({}, function (resp) {
			auth.info(resp);
			$scope.strategyArray = resp;
			
			for (var i = 0; i < resp.length; i++){
				var one = resp[i];
				$scope.strategyMap[one.id] = one.name;
			}

		}, function (err) {
			auth.error(err);
			$scope.dataSourceArray = [];
		});
	}


}]);