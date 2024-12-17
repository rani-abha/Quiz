package com.app.quiz.controller;

import com.app.quiz.exception.QuizAlreadyAnsweredException;
import com.app.quiz.exception.QuizNotFoundException;
import com.app.quiz.model.Question;
import com.app.quiz.model.QuizResult;
import com.app.quiz.response.Response;
import com.app.quiz.service.QuizService;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping("/start")
    public ResponseEntity<Response> startQuizSession() {
        try {
            return ResponseEntity.ok(Response.builder()
                .responseTime(LocalDateTime.now())
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message("Quiz session started")
                .data(Map.of("sessionId", quizService.startQuizSession()))
                .build());
        } catch (Exception e) {
                return ResponseEntity.status(404).body(Response.builder()
                .responseTime(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND)
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message("ERROR" + e.getMessage())
                .build());
            }
    }

    @GetMapping("/question")
    public ResponseEntity<Response> getQuestion(@RequestParam Long sessionId) {
        try {
            Question question = quizService.getRandomQuestion(sessionId);
            if (question != null) {
                return ResponseEntity.ok(Response.builder()
                    .responseTime(LocalDateTime.now())
                    .status(HttpStatus.OK)
                    .statusCode(HttpStatus.OK.value())
                    .data(Map.of("Question", question))
                    .build());
            } else {
                return ResponseEntity.ok(Response.builder()
                    .responseTime(LocalDateTime.now())
                    .status(HttpStatus.OK)
                    .statusCode(HttpStatus.OK.value())
                    .message("No more questions available")
                    .build());
            }
        } catch (Exception e) {
                return ResponseEntity.status(404).body(Response.builder()
                    .responseTime(LocalDateTime.now())
                    .status(HttpStatus.NOT_FOUND)
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .message("ERROR" + e.getMessage())
                    .build());
            
        }
    }

    @PostMapping("/submit")
    public ResponseEntity<Response> submitAnswer(@RequestParam Long sessionId, @RequestParam Long questionId, @RequestParam String selectedOption) {
        try {
            if (!selectedOption.matches("[abcdABCD]")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.builder()
                    .responseTime(LocalDateTime.now())
                    .status(HttpStatus.BAD_REQUEST)
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .message("Invalid option selected. Please choose a, b, c, or d.")
                    .build());
            }
            boolean isCorrect = quizService.submitAnswer(sessionId, questionId, selectedOption);
            return ResponseEntity.ok(Response.builder()
                .responseTime(LocalDateTime.now())
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .message(isCorrect ? "Correct" : "Incorrect")
                .build());
        } catch (Exception e) {
            return ResponseEntity.status(404).body(Response.builder()
            .responseTime(LocalDateTime.now())
            .status(HttpStatus.NOT_FOUND)
            .statusCode(HttpStatus.NOT_FOUND.value())
            .message("ERROR" + e.getMessage())
            .build());
        }
    }

    @GetMapping("/results")
    public ResponseEntity<Response> getQuizResults(@RequestParam Long sessionId) {
        try {
            QuizResult result = quizService.getQuizResults(sessionId);
            return ResponseEntity.ok(Response.builder()
                .responseTime(LocalDateTime.now())
                .status(HttpStatus.OK)
                .statusCode(HttpStatus.OK.value())
                .data(Map.of("result", result))
                .build());
        } catch (QuizNotFoundException e) {
            return ResponseEntity.status(404).body(Response.builder()
                .responseTime(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND)
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message("Quiz not found: " + e.getMessage())
                .build());
        }
    }
}
