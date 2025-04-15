package com.example.education.payment.controller;

import com.example.education.payment.dto.PaymentRequestDTO;
import com.example.education.payment.dto.PaymentResultDTO;
import com.example.education.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create")
    public PaymentResultDTO createOrder(@RequestBody PaymentRequestDTO request) {
        return paymentService.createOrder(request);
    }

    @PostMapping("/retry/{orderId}")
    public PaymentResultDTO retryPayment(@PathVariable String orderId) {
        return paymentService.retryPayment(orderId);
    }

    @PostMapping("/cancelTimeout")
    public void cancelTimeoutOrders() {
        paymentService.cancelTimeoutOrders();
    }
} 