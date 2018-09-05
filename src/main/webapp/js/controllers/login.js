'use strict';

myApp.controller('SigninCtrl', ['$scope','$state', '$location', '$http', 'auth', '$timeout', 'GlobalKey', '$cookies', 
		function ($scope, $state, $location, $http, auth, $timeout, GlobalKey, $cookies) {
	$scope.user = {};
	$scope.signinResult = "";
	
	var _err = $location.search().err;
	switch (parseInt(_err)){
		case 403:
			//user.signout();
			$scope.signinResult = "您尚未登录或登录超时";
			break;
	}
	
	//check sso
	var ssokey = $location.search()['ssoKey'];
	ssoLogin(ssokey);
	
	function ssoLogin(ssokey){
		if(!ssokey){
			return;
		}
		$http.get(auth.restHost+'/rest/user/ssologin?token='+ssokey, {}) 
        .success(function (data, status, headers, config) {
        	if(status==401){
        		$scope.signinResult = "登录失败,错误的SSO KEY";
        		return;
        	}

            $cookies['Authorization'] = ssokey;
            $cookies['User'] = data.name;
            $state.go("main.collect");
            
        }).error(function(err) {  
        	console.error(err);
        	$scope.signinResult = "登录失败,内部错误";
        	
        }); 
	}
	
	//check login 2 times
	if($cookies['Authorization']){
		$state.go("main.collect");
	}


	$scope.signin = function(){
		//do signin
		$http.post(auth.restHost+'/rest/user/login', $scope.user) 
        .success(function (data, status, headers, config) {
        	if(status==401){
        		$scope.signinResult = "登录失败,用户名密码错误";
        		$scope.$broadcast("enableSubmitBtn");
        		return;
        	}

            $cookies['Authorization'] = headers('Authorization');
            $cookies['User'] = data.name;
            if(data.role_id==1){
            	$cookies['Role'] = 'ADMIN';
            }else{
            	$cookies['Role'] = 'USER';
            }
            $state.go("main.collect");
            $scope.$broadcast("enableSubmitBtn");
        }).error(function(err) {  
        	console.error(err);
        	$scope.signinResult = "登录失败,内部错误";
        	$scope.$broadcast("enableSubmitBtn");
        }); 
	}

}]);