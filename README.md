# Petclinic Quarkus

REST API written with Quarkus + ReactJS backend taken from [spring-petclinic-react](https://github.com/spring-petclinic/spring-petclinic-reactjs) repository

It is recommended to use server-based H2 database with docker:

```
docker run -d -p 1521:1521 -p 81:81 -e H2_OPTIONS='-ifNotExists' --name=h2 oscarfonts/h2:alpine
```

To run the server in dev mode, use standard Quarkus command in the project root
```
mvn compile quarkus:dev
```
Server API is available on port 8080. Almost all endpoints are protected with BASIC authorisation. You can find usernames and passwords in ```$project.root/src/main/resources/users.properties```.


To run the client, after checkout got to ```$project.root/client``` folder and execute 
```
npm install
```
then
```
npm start
```

Client UI will be available at URL: ```http://localhost:3000```. 
