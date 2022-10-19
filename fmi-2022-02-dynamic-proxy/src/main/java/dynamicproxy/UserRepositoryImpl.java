package dynamicproxy;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.iproduct.di.BeanScope;
import org.iproduct.di.annotations.InMemory;
import org.iproduct.di.annotations.Repository;
import org.iproduct.di.annotations.Scope;

@Scope(BeanScope.SINGLETON)
@Repository
@InMemory
public class UserRepositoryImpl implements UserRepository{
	Map<Long, User> users = new ConcurrentHashMap<Long, User>();
	AtomicLong sequence = new AtomicLong();

	@Override
	public Collection<User> findAll() {
		return users.values();
	}

	@Override
	public User addUser(User user) {
		user.setId(sequence.incrementAndGet());
		users.put(user.getId(), user);
		return user;
	}
	
}
