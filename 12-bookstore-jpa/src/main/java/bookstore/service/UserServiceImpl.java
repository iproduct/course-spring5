package bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import bookstore.dao.UserRepository;
import bookstore.exception.EntityExistsException;
import bookstore.exception.EntityNotFoundException;
import bookstore.model.User;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public List<User> getAll() {
		return userRepository.findAll();
	}

	@Override
	public User getById(int id) throws EntityNotFoundException {
		return userRepository.findById(id).orElseThrow(() -> new  EntityNotFoundException());
	}

	@Override
	public User create(User user) throws EntityExistsException {
		log.info(">>> User Password: {}", user.getPassword());
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(true);
		return userRepository.save(user);
	}

	@Override
	public User update(User user) throws EntityNotFoundException {
		
		return userRepository.save(user);
	}

	@Override
	public User delete(int userId) throws EntityNotFoundException {
		User user=getById(userId);
		 userRepository.deleteById(userId);
		 return user;
	}

	@Override
	public User getByUsername(String username) throws EntityNotFoundException {
		return userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException());
	}

}
