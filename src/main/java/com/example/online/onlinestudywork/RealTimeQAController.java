package com.example.online.onlinestudywork;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/qa")
public class RealTimeQAController {

    @Autowired
    private QuestionService questionService;

    // 学生提问接口
    @PostMapping("/ask")
    public String askQuestion(@RequestParam Long studentId,
                              @RequestParam Long courseId,
                              @RequestParam String content) {
        return questionService.askQuestion(studentId, courseId, content);
    }

    // 教师回答问题接口
    @PostMapping("/answer")
    public String answerQuestion(@RequestParam Long teacherId,
                                 @RequestParam Long questionId,
                                 @RequestParam String answerContent) {
        return questionService.answerQuestion(teacherId, questionId, answerContent);
    }

    // 获取某课程下的所有未解答问题
    @GetMapping("/questions/{courseId}")
    public List<Question> getUnansweredQuestions(@PathVariable Long courseId) {
        return questionService.getUnansweredQuestions(courseId);
    }

    // 获取已解答问题列表
    @GetMapping("/answered")
    public List<Question> getAnsweredQuestions() {
        return questionService.getAnsweredQuestions();
    }
}
