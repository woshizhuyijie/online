package com.example.education.payment.service;

import com.example.education.payment.dto.PaymentRequestDTO;
import com.example.education.payment.dto.PaymentResultDTO;

public interface PaymentService {
    PaymentResultDTO createOrder(PaymentRequestDTO request);
    PaymentResultDTO retryPayment(String orderId);
    void cancelTimeoutOrders();
}