package com.shipwire.orderProcessor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }
}