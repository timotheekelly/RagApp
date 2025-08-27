package com.mongodb.RagApp.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class RagController {

    private final ChatClient chatClient;

    public RagController(ChatClient.Builder builder, VectorStore vectorStore) {
        this.chatClient = builder.defaultAdvisors(new QuestionAnswerAdvisor(vectorStore)).build();
    }

    @GetMapping("/faq")
    public String faq(@RequestParam(value = "message", defaultValue = "How to analyze time-series data with Python and MongoDB? Explain all the steps.") String message) {

        return chatClient.prompt()
                .user(message)          // User message (the query)
                .call()                 // Call the model
                .content();
    }

}
