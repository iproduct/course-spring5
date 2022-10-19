package org.iproduct.di;

import static org.iproduct.di.BeanScope.SINGLETON;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iproduct.di.annotations.Component;
import org.iproduct.di.annotations.Repository;
import org.iproduct.di.annotations.Scope;
import org.iproduct.di.annotations.Service;
import org.iproduct.di.exceptions.BeanInstantiationException;
import org.iproduct.di.exceptions.ContextInitializationException;
import org.iproduct.di.exceptions.BeanLookupException;

import dynamicproxy.User;
import dynamicproxy.UserController;
import dynamicproxy.UserRepository;
import io.github.classgraph.AnnotationInfo;
import io.github.classgraph.AnnotationInfoList;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import lombok.extern.slf4j.Slf4j;

import static org.iproduct.di.BeanScope.*;

@Slf4j
public class ApplicationContext implements Context {
	public static final Class<?>[] BEAN_ANNOTATIONS = { Repository.class, Component.class, Service.class, };
	private Map<String, BeanDescriptor> beanByName = new HashMap<>();
	private Map<String, List<BeanDescriptor>> beansByType = new HashMap<>();

	public ApplicationContext() {
	}

	public ApplicationContext(String... packageNames) {
		for (String pName : packageNames) {
			scanForBeans(pName);
		}
	}

	@Override
	public <T> T getBean(String name, Class<T> cls) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getBean(Class<T> cls) {
		List<BeanDescriptor> beanDescriptors = beansByType.get(cls.getCanonicalName());
		if (beanDescriptors.size() != 1) {
			throw new BeanLookupException("There should be exactly one bean of type '" + cls + ", but " + beanDescriptors.size() + " found.");
		}
		return (T) getProxyInstance(cls, beanDescriptors.get(0));
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> List<T> getBeans(Class<T> cls) {
		List<BeanDescriptor> beanDescriptors = beansByType.get(cls.getCanonicalName());
		List<T> results = new ArrayList<>();
		for (BeanDescriptor bd : beanDescriptors) {
			if (cls.isAssignableFrom(bd.getClassInfo().loadClass())) {
				results.add((T) getProxyInstance(cls, bd));
			}
		}
		return results;
	}

	public Object getProxyInstance(Class<?> cls, BeanDescriptor bd) {
		if (SINGLETON.equals(bd.getScope())) {
			return bd.getInstance();
		} else {
			try {
				return Proxy.newProxyInstance(bd.getClassInfo().loadClass().getClassLoader(), new Class[] { cls },
						new DIProxy(this, bd.getClassInfo().loadClass().getDeclaredConstructor().newInstance(), bd));

			} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
				throw new BeanInstantiationException(
						"Error instantiating prototype scoped bean '" + bd.getName() + "':", e);
			}
		}
	}

	protected void scanForBeans(String packageName) {
		try (ScanResult result = new ClassGraph().enableClassInfo().enableAnnotationInfo()
				.enableMethodInfo().enableFieldInfo().ignoreFieldVisibility()
				.whitelistPackages(packageName).scan()) {
			for (Class<?> cls : BEAN_ANNOTATIONS) {
				ClassInfoList classInfos = result.getClassesWithAnnotation(cls.getName());
				for (ClassInfo ci : classInfos) {
					if (!ci.isStandardClass()) { // only standard classes are instantiated
						continue;
					}
					BeanDescriptor desc = new BeanDescriptor();
					desc.setClassInfo(ci);
					AnnotationInfoList annotations = ci.getAnnotationInfo();
					Class<?> beanClass = ci.loadClass();

					// get bean name
					AnnotationInfo ai = annotations.get(cls.getCanonicalName());
					Object beanNameObj = ai.getParameterValues().getValue("value");
					String beanName = null;
					if (beanNameObj == null || beanNameObj.toString().length() == 0) { // get the className as default
																						// bean name
						StringBuilder beanNameBuilder = new StringBuilder(beanClass.getSimpleName());
						beanNameBuilder.setCharAt(0, Character.toLowerCase(beanNameBuilder.charAt(0)));
						beanName = beanNameBuilder.toString();
					} else {
						beanName = beanNameObj.toString();
					}
					if (beanName == null) {
						throw new ContextInitializationException(
								"Error finding the bean name for bean class '" + beanClass.getName() + "'.");
					}
					desc.setName(beanName);

					// add the BeanDescriptor to the context
					beanByName.put(beanName, desc);
					Class<?>[] interfaces = beanClass.getInterfaces();
					for (Class<?> i : interfaces) {
						List<BeanDescriptor> bds = beansByType.get(i.getCanonicalName());
						if (bds == null) { // create BeanDescriptor list if not exists
							bds = new ArrayList<>();
							beansByType.put(i.getCanonicalName(), bds);
						}
						bds.add(desc);
					}

					// get bean scope and instantiate it if necessary
					ai = annotations.get(Scope.class.getCanonicalName());
					log.info("!!!! {}", ai);
					if (ai == null || ai.getParameterValues().getValue("value").toString().endsWith(SINGLETON.toString())) {
						desc.setScope(SINGLETON);
						try {
							desc.setInstance(
								Proxy.newProxyInstance(beanClass.getClassLoader(), interfaces,
									new DIProxy(this, beanClass.getDeclaredConstructor().newInstance(), desc)));
						} catch (InstantiationException | IllegalAccessException e) {
							new BeanInstantiationException(
									"Error instantiating singleton scoped bean '" + ci.getName() + "':", e);
						}
					} else {
						desc.setScope(PROTOTYPE);
					}

				}
			}
			log.info("beanByName: {}", beanByName);
			log.info("beansByType: {}", beansByType);
		} catch(Exception e) {
			log.error("Error scanning beans:", e);
		}
	}

	public static void main(String[] args) {
		Context ctx = new ApplicationContext(UserRepository.class.getPackage().getName());
		List<UserRepository> repos = ctx.getBeans(UserRepository.class);
		for (UserRepository r : repos) {
			System.out.println(r);
			r.addUser(new User("Ivan Petrov", "ivan@abv.bg"));
			r.addUser(new User("Dimitar Simeonov", "mitko@gmail.com"));
			System.out.println(r.findAll());
		}

		UserRepository repo = ctx.getBean(UserRepository.class); // service locator pattern
		System.out.println(repo);
		repo.addUser(new User("Ivan Petrov", "ivan@abv.bg"));
		repo.addUser(new User("Dimitar Simeonov", "mitko@gmail.com"));
		System.out.println(repo.findAll());
		
		UserController ctrl = ctx.getBean(UserController.class);
		System.out.println("\nUserController:");
		System.out.println(ctrl.findAllUsers());

//		assertThat(classInfos).extracting(ClassInfo::getName).contains(UserRepositoryImpl.class.getName());

	}

}
