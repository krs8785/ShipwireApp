# ShipwireApp

<b>Description :</b>

A REST oriented application that accepts a stream of order which are processed asynchronously and allocates products from the inventory to it. The application is capable of creating, updating the inventory dynamically at any given time. At the same time it can also continously accept orders from multiple streams. 

<b>Assumptions</b>

1. In a real world scenario the inventory is dynamic meaning that the products can come in and go out at any time depending on orders. Hence initially the inventory is empty to start with. Now you can push X products with Y quantity into the inventory similar to a real wareshouse where the merchant can send in his product based on order demand.

2. The REST endpoint to populate an inventory accepts a Txt file (instead of Json typically) which consists of the Products and Quantity respectively each on new line. I designed the API in this fashion because I wanted to put all the products in quickly. 

3. Order can come in at any time, so I have a REST endpoint that can accept an order stream(again a Txt file).  Only the valid orders are processed depending on availibility. As per the code challenge the system should halt and print results. I have kept it this way for now.

<b>Requirements :</b>

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


<b>Input Example :</b>

The inventory example I used is

<pre>
A 2
B 3
C 1
D 0
E 0
</pre>

The order example used is 

<pre>
{"Header": 1, "Lines": {"Product": "A", "Quantity": "1"}{"Product": "C", "Quantity": "1"}} 
{"Header": 2, "Lines": {"Product": "E", "Quantity": "5"}} 
{"Header": 3, "Lines": {"Product": "D", "Quantity": "4"}} 
{"Header": 4, "Lines": {"Product": "A", "Quantity": "1"}{"Product": "C", "Quantity": "1"}} 
{"Header": 5, "Lines": {"Product": "B", "Quantity": "3"}} 
{"Header": 6, "Lines": {"Product": "D", "Quantity": "4"}} 
</pre>

<b>Output Example  :</b>

The format of the output is 
<pre>
HEADER: STREAM_ID
PRODUCTNAME QUANTITY QUANTITYALLOCATED QUANTITYBACKORDER
</pre>

Output of the above input 

<pre>Header: 1
ProductName: A Quantity: 1 QuantityAllocated: 1 QuantityBackorder: 0
ProductName: C Quantity: 1 QuantityAllocated: 1 QuantityBackorder: 0

Header: 2
ProductName: E Quantity: 5 QuantityAllocated: 0 QuantityBackorder: 5

Header: 3
ProductName: D Quantity: 4 QuantityAllocated: 0 QuantityBackorder: 4

Header: 4
ProductName: A Quantity: 1 QuantityAllocated: 1 QuantityBackorder: 0
ProductName: C Quantity: 1 QuantityAllocated: 0 QuantityBackorder: 1

Header: 5
ProductName: B Quantity: 3 QuantityAllocated: 3 QuantityBackorder: 0</pre>


<b>TODO :</b>





  


