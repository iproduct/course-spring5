package coredemo.events;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class EntityCreationEventListener implements ApplicationListener<EntityCreationEvent> {
//    final static Logger log = LoggerFactory.getLogger(EntityCreationEventListener.class);

    @Override
    public void onApplicationEvent(EntityCreationEvent event) {
        log.info("!!!!!!! Entity created [{}]: {}", event.getEntityName(), event.getEntity().toString());
    }
}
