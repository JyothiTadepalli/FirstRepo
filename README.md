The code demonstrates using CircuitBreaker with two different libraries
a. Hystrix
b. Apache Polygene.
Product-Backend : Provides a list of products. :http://localhost:8084
Customer-Backend : Provides a list of customers:http://localhost:8090
Order-Backend : Calls Product-Backend and Customer-Backend services for returning the order data.
The circuit breakers are implemented in this project. http://localhost:8083/order?idCustomer=1&idProduct=1&amount=100
Product service is covered with Polygene and Customer call is handled with Hystrix. 
