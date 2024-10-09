package course.spring.intro.dto;

import java.util.Set;

public record PostCreateDto(String title, String content, Set<String> tags) {
}
