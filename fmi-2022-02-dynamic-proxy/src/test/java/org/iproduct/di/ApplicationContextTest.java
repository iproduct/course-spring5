package org.iproduct.di;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

import org.iproduct.di.annotations.Repository;
import org.iproduct.di.annotations.Schedule;
import org.iproduct.di.annotations.Schedules;
import org.junit.jupiter.api.Test;

import dynamicproxy.UserRepository;
import dynamicproxy.UserRepositoryImpl;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;

class ApplicationContextTest {

	@Test
	void testApplicationContext() {
		fail("Not yet implemented");
	}

	@Test
	void testApplicationContextString() {
		fail("Not yet implemented");
	}

	@Test
	void testClassesWithAnnotation() {
		try (ScanResult result = new ClassGraph().enableClassInfo().enableAnnotationInfo()
				.whitelistPackages(UserRepository.class.getPackage().getName()).scan()) {

			ClassInfoList classInfos = result.getClassesWithAnnotation(Repository.class.getName());

			assertThat(classInfos).extracting(ClassInfo::getName).contains(UserRepositoryImpl.class.getName());
		}
	}

	@Schedules({
		@Schedule(dayOfMonth="last"),
		@Schedule(dayOfWeek="Fri", hour=23)
	})
	public void doPeriodicCleanup() { 
		//... 
	}

}
