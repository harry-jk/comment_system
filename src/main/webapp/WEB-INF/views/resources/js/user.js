var UserController = (function() {
    var controller = function(App) {
        var ctlr = App.controller('UserController',
                ['$scope', '$rootScope', '$cookies', 'REQUEST_USER', function($scope, $rootScope, $cookies, REQUEST) {
            var context = this;
            $rootScope.user = {
            }
            $rootScope.user.uid = parseInt($cookies.get('uid'));


            // Events
            $rootScope.$on('request::user::edit', function(event, user) {
                REQUEST.edit(
                    {},
                    {
                        uid: user.uid,
                        id: user.id,
                        password: user.password,
                        name: user.name,
                        description: user.description,
                        profile_image_url: user.profile_image_url
                    },
                    function(data) {
                        if(data.status != 200) {
                            $rootScope.$broadcast('user::edit::fail', data);
                            return;
                        }
                        data.user.password = user.password;
                        $rootScope.user = data.user;
                        context.user = data.user;
                        $rootScope.$broadcast('user::edit::success', data);
                    }
                );
            });

            $rootScope.$on('authorization::signin::success', function(event, data) {
                $rootScope.user = data.user;
                context.user = data.user;
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
