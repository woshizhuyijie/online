package com.example.online.rufund;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Student student;

    @ManyToOne
    private Course course;

    private LocalDateTime purchaseDate;
    private LocalDateTime assignmentSubmissionDate;
    private double videoProgress;

    // Getters and Setters

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public LocalDateTime getAssignmentSubmissionDate() {
        return assignmentSubmissionDate;
    }

    public void setAssignmentSubmissionDate(LocalDateTime assignmentSubmissionDate) {
        this.assignmentSubmissionDate = assignmentSubmissionDate;
    }

    public double getVideoProgress() {
        return videoProgress;
    }

    public void setVideoProgress(double videoProgress) {
        this.videoProgress = videoProgress;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
