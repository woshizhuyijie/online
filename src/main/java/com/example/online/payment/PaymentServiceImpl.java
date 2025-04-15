package com.example.education.payment.service.impl;

import com.example.education.payment.dto.PaymentRequestDTO;
import com.example.education.payment.dto.PaymentResultDTO;
import com.example.education.payment.entity.Order;
import com.example.education.payment.enums.OrderStatus;
import com.example.education.payment.repository.OrderRepository;
import com.example.education.payment.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public PaymentResultDTO createOrder(PaymentRequestDTO request) {
        Order order = new Order();
        order.setOrderId(UUID.randomUUID().toString());
        order.setUserId(request.getUserId());
        order.setCourseId(request.getCourseId());
        order.setStatus(OrderStatus.PENDING);
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        orderRepository.save(order);

        // Normally call third-party payment here
        PaymentResultDTO result = new PaymentResultDTO();
        result.setOrderId(order.getOrderId());
        result.setStatus("PENDING");
        result.setMessage("Order created, awaiting payment");
        return result;
    }

    @Override
    public PaymentResultDTO retryPayment(String orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        if (order.getStatus() == OrderStatus.CANCELLED) {
            return createOrder(new PaymentRequestDTO(order.getUserId(), order.getCourseId(), "wechat"));
        }
        PaymentResultDTO result = new PaymentResultDTO();
        result.setOrderId(orderId);
        result.setStatus(order.getStatus().name());
        result.setMessage("Order is still valid");
        return result;
    }

    @Override
    public void cancelTimeoutOrders() {
        LocalDateTime timeoutLimit = LocalDateTime.now().minusMinutes(30);
        List<Order> timeoutOrders = orderRepository.findByStatusAndCreateTimeBefore(OrderStatus.PENDING, timeoutLimit);
        for (Order order : timeoutOrders) {
            order.setStatus(OrderStatus.CANCELLED);
            order.setUpdateTime(LocalDateTime.now());
            orderRepository.save(order);
        }
    }
}
