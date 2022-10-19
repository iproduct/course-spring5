package org.iproduct.di;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.inject.Inject;

import dynamicproxy.*;
import org.iproduct.di.exceptions.BeanInstantiationException;

import io.github.classgraph.AnnotationInfo;
import io.github.classgraph.FieldInfo;
import io.github.classgraph.FieldInfoList;

public class DIProxy implements InvocationHandler {
	Context context;
	Object target;
	BeanDescriptor descriptor;

	public DIProxy(Context ctx, Object target, BeanDescriptor descriptor) {
		this.context = ctx;
		this.target = target;
		this.descriptor = descriptor;
		initializeTarget();
	}

	protected void initializeTarget() {
		Class<?> targetClass = descriptor.getClassInfo().loadClass();
		FieldInfoList fil = descriptor.getClassInfo().getFieldInfo();
		for(FieldInfo f: fil) {
			AnnotationInfo injectInfo = f.getAnnotationInfo(Inject.class.getName());
			if(injectInfo == null) continue;
			Class<?> fieldType;
			try {
				fieldType = targetClass.getDeclaredField(f.getName()).getType();
			} catch (NoSuchFieldException | SecurityException e) {
				throw new BeanInstantiationException("Field lookup error for: '" + f.getName() + "'.", e);
			}
			Object injected = null;
			injected = context.getBean(fieldType);
			try {
				Field field = descriptor.getClassInfo().loadClass().getDeclaredField(f.getName());
				field.setAccessible(true);
				field.set(target, injected);
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
				throw new BeanInstantiationException("Error injecting field '" + f.getName() + "' with value: " + injected, e);
			}

//			Map<String, AnnotationParameterValue> params = injectInfo.getParameterValues().asMap();
//			params.get(arg0)

		}
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
		UserRepository instance = new UserRepositoryImpl();
		UserRepository proxy = (UserRepository) Proxy.newProxyInstance(UserRepository.class.getClassLoader(),
				new Class[] { UserRepository.class  },
				new DIProxy(null, instance, null));
		proxy.addUser(new User(1, "Ivan Petrov", "ivan@gmail.com"));
		proxy.addUser(new User(2, "John Doe", "john.doe@gmail.com"));
		proxy.addUser(new User(2, "Jane Doe", "jane.doe@gmail.com"));
		System.out.println(proxy.findAll());
		System.out.println(proxy.findAll());

		System.out.println("\n============================================================\n\nController profiling:\n");

		UserController ucInstance = new UserControllerImpl();
		ucInstance.setRepo(proxy);
		UserController ucProxy = (UserController) Proxy.newProxyInstance(UserController.class.getClassLoader(),
				new Class[] { UserController.class  },
				new DIProxy(null, ucInstance, null));
		ucProxy.findAllUsers();
	}
}
