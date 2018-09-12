'use strict';

myApp.controller('DashboardCtrl', ['$scope', 'auth', function ($scope, auth) {
	$scope.dashboard = {};
	$scope.dashboard.hostLength = auth.hostArray.length;
	query();

	function query(){
		//query user
		auth.dashboardResource.get({}, function (resp) {
			auth.info(resp);
			$scope.dashboard = resp;
			$scope.dashboard.hostLength = auth.hostArray.length;
		}, function (err) {
			auth.error(err);
		});
	}

	$scope.formatSize = function(size){
		if(!size){
			return "";
		}
		if(size<1024){
			return size + "MB";
		}
		if(size<1024*1024){
			return (size/1024).toFixed(1) + "GB";
		}
		return (size/1024/1024).toFixed(1) + "TB";
	}

}]);