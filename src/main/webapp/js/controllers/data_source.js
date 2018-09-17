'use strict';

myApp.controller('DataSourceCtrl', ['$scope', 'auth', '$filter', function ($scope, auth, $filter) {
	$scope.users = [];
	$scope.searchKey = "";

	auth.reSizeTable();
	
	function reset(){
		$scope.selectedUser = {};
		$scope.createUser = {};
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
		auth.dataSourceResource.query({}, function (resp) {
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
		auth.dataSourceResource.save({id:'create'}, $scope.createUser, function (resp) {
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
		auth.dataSourceResource.save({id:'update'}, $scope.selectedUser, function (resp) {
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
		auth.dataSourceResource.remove({id:$scope.selectedUser.id}, {}, function (resp) {
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