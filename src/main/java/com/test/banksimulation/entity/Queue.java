package com.test.banksimulation.entity;

import com.test.banksimulation.entity.enums.QueueNotificationStatusEnum;
import com.test.banksimulation.entity.enums.QueueStatusEnum;
import javax.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "queue")
public class Queue {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "uuid", nullable = false)
  private String uuid;

  @Column(name = "data")
  private String data;

  @Column(name = "status", nullable = false)
  @Enumerated(EnumType.STRING)
  private QueueStatusEnum status;

  @Column(name = "notification_uri", nullable = false)
  private String notificationUri;

  @Column(name = "notification_status", nullable = false)
  @Enumerated(EnumType.STRING)
  private QueueNotificationStatusEnum notificationStatus;

  @Column(name = "fecha", nullable = false)
  private Date fecha;

  @Column(name = "intentos", nullable = false)
  private Integer intentos;

  @Column(name = "response_data")
  private String responseData;
}
