var AuthorizationController = (function() {
    var controller = function(App) {
        var ctlr = App.controller('AuthorizationController',
                ['$scope', '$rootScope', '$cookies', 'REQUEST_AUTHORIZATION', function($scope, $rootScope, $cookies, REQUEST) {
            var context = this;
            $rootScope.authorization = {
            }
            $rootScope.authorization.uid = parseInt($cookies.get('uid'));
            $rootScope.sign = this.sign;

            console.log($rootScope.authorization);

            function signin(request) {
                if(request.id == null || request.id.length < 3 || request.id.length > 20) {
                    $rootScope.$broadcast('authorization::signin::fail', null);
                    return;
                }
                if(request.password == null || request.password.length < 3 || request.password.length > 20) {
                    $rootScope.$broadcast('authorization::signin::fail', null);
                    return;
                }
                console.log(request);
                REQUEST.signin(
                    {},
                    {
                        id: request.id,
                        password: request.password
                    },
                    function(data) {
                        console.log(data);
                        if(data.status == 200) {
                            $rootScope.authorization.uid = data.user.uid;
                            context.setSign(true);
                            $cookies.put('uid', data.user.uid);
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
                var uid = $cookies.get('uid');
                var isSignin = uid != null && $rootScope.authorization != null && $rootScope.user != null &&
                        $rootScope.authorization.uid == uid && $rootScope.user.uid == uid && context.sign;
                console.log(isSignin, $rootScope.sign);
                return isSignin;
            }

            // Events
            $rootScope.$on('request::authorization::signin', function(event, data) {
                signin(data);
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
