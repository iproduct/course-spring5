package course.hibernate.spring.dao;

import course.hibernate.spring.entity.*;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
@Transactional
public class SubsystemRepository {
    @PersistenceContext
    private EntityManager em;

    public Subsystem createSubsystem(Subsystem subsystem) {
        em.unwrap(Session.class).persist(subsystem);
        return subsystem;
    }
    public SystemUser createUser(SystemUser user) {
        em.persist(user);
        return user;
    }
    public SystemUserEmbeddedId createUserEmbeddedId(SystemUserEmbeddedId user) {
        user.getId().getSubsystem().getSystemUsers().add(user);
        em.persist(user);
        return user;
    }
    public SystemUser findUserBySubsystemUsername(String subsystemName, String username){
        return em.find(SystemUser.class, new PK(subsystemName, username));
    }

    public SystemUserEmbeddedId findUserEmbeddedId(EmbeddedPK id){
        return em.find(SystemUserEmbeddedId.class, id);
    }

}
