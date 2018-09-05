'use strict';

/*myApp.directive('submitButton', function() {
	return {
		scope: {
			clickAndDisable: '&'
		},
		link: function(scope, iElement, iAttrs) {
			iElement.bind('click', function() {
				iElement.prop('disabled',true);
				scope.clickAndDisable().finally(function() {
					iElement.prop('disabled',false);
				})
			});
		}
	};
})*/
myApp.directive('submitButton', function() {
	return {
		link : function postLink(scope, element) {
			element.on('click', function(event) {
				$(this).attr('disabled', 'disabled');
				return true;
			});

			scope.$on('enableSubmitBtn', function() {
				element.removeAttr('disabled');
			});

		}
	};
});
