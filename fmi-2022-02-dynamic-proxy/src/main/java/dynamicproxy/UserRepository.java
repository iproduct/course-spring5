package dynamicproxy;

import java.util.Collection;

public interface UserRepository {
	Collection<User> findAll();
	User addUser(User u);
}
