package com.shipwire.orderProcessor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The orderExecutor thread runs parallel and keep processing
 * orders as soon as they are in the order Queue. 
 *  
 * @author karan
 */
public class OrderExecutor extends Thread {

  ExecutorService executorService = Executors.newFixedThreadPool(10);

  public OrderExecutor() {
  }

  /**
   * Execute order. Thread pool create thread for each thread
   */
  public void run() {
    while (true) {
      try {
        if (!OrderGenerator.orderList.isEmpty()) {
          executorService.execute(new InventoryAllocator(OrderGenerator.orderList.remove()));
        }
        Thread.sleep(100);
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }
}