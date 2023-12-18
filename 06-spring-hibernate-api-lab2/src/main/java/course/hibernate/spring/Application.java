package course.hibernate.spring;

import de.invesdwin.instrument.DynamicInstrumentationLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableLoadTimeWeaving;
import org.springframework.context.annotation.LoadTimeWeavingConfigurer;
import org.springframework.instrument.classloading.InstrumentationLoadTimeWeaver;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.instrument.classloading.ReflectiveLoadTimeWeaver;

//@EnableLoadTimeWeaving
@SpringBootApplication
public class Application { //implements LoadTimeWeavingConfigurer {

//    @Override
//    public LoadTimeWeaver getLoadTimeWeaver() {
//        return new ReflectiveLoadTimeWeaver();
//    }
//
//    @Bean
//    public InstrumentationLoadTimeWeaver loadTimeWeaver2() throws Throwable {
//        InstrumentationLoadTimeWeaver loadTimeWeaver = new InstrumentationLoadTimeWeaver();
//        return loadTimeWeaver;
//    }

    public static void main(String[] args) {
//        DynamicInstrumentationLoader.waitForInitialized(); //dynamically attach java agent to jvm if not already present
//        DynamicInstrumentationLoader.initLoadTimeWeavingContext(); //weave all classes before they are loaded as beans
        SpringApplication.run(Application.class, args);
    }
}
