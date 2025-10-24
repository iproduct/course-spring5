package course.spring.springai.demo;

import org.springframework.lang.Nullable;

import java.util.UUID;

record ChatRequest(@Nullable UUID chatId, String question) {
}
