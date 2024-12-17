package com.app.quiz.model;

import lombok.Data;

@Data
public class QuizResult {
    private int totalQuestions;
    private int correctAnswers;
    private int incorrectAnswers;
}
