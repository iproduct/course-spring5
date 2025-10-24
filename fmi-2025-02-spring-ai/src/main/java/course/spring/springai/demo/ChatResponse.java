package course.spring.springai.demo;

import java.util.UUID;

record ChatResponse(UUID chatId, String chainOfThought, String answer) {
}
