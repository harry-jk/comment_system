var AuthorizationController = (function() {
    var controller = function(App) {
        var ctlr = App.controller('AuthorizationController',
                ['$scope', '$rootScope', '$cookies', 'REQUEST_AUTHORIZATION', function($scope, $rootScope, $cookies, REQUEST) {
            var context = this;
            $rootScope.authorization = {
            }
            $rootScope.authorization.uid = parseInt($cookies.get('uid'));
            $rootScope.sign = this.sign;


            function signin(user) {
                if(user.id == null || user.id.length < 3 || user.id.length > 20) {
                    $rootScope.$broadcast('authorization::signin::fail', null);
                    return;
                }
                if(user.password == null || user.password.length < 3 || user.password.length > 20) {
                    $rootScope.$broadcast('authorization::signin::fail', null);
                    return;
                }
                REQUEST.signin(
                    {},
                    {
                        id: user.id,
                        password: user.password
                    },
                    function(data) {
                        if(data.status == 200) {
                            $rootScope.authorization.uid = data.user.uid;
                            context.setSign(true);
                            $cookies.put('uid', data.user.uid);
                            data.user.password = user.password;
                            $rootScope.$broadcast('authorization::signin::success', data);
                        } else {
                            $rootScope.$broadcast('authorization::signin::fail', data);
                        }
                    }
                );
            }

            this.setSign = function(sign) {
                context.sign = sign;
                $rootScope.sign = sign;
            }

            this.isSignin = function() {
                var uid = parseInt($cookies.get('uid'));
                var isSignin = uid != null && $rootScope.authorization != null && $rootScope.user != null &&
                        $rootScope.authorization.uid == uid && $rootScope.user.uid == uid && context.sign;
                return isSignin;
            }

            // Events
            $rootScope.$on('request::authorization::signin', function(event, user) {
                signin(user);
            });
            $rootScope.$on('request::authorization::signup', function(event, user) {

            });
        }]);

        ctlr.factory('REQUEST_AUTHORIZATION', ['$resource', function($resource) {
            return $resource(
                '',
                {},
                {
                    signin: {
                        method: 'POST',
                        url: '/auth/signin'
                    },
                    signout: {
                        method: 'GET',
                        url: '/auth/signout'
                    },
                    signup: {
                        method: 'POST',
                        url: '/auth/signup'
                    }
                }
            );
        }]);
    };

    return controller;
})();
