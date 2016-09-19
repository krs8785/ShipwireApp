# ShipwireApp

<b>Description :</b>

A REST oriented application that maintains an inventory and accepts a stream of order which are processed asynchronously. The application is capable of creating, updating the inventory dynamically at any given time. At the same time it can also continously accept orders from multiple streams. 

<b>Requirement :</b>

1. Your favourite IDE
2. MAVEN
3. JAVA 1.8
4. POSTMAN / CURL or similar

<b> Running the app : </b>

1. Go into the project directory and type the command - 
   ```mvn spring-boot:run```
   
2. Initially the inventory is empty so its important to initialize it. You can make a REST call to 
  
    ```curl -X POST localhost:8080/createInventory -F "file=@inventory.txt"```

3. Now you can start sending in orders in the same manner. REST call to
   
    ```curl -X POST localhost:8080/createOrder -F "file=@orderStream.txt"```

<b>Reading the output :</b>

The format of the output is 

<pre>Header: 1
ProductName: A Quantity: 1 QuantityFilled: 1 QuantityBackorder: 0
ProductName: C Quantity: 1 QuantityFilled: 1 QuantityBackorder: 0

Header: 2
ProductName: E Quantity: 5 QuantityFilled: 0 QuantityBackorder: 5

Header: 3
ProductName: D Quantity: 4 QuantityFilled: 0 QuantityBackorder: 4

Header: 4
ProductName: A Quantity: 1 QuantityFilled: 1 QuantityBackorder: 0
ProductName: C Quantity: 1 QuantityFilled: 0 QuantityBackorder: 1

Header: 5
ProductName: B Quantity: 3 QuantityFilled: 3 QuantityBackorder: 0</pre>


  


