package com.test.banksimulation.repository;

import com.test.banksimulation.entity.Queue;
import com.test.banksimulation.entity.enums.QueueNotificationStatusEnum;
import com.test.banksimulation.entity.enums.QueueStatusEnum;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QueueRepository extends JpaRepository<Queue, Long> {
  @Query("SELECT q FROM Queue q WHERE q.status = :status ORDER BY q.id ASC ")
  List<Queue> findAllPending(QueueStatusEnum status, Pageable pageable);

  @Query("SELECT q FROM Queue q WHERE q.notificationStatus = :notificationStatusEnum ORDER BY q.id ASC")
  List<Queue> findAllToNotify(QueueNotificationStatusEnum notificationStatusEnum, Pageable pageable);
}
