package com.test.banksimulation;

import com.test.banksimulation.service.NotificationQueueProcessor;
import com.test.banksimulation.service.QueueProcessor;
import com.test.banksimulation.service.QueueService;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankSimulationApplication {

  public static void main(String[] args) {
    SpringApplication.run(BankSimulationApplication.class, args);
  }

  @Autowired QueueService queueService;

  @Value("${time.to.sleep.in.seconds}")
  private int delaySeconds;

  @PostConstruct
  public void execute() {
    QueueProcessor queueProcessor = new QueueProcessor();
    queueProcessor.setDelaySeconds(delaySeconds);
    queueProcessor.setQueueService(queueService);

    queueProcessor.start();

    NotificationQueueProcessor notificationQueueProcessor = new NotificationQueueProcessor();
    notificationQueueProcessor.setDelaySeconds(delaySeconds);
    notificationQueueProcessor.setQueueService(queueService);

    notificationQueueProcessor.start();
  }
}
