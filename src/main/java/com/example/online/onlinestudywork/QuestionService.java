package com.example.online.onlinestudywork;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    // 模拟数据库
    private static List<Question> questionDatabase = new ArrayList<>();

    // 学生提问
    public String askQuestion(Long studentId, Long courseId, String content) {
        Question question = new Question();
        question.setId((long) (questionDatabase.size() + 1));
        question.setStudentId(studentId);
        question.setCourseId(courseId);
        question.setContent(content);
        question.setTimestamp(java.time.LocalDateTime.now());
        question.setStatus("未解决");

        questionDatabase.add(question);
        return "问题已提交";
    }

    // 教师回答
    public String answerQuestion(Long teacherId, Long questionId, String answerContent) {
        Question question = questionDatabase.stream()
                .filter(q -> q.getId().equals(questionId))
                .findFirst()
                .orElse(null);

        if (question != null && "未解决".equals(question.getStatus())) {
            question.setTeacherId(teacherId);
            question.setAnswerContent(answerContent);
            question.setAnswerTimestamp(java.time.LocalDateTime.now());
            question.setStatus("已解决");
            return "回答已提交";
        } else {
            return "问题未找到或已解答";
        }
    }

    // 获取未解答问题
    public List<Question> getUnansweredQuestions(Long courseId) {
        List<Question> unansweredQuestions = new ArrayList<>();
        for (Question question : questionDatabase) {
            if ("未解决".equals(question.getStatus()) && question.getCourseId().equals(courseId)) {
                unansweredQuestions.add(question);
            }
        }
        return unansweredQuestions;
    }

    // 获取已解答问题
    public List<Question> getAnsweredQuestions() {
        List<Question> answeredQuestions = new ArrayList<>();
        for (Question question : questionDatabase) {
            if ("已解决".equals(question.getStatus())) {
                answeredQuestions.add(question);
            }
        }
        return answeredQuestions;
    }
}
