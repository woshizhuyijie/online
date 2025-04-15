package com.example.education.payment.entity;

import com.example.education.payment.enums.OrderStatus;
import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Order {
    @Id
    private String orderId;

    private Long userId;
    private Long courseId;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}