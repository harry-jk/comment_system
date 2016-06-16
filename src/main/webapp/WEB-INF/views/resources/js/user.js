var UserController = (function() {
    var controller = function(App) {
        var ctlr = App.controller('UserController',
                ['$scope', '$rootScope', '$cookies', 'REQUEST_USER', function($scope, $rootScope, $cookies, REQUEST) {
            $rootScope.user = {
            }
            $rootScope.user.uid = $cookies.get('uid');

        }]);

        ctlr.factory('REQUEST_USER', ['$resource', function($resource) {
            return $resource(
                '',
                {},
                {
                    edit: {
                        method: 'POST',
                        url: '/users'
                    }
                }
            );
        }]);
    };

    return controller;
})();
