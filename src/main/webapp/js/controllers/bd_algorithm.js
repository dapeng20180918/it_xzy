'use strict';

myApp.controller('BDAlgorithmCtrl', ['$scope', 'auth', function ($scope, auth) {
$scope.users = [];
	$scope.searchKey = "";
	$scope.adapterArray = [];
	$scope.adapterMap = {};

	query();

	function reset(){
		$scope.selectedUser = {};
		$scope.createUser = {class_type:"数据加工"};
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
		auth.AnalyzeStrategyResource.query({}, function (resp) {
			auth.info(resp);
			$scope.users = resp;
		}, function (err) {
			auth.error(err);
			$scope.users = [];
		});

		//query data source
		$scope.adapterArray = [];
		$scope.adapterMap = {};
		auth.AnalyzeAdapterResource.query({}, function (resp) {
			auth.info(resp);
			$scope.adapterArray = resp;
			
			for (var i = 0; i < resp.length; i++){
				var one = resp[i];
				$scope.adapterMap[one.id] = one.name;
			}
			//reset default create role selected
			if($scope.adapterArray.length>0)
				$scope.createUser.adapter_id = $scope.adapterArray[0].id;
		}, function (err) {
			auth.error(err);
			$scope.dataSourceArray = [];
		});
	}
	
	$scope.createConfirm = function(){
		//do something
		auth.info("createConfirm:");
		auth.info($scope.createUser);
		auth.AnalyzeStrategyResource.save({id:'create'}, $scope.createUser, function (resp) {
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
		if($scope.adapterArray.length>0)
			$scope.createUser.adapter_id = $scope.adapterArray[0].id;
		$('#myInfoModal').modal('hide');
	}

	$scope.updateConfirm = function(){
		//do something
		auth.info("updateConfirm:");
		auth.info($scope.selectedUser);
		auth.AnalyzeStrategyResource.save({id:'update'}, $scope.selectedUser, function (resp) {
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
		auth.AnalyzeStrategyResource.remove({id:$scope.selectedUser.id}, {}, function (resp) {
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