'use strict';

myApp.controller('BDAdapterCtrl', ['$scope', 'auth', function ($scope, auth) {
	$scope.users = [];
	$scope.searchKey = "";
	$scope.storageTypeArray = auth.storageTypeArray;
	
	auth.reSizeTable();
	
	function reset(){
		$scope.selectedUser = {};
		$scope.createUser = {type:"关系数据库表"};
		$scope.ifSelectedUser = false;
		$scope.errorMsg = "";
		$("tr[value]").removeClass("success");
		$("input[name=myRadio]").attr("checked",false); 
	}

	query();

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
		auth.AnalyzeAdapterResource.query({}, function (resp) {
			auth.info(resp);
			$scope.users = resp;
		}, function (err) {
			auth.error(err);
			$scope.users = [];
		});
	
	}

	$scope.createConfirm = function(){
		//do something
		auth.info("createConfirm:");
		auth.info($scope.createUser);
		auth.AnalyzeAdapterResource.save({id:'create'}, $scope.createUser, function (resp) {
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
		$('#myInfoModal').modal('hide');
	}

	$scope.updateConfirm = function(){
		//do something
		auth.info("updateConfirm:");
		auth.info($scope.selectedUser);
		auth.AnalyzeAdapterResource.save({id:'update'}, $scope.selectedUser, function (resp) {
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
		auth.AnalyzeAdapterResource.remove({id:$scope.selectedUser.id}, {}, function (resp) {
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