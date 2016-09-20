package com.shipwire.domain;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Inventory is a singleton class which is essentially a representation of 
 * the database. It consists of a key/value map which stores the product/quantity.
 * 
 * @author karan
 */
public class Inventory {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private static Inventory inventory;
  private HashMap<String, Integer> totalInventory;

  private Inventory() {
    totalInventory = new HashMap<String, Integer>();
  }

  /**
   * Singleton Class.
   * 
   * @return Inventory
   */
  public static Inventory getInstance() {
    if (inventory == null) {
      inventory = new Inventory();
    }
    return inventory;
  }

  public HashMap<String, Integer> getTotalInventory() {
    return totalInventory;
  }

  /**
   * Decrement the inventory when order is processed.
   * 
   * @param productName productName
   * @param quantity quantity
   */ 
  public synchronized void decrementProduct(String productName, int quantity) {
    logger.info("Removing " + productName + " Quantity " 
          + (getTotalInventory().get(productName) - quantity) + " from Inventory");
    getTotalInventory().put(productName, quantity);
  }
}