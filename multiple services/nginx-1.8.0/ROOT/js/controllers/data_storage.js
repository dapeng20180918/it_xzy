'use strict';

myApp.controller('DataStorageCtrl', ['$scope', 'auth',  function ($scope, auth) {
	$scope.treeData = auth.treeData;
	$scope.selectName = "资料名称";
	$scope.dsinfo = [];

	$scope.treeOptions = {  
		nodeChildren: "child",  
		dirSelectable: true,
		isLeaf: function(node) {
			return node.id > 0;
		},
		injectClasses: {  
			ul: "a1",  
			li: "a2",  
			liSelected: "a7",  
			iExpanded: "a3",  
			iCollapsed: "a4",  
			iLeaf: "a5",  
			label: "a6",  
			labelSelected: "a8"  
		}  
	};

	$scope.showSelected = function(sel) {
		$scope.selectedNode = sel;
		$scope.selectName = sel.name;
		if(sel.id>0){//leaf
			//rest api
			auth.dataStorageInfoResource.query({id:sel.id}, function (resp) {
				auth.info(resp);
				$scope.dsinfo = resp;
			}, function (err) {
				auth.error(err);
				$scope.dsinfo = [];
			});
		}
	};

	$scope.formatSize = function(size){
		if(!size){
			return "";
		}
		if(size<1024){
			return size + "MB";
		}
		return (size/1024).toFixed(1) + "GB";
	}
}]);