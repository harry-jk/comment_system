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

            this.replaceTime = function(timeStr) {
                var current = new Date();
                var time = new Date(timeStr)
                var diff = Math.floor((current - time) / 1000); // second
                if(diff < 60) {
                    return Math.floor(diff) + "초 전";
                } else if(diff < (60 * 60)) {
                    return Math.floor(diff/60) + "분 전";
                } else if(diff < (60 * 60 * 24)) {
                    return Math.floor(diff/60/24) + "시간 전";
                } else {
                    return current.getFullYear() + "." + (current.getMonth() + 1) + "." + current.getDate();
                }

            };


            function requestPage(pageNo) {
                REQUEST.list(
                    {
                        page: pageNo
                    },
                    function(data) {
                        if(data.status != 200) {
                            $rootScope.$broadcast('comment::load::fail', data);
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
                            else if(start + 5 > data.totalPage) start = start - (10 - data.totalPage  + start) + 1;
                            else start = start - 4;
                            for(var page = start; page <= data.page; ++page) {
                                context.pages.push(page);
                            }
                        }

                        if(context.pages.length < 10) {
                            var start = context.pages[context.pages.length -1];
                            if(start < data.totalPage) {
                                for(var page = start + 1;
                                    (page <= data.totalPage && context.pages.length < 10);
                                    ++page) {
                                    context.pages.push(page);
                                }
                            }
                        }

                        $rootScope.$broadcast('comment::load::success', data);
                    }
                );
            };
            requestPage(1);


            // Events
            $rootScope.$on('request::comment::like', function(event, comment, dom) {
                REQUEST.like(
                    {
                        id: comment.cid
                    },
                    function(data) {
                        if(data.status != 200) {
                            $rootScope.$broadcast('comment::like::fail', data, dom);
                            return;
                        }
                        comment.like = data.opinion.comment.like;
                        comment.dislike = data.opinion.comment.dislike;
                        $rootScope.$broadcast('comment::like::success', data, dom);
                    }
                );
            });
            $rootScope.$on('request::comment::dislike', function(event, comment, dom) {
                REQUEST.dislike(
                    {
                        id: comment.cid
                    },
                    function(data) {
                        if(data.status != 200) {
                            $rootScope.$broadcast('comment::dislike::fail', data, dom);
                            return;
                        }
                        comment.like = data.opinion.comment.like;
                        comment.dislike = data.opinion.comment.dislike;
                        $rootScope.$broadcast('comment::dislike::success', data, dom);
                    }
                );
            });
            $rootScope.$on('request::comment::delete', function(event, comment, dom) {
                REQUEST.delete(
                    {
                        id: comment.cid
                    },
                    function(data) {
                        if(data.status != 200) {
                            $rootScope.$broadcast('comment::delete::fail', data, dom);
                            return;
                        }
                        for(var index = 0; index < context.comments.length; ++index) {
                            if(context.comments[index].cid == comment.cid) {
                                context.comments.splice(index, 1);
                                break;
                            }
                        }
                        $rootScope.$broadcast('comment::delete::success', data, dom);
                    }
                );
            });

            $rootScope.$on('request::comment::load::prev', function(event) {
                if(context.page == 1) return;
                requestPage(context.page - 1);
            });
            $rootScope.$on('request::comment::load::next', function(event) {
                requestPage(context.page + 1);
            });
            $rootScope.$on('request::comment::load::page', function(event, page) {
                requestPage(page);
            });

            $rootScope.$on('request::comment::write', function(event, comment) {
                REQUEST.post(
                    {},
                    {
                        commentStr: comment
                    },
                    function(data) {
                        if(data.status != 200) {
                            $rootScope.$broadcast('comment::write::fail', data);
                            return;
                        }
                        context.comments.unshift(data.comment);
                        $rootScope.$broadcast('comment::write::success', data);
                    }
                );
            });



            $rootScope.$on('user::edit::success', function(event, data) {
                requestPage(context.page);
            });
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
                    delete: {
                        method: 'DELETE',
                        url: '/comments/:id'
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
