var UserController = (function() {
    var controller = function(App) {
        var ctlr = App.controller('UserController',
                ['$scope', '$rootScope', '$cookies', 'REQUEST_USER', function($scope, $rootScope, $cookies, REQUEST) {
            var context = this;
            $rootScope.user = {
            }
            $rootScope.user.uid = parseInt($cookies.get('uid'));


            // Events
            $rootScope.$on('authorization::signin::success', function(event, data) {
                $rootScope.user = data.user;
                context.user = data.user;
                console.log($rootScope.user);
            });
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
