package com.test.banksimulation.service;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NotificationQueueProcessor extends Thread {

  @Setter private int delaySeconds;
  @Setter QueueService queueService;

  @Override
  public void run() {
    while (true) {
      log.info("Procesando Notificacion QUEUE!!!");
      processNotification();
    }
  }

  public void processNotification() {
    try {
      queueService.processNotify();
      Thread.sleep(delaySeconds * 1000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
