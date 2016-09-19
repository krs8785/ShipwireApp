package com.shipwire.services;

import org.springframework.web.multipart.MultipartFile;

public interface InventoryService {

  public boolean populateInventory(MultipartFile file);
  
  public boolean isInventoryEmpty();
  
  public boolean isProductAvailable(String productName);
  
  public void addToInventory(String productName, int quantity);
  
  public int getProductQuantity(String productName);
  
  public void decrementInventory(String productName, int quantity);

}