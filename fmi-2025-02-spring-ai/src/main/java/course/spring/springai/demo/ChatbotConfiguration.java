package course.spring.springai.demo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ChatbotConfiguration {

//    @Bean
//    ChatMemory chatMemory() {
//        return new InMemoryChatMemoryRepository();
//    }

    /**
     * When using alternate providers, use the following qualifiers instead:
     * - @Qualifier("bedrockProxyChatModel") for Amazon Bedrock Converse API
     * - @Qualifier("ollamaChatModel") for Ollama
     */
    @Bean
    ChatClient chatClient(
            @Qualifier("ollamaChatModel")
            ChatModel chatModel,
            ChatMemory chatMemory
    ) {
        return ChatClient
                .builder(chatModel)
//                .defaultOptions(ChatOptions.builder().build())
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }

}
