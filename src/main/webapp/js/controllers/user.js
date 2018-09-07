'use strict';

myApp.controller('UserCtrl', ['$scope', 'auth', '$filter', function ($scope, auth, $filter) {
	$scope.statusArray = [];
	$scope.users = [];
	$scope.roleMap = {};
	$scope.roleArray = [];
	$scope.searchKey = "";

	$scope.getStatusPic = function(status){
		return auth.getUserStatusPic(status);
	}

	function reset(){
		$scope.selectedUser = {};
		$scope.createUser = {};
		$scope.ifSelectedUser = false;
		$scope.errorMsg = "";
		$("tr[value]").removeClass("success");
		$("input[name=myRadio]").attr("checked",false); 
	}

	$scope.statusArray = auth.getStatusArray();
	query();
	

	function initRoles(){
		$scope.roleMap = {};
		$scope.roleArray = [];
		auth.roleResource.query({}, function (resp) {
			auth.info(resp);
			
			var _roleMap = resp;
			for (var i = 0; i < _roleMap.length; i++){
				var one = _roleMap[i];
				$scope.roleMap[one.id] = one.name;
				var _role = {};
				_role["key"] = one.name;
				_role["value"] = one.id;
				$scope.roleArray.push(_role);
			}
			//reset default create role selected
			if($scope.roleArray.length>0)
				$scope.createUser.role_id = $scope.roleArray[0].value;
		}, function (err) {
			auth.error(err);
		});
		
	}

	$scope.clickRadio = function(user){
		$("tr[value]").removeClass("success");
		$("tr[value=\'" + user.id + "\']").addClass("success");
		$scope.selectedUser = angular.copy(user);
		$scope.selectedUser._create_date = $filter('date')(user.create_date, "yyyy-MM-dd HH:mm:ss");
		$scope.selectedUser.passwordConfirm = $scope.selectedUser.password;
		$scope.ifSelectedUser = true;
		auth.info("selected:");
		auth.info($scope.selectedUser);
	}

	$scope.getStatusString = function(id){
		return auth.getStatusString(id);
	}

	$scope.getRoleNameString = function(id){
		return $scope.roleMap[id];
	}

	function query(){
		//reset
		reset();

		//query user
		auth.userResource.query({}, function (resp) {
			auth.info(resp);
			$scope.users = resp;
		}, function (err) {
			auth.error(err);
			$scope.users = [];
		});
		
		//query role
		initRoles();
	
	}

	$scope.createConfirm = function(){
		//check
		if($scope.createUser.password!=$scope.createUser.passwordConfirm){
			$scope.errorMsg = "密码不一致";
			$scope.$broadcast("enableSubmitBtn");
			return;
		}
		//do something
		auth.info("createConfirm:");
		auth.info($scope.createUser);
		auth.userResource.save({id:'create'}, $scope.createUser, function (resp) {
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
		if($scope.roleArray.length>0)
			$scope.createUser.role_id = $scope.roleArray[0].value;
		$('#myInfoModal').modal('hide');
	}

	$scope.updateConfirm = function(){
		//check
		if($scope.selectedUser.password!=$scope.selectedUser.passwordConfirm){
			$scope.errorMsg = "密码不一致";
			$scope.$broadcast("enableSubmitBtn");
			return;
		}
		//do something
		auth.info("updateConfirm:");
		auth.info($scope.selectedUser);
		auth.userResource.save({id:'update'}, $scope.selectedUser, function (resp) {
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
		auth.userResource.remove({id:$scope.selectedUser.id}, {}, function (resp) {
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