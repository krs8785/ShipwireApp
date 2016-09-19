package com.shipwire.controller;

import com.shipwire.constants.Constants;
import com.shipwire.services.impl.InventoryServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class InventoryController {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private InventoryServiceImpl inventoryService;

  /**
   * REST API for creating inventory.
   * 
   * @param file file
   * @return Response
   */
  @RequestMapping(value = "/createInventory", method = RequestMethod.POST)
  public ResponseEntity<String> create(@RequestParam("file") MultipartFile file) {
    if (!file.isEmpty()) {
      logger.info("New inventory is recieved " + file.getOriginalFilename());
      if (inventoryService.populateInventory(file)) {
        return new ResponseEntity<String>(Constants.INVENTORY_RECIEVED, HttpStatus.OK);
      } else {
        return new ResponseEntity<String>(Constants.INVENTORY_PROCESSING_ERROR, 
               HttpStatus.INTERNAL_SERVER_ERROR);
      }
    } else {
      logger.error("New inventory is empty " + file.getOriginalFilename());
      return new ResponseEntity<String>(Constants.INVENTORY_FILE_EMPTY, HttpStatus.BAD_REQUEST);
    }
  }
}