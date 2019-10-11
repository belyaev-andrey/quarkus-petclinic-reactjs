# Petclinic Quarkus

REST API written with Quarkus + ReactJS backend taken from [spring-petclinic-react](https://github.com/spring-petclinic/spring-petclinic-reactjs) repository

It is recommended to use server-based H2 database with docker:

```
docker run -d -p 1521:1521 -p 81:81 -e H2_OPTIONS='-ifNotExists' --name=h2 oscarfonts/h2:alpine
```
