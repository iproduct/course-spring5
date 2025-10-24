package course.spring.springai.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.api.OllamaModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
class ChatbotService {

    private final ChatClient chatClient;

    ChatbotService(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @SneakyThrows
    ChatResponse chat(ChatRequest chatRequest) {
        UUID chatId = Optional
            .ofNullable(chatRequest.chatId())
            .orElse(UUID.randomUUID());
        String jsonSchema = """
        {
            "type": "object",
            "properties": {
                "steps": {
                    "type": "array",
                    "items": {
                        "type": "object",
                        "properties": {
                            "explanation": { "type": "string" },
                            "output": { "type": "string" }
                        },
                        "required": ["explanation", "output"],
                        "additionalProperties": false
                    }
                },
                "final_answer": { "type": "string" }
            },
            "required": ["steps", "final_answer"],
            "additionalProperties": false
        }
        """;
        //"how can I solve 8x + 7 = -23"
        Prompt prompt = new Prompt(chatRequest.question(),
                OllamaOptions.builder()
                        .model(OllamaModel.LLAMA3_2.getName())
                        .format(new ObjectMapper().readValue(jsonSchema, Map.class))
                        .build());

        ChatModelResponse response = chatClient
            .prompt(prompt)
//            .user(chatRequest.question())
            .advisors(advisorSpec ->
                advisorSpec
                    .param("chat_memory_conversation_id", chatId))
            .call()
            .entity(new ChatModelOutputConverter());
        return new ChatResponse(chatId, response.chainOfThought(), response.answer());
    }

}
