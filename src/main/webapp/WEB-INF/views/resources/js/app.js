(function() {
    var App = angular.module('App', ['ngResource', 'ngCookies', function(){
        console.log(this);
    }]);


    var UICtlr = new UIController(App);
    var AuthorizationCtlr = new AuthorizationController(App);
    var UserCtlr = new UserController(App);
    var CommentCtlr = new CommentController(App);
})();
