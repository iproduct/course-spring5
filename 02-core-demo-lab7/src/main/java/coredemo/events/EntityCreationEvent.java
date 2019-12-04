package coredemo.events;

import org.springframework.context.ApplicationEvent;

public class EntityCreationEvent extends ApplicationEvent {
        private String entityName;
        private Object entity;

    public EntityCreationEvent(Object source, String entityName, Object entity) {
        super(source);
        this.entityName = entityName;
        this.entity = entity;
    }

    public String getEntityName() {
        return entityName;
    }

    public Object getEntity() {
        return entity;
    }
}
