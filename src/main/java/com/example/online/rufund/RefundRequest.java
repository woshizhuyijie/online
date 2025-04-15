package com.example.online.rufund;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RefundRequest {
    private Long id; // 退款请求ID
    private Long studentId; // 学生ID
    private Long courseId; // 课程ID
    private String refundReason; // 退款原因
    private LocalDateTime requestDate; // 申请时间
    private String status; // 状态（待审核、已批准、已拒绝）
    private LocalDateTime processedDate; // 处理时间
    private String comments; // 教师审核评论
}
