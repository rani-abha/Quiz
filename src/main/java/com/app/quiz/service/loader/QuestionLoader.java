package com.app.quiz.service.loader;

import com.app.quiz.model.Question;
import com.app.quiz.repository.QuestionRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class QuestionLoader implements CommandLineRunner {

    private final QuestionRepository questionRepository;
    private final ObjectMapper objectMapper;

    public QuestionLoader(QuestionRepository questionRepository, ObjectMapper objectMapper) {
        this.questionRepository = questionRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        if (questionRepository.count() == 0) {
            loadQuestions();
        }
    }

    private void loadQuestions() throws IOException {
        // Load the questions from the JSON file as a JsonNode
        File file = new File("src/main/resources/static/questions.json");
        JsonNode questionsNode = objectMapper.readTree(file);

        List<Question> questions = new ArrayList<>();

        // Iterate over the JSON array and create Question entities
        Iterator<JsonNode> elements = questionsNode.elements();
        while (elements.hasNext()) {
            JsonNode questionNode = elements.next();

            // Manually extract each field, including the correctOption
            String questionText = questionNode.get("questionText").asText();
            String optionA = questionNode.get("optionA").asText();
            String optionB = questionNode.get("optionB").asText();
            String optionC = questionNode.get("optionC").asText();
            String optionD = questionNode.get("optionD").asText();
            String correctOption = questionNode.get("correctOption").asText();

            // Create a new Question entity
            Question question = new Question(questionText, optionA, optionB, optionC, optionD, correctOption);
            questions.add(question);
        }

        // Save questions to the database
        questionRepository.saveAll(questions);
    }
}
