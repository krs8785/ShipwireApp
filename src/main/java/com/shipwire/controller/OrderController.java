package com.shipwire.controller;


import com.shipwire.constants.Constants;
import com.shipwire.orderProcessor.OrderGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class OrderController {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  /**
   * REST API to send orders.
   * 
   * @param file file
   * @return Response
   */
  @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
  public ResponseEntity<String> create(@RequestParam("file") MultipartFile file) {

    if (!file.isEmpty()) {
      logger.info("New order stream has been recevied " + file.getOriginalFilename());
      OrderGenerator orderGen = new OrderGenerator();
      if (orderGen.generateOrders1(file)) {
        return new ResponseEntity<String>(Constants.ORDER_RECIEVED, HttpStatus.OK);
      } else {
        return new ResponseEntity<String>(Constants.ORDER_PROCESSING_ERROR,
               HttpStatus.INTERNAL_SERVER_ERROR);
      }
    } else {
      logger.error("New order stream is empty " + file.getOriginalFilename());
      return new ResponseEntity<String>(Constants.ORDER_FILE_EMPTY, HttpStatus.BAD_REQUEST);
    }
  }
}