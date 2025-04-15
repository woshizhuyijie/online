package com.example.online.rufund;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/refund")
public class RefundController {

    @Autowired
    private RefundService refundService;

    // 学生申请退款
    @PostMapping("/request")
    public String requestRefund(@RequestParam Long studentId,
                                @RequestParam Long courseId,
                                @RequestParam String refundReason) {
        return refundService.requestRefund(studentId, courseId, refundReason);
    }

    // 教师审核退款申请
    @PostMapping("/approve")
    public String approveRefund(@RequestParam Long teacherId,
                                @RequestParam Long refundId,
                                @RequestParam String status,
                                @RequestParam(required = false) String comments) {
        return refundService.approveRefund(teacherId, refundId, status, comments);
    }

    // 获取所有退款申请
    @GetMapping("/requests")
    public List<RefundRequest> getRefundRequests() {
        return refundService.getRefundRequests();
    }

    // 获取某学生的退款状态
    @GetMapping("/status/{studentId}")
    public RefundRequest getRefundStatus(@PathVariable Long studentId) {
        return refundService.getRefundStatus(studentId);
    }
}
