# starbux
Spring Boot REST API services with H2DB. A backend API for online coffee place, starbux coffee, where users can order
drinks/toppings and admins can create/update/delete drinks/toppings and have access to reports.

## Technology
Spring Boot, Spring Security, H2DB, Docker, Swagger2

## Build
After installation of Docker, you may run "docker-compose build" from command line.

## Run
After a successful build, you should run "docker-compose-up" from command line. Application runs on http://localhost:8888 address.

## Authentication
API services and documentation requires authentication. Default admin username : admin, password : admin. Customer username : customer, password : customer. HTTP basic authentication is used.

## API documentation
http://localhost:8888/swagger-ui.html

