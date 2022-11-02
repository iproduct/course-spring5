package course.spring.core.service;

import course.spring.core.model.Article;
import course.spring.core.model.User;

import java.util.List;

public interface UserProvider {
    List<User> getUsers();
}
