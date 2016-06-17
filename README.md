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
|Url		|Method		|Description		|Authorization	|
|-----------|-----------|-------------------|---------------|
|/users 	|GET		|get user list		|None			|
|/users 	|POST		|update current user|Required		|
|/users/:id	|GET		|get user by id		|None			|

#### Comment
|Url					|Method		|Description			|Authorization	|
|-----------------------|-----------|-----------------------|---------------|
|/comments 				|GET		|get comment list		|None			|
|/comments 				|POST		|add comment			|Required		|
|/comments/:id			|GET		|get comment by id		|None			|
|/comments/:id			|DELETE		|delete comment by id	|Required		|
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
 - __Parameter__
 
 |Key		|Value Type	|Required	|Default	|
 |----------|-----------|-----------|-----------|
 |id		|String		|yes		|			|
 |password	|String		|yes		|			|
 - __Response__
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
 - __Response__
```json
{
	"request": "/auth/signout",
	"status": 200
}
```
- __POST__ /auth/signup
 - __Parameter__
 
 |Key				|Value Type		|Required	|Default	|
 |------------------|---------------|-----------|-----------|
 |id				|String(3-20)	|yes		|			|
 |password			|String(3-20)	|yes		|			|
 |name				|String(1-20)	|yes		|			|
 |description		|String			|no			|			|
 |profile_image_url	|String			|no			|			|
 - __Response__
```json
{
	"request": "/auth/signup",
	"status": 200,
	"user": {
		"uid": 50,
		"id": "harry.jk",
		"name": "Harry",
		"description": "Software Engineer",
		"profile_image_url": "/resources/harry.jpg"
	}
}
```

#### Users
----
- __GET__ /users
 - __Parameter__
 
 |Key	|Value Type	|Required	|Default	|
 |------|-----------|-----------|-----------|
 |page	|Integer	|no			|1			|
 |size	|Integer	|no			|15			|
 - __Response__
```json
{
	"request": "/users",
	"status": 200,
	"size": 15,
	"totalPage": 1,
	"page": 1,
	"last": true,
	"first": true,
	"users": [
		{
			"uid": 1,
			"id": "harry.jk",
			"name": "Harry",
			"description": "Software Engineer",
			"profile_image_url": "/resources/harry.jpg"
		}
	]
}
```
- __POST__ /users
 - __Parameter__
 
 |Key				|Value Type		|Required	|Default	|
 |------------------|---------------|-----------|-----------|
 |uid				|Integer		|yes		|			|
 |id				|String			|yes		|			|
 |password			|String(3-20)	|yes		|			|
 |name				|String(1-20)	|yes		|			|
 |description		|String			|no			|			|
 |profile_image_url	|String			|no			|			|
 - __Response__
```json
{
	"request": "/users",
	"status": 200,
	"user": {
		"uid": 1,
		"id": "harry.jk",
		"name": "Harry",
		"description": "Software Engineer",
		"profile_image_url": "/resources/harry.jpg"
	}
}
```
- __GET__ /users/{id:[0-9]+}
 - __Response__
```json
{
	"request": "/users/1",
	"status": 200,
	"user": {
		"uid": 1,
		"id": "harry.jk",
		"name": "Harry",
		"description": "Software Engineer",
		"profile_image_url": "/resources/harry.jpg"
	}
}
```

#### Comments
----
- __GET__ /comments
 - __Parameter__
 
 |Key	|Value Type	|Required	|Default	|
 |------|-----------|-----------|-----------|
 |page	|Integer	|no			|1			|
 |size	|Integer	|no			|15			|
 - __Response__
```json
{
	"request": "/comments",
	"status": 200,
	"size": 15,
	"totalPage": 1,
	"page": 1,
	"last": true,
	"first": true,
	"comments": [
		{
			"cid": 1,
			"user": {
				"uid": 1,
				"id": "harry.jk",
				"name": "Harry",
				"description": "Software Engineer",
				"profile_image_url": "/resources/harry.jpg"
			},
			"comment": "i'm Harry!",
			"like": 1,
			"dislike": 0,
			"created_at": "2016-06-15 15:42:29"
		}
	]
}
```
- __POST__ /comments
 - __Parameter__
 
 |Key		|Value Type	|Required	|Default	|
 |----------|-----------|-----------|-----------|
 |commentStr|String		|yes		|			|
 - __Response__
```json
{
	"request": "/comments",
	"status": 200,
	"comment": {
		"cid": 122,
		"user": {
			"uid": 1,
			"id": "harry.jk",
			"name": "Harry",
			"description": "Software Engineer",
			"profile_image_url": "/resources/harry.jpg"
		},
		"comment": "I'm Harry!",
		"like": 0,
		"dislike": 0,
		"created_at": "2016-06-16 04:43:10"
	}
}
```
- __GET__ /comments/{id:[0-9]+}
 - __Response__
```json
{
	"request": "/comments/1",
	"status": 200,
	"comment": {
		"cid": 1,
		"user": {
			"uid": 1,
			"id": "harry.jk",
			"name": "Harry",
			"description": "Software Engineer",
			"profile_image_url": "/resources/harry.jpg"
		},
		"comment": "i'm Harry!",
		"like": 1,
		"dislike": 0,
		"created_at": "2016-06-15 15:42:29"
	}
}
```
- __DELETE__ /comments/{id:[0-9]+}
 - __Response__
```json
{
	"request": "/comments/1",
	"status": 200,
	"cid": 1
}
```
- __GET__ /comments/{id:[0-9]+}/like
 - __Response__
```json
{
	"request": "/comments/1/like",
	"status": 200,
	"opinion": {
		"type": "LIKE",
		"comment": {
			"cid": 1,
			"user": {
				"uid": 1,
				"id": "harry.jk",
				"name": "Harry",
				"description": "Software Engineer",
				"profile_image_url": "/resources/harry.jpg"
			},
			"comment": "i'm Harry!",
			"like": 3,
			"dislike": 2,
			"created_at": "2016-06-15 15:42:29"
		},
		"user": {
			"uid": 1,
			"id": "harry.jk",
			"name": "Harry",
			"description": "Software Engineer",
			"profile_image_url": "/resources/harry.jpg"
		}
	}
}
```
- __GET__ /comments/{id:[0-9]+}/dislike
 - __Response__
```json
{
	"request": "/comments/1/dislike",
	"status": 200,
	"opinion": {
		"type": "DISLIKE",
		"comment": {
			"cid": 1,
			"user": {
				"uid": 1,
				"id": "harry.jk",
				"name": "Harry",
				"description": "Software Engineer",
				"profile_image_url": "/resources/harry.jpg"
			},
			"comment": "i'm Harry!",
			"like": 3,
			"dislike": 3,
			"created_at": "2016-06-15 15:42:29"
		},
		"user": {
			"uid": 1,
			"id": "harry.jk",
			"name": "Harry",
			"description": "Software Engineer",
			"profile_image_url": "/resources/harry.jpg"
		}
	}
}
```
