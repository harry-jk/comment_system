(function() {
    var App = angular.module('App', ['ngResource', 'ngCookies', function(){
        console.log(this);
    }]);

    App.config(function ($httpProvider) {
        $httpProvider.defaults.transformRequest = function(data) {
            if (data === undefined) return data;

            var fd = new FormData();
            angular.forEach(data, function(value, key) {
                if (value instanceof FileList) {
                    if (value.length == 1) {
                        fd.append(key, value[0]);
                    } else {
                        angular.forEach(value, function(file, index) {
                            fd.append(key + '_' + index, file);
                        });
                    }
                } else {
                    fd.append(key, value);
                }
            });

            return fd;
        }
        $httpProvider.defaults.headers.post['Content-Type'] = undefined;
    });

    var UICtlr = new UIController(App);
    var AuthorizationCtlr = new AuthorizationController(App);
    var UserCtlr = new UserController(App);
    var CommentCtlr = new CommentController(App);
})();
