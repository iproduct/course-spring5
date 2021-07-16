package course.spring.blogs.service.impl;

import course.spring.blogs.dao.UserRepository;
import course.spring.blogs.dao.UserRepository;
import course.spring.blogs.dto.UserCreateDto;
import course.spring.blogs.dto.UserDto;
import course.spring.blogs.entity.User;
import course.spring.blogs.exception.InvalidEntityDataException;
import course.spring.blogs.exception.NonexistingEntityException;
import course.spring.blogs.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> mapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new NonexistingEntityException(String.format("User with ID=%d does not exist", id))
        );
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() ->
                new AuthenticationCredentialsNotFoundException("Username not valid"));
    }

    @Override
    public UserDto addUser(UserCreateDto userDto) {
        User user = mapper.map(userDto, User.class);
        user.setId(null);
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        return mapper.map(userRepository.save(user), UserDto.class);
    }

    @Override
    public User updateUser(User user) {
        User old = getUserById(user.getId());
        if (user.getPosts() != null) {
            throw new InvalidEntityDataException(String.format("User posts should not be changed here: %s",
                    user.getPosts()));
        }
        user.setPosts(old.getPosts());
        user.setPassword(old.getPassword());
        user.setModified(LocalDateTime.now());
        User updated = userRepository.save(user);
        return updated;
    }

    @Override
    public User deleteUserById(Long id) {
        User deleted = getUserById(id);
        userRepository.deleteById(id);
        return deleted;
    }

    @Override
    public long getUsersCount() {
        return userRepository.count();
    }
}
