package course.spring.service;

import course.spring.dao.UserRepositoryJpa;
import course.spring.exception.NonexisitngEntityException;
import course.spring.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static course.spring.model.Role.AUTHOR;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepositoryJpa userRepository;

    @Override
//    @PostFilter("(filterObject.id == authentication.principal.id) or hasAuthority('ROLE_ADMIN')")
    public List<User> getUsers() {
        return userRepository.findAll().stream()
                .peek(user -> user.setPassword(null))
                .collect(Collectors.toList()); // remove passwords when returning users
    }

    @Override
//    @PreAuthorize("(#userId == authentication.principal.id) or hasRole('ADMIN')")
    public User getUserById(Long userId) {
        User found = userRepository.findById(userId).orElseThrow(() -> new NonexisitngEntityException(
                String.format("User with ID='%s' does not exist.", userId)));
        found.setPassword(null);
        return found;
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new NonexisitngEntityException(
                String.format("User with username='%s' does not exist.", username)));
    }

    @Override
//    @RolesAllowed("ADMIN")
    public User createUser(User user) {
        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        if(user.getRoles() == null || user.getRoles().size() == 0) {
            user.setRoles(Set.of(AUTHOR));
        }
//        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//        user.setPassword(encoder.encode(user.getPassword()));
        user.setActive(true);
        return userRepository.save(user);
    }

    @Override
//    @PreAuthorize("(#user.id == authentication.principal.id) or hasRole('ADMIN')")
    public User updateUser(User user) {
        User old = userRepository.findById(user.getId()).orElseThrow(() -> new NonexisitngEntityException(
                String.format("User with ID='%s' does not exist.", user.getId())));
        user.setUsername(old.getUsername());
        if(user.getPassword() == null || user.getPassword().length() == 0) {
            user.setPassword(old.getPassword());
        }
//        else {
//            PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//            user.setPassword(encoder.encode(user.getPassword()));
//        }
        user.setCreated(old.getCreated());
        user.setModified(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
//    @RolesAllowed("ADMIN")
    public User removeUser(Long userId) {
        User old = getUserById(userId);
        userRepository.deleteById(userId);
        old.setPassword(null);
        return old;
    }

    @Override
    public long getUserCount() {
        return userRepository.count();
    }
}
