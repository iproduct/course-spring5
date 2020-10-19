package course.spring.restmvc.service;

import course.spring.restmvc.exception.InvalidEntityDataException;
import course.spring.restmvc.exception.NonexistingEntityException;
import course.spring.restmvc.model.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id) throws NonexistingEntityException;
    User getUserByUsername(String username) throws NonexistingEntityException;
    User createUser(User user) throws InvalidEntityDataException;
    User updateUser(User user) throws InvalidEntityDataException, NonexistingEntityException;
    User deleteUser(Long id) throws NonexistingEntityException;
    long count();
}
