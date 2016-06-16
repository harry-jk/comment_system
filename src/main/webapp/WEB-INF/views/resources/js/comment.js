var CommentController = (function() {
    var controller = function(App) {
        var ctlr = App.controller('CommentController',
                ['$scope', '$rootScope', 'REQUEST_COMMENT', function($scope, $rootScope, REQUEST) {
            var context = this;
            this.comments = [];
            this.pages = [];

            this.page = 1;
            this.pageSize = 15;
            this.total = 1;
            this.last = true;
            this.first = true;

            REQUEST.list(
                {
                    page: this.page
                },
                function(data) {
                    console.log(data);
                    if(data.status != 200) {
                        return;
                    }
                    context.page = data.page;
                    context.pageSize = data.size;
                    context.total = data.totalPage;
                    context.last = data.last;
                    context.first = data.first;
                    context.comments = data.comments;
                    context.pages = [];

                    if(data.first) {
                        context.pages.push(data.page);
                    } else if(data.last) {
                        var start = data.page;
                        if(start < 10) start = 1;
                        else start = start - 9;
                        for(var page = start; page <= data.page; ++page) {
                            context.pages.push(page);
                        }
                    } else {
                        var start = data.page;
                        if(start < 5) start = 1;
                        else start = start - 4;
                        for(var page = start; page <= data.page; ++page) {
                            context.pages.push(page);
                        }
                    }

                    if(context.pages.length < 10) {
                        var start = context.pages[context.pages.length -1];
                        if(start < data.totalPage) {
                            for(var page = start + 1;
                                page < data.totalPage && context.pages.length <= 10;
                                ++page) {
                                context.pages.push(page);
                            }
                        }
                    }

                    console.log(context.comments);
                }
            );
        }]);

        ctlr.factory('REQUEST_COMMENT', ['$resource', function($resource) {
            return $resource(
                '',
                {},
                {
                    list: {
                        method: 'GET',
                        url: '/comments'
                    },
                    post: {
                        method: 'POST',
                        url: '/comments'
                    },
                    like: {
                        method: 'GET',
                        url: '/comments/:id/like'
                    },
                    dislike: {
                        method: 'GET',
                        url: '/comments/:id/dislike'
                    }
                }
            );
        }]);
    };

    return controller;
})();
