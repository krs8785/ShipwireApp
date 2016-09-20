package com.shipwire.domain;

/**
 * The Item class is a model for product/quantity.
 *  
 * @author karan
 */
public class Item {

  private String productName;
  private int quantity;

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
}