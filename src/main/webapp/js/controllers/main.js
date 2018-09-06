'use strict';

myApp.controller('MainCtrl', ['$scope', '$state', 'auth', '$location', '$cookieStore', '$cookies', '$http', 
		function ($scope, $state, auth, $location, $cookieStore, $cookies, $http) {
	$scope.username = $cookies['User'];
	$scope.roleadmin = $cookies['Role']=='ADMIN';
	$scope.createUser = {};

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
	
}]);