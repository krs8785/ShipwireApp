package com.shipwire;

import com.shipwire.orderProcessor.OrderExecutor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

  /** 
   * Main Method. Run orderExecutor thread.
   * 
   * @param args Arguments
   */
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
    OrderExecutor orderExec = new OrderExecutor();
    orderExec.start();
  }
}