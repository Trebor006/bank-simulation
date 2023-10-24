package com.test.banksimulation.service;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class QueueProcessor extends Thread {

  @Setter private int delaySeconds;
  @Setter QueueService queueService;

  @Override
  public void run() {
    while (true) {
      log.info("Procesando QUEUE!!!");
      processPayment();
    }
  }

  public void processPayment() {
    try {
      queueService.processPayment();
      Thread.sleep(delaySeconds * 1000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
