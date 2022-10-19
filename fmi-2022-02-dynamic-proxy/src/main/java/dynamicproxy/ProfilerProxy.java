package dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProfilerProxy implements InvocationHandler {
	Object target;

	public ProfilerProxy(Object target) {
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		long before = System.nanoTime();
		Object result = method.invoke(target, args);
		long after = System.nanoTime();
		System.out.println("***** Profiling: " + method + ", args: " + args + " executed in: " + (after-before));
		return result;
	}
	
	public static void main(String[] args) {
		UserRepository real = new UserRepositoryImpl();
		UserRepository proxy = (UserRepository) Proxy.newProxyInstance(
			UserRepository.class.getClassLoader(), 
			new Class[]{UserRepository.class}, 
			new ProfilerProxy(real)
		);
		proxy.addUser(new User("Ivan Petrov", "ivan@abv.bg"));
		proxy.addUser(new User("Dimitar Simeonov", "mitko@gmail.com"));
		System.out.println(proxy.findAll());
	}


}
