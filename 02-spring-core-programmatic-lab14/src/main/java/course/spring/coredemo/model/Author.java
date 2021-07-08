package course.spring.coredemo.model;

import lombok.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Author {
    private static int nextId = 0;
    private int id = ++nextId;
    @NonNull
    private String name;
}
