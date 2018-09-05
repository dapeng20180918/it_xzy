'use strict';

myApp.controller('BDSettingCtrl', ['$scope', 'auth', function ($scope, auth) {
	$scope.hostArray = auth.hostArray;
	query();
	
	function reset(){
		$scope.selectedUser = {};
	}

	$scope.setPanel = function(id){
		$("div.tab-pane").removeClass("active");
		$("#"+id).addClass("active");
	}

	function query(){
		//reset
		reset();

		//query
		auth.AnalyzeSettingResource.get({}, function (resp) {
			auth.info(resp);
			$scope.selectedUser = resp;
		}, function (err) {
			auth.error(err);
			$scope.selectedUser = {};
		});
	
	}

	$scope.updateConfirm = function(){
		//do something
		auth.info("updateConfirm:");
		auth.info($scope.selectedUser);
		auth.AnalyzeSettingResource.save({id:'update'}, $scope.selectedUser, function (resp) {
			auth.info(resp);
			//query
			$scope.$broadcast("enableSubmitBtn");
			query();
		}, function (err) {
			$scope.$broadcast("enableSubmitBtn");
			auth.error(err);
		});
	}
}]);