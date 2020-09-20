First of all, to generate this data, we need a Rest API that includes the CRUD operations of the
team and players.

A reminder before we get started:

The application should be lifted with the model and data that you will determine.

### **Scenario 1**

REST API with insert update and delete requests to generate team and player data.

### **Scenario 2**

The client will list all players and then receive the selected player's teams in the second request.

### **Scenario 3**

We will design an endpoint where players' transfer fees are charged. Each team will calculate
the contract fee in its own currency
Transfer Fee = Months of experience * 100,000 / AGE
The Team Commission will be up to 10% of the Transfer Fee, and the contract fee will be
calculated as the Transfer Fee + Team Commission.

#### **Expectations**

We will be glad if you write your project using Java 8 in accordance with microservice
architecture.
It should also be able to work with a relational database and be suitable for Rest practices,
Unit tests are a must.
If you submit the project using Swagger and Postman tools, we can test it more easily






**H2 db console**
http://localhost:8080/h2-console

**swagger** 
http://localhost:8080/swagger-ui.html