# personinfo app

#### Start application
```$xslt
sh startup.sh

-- The test cases will run and application will run on the port specified in the file. 
```

#### set username, password and port in startup.sh
```$xslt
1. set the username and password for the security.
2. if the port is not provided then the default 8080 is configured
```

#### client
```$xslt
1. When the application is started 
2. http://localhost:8000/swagger-ui.html, we can execute any api from swagger page to test.
3. When can we execute various APIs

curl --location --request DELETE 'http://localhost:8000/person/1' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic YWRtaW46YWRtaW4=' \
--data-raw ''

curl --location --request GET 'http://localhost:8000/person/1' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic YWRtaW46YWRtaW4=' \
--data-raw ''

curl --location --request PATCH 'http://localhost:8000/person' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic YWRtaW46YWRtaW4=' \
--data-raw '{
	"first_name": "rajat",
	"last_name": "kayal",
	"age": 1,
	"favourite_color":"black",
	"hobbies": ["cricket", "football"]
}'

curl --location --request POST 'http://localhost:8000/person' \
--header 'Content-Type: application/json' \
--header 'Authorization: Basic YWRtaW46YWRtaW4=' \
--data-raw '{
	"first_name": "rajat",
	"last_name": "kayal",
	"age": 1,
	"favourite_color":"black",
	"hobbies": ["cricket", "football"]
}'
```

#### Technology Stak
```$xslt
1. Java 
2. Spring boot
3. H2 database
4. swagger
5. Authentication - basic authentication

```



