package library.repository;


import library.domain.User;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;

/**
 * Created by zotova on 17.08.2016.
 */
@ImportResource("classpath:applicationContext.xml")
@Repository("userDao")
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public User findById(int id) {
        Collection<User> users = entityManager.createNamedQuery(User.FINDBYID)
                .setParameter("id", id).getResultList();
        return users.iterator().hasNext() ? users.iterator().next() : null;
    }
}
