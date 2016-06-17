var UserController = (function() {
    var controller = function(App) {
        var ctlr = App.controller('UserController',
                ['$scope', '$rootScope', '$cookies', 'REQUEST_USER', function($scope, $rootScope, $cookies, REQUEST) {
            var context = this;
            $rootScope.user = {
            }
            $rootScope.user.uid = parseInt($cookies.get('uid'));


            function requestEdit(user, image) {
                if(image != null) user.profile_image_url = image;
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
            }

            // Events
            $rootScope.$on('request::user::edit', function(event, user) {
                if(user.profile_image_file == null) {
                    requestEdit(user, null);
                } else {
                    REQUEST.image(
                        {},
                        {
                            file: user.profile_image_file
                        },
                        function(data) {
                            console.log(data);
                            if(data.status != 200) {
                                $rootScope.$broadcast('user::edit::fail', data);
                                return;
                            }
                            requestEdit(user, data.image);
                        }
                    );
                }
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
                    },
                    image: {
                        method: 'POST',
                        url: '/files/profile'
                    }
                }
            );
        }]);
    };

    return controller;
})();
