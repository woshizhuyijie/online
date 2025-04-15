package com.example.online.rufund;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class RefundService {

    // 模拟数据库
    private static List<RefundRequest> refundRequestDatabase = new ArrayList<>();

    // 模拟退款条件
    private static final int MAX_REFUND_DAYS = 7;
    private static final double MAX_VIDEO_WATCHED_PERCENT = 0.2;
    private static final String ALLOWED_REFUND_REASON = "课程内容与描述不符";

    // 学生申请退款
    public String requestRefund(Long studentId, Long courseId, String refundReason) {
        if (!ALLOWED_REFUND_REASON.equals(refundReason)) {
            return "退款原因不符合要求";
        }

        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setId((long) (refundRequestDatabase.size() + 1));
        refundRequest.setStudentId(studentId);
        refundRequest.setCourseId(courseId);
        refundRequest.setRefundReason(refundReason);
        refundRequest.setRequestDate(java.time.LocalDateTime.now());
        refundRequest.setStatus("待审核");

        refundRequestDatabase.add(refundRequest);
        return "退款申请已提交，等待审核";
    }

    // 教师审核退款
    public String approveRefund(Long teacherId, Long refundId, String status, String comments) {
        RefundRequest refundRequest = refundRequestDatabase.stream()
                .filter(r -> r.getId().equals(refundId))
                .findFirst()
                .orElse(null);

        if (refundRequest == null) {
            return "退款申请未找到";
        }

        if (!"待审核".equals(refundRequest.getStatus())) {
            return "该退款申请已被处理";
        }

        refundRequest.setStatus(status);
        refundRequest.setComments(comments);
        refundRequest.setProcessedDate(java.time.LocalDateTime.now());

        if ("已批准".equals(status)) {
            // 模拟退款处理（退回到支付账户）
            processRefund(refundRequest.getStudentId(), refundRequest.getCourseId());
            // 模拟回收课程权限
            revokeCourseAccess(refundRequest.getStudentId(), refundRequest.getCourseId());
        }

        return "退款处理已完成";
    }

    // 模拟退款处理
    private void processRefund(Long studentId, Long courseId) {
        // 实际系统中，这里会调用支付宝、微信等支付平台的退款接口
        System.out.println("退款处理：学生 " + studentId + " 退款 " + courseId + " 课程费用");
    }

    // 模拟回收课程权限
    private void revokeCourseAccess(Long studentId, Long courseId) {
        // 实际系统中，这里会调用课程权限管理模块来回收课程访问权限
        System.out.println("回收权限：学生 " + studentId + " 对课程 " + courseId + " 的访问权限");
    }

    // 获取所有退款申请
    public List<RefundRequest> getRefundRequests() {
        return refundRequestDatabase;
    }

    // 获取某学生的退款状态
    public RefundRequest getRefundStatus(Long studentId) {
        return refundRequestDatabase.stream()
                .filter(r -> r.getStudentId().equals(studentId))
                .findFirst()
                .orElse(null);
    }
}
