'use strict';

myApp.controller('DataScheduleCtrl', ['$scope', 'auth', function ($scope, auth) {
	$scope.users = [];
	$scope.searchKey = "";
	$scope.scheduleStatusArray = auth.scheduleStatusArray;
	$scope.scheduleStatusMap = {};

	$scope.dataCollectorArray = [];
	$scope.dataCollectorMap = {};

	auth.reSizeTable();
	
	for (var i = 0; i < $scope.scheduleStatusArray.length; i++){
		var one = $scope.scheduleStatusArray[i];
		$scope.scheduleStatusMap[one.value] = one.key;
	}

	$scope.getStatusPic = function(status){
		return auth.getUserStatusPic(status);
	}
	
	query();

	function reset(){
		$scope.selectedUser = {};
		$scope.createUser = {};
		$scope.ifSelectedUser = false;
		$scope.errorMsg = "";
		$("tr[value]").removeClass("success");
		$("input[name=myRadio]").attr("checked",false); 
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
		auth.dataCollectScheduleResource.query({}, function (resp) {
			auth.info(resp);
			$scope.users = resp;
		}, function (err) {
			auth.error(err);
			$scope.users = [];
		});

		//query data collector
		$scope.dataCollectorArray = [];
		$scope.dataCollectorMap = {};
		auth.dataCollectorResource.query({}, function (resp) {
			auth.info(resp);
			$scope.dataCollectorArray = resp;
			for (var i = 0; i < resp.length; i++){
				var one = resp[i];
				$scope.dataCollectorMap[one.id] = one.name;
			}
			//reset default create role selected
			if($scope.dataCollectorArray.length>0)
				$scope.createUser.datacollector_id = $scope.dataCollectorArray[0].id;
		}, function (err) {
			auth.error(err);
			$scope.dataSourceArray = [];
		});
	}

	$scope.createConfirm = function(){
		//do something
		auth.info("createConfirm:");
		auth.info($scope.createUser);
		auth.dataCollectScheduleResource.save({id:'create'}, $scope.createUser, function (resp) {
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
		if($scope.dataCollectorArray.length>0)
				$scope.createUser.datacollector_id = $scope.dataCollectorArray[0].id;
		$('#myInfoModal').modal('hide');
	}

	$scope.updateConfirm = function(){
		//do something
		auth.info("updateConfirm:");
		auth.info($scope.selectedUser);
		auth.dataCollectScheduleResource.save({id:'update'}, $scope.selectedUser, function (resp) {
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
		auth.dataCollectScheduleResource.remove({id:$scope.selectedUser.id}, {}, function (resp) {
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