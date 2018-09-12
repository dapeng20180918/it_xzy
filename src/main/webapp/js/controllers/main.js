'use strict';

myApp.controller('MainCtrl', ['$scope', '$state', 'auth', '$location', '$cookieStore', '$cookies', '$http', '$interval', '$timeout', 
		function ($scope, $state, auth, $location, $cookieStore, $cookies, $http, $interval, $timeout) {
	$scope.username = $cookies['User'];
	$scope.roleadmin = $cookies['Role']=='ADMIN';
	$scope.createUser = {};//Feedback
	$scope.messageList = [];
	$scope.selectedUser = {};//User

	$timeout(function(){
		query();
		auth.userResource.get({id: $scope.username}, {}, function (resp) {
			auth.info(resp);
			$scope.selectedUser = resp;
		}, function (err) {
			auth.error(err);
			$scope.selectedUser = {};
		});
	},2000);
	
	$scope.timer = $interval(function(){
		query();
	},30*1000);

	$scope.$on("$destroy", function() {
		$interval.cancel($scope.timer);
	})

	function query(){
		//reset
		$scope.messageList = [];

		//query message
		auth.messageResource.query({}, function (resp) {
			$scope.messageList = resp;
		}, function (err) {
			auth.error(err);
		});
	}

	$scope.signout = function(){
		$http.delete(auth.restHost+'/rest/user/logout', {}) 
        .success(function (data) {
            
        }).error(function(err) {  
        	console.error(err);
        }); 

		$cookieStore.remove('Authorization');
		$state.go("login");
	};

	$scope.createConfirm = function(){
		if(!$scope.createUser.name || $scope.createUser.name==""){
			auth.error("topic is null.");
			$scope.$broadcast("enableSubmitBtn");
			return;
		}
		if(!$scope.createUser.content || $scope.createUser.content==""){
			auth.error("content is null.");
			$scope.$broadcast("enableSubmitBtn");
			return;
		}
		//do something
		auth.info("createConfirm:");
		auth.info($scope.createUser);
		auth.feedbackResource.save({id:'create'}, $scope.createUser, function (resp) {
			auth.info(resp);
			$scope.$broadcast("enableSubmitBtn");
			$('#myFeedback').modal('hide');
			$scope.createUser = {};
		}, function (err) {
			$scope.$broadcast("enableSubmitBtn");
			auth.error(err);
		});
	}

	$scope.updateConfirm = function(){
		// do something
		auth.info("updateConfirm:");
		auth.messageResource.save({id:'update'}, {}, function (resp) {
			auth.info(resp);
			query();
			$scope.$broadcast("enableSubmitBtn");
		}, function (err) {
			$scope.$broadcast("enableSubmitBtn");
			query();
			auth.error(err);
		});
	}
	
}]);