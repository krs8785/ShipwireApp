package com.shipwire.orderProcessor;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shipwire.constants.Constants;
import com.shipwire.domain.LineObject;
import com.shipwire.domain.Order;
import com.shipwire.services.impl.InventoryServiceImpl;

public class InventoryAllocator implements Runnable {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private Order order;
  private InventoryServiceImpl inventoryService;
  private static ArrayList<Order> processedOrders = new ArrayList<Order>();

  public InventoryAllocator(Order order) {
    setOrder(order);
    inventoryService = new InventoryServiceImpl();
  }

  /** Each order thread is processed if inventory is available.
   * 
   */
  public void run() {
    try {
      if (inventoryService.isInventoryEmpty()) {
        printOrderLog();
      } else {
        allocateInventory(order);
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  /** Allocate inventory to order and update order.
   * 
   * @param order Order 
   */
  public void allocateInventory(Order order) {

    ArrayList<LineObject> lineObjects = order.getLine();
    
    for (int i = 0; i < lineObjects.size(); i++) {
    
      LineObject currentLineObject = lineObjects.get(i);
      if (currentLineObject.getStatus().equals(Constants.INVALID_ORDER)) {
        //invalid order whose quantity is <1 or >5
      } else if (currentLineObject.getStatus().equals(Constants.VALID_ORDER)) {
    
        if (inventoryService.isProductAvailable(currentLineObject.getProductName())) {
        
          int quantityInInventory = inventoryService.getProductQuantity(currentLineObject
                                    .getProductName());
          int difference = Math.abs(quantityInInventory - currentLineObject.getQuantity());
          if (quantityInInventory > currentLineObject.getQuantity()) {
            //Inventory available in surplus
            order.getLine().get(i).setQuantityFilled(currentLineObject.getQuantity());
            order.getLine().get(i).setQuantityBackordered(0);
            inventoryService.decrementInventory(currentLineObject.getProductName(), difference);
          } else if (difference == 0) {
            //Inventory available exact
            order.getLine().get(i).setQuantityFilled(currentLineObject.getQuantity());
            order.getLine().get(i).setQuantityBackordered(0);
            inventoryService.decrementInventory(currentLineObject.getProductName(), 0);
          } else {
            //Inventory is scarce
            order.getLine().get(i).setQuantityFilled(inventoryService
                 .getProductQuantity(currentLineObject.getProductName()));
            order.getLine().get(i).setQuantityBackordered(difference);
            inventoryService.decrementInventory(currentLineObject.getProductName(), 0);
          }
        } else {
          //Order should go to backOrder if product is not in inventory
          order.getLine().get(i).setQuantityFilled(0);
          order.getLine().get(i).setQuantityBackordered(currentLineObject.getQuantity());
        }
      }
    }
    logger.info("Order with header " + order.getHeader() + " has been processed");
    processedOrders.add(order);
  }

  private void printOrderLog() {

    System.out.println();
    for (int i = 0; i < processedOrders.size(); i++) {

      StringBuffer orderInfo = new StringBuffer();
      Order orderTemp = processedOrders.get(i);
      orderInfo.append("Header: " + orderTemp.getHeader() + "\n");
      ArrayList<LineObject> lineObjects = orderTemp.getLine();
 
      for (int j = 0; j < lineObjects.size(); j++) {

        LineObject lineObjectTemp = lineObjects.get(j);
        orderInfo.append("ProductName: " + lineObjectTemp.getProductName()
                 + " Quantity: " + lineObjectTemp.getQuantity() + " QuantityAllocated: "
                 + lineObjectTemp.getQuantityFilled() 
                 + " QuantityBackorder: " + lineObjectTemp.getQuantityBackordered() + "\n");
      }
      System.out.println(orderInfo);
    }
    System.exit(0);
  }

  public void setOrder(Order order) {
    this.order = order;
  }

  public Order getOrder() {
    return order;
  }
}