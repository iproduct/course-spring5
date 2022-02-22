package course.ws.service.impl;

import course.ws.dao.ArticleRepository;
import course.ws.model.Article;
import course.ws.model.User;
import course.ws.service.ArticleProvider;
import course.ws.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Value("${users.default.admin}")
    private String[] defaultAdminData;
    @Value("${users.default.author}")
    private String[] defaultAuthorData;
    @Value("${users.default.reader}")
    private String[] defaultReaderData;

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public User getUserById(Long id) {
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        return null;
    }

    @Override
    public User createUser(User user) {
        return null;
    }
}
