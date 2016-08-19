package library.repository;

import library.domain.User;

/**
 * Created by zotova on 17.08.2016.
 */
public interface UserRepository {

    User findById(int id);
}
