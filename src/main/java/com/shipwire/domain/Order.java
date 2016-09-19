package com.shipwire.domain;

import java.util.ArrayList;

/*
 * Author: Karan Shah
 * Date: 09/16/2016
 * 
 * Description: The order class is a collection of lines received from the data stream
 */
public class Order {

  private long header;
  private ArrayList<LineObject> line;

  public Order(long header) {
    line = new ArrayList<LineObject>();
    setHeader(header);
  }

  public long getHeader() {
    return header;
  }
 
  public void setHeader(long header) {
    this.header = header;
  }

  public void addLineObject(LineObject lineObj) {
    this.line.add(lineObj);
  }

  public ArrayList<LineObject> getLine() {
    return line;
  }
}