# User Service MAIN

Main microservice

Instruction to run application:

1) `git clone https://github.com/AndreResh/User_Service_CLIENT.git`
2) open downloaded file
3) `docker-compose run -d`

Role names:
 - ROLE_USER
 - ROLE_ADMIN

Endpoints:

- GET /api/users - to get list of users from second microservice
- GET /api/users/{id} - to get user by id
- POST /api/users - to save user
- 
    - Example:
  
      {
            "email":"andrew@yandex.ru",
            "password":"andrew",
            "roles":["ROLE_ADMIN"]
      }
  
- PUT  /api/users/{id} - to update user by id
- DELETE /api/users/{id} - to delete user by id
- PATCH  /api/users/{id}/addRole - to add role to user with this id (name role in params)
- PATCH  /api/users/{id}/removeRole - to remove role to user with this id (name role in params)

EXAMPLE: http://localhost:8080/api/users

Link for second microservice: https://github.com/AndreResh/User_Service_CLIENT
