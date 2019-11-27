package course.spring.restmvc.domain;

import course.spring.restmvc.dao.UsersRepository;
import course.spring.restmvc.exception.NonexisitngEntityException;
import course.spring.restmvc.model.Article;
import course.spring.restmvc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService{
    @Autowired
    private UsersRepository usersRepository;

    @Override
    public List<User> findAll() {
        return usersRepository.findAll();
    }

    @Override
    public User findById(String userId) {
        return usersRepository.findById(userId).orElseThrow(() -> new NonexisitngEntityException(
                String.format("User with ID='%s' does not exist.", userId)));
    }

    @Override
    public User findByUsername(String username) {
        return usersRepository.findByUsername(username).orElseThrow(() -> new NonexisitngEntityException(
                String.format("User with username='%s' does not exist.", username)));
    }

    @Override
    public User add(User user) {
        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        if(user.getRoles() == null || user.getRoles().trim().length() == 0) {
            user.setRoles("ROLE_AUTHOR");
        }
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setActive(true);
        return usersRepository.insert(user);
    }

    @Override
    public User update(User user) {
        User old = findById(user.getId());
        user.setCreated(old.getCreated());
        user.setModified(LocalDateTime.now());
        return usersRepository.save(user);
    }

    @Override
    public User remove(String userId) {
        User old = findById(userId);
        usersRepository.deleteById(userId);
        return old;
    }

    @Override
    public long count() {
        return usersRepository.count();
    }
}
