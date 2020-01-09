package course.spring.webmvc.domain;

import course.spring.webmvc.dao.UsersRepository;
import course.spring.webmvc.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsersServiceImpl implements UsersService {
    @Autowired
    private UsersRepository usersRepository;

    @Override
    @PostFilter("(filterObject.id == authentication.principal.id) or hasAuthority('ROLE_ADMIN')")
    public List<User> findAll() {
        return usersRepository.findAll().stream()
                .peek(user -> user.setPassword(null))
                .collect(Collectors.toList()); // remove passwords when returning users
    }

    @Override
    @PreAuthorize("(#userId == authentication.principal.id) or hasRole('ADMIN')")
    public User findById(String userId) {
        User found = usersRepository.findById(userId).orElseThrow(() -> new course.spring.restmvc.exception.NonexisitngEntityException(
                String.format("User with ID='%s' does not exist.", userId)));
        found.setPassword(null);
        return found;
    }

    @Override
    public User findByUsername(String username) {
        return usersRepository.findByUsername(username).orElseThrow(() -> new course.spring.restmvc.exception.NonexisitngEntityException(
                String.format("User with username='%s' does not exist.", username)));
    }

    @Override
    @RolesAllowed("ADMIN")
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
    @PreAuthorize("(#user.id == authentication.principal.id) or hasRole('ADMIN')")
    public User update(User user) {
        User old = usersRepository.findById(user.getId()).orElseThrow(() -> new course.spring.restmvc.exception.NonexisitngEntityException(
                String.format("User with ID='%s' does not exist.", user.getId())));
        user.setUsername(old.getUsername());
        if(user.getPassword() == null || user.getPassword().length() == 0) {
            user.setPassword(old.getPassword());
        } else {
            PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
        }
        user.setCreated(old.getCreated());
        user.setModified(LocalDateTime.now());
        return usersRepository.save(user);
    }

    @Override
    @RolesAllowed("ADMIN")
    public User remove(String userId) {
        User old = findById(userId);
        usersRepository.deleteById(userId);
        old.setPassword(null);
        return old;
    }

    @Override
    public long count() {
        return usersRepository.count();
    }
}
