'use strict';

myApp.controller('RoleCtrl', ['$scope', 'auth', '$filter', '$window', function ($scope, auth, $filter, $window) {
	$scope.users = [];
	$scope.ruleArray = [];
	
	auth.reSizeTable();
	
	function reset(){
		$scope.selectedUser = {};
		$scope.createUser = {};
		$scope.ifSelectedUser = false;
		$scope.errorMsg = "";
		$("tr[value]").removeClass("success");
		$("input[name=myRadio]").attr("checked",false); 
	}
	
	initRules();
	query();

	$("#createRoleCheckAll").on("click", function(){
		var arr = $("[name = \'createRoleCB\']:checkbox");
		if($(this).is(":checked")){
	        arr.prop("checked", true)
	    }else {
	        arr.prop("checked", false)
	    }
	});
	
	$("#updateRoleCheckAll").on("click", function(){
		var arr = $("[name = \'updateRoleCB\']:checkbox");
		if($(this).is(":checked")){
	        arr.prop("checked", true)
	    }else {
	        arr.prop("checked", false)
	    }
	});
	
	function initRules(){
		$scope.ruleArray = [];
		auth.ruleResource.query({}, function (resp) {
			$scope.ruleArray = resp;
			auth.info(resp);
		}, function (err) {
			auth.error(err);
		});
	}
	
	$scope.ifChecked = function(id, str){
		if(!str){
			return false;
		}
		return (str.split(";").indexOf(id.toString())!=-1);
	}

	$scope.clickRadio = function(user){
		$("tr[value]").removeClass("success");
		$("tr[value=\'" + user.id + "\']").addClass("success");
		$scope.selectedUser = angular.copy(user);
		$scope.selectedUser._create_date = $filter('date')(user.create_date, "yyyy-MM-dd HH:mm:ss");
		$scope.ifSelectedUser = true;
		auth.info("selected:");
		auth.info($scope.selectedUser);
	}

	function query(){
		//reset
		reset();
		//query
		auth.roleResource.query({}, function (resp) {
			auth.info(resp);
			$scope.users = resp;
		}, function (err) {
			auth.error(err);
			$scope.users = [];
		});
		
	}

	$scope.createConfirm = function(){
		//do something
		var arr = $('input[type=checkbox]:checked[name=\'createRoleCB\']');
		var outstr = "";
		for (var i = 0; i < arr.length; i++){
			outstr += arr[i].defaultValue + ";"
		}
		$scope.createUser.rule_str = outstr;

		//do something
		auth.info("createConfirm:");
		auth.info($scope.createUser);
		auth.roleResource.save({id:'create'}, $scope.createUser, function (resp) {
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
		var arr = $('input[type=checkbox]:checked[name=\'updateRoleCB\']');
		arr.prop("checked", false);
		// $window.location.reload();
		$('#myInfoModal').modal('hide');
	}

	$scope.updateConfirm = function(){
		//do something
		var arr = $('input[type=checkbox]:checked[name=\'updateRoleCB\']');
		var outstr = "";
		for (var i = 0; i < arr.length; i++){
			outstr += arr[i].defaultValue + ";"
		}
		$scope.selectedUser.rule_str = outstr;

		auth.info("updateConfirm:");
		auth.info($scope.selectedUser);
		auth.roleResource.save({id:'update'}, $scope.selectedUser, function (resp) {
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
		auth.roleResource.remove({id:$scope.selectedUser.id}, {}, function (resp) {
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