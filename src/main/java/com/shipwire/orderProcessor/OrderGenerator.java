package com.shipwire.orderProcessor;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.shipwire.constants.Constants;
import com.shipwire.domain.LineObject;
import com.shipwire.domain.Order;

public class OrderGenerator {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  public static LinkedList<Order> orderList = new LinkedList<Order>();

  public OrderGenerator() {
  }

  /**
   * Validate order.
   * 
   * @param jsonObject JSON object
   * @return Order
   */
  public Order validateOrder(JSONObject jsonObject) {
    
    JSONArray lines = (JSONArray) jsonObject.get("Lines");
    @SuppressWarnings("unchecked")
    Iterator<JSONObject> iterator = lines.iterator();
    Order order = new Order((Long) jsonObject.get("Header"));
    
    while (iterator.hasNext()) {
    
      JSONObject lineJsonObject = iterator.next();
      int quantity = Integer.parseInt((String) lineJsonObject.get("Quantity"));
      LineObject lineObject = new LineObject((String) lineJsonObject.get("Product"), quantity);
      
      //Validate order if quantity if less than 1 or greater than 5
      if (quantity < Constants.MIN_QUANTITY_ORDER || quantity > Constants.MAX_QUANTITY_ORDER) {
    
        logger.warn("Recieved stream " + order.getHeader() + " has invalid lineOrder " 
              + lineObject.getProductName()
              + " quantity of " + lineObject.getQuantity());
        lineObject.setStatus(Constants.INVALID_ORDER);
      }
      order.addLineObject(lineObject);
    }
    return order;
  }

  /**
   * Generate order and add to queue.
   * 
   * @param file file
   * @return boolean
   */
  public boolean generateOrders1(MultipartFile file) {

    try {
      byte[] byteArr;
      byteArr = file.getBytes();

      InputStream inputStream = new ByteArrayInputStream(byteArr);
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      String orderLine;
   
      while ((orderLine = reader.readLine()) != null) {
    
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(orderLine);
        JSONObject jsonObject = (JSONObject) obj;
        Order order = validateOrder(jsonObject);
        logger.info("Order with header " + order.getHeader() + " has been generated.");
        orderList.add(order);
      }
      return true;
    } catch (Exception ex) {
      ex.printStackTrace();
      return false;
    }
  }
}