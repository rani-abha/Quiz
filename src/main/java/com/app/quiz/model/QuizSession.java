package com.app.quiz.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import org.hibernate.annotations.Where;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "is_expired = false")
public class QuizSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sessionId;

    @ElementCollection
    private List<Long> answeredQuestions;  

    private int correctAnswersCount;  
    private int totalQuestionsCount;
    private boolean isExpired = true;   
}
