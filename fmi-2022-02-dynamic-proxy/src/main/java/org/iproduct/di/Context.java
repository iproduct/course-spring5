package org.iproduct.di;

import java.util.List;

public interface Context {
	<T> T getBean(String name, Class<T> cls);
	<T> T getBean(Class<T> cls);
	<T> List<T> getBeans(Class<T> cls);
}
