package course.spring.blogs.service;

import course.spring.blogs.dto.UserCreateDto;
import course.spring.blogs.dto.UserDto;
import course.spring.blogs.entity.User;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();
    UserDto getUserById(Long id);
    UserDto getUserByUsername(String username);
    UserDto addUser(UserCreateDto user);
    UserDto updateUser(UserDto userDto);
    UserDto deleteUserById(Long id);
    long getUsersCount();
}
