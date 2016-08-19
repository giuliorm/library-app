package library.services;

import library.config.JWTToken;

/**
 * Created by zotova on 03.08.2016.
 */
public interface TokenService {

    String getToken(String username, String password);
    String getKey();
    JWTToken parseToken(String token);
    String getCurrentToken();
}
