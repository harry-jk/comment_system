var AuthorizationController = (function() {
    var controller = function(App) {
        var ctlr = App.controller('AuthorizationController',
                ['$scope', '$rootScope', '$cookies', 'REQUEST_AUTHORIZATION', function($scope, $rootScope, $cookies, REQUEST) {
            $rootScope.authorization = {
            }
            $rootScope.authorization.uid = $cookies.get('uid');
            console.log($rootScope.authorization);

            this.signinForm = {
                id: "",
                password: ""
            }
            this.signin = function() {
                if(id == null || id.length < 3 || id.length > 20) {
                    return;
                }
                if(password == null || password.length < 3 || password.length > 20) {
                    return;
                }
                REQUEST.signin(
                    {},
                    {
                        id: $scope.signinForm.id,
                        password: $scope.signinForm.password
                    },
                    function(data) {
                        console.log(data);
                        if(data.status == 200) {

                        } else {

                        }
                    }
                );
            }
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
