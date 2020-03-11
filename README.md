# starbux
Spring Boot REST API services with H2DB. A backend API for online coffee place, starbux coffee, where users can order
drinks/toppings and admins can create/update/delete drinks/toppings and have access to reports.

## Technology
Spring Boot, Spring Security, H2DB, Docker, Swagger2

## Build
After installation of Docker, you may run "docker-compose build" from command line.

## Run
After a successful build, you should run "docker-compose up" from command line. Application runs on http://localhost:8888 address.

## Authentication
API services and documentation requires authentication. Default admin username : admin, password : admin. Customer username : customer, password : customer. HTTP basic authentication is used.

## API documentation
http://localhost:8888/swagger-ui.html

## Application logic
### Default drinks:
Black Coffee - 4 eur
Latte - 5 eur
Mocha - 6 eur
Tea - 3 eur
### Toppings/sides:
Milk - 2 eur
Hazelnut syrup - 3 eur
Chocolate sauce - 5 eur
Lemon - 2 eur
### Discount logic:
If the total of the cart is more than 12 euros, there should be a 25% discount.
If there are 3 or more drinks in the cart, the one with the lowest amount (including
toppings) should be free.
If the cart is eligible for both promotions, the promotion with the lowest cart amount
should be used and the other one should be ignored.
#### Admin api:
 Should be able to create/update/delete products and toppings.
#### Reports:
 Total amount of the orders per customer.
 Most used toppings for drinks.
