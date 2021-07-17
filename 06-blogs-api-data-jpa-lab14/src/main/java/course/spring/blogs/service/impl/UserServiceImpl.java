package course.spring.blogs.service.impl;

import course.spring.blogs.dao.UserRepository;
import course.spring.blogs.dto.UserCreateDto;
import course.spring.blogs.dto.UserDto;
import course.spring.blogs.entity.User;
import course.spring.blogs.exception.InvalidEntityDataException;
import course.spring.blogs.exception.NonexistingEntityException;
import course.spring.blogs.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.security.RolesAllowed;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private ModelMapper mapper;

    @Override
    @Transactional(readOnly = true)
    @PostFilter("filterObject.id == authentication.principal.id or hasRole('ADMIN')")
    public List<UserDto> getAllUsers() {
        return userRepo.findAll().stream()
                .map(user -> mapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    public UserDto getUserById(Long id) {
        return mapper.map(userRepo.findById(id).orElseThrow(() ->
                new NonexistingEntityException(String.format("User with ID=%d does not exist", id))
        ), UserDto.class);
    }

    @Override
    @RolesAllowed("ADMIN")
    public UserDto getUserByUsername(String username) {
        return mapper.map(userRepo.findByUsername(username).orElseThrow(() ->
                new AuthenticationCredentialsNotFoundException("Username not valid")), UserDto.class);
    }

    @Override
    @RolesAllowed("ADMIN")
    public UserDto addUser(UserCreateDto userDto) {
        User user = mapper.map(userDto, User.class);
        user.setId(null);
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setCreated(LocalDateTime.now());
        user.setModified(LocalDateTime.now());
        return mapper.map(userRepo.save(user), UserDto.class);
    }

    @Override
    @PreAuthorize("#userDto.id == authentication.principal.id or hasRole('ADMIN')")
    public UserDto updateUser(UserDto userDto) {
        User user = mapper.map(userDto, User.class);
        User old = userRepo.findById(user.getId()).orElseThrow(() ->
                new NonexistingEntityException(String.format("User with ID=%d does not exist", user.getId())));
        if (user.getPosts() != null) {
            throw new InvalidEntityDataException(String.format("User posts should not be changed here: %s",
                    user.getPosts()));
        }
        user.setPosts(old.getPosts());
        user.setPassword(old.getPassword());
        user.setModified(LocalDateTime.now());
        User updated = userRepo.save(user);
        return mapper.map(updated, UserDto.class);
    }

    @Override
    @RolesAllowed("ADMIN")
    @Secured("ROLE_ADMIN")
    public UserDto deleteUserById(Long id) {
        UserDto deleted = getUserById(id);
        userRepo.deleteById(id);
        return deleted;
    }

    @Override
    public long getUsersCount() {
        return userRepo.count();
    }
}
