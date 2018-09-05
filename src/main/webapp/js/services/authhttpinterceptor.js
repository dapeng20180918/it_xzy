'use strict';

myApp.factory('authHttpInterceptor',  ['$q', '$location', 'GlobalKey', '$cookieStore', function ($q, $location, GlobalKey, $cookieStore) {
    return {
        'request': function (config) {
            var _url =  config.url;
            if(_url.indexOf('/rest/') === 0){
                _url = _url.indexOf('?') > 0 ? _url+'&' : _url + '?';
                _url = _url+'rnd='+Math.random();
            }

            config.url = _url;
            return config;
        },

        'responseError': function(resp) {
            // debugger
            if(resp.status === 403){
                $cookieStore.remove('Authorization');
                $location.url('/login?err=403');
            }
            return resp;
        }
    };
}]);
