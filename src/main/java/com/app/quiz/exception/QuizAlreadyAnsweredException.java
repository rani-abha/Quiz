package com.app.quiz.exception;

public class QuizAlreadyAnsweredException extends RuntimeException {

    public QuizAlreadyAnsweredException(String message) {
        super(message);
    }
}
