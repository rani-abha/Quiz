//package com.app.quiz.exception.handler;
//
//import com.app.quiz.exception.QuizNotFoundException;
//import com.app.quiz.exception.ErrorResponse; // Add this import for the custom response class
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//    // Handle QuizNotFoundException
//    @ExceptionHandler(QuizNotFoundException.class)
//    public ResponseEntity<ErrorResponse> handleQuizNotFoundException(QuizNotFoundException e) {
//        ErrorResponse errorResponse = new ErrorResponse("QUIZ_NOT_FOUND", e.getMessage());
//        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
//    }
//
//    // Handle any other generic exception
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
//        ErrorResponse errorResponse = new ErrorResponse("INTERNAL_SERVER_ERROR", e.getMessage());
//        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//}
