package com.example.education.payment.repository;

import com.example.education.payment.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findByStatusAndCreateTimeBefore(OrderStatus status, LocalDateTime deadline);
}
