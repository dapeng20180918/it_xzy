'use strict';

myApp.controller('EventCtrl', ['$scope', 'auth', '$filter', function ($scope, auth, $filter) {
	$scope.users = [];
	$scope.searchKey = "";
	$scope.startTime = 0;
	$scope.endTime = 0;

	$scope.selectdate = function(){
		var start = $("#dateS").val();
		var end = $("#dateE").val();
		start = start==""?'1970-1-1':start;
		end = end==""?'2050-1-1':end;
		start = new Date(start);
		start.setHours(start.getHours() - 8);
		$scope.startTime = start.getTime();
		end = new Date(end);
		end.setHours(end.getHours() - 8);
		$scope.endTime = end.getTime();
	}
	query();

	function query(){
		//query user
		auth.eventResource.query({}, function (resp) {
			auth.info(resp);
			$scope.users = resp;
		}, function (err) {
			auth.error(err);
			$scope.users = [];
		});	
		$scope.selectdate();
	}

	$scope.myFilter = function(data){
		var thisTime = new Date(data.create_date).getTime();
		if(thisTime <= $scope.endTime && thisTime >= $scope.startTime){
			return true;
		}
		return false;
	};
}]);