package br.com.fiap.suporte.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.pgvector.PgVectorStore;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;

@Service
public class ChatService {

    private final PgVectorStore vectorStore;
    private ChatClient chatClient;

    public ChatService(ChatClient.Builder builder, PgVectorStore vectorStore) {
        this.chatClient = builder
                .defaultAdvisors(QuestionAnswerAdvisor.builder(vectorStore).build())
                .build();
        this.vectorStore = vectorStore;
    }

    public String sendMessageToAI(String message) {
        var image = new ClassPathResource("docs/img.png");
        return chatClient.prompt()
                .user(u -> u
                        .text(message)
                        .media(MimeTypeUtils.IMAGE_PNG, image)
                )
                .call()
                .content();
    }

}
