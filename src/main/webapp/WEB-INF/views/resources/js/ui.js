var UIController = (function() {
    var controller = function(App) {
        var ctlr = App.controller('UIController',
                ['$scope', '$rootScope', function($scope, $rootScope) {
            var context = this;

            var signinModal = $("#cs_signin_modal");
            var writeModal = $("#cs_write_modal");
            var profileModal = $("#cs_profile_modal");

            this.user = {
                id: "",
                password: "",
                name: ""
            }
            this.signin = {
                id: "",
                password: ""
            }
            this.modalType = "JOIN";

            // Common function
            function checkSignin() {
                if($rootScope.authorization == null ||
                    $rootScope.user == null ||
                    $rootScope.user.uid == null ||
                    $rootScope.authorization.uid != $rootScope.user.uid ||
                    !$rootScope.sign) {
                    signinModal.modal('show');
                    return false;
                }
                return true;
            }

            this.checkSession = function(uid) {
                return uid == $rootScope.user.uid && $rootScope.authorization.uid == uid && $rootScope.sign;
            }

            // Header
            this.openJoin = function() {
                context.modalType = "JOIN";
                profileModal.modal('show');
            }
            this.openEditProfile = function(user) {
                context.modalType = "EDIT";
                context.user = user;
                profileModal.modal('show');
            }
            this.openWrite = function() {
                if(checkSignin()) {
                    writeModal.modal('show');
                }
            }

            // Modal
            function checkIDAndPasswordLength(text) {
                return text == null || text.length < 3 || text.length > 20;
            }

            // Modal - Signin
            this.checkSigninID = function() {
                var className = "";
                var signin = context.signin;
                if(checkIDAndPasswordLength(signin.id)) {
                    className = "has-error";
                }
                return className;
            }
            this.checkSigninPassword = function() {
                var className = "";
                var signin = context.signin;
                if(checkIDAndPasswordLength(signin.password)) {
                    className = "has-error";
                }
                return className;
            }

            this.requestSignin = function() {
                var signin = context.signin;
                $rootScope.$broadcast('request::authorization::signin', signin);
            }

            //Modal - profile
            this.checkProfileID = function() {
                var className = "";
                var user = context.user;
                if(checkIDAndPasswordLength(user.id)) {
                    className = "has-error";
                }
                return className;
            }
            this.checkProfilePassword = function() {
                var className = "";
                var user = context.user;
                if(checkIDAndPasswordLength(user.password)) {
                    className = "has-error";
                }
                return className;
            }
            this.checkProfileName = function() {
                var className = "";
                var user = context.user;
                if(user.name == null || user.name.length < 1 || user.name.length > 20) {
                    className = "has-error";
                }
                return className;
            }

            this.requestJoin = function() {
                $rootScope.$broadcast('request::authorization::signup', context.user);
            }
            this.requestEdit = function() {
                $rootScope.$broadcast('request::user::edit', context.user);
            }

            // Comment list
            this.like = function(comment, event) {
                if(checkSignin()) {
                    $rootScope.$broadcast('request::comment::like', comment, event.currentTarget);
                }
            }

            this.dislike = function(comment, event) {
                if(checkSignin()) {
                    $rootScope.$broadcast('request::comment::dislike', comment, event.currentTarget);
                }
            }
            this.requestDeleteComment = function(comment, event) {
                if(checkSignin()) {
                    $rootScope.$broadcast('request::comment::delete', comment, event.currentTarget);
                }
            }
            this.requestPrevPage = function() {
                $rootScope.$broadcast('request::comment::load::prev');
            }

            this.requestNextPage = function() {
                $rootScope.$broadcast('request::comment::load::next');
            }
            this.requestPage = function(page) {
                $rootScope.$broadcast('request::comment::load::page', page);
            }

            // Write Comment
            this.requestWriteComment = function() {
                if(checkSignin()) {
                    $rootScope.$broadcast('request::comment::write', context.comment);
                }
            }

            // Events
            $rootScope.$on('authorization::signin::success', function(event, data) {
                signinModal.modal('hide');
            });
            $rootScope.$on('authorization::signin::fail', function(event, data) {
            });
            $rootScope.$on('authorization::signup::success', function(event, data) {
                profileModal.modal('hide');
            });

            $rootScope.$on('user::edit::success', function(event, data) {
                profileModal.modal('hide');
            });


            $rootScope.$on('comment::like::success', function(event, data, dom) {

            });
            $rootScope.$on('comment::dislike::success', function(event, data, dom) {
            });
            $rootScope.$on('comment::delete::success', function(event, data, dom) {
                $(dom).parent('.cs_content').remove();
            });

            $rootScope.$on('comment::load::success', function(event, data) {
            });
            $rootScope.$on('comment::write::success', function(event, data) {
            });
        }]);
    };

    return controller;
})();
