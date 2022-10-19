package org.iproduct.di;

import org.iproduct.di.annotations.Scope;

import io.github.classgraph.ClassInfo;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class BeanDescriptor {
	@NonNull
	private String name;
	@NonNull
	private ClassInfo classInfo;
	@NonNull
	private BeanScope scope;
	private Object instance;
}
