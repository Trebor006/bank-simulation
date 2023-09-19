# BankAPI!!

This solution was resolved using
"Postgres", "Liquibase", "Spring", "RestServices", "Hibernate"

### Reference Repository

https://github.com/Trebor006/bank-simulation


### Running the project

- Requisites
  - docker
  - postman
  - Ports required for the project
    - 8080 for BankAPI Microservice (bank-simulation) 
    - 5432 for postgresDB

- run into the terminal
  docker-compose up

- import the file "Devsu.postman_collection.json" to your postman
  - A folder called "Devsu" should appear with the folowing folders: "Customer", "Account", "Movements" and "Reports"  
  - now you should be able to execute the rest services


Actuator is enabled, so you can review it in this url
- http://localhost:8080/api/actuator

Have fun!
