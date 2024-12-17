package com.app.quiz.service.impl;

import com.app.quiz.exception.QuizAlreadyAnsweredException;
import com.app.quiz.exception.QuizNotFoundException;
import com.app.quiz.model.Question;
import com.app.quiz.model.QuizResult;
import com.app.quiz.model.QuizSession;
import com.app.quiz.repository.QuestionRepository;
import com.app.quiz.repository.QuizSessionRepository;
import com.app.quiz.service.QuizService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class QuizServiceImpl implements QuizService {

    private final QuizSessionRepository quizSessionRepository;
    private final QuestionRepository questionRepository;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public QuizServiceImpl(QuizSessionRepository quizSessionRepository, QuestionRepository questionRepository) {
        this.quizSessionRepository = quizSessionRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public QuizSession startQuizSession() {
        QuizSession quizSession = new QuizSession();
        quizSession.setAnsweredQuestions(new ArrayList<>());
        quizSession.setCorrectAnswersCount(0);
        quizSession.setTotalQuestionsCount(0);
        quizSession.setExpired(false);

        QuizSession savedSession = quizSessionRepository.save(quizSession);
        scheduler.schedule(() -> expireSession(savedSession.getSessionId()), 5, TimeUnit.MINUTES);
        return savedSession;
    }

    private void expireSession(Long sessionId) {
        Optional<QuizSession> session = quizSessionRepository.findById(sessionId);
        if (session.isPresent()) {
            QuizSession quizSession = session.get();
            quizSession.setExpired(true);
            quizSessionRepository.save(quizSession);
        }
    }


    @Override
    public Question getRandomQuestion(Long sessionId) {
        Optional<QuizSession> session = quizSessionRepository.findById(sessionId);
        if (session.isEmpty()) {
            throw new QuizNotFoundException("Quiz session with ID " + sessionId + " not found.");
        }

        List<Question> allQuestions = questionRepository.findAll();
        List<Long> answeredQuestionIds = session.get().getAnsweredQuestions();
        List<Question> unansweredQuestions = new ArrayList<>();

        for (Question question : allQuestions) {
            if (!answeredQuestionIds.contains(question.getId())) {
                unansweredQuestions.add(question);
            }
        }

        if (unansweredQuestions.isEmpty()) {
            return null;
        }

        Random random = new Random();
        return unansweredQuestions.get(random.nextInt(unansweredQuestions.size()));
    }

    @Override
    public boolean submitAnswer(Long sessionId, Long questionId, String selectedOption) {
        Optional<QuizSession> session = quizSessionRepository.findById(sessionId);
        if (session.isEmpty()) {
            throw new QuizNotFoundException("Quiz session with ID " + sessionId + " not found.");
        }

        Optional<Question> question = questionRepository.findById(questionId);
        if (question.isEmpty()) {
            throw new QuizNotFoundException("Question with ID " + questionId + " not found.");
        }

        if (session.get().getAnsweredQuestions().contains(questionId)) {
            throw new QuizAlreadyAnsweredException("Question with ID " + questionId + " has already been answered.");
        }

        boolean isCorrect = question.get().getCorrectOption().equalsIgnoreCase(selectedOption);
        session.get().getAnsweredQuestions().add(questionId);
        session.get().setTotalQuestionsCount(session.get().getTotalQuestionsCount() + 1);

        if (isCorrect) {
            session.get().setCorrectAnswersCount(session.get().getCorrectAnswersCount() + 1);
        }

        quizSessionRepository.save(session.get());
        return isCorrect;
    }

    @Override
    public QuizResult getQuizResults(Long sessionId) {
        Optional<QuizSession> session = quizSessionRepository.findById(sessionId);
        if (session.isEmpty()) {
            throw new QuizNotFoundException("Quiz session with ID " + sessionId + " not found.");
        }

        QuizResult quizResult = new QuizResult();
        quizResult.setTotalQuestions(session.get().getTotalQuestionsCount());
        quizResult.setCorrectAnswers(session.get().getCorrectAnswersCount());
        quizResult.setIncorrectAnswers(session.get().getTotalQuestionsCount() - session.get().getCorrectAnswersCount());

        return quizResult;
    }
}
