'use strict';

myApp.controller('MainCtrl', ['$scope', '$state', 'auth', '$location', '$cookieStore', '$cookies', '$http', '$interval', '$timeout', 
		function ($scope, $state, auth, $location, $cookieStore, $cookies, $http, $interval, $timeout) {
	$scope.username = $cookies['User'];
	$scope.roleadmin = $cookies['Role']=='ADMIN';
	$scope.createUser = {};
	$scope.messageList = [];

	$timeout(function(){
    	query();
	},3000);
	
	$scope.timer = $interval(function(){
		query();
	},20*1000);

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