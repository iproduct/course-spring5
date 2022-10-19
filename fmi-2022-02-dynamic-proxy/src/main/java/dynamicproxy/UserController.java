package dynamicproxy;

import java.util.Collection;
import java.util.List;

public interface UserController {
	UserRepository getRepo();
	Collection<User> findAllUsers();
	void setRepo(UserRepository repo);
}
