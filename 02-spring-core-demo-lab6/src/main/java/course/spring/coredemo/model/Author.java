package course.spring.coredemo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Properties;

@Data
@NoArgsConstructor
public class Author {
    private String name;
    private int age;
    private Properties emails;
}
