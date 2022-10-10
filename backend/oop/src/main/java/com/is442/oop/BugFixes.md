## Bug Fixes / Notes

`Access denied for user 'root'@'localhost' (using password: YES)`

Navigate to src/main/resources/application.properties and include your username and password for mySQL db like: 
`spring.datasource.url=jdbc:mysql://root:password@localhost:3306/OOP?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC&useLegacyDatetimeCode=false
`
