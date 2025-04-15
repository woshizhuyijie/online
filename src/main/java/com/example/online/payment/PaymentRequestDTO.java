package com.example.education.payment.dto;

import lombok.Data;

@Data
public class PaymentRequestDTO {
    private Long userId;
    private Long courseId;
    private String paymentMethod; // e.g. "alipay", "wechat"
}

// ------------------------

package com.example.education.payment.dto;

import lombok.Data;

@Data
public class PaymentResultDTO {
    private String orderId;
    private String status;
    private String message;
}
