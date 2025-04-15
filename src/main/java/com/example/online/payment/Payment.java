package com.edu.payment.domain;

public class Payment {
    private String orderId;
    private Long studentId;
    private Long courseId;
    private Double amount;
    private String status; // PAID, FAILED, REFUNDED
    private Date paymentDate;

    // getters & setters
}
