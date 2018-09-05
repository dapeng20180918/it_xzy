'use strict';

myApp.controller('MainCtrl', ['$scope', '$state', 'auth', '$location', '$cookieStore', '$cookies', '$http', 
		function ($scope, $state, auth, $location, $cookieStore, $cookies, $http) {
	$scope.username = $cookies['User'];
	$scope.roleadmin = $cookies['Role']=='ADMIN';
	$scope.signout = function(){
		$http.delete(auth.restHost+'/rest/user/logout', {}) 
        .success(function (data) {
            
        }).error(function(err) {  
        	console.error(err);
        }); 

		$cookieStore.remove('Authorization');
		$state.go("login");
	};
	if(!$scope.roleadmin){
		$cookieStore.remove('Authorization');
        $location.url('/login?err=403');
	}
	
}]);