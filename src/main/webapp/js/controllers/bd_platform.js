'use strict';

myApp.controller('BDCloud', ['$scope', 'auth',  function ($scope, auth) {
	reSizeTable();
	
	$(window).resize(function() {
		if($("#myIframe").length){
			reSizeTable();
		}
	});

	function reSizeTable(){
		console.info($(window).height());
		console.info($("#myIframe").offset().top);
		var len1 = $(window).height() - $("#myIframe").offset().top - 5;
		len1  = len1 + 'px';
		$("#myIframe").css({"max-height": len1});
	}

}]);