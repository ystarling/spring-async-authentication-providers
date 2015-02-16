# Multiple authentication endpoints in Spring security

Example how to to authenticate user against two independent authentication providers,
 but you only care if one if the providers authenticate the user.

## Endpoints:

### GET /auth-1/{username}
 first authorization provider
 
### GET /auth-2/{username}
 second authorization provider two
 
### GET /content
 content that is accessible if at least one of the providers authenticate user 

## Users:
| username | first provider | second provider | access to content |
|----------|----------------|-----------------|-------------------|
| 200_200  | 200            | 200             | true              |
| 200_401  | OK             | 401             | true              |
| 401_200  | 401            | 200             | true              |
| 401_401  | 401            | 401             | false             |
| NONE_200 | TIMEOUT        | 200             | true              |
| 200_NONE | 200            | TIMEOUT         | true              |
| NONE_NONE| TIMEOUT        | TIMEOUT         | false             |
