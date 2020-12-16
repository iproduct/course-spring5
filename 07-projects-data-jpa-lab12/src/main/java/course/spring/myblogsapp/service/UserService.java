package course.spring.myblogsapp.service;

import course.spring.myblogsapp.entity.User;

import javax.validation.Valid;
import java.util.List;

public interface UserService {
    List<User>  getAllUsers();
    User  getUserById(Long id);
    User addUser(@Valid User post);
    User updateUser(@Valid User post);
    User deleteUser(Long id);
    long getUsersCount();
}
