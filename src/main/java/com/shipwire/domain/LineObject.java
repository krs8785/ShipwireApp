package com.shipwire.domain;

import com.shipwire.constants.Constants;

/**
 * The LineObject class inherits from the Item. It represents the 
 * orders and its properties respectively.
 * 
 * @author karan
 */
public class LineObject extends Item {

  private String status = Constants.VALID_ORDER;
  private int quantityFilled;
  private int quantityBackordered;

  public LineObject() {
  }

  public LineObject(String productName, int quantity) {
    setProductName(productName);
    setQuantity(quantity);
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String valid) {
    this.status = valid;
  }

  public int getQuantityFilled() {
    return quantityFilled;
  }

  public void setQuantityFilled(int quantityFilled) {
    this.quantityFilled = quantityFilled;
  }

  public int getQuantityBackordered() {
    return quantityBackordered;
  }

  public void setQuantityBackordered(int quantityBackordered) {
    this.quantityBackordered = quantityBackordered;
  }
}