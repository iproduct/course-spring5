package course.spring.core.service.impl;

import course.spring.core.service.Consumer;
import course.spring.core.service.UserProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userConsumer")
@Slf4j
public class ConsoleUserPresenter implements Consumer {
    private UserProvider provider;


    @Autowired
    public ConsoleUserPresenter(UserProvider provider) {
        this.provider = provider;
    }


    @Override
    public void consume() {
        provider.getUsers().forEach(System.out::println);
    }
}
