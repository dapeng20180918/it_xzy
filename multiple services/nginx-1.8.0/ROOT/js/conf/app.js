'use strict';

var myApp = angular.module("myApp", ['ui.router', 'ngResource', 'treeControl', 'ngCookies']);
myApp.config(["$stateProvider", "$urlRouterProvider", '$httpProvider', function ($stateProvider, $urlRouterProvider, $httpProvider) {
	$httpProvider.interceptors.push('authHttpInterceptor'); 
	//default
	//$urlRouterProvider.otherwise("user");
	$urlRouterProvider.when("", "/login");
    
	//router
	$stateProvider
	.state("login", {
        url: "/login",
        templateUrl: "views/service/login.html",
        controller: 'SigninCtrl'
    })
	/* .state("main", {
        url: "/main",
        template: '<div ng-include src="\'views/user/header.html\'"></div> \
					<div ng-include src="\'views/user/navbar.html\'"></div>',
        controller: 'MainCtrl'
    })*/
    .state("service", {
        url: "/service",
        template: '<div ng-include src="\'views/service/header.html\'"></div> \
					<div ng-include src="\'views/service/navbar.html\'"></div>',
        controller: 'MainCtrl'
    })

    //user
	/*.state("main.user", {
        url: "/user",
        templateUrl: "views/user/user.html",
        controller: 'UserCtrl'
    })
    .state("main.role", {
        url: "/role",
        templateUrl: "views/user/role.html",
        controller: 'RoleCtrl'
    })
    .state("main.event", {
        url: "/event",
        templateUrl: "views/user/event.html",
        controller: 'EventCtrl'
    })
    .state("main.collect", {
        url: "/collect",
        templateUrl: "views/user/collect.html",
        controller: 'CollectCtrl'
    })
    .state("main.analyze", {
        url: "/analyze",
        templateUrl: "views/user/analyze.html",
        controller: 'AnalyzeCtrl'
    })*/

    //service-datasource
    .state("service.data_source", {
        url: "/data_source",
        templateUrl: "views/service/data_source.html",
        controller: 'DataSourceCtrl'
    })
    .state("service.data_collect", {
        url: "/data_collect",
        templateUrl: "views/service/data_collect.html",
        controller: 'DataCollectCtrl'
    })
    .state("service.data_storage", {
        url: "/data_storage",
        templateUrl: "views/service/data_storage.html",
        controller: 'DataStorageCtrl'
    })
    .state("service.data_schedule", {
        url: "/data_schedule",
        templateUrl: "views/service/data_schedule.html",
        controller: 'DataScheduleCtrl'
    })

    // service-bigdata
    .state("service.bd_adapter", {
        url: "/bd_adapter",
        templateUrl: "views/service/bd_adapter.html",
        controller: 'BDAdapterCtrl'
    })
    .state("service.bd_algorithm", {
        url: "/bd_algorithm",
        templateUrl: "views/service/bd_algorithm.html",
        controller: 'BDAlgorithmCtrl'
    })
    .state("service.bd_schedule", {
        url: "/bd_schedule",
        templateUrl: "views/service/bd_schedule.html",
        controller: 'BDScheduleCtrl'
    })

    //link others
    .state("service.cloud_platform", {
        url: "/cloud_platform",
        templateUrl: "views/service/cloud_platform.html"
    })
    .state("service.bigdata_platform", {
        url: "/bigdata_platform",
        templateUrl: "views/service/bigdata_platform.html",
        controller: 'BDSettingCtrl'
    });

}]);
