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
                    $rootScope.authorization.uid != $rootScope.user.uid) {
                    signinModal.modal('show');
                    return false;
                }
                return true;
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
                console.log(signin);
                signinModal.modal('hide');
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
                var user = context.user;
                profileModal.modal('hide');
            }
            this.requestEdit = function() {
                var user = context.user;
                profileModal.modal('hide');

            }

            // Comment list
            this.like = function(comment, event) {
                console.log(comment);
                console.log(event);
                if(checkSignin()) {

                }
            }

            this.dislike = function(comment, event) {
                console.log(comment);
                console.log(event);
                if(checkSignin()) {

                }
            }
            this.requestDeleteComment = function(comment) {
                console.log(comment);
                if(checkSignin()) {

                }
            }
        }]);
    };

    return controller;
})();
