package course.spring.blogs.service;

import course.spring.blogs.entity.User;
import course.spring.blogs.exception.InvalidEntityDataException;
import course.spring.blogs.exception.NonexistingEntityException;

import java.util.List;

/**
 * UserService interface
 */
public interface UserService {
    /**
     * @return list all {@link User} entities
     */
    List<User> getAllUsers();
    /**
     * @param id the ID of User to be found
     * @return The User with given ID, if such exists
     * @throws NonexistingEntityException when the User with given ID does not exist
     */
    User getUserById(Long id) throws NonexistingEntityException;
    User getUserByUsername(String username) throws NonexistingEntityException;
    User create(User user) throws InvalidEntityDataException;
    User update(User user) throws NonexistingEntityException, InvalidEntityDataException;
    User deleteUserById(Long id) throws NonexistingEntityException;
    long getUsersCount();
}
