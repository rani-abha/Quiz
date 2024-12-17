package com.app.quiz.service;

import com.app.quiz.model.Question;
import com.app.quiz.model.QuizSession;
import com.app.quiz.model.QuizResult;

import java.util.List;

public interface QuizService {

    /**
     * Starts a new quiz session and returns the session details.
     *
     * @return a new QuizSession object
     */
    QuizSession startQuizSession();

    /**
     * Gets a random question for the given session.
     *
     * @param sessionId the ID of the quiz session
     * @return a random Question
     */
    Question getRandomQuestion(Long sessionId);

    /**
     * Submits an answer for a given question in the quiz session.
     *
     * @param sessionId the ID of the quiz session
     * @param questionId the ID of the question
     * @param selectedOption the option selected by the user (A, B, C, D)
     * @return true if the answer is correct, false otherwise
     */
    boolean submitAnswer(Long sessionId, Long questionId, String selectedOption);

    /**
     * Retrieves the total number of answered questions along with details on correct and incorrect answers.
     *
     * @param sessionId the ID of the quiz session
     * @return a QuizResult object with the results
     */
    QuizResult getQuizResults(Long sessionId);
}
