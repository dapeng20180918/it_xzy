'use strict';

myApp.controller('BBSCtrl', ['$scope', 'auth', function ($scope, auth) {
	$scope.users = [];
	$scope.searchKey = "";

	$scope.topic = {};
	$scope.replys = [];
	$scope.createReply = {};
	$scope.showTopic = 1;

	query();

	function query(){//topic
		//query user
		auth.feedbackResource.query({}, function (resp) {
			auth.info(resp);
			$scope.users = resp;
		}, function (err) {
			auth.error(err);
			$scope.users = [];
		});	
		
	}

	function queryReply(){
		//queryReply
		auth.replyResource.query({id:$scope.topic.id}, function (resp) {
			auth.info(resp);
			$scope.replys = resp;
		}, function (err) {
			auth.error(err);
			$scope.replys = [];
		});	
		
	}

	function replyCnt(){
		//queryReply
		auth.feedbackResource.save({id:'update'}, $scope.topic, function (resp) {

		}, function (err) {
			auth.error(err);
		});	
		
	}

	$scope.clickTopic = function(user){
		$scope.topic = user;
		$scope.showTopic = 0;
		//click++
		replyCnt();
		//query
		queryReply();
	}

	$scope.replyConfirm = function(){
		$scope.createReply.toplicId = $scope.topic.id;
		
		if(!$scope.createReply.content || $scope.reateReply.content==""){
			auth.error("content is null.");
			$scope.$broadcast("enableSubmitBtn");
			return;
		}
		auth.replyResource.save({id:'create'}, $scope.createReply, function (resp) {
			auth.info(resp);
			//query
			query();
			queryReply();
			$scope.$broadcast("enableSubmitBtn");
			$('#myReply').modal('hide');
		}, function (err) {
			$scope.$broadcast("enableSubmitBtn");
			auth.error(err);
		});
	}

}]);