# Bank-account-kata

### A SpringBoot application offering Rest Endpoints in order to interract with a basic bank account 


## This application offers 3 Endpoints allowing:
 1.. Deposit of a given amount of money on an account 
 ```
 POST /api/accounts/{accountId}/deposit
 ```
       
 2.. Withdrawal of a given amount of money from an account:
  
 ``` 
 POST /api/accounts/{accountId}/withdrawal
 ```
   
 3.. Account balance and all operations:
 
   ```
   GET /api/accounts/{accountId}/statement 
   ```  
    

 ## Environment setup
```bash
mvn clean install
```

## H2 Database access
url: http://localhost:8080/h2-console

username: bakata

password:

## Swagger url
http://localhost:8080/swagger-ui.htm
 
 ## Testing 
 The application is tested using Junit and assertJ.
 1. Unit tests for the services
 2. Integration tests for the controllers
 
 ## Initialized data to test with
 
 #### Bank Accounts
 
 
 BankAccount 1:
  ```bash
  id=1, balance = 3000
 ```
 
 BankAccount 2:
  ```bash
  id=2, balance = 1000
 ```
 BankAccount 3: 
 ```bash
 id=3, balance = 4500
 ```

 #### Operations

 
  
 Operation 1:  
 ```bash
 id=1, amount=200, date='2020-01-01 10:00:00.000', operationType='Deposit', accountId=1;
 ```
 
 Operation 2:
 ```bash  
 id=2, amount=100, date='2020-01-01 10:00:00.000', operationType='Withdrawal', accountId=2;
 ```
 
 Operation 3:
 ```bash
 id=3, amount=2000, date='2020-01-01 10:00:00.000', operationType='Deposit', accountId=3;
 ```
 
