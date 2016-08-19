package library.services;

import library.domain.User;

/**
 * Created by zotova on 12.08.2016.
 */
public interface UserService {

    User findById(int id);
}
