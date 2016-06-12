# Comment System
----


## API Document
###API List
----
#### User
|Url		|Method		|Description	|Authorization	|
|-----------|-----------|---------------|---------------|
|/users 	|GET		|get user list	|None			|
|/users 	|POST		|add user		|None			|
|/users/:id	|GET		|get user by id	|None			|
|/users/:id	|POST		|update user	|Required		|

#### Comment
|Url					|Method		|Description			|Authorization	|
|-----------------------|-----------|-----------------------|---------------|
|/comments 				|GET		|get comment list		|None			|
|/comments 				|POST		|add comment			|Required		|
|/comments/:id			|GET		|get comment by id		|None			|
|/comments/:id/like		|POST		|like comment by id		|Required		|
|/comments/:id/dislike	|POST		|dislike comment by id	|Required		|


###API Response
----
