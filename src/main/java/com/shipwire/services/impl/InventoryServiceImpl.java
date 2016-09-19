package com.shipwire.services.impl;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.shipwire.domain.Inventory;
import com.shipwire.services.InventoryService;

@Service
public class InventoryServiceImpl implements InventoryService {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private static Inventory inventory;
  
  public InventoryServiceImpl() {
    inventory = Inventory.getInstance();
  }

  /**
   * Populate the inventory.
   */
  public boolean populateInventory(MultipartFile file) {
    
    try {
      byte[] byteArr;
      byteArr = file.getBytes();
      InputStream inputStream = new ByteArrayInputStream(byteArr);
      BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      String productLine;
   
      while ((productLine = reader.readLine()) != null) {
        String[] productInput = productLine.split(" ");
        addToInventory(productInput[0], Integer.parseInt(productInput[1]));
      }
      return true;
    } catch (Exception ex) {
      ex.printStackTrace();
      return false;
    }
  }

  /**
   * Return true if inventory is empty.
   */
  public boolean isInventoryEmpty() {
  
    int sum = 0;
    @SuppressWarnings("rawtypes")
    Iterator iter = inventory.getTotalInventory().entrySet().iterator();
   
    while (iter.hasNext()) {
      @SuppressWarnings("rawtypes")
      Map.Entry pair = (Map.Entry) iter.next();
      sum += (Integer) pair.getValue();
    }
    if (sum == 0) {
      return true;
    }
    return false;
  }

  /**
   * Return true is product is in the inventory.
   */
  public boolean isProductAvailable(String productName) {
  
    if (inventory.getTotalInventory().containsKey(productName)) {
      if (inventory.getTotalInventory().get(productName) <= 0) {
        return false;
      }
    } else {
      return false;
    }
    return true;
  }

  public int getProductQuantity(String productName) {
    return inventory.getTotalInventory().get(productName);
  }

  public void addToInventory(String productName, int quantity) {
    logger.info("Adding " + productName + " Quantity " + quantity + " to Inventory");
    inventory.getTotalInventory().put(productName, quantity);
  }

  public void decrementInventory(String productName, int quantity) {
    inventory.decrementProduct(productName, quantity);
  }
}