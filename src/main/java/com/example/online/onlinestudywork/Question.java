package com.example.online.onlinestudywork;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Question {
    private Long id; // 问题ID
    private Long studentId; // 提问学生ID
    private Long courseId; // 课程ID
    private String content; // 问题内容
    private LocalDateTime timestamp; // 提问时间
    private String status; // 状态（未解决、已解决）
    private Long teacherId; // 回答教师ID
    private String answerContent; // 答复内容
    private LocalDateTime answerTimestamp; // 回答时间
}
