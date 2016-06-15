# Comment System
----


## API Document
###API List
----
#### Authorization
|Url    		|Method		|Description	        |Authorization	|
|---------------|-----------|-----------------------|---------------|
|/auth/signin   |GET		|sign in  (log in)      |None			|
|/auth/signout  |GET		|sign out (log out)	    |None			|
|/auth/signup   |POST		|sign up (register)	    |None			|

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
#### Common
----
- __Response Form__
	```json
	{
		"request": "/request_path",
		"status": status code (int)
	}
	```
- __Status Code__
|Code   |Description    |
|-------|---------------|
|200    |Success        |
|400    |Bad Request	|
|401    |Unauthorized	|
|403    |Forbidden		|
|404    |Not Found		|
|409    |Conflict		|
|500    |Server Error	|

#### Error
----
```json
{
	"request": "/request_path",
	"status": status code (int),
	"reason": "reason for this error"
}
```

#### Authorization
----
- __POST__ /auth/signin
```json
{
	"request": "/auth/signin",
	"status": 200,
	"user": {
		"uid": 1,
		"id": "harry.jk",
		"name": "Harry",
		"description": "Software Engineer",
		"profile_image_url": "/resources/test.jpg"
	}
}
```
- __GET__ /auth/signout
```json
{
	"request": "/auth/signout",
	"status": 200
}
```
- __POST__ /auth/signup
```json
{
	"request": "/auth/signup",
	"user": {
		"uid": 50,
		"id": "harry.jk",
		"name": "Harry",
		"description": "Software Engineer",
		"profile_image_url": "/resources/harry.jpg"
	},
	"status": 200
}
```
