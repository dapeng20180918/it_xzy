'use strict';

myApp.controller('DataCollectCtrl', ['$scope', 'auth', '$filter', function ($scope, auth, $filter) {
	$scope.users = [];
	$scope.searchKey = "";
	$scope.storageTypeArray = auth.storageTypeArray;
	$scope.dataSourceArray = [];
	$scope.dataSourceMap = {};

	$scope.treeData = [];
	$scope.treeData = auth.treeData;
	$scope.showCreateTree = false;

	$scope.treeOptions = {  
		nodeChildren: "child",  
		dirSelectable: true,
		isLeaf: function(node) {
			return node.id > 0;
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

	query();

	$scope.showTree = function (){
		$scope.showCreateTree = true;
	}

	$scope.showSelected = function(sel) {
		$scope.selectedNode = sel;
		if($scope.selectedNode.id>0){
			var name = $scope.selectedNode.parent + " → " + $scope.selectedNode.name;
			$scope.createUser.tree_name = name;
			$scope.showCreateTree = false;
		}
	};

	function reset(){
		$scope.selectedUser = {};
		$scope.showCreateTree = false;
		$scope.createUser = {storage_type:"关系数据库表"};
		$scope.ifSelectedUser = false;
		$scope.errorMsg = "";
		$("tr[value]").removeClass("success");
		$("input[name=myRadio]").attr("checked",false); 
	}

	$scope.fileChanged = function(ele){
    	$scope.files = ele.files;  
    	var file = document.querySelector('input[type=file]').files[0];  
    	var filename = $scope.files[0].name; 

    	$scope.selectedUser.jar_name = filename;
    	$scope.createUser.jar_name = filename;
	}
	
	$scope.clickRadio = function(user){
		$("tr[value]").removeClass("success");
		$("tr[value=\'" + user.id + "\']").addClass("success");
		$scope.selectedUser = angular.copy(user);
		$scope.ifSelectedUser = true;
		auth.info("selected:");
		auth.info($scope.selectedUser);
	}

	function query(){
		//reset
		reset();
		//query user
		auth.dataCollectorResource.query({}, function (resp) {
			auth.info(resp);
			$scope.users = resp;
		}, function (err) {
			auth.error(err);
			$scope.users = [];
		});

		//query data source
		$scope.dataSourceArray = [];
		$scope.dataSourceMap = {};
		auth.dataSourceResource.query({}, function (resp) {
			auth.info(resp);
			$scope.dataSourceArray = resp;
			
			for (var i = 0; i < resp.length; i++){
				var one = resp[i];
				$scope.dataSourceMap[one.id] = one.name;
			}
			//reset default create role selected
			if($scope.dataSourceArray.length>0)
				$scope.createUser.datasource_id = $scope.dataSourceArray[0].id;
		}, function (err) {
			auth.error(err);
			$scope.dataSourceArray = [];
		});
	}
	
	$scope.createConfirm = function(){
		//do something
		auth.info("createConfirm:");
		auth.info($scope.createUser);
		auth.dataCollectorResource.save({id:'create'}, $scope.createUser, function (resp) {
			auth.info(resp);
			//query
			query();
			$scope.$broadcast("enableSubmitBtn");
			$('#myCreateModal').modal('hide');
		}, function (err) {
			$scope.$broadcast("enableSubmitBtn");
			auth.error(err);
		});
	}

	$scope.updateCancel= function(){
		reset();
		if($scope.dataSourceArray.length>0)
			$scope.createUser.datasource_id = $scope.dataSourceArray[0].id;
		$('#myInfoModal').modal('hide');
	}

	$scope.updateConfirm = function(){
		//do something
		auth.info("updateConfirm:");
		auth.info($scope.selectedUser);
		auth.dataCollectorResource.save({id:'update'}, $scope.selectedUser, function (resp) {
			auth.info(resp);
			//query
			query();
			$scope.$broadcast("enableSubmitBtn");
			$('#myInfoModal').modal('hide');
		}, function (err) {
			$scope.$broadcast("enableSubmitBtn");
			auth.error(err);
		});
	}

	$scope.deleteConfirm = function(){
		//do something
		auth.info("deleteConfirm:");
		auth.info($scope.selectedUser);
		auth.dataCollectorResource.remove({id:$scope.selectedUser.id}, {}, function (resp) {
			auth.info(resp);
			//query
			query();
			$scope.$broadcast("enableSubmitBtn");
			$('#myDeleteModal').modal('hide');
		}, function (err) {
			$scope.$broadcast("enableSubmitBtn");
			auth.error(err);
		});
	}

}]);