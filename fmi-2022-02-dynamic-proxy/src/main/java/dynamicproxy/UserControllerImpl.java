package dynamicproxy;

import javax.inject.Inject;

import org.iproduct.di.annotations.Component;
import org.iproduct.di.annotations.InMemory;
import org.iproduct.di.annotations.JaxbRepository;
import org.iproduct.di.annotations.Service;

import java.util.Collection;
import java.util.List;

@Service("userService")
public class UserControllerImpl implements UserController {
	@Inject
	@InMemory
	private UserRepository repo;

	public UserRepository getRepo() {
		return repo;
	}


	public void setRepo(UserRepository repo) {
		this.repo = repo;
	}

	@Override
	public Collection<User> findAllUsers() {
		return repo.findAll();
	}
	
}
