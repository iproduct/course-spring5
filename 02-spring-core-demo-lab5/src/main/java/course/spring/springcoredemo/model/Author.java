package course.spring.springcoredemo.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component("author")
@Scope("prototype")
public class Author {
    private String name;
    private int age;
    private Properties emails;

    public String getName() {
        return name;
    }

    @Autowired
    public void setName(@Value("${articles.author}")String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Properties getEmails() {
        return emails;
    }

    public void setEmails(Properties emails) {
        this.emails = emails;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Author{");
        sb.append("name='").append(name).append('\'');
        sb.append(", age=").append(age);
        sb.append(", emails=").append(emails);
        sb.append('}');
        return sb.toString();
    }
}
