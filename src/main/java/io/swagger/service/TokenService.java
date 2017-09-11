package io.swagger.service;

import io.swagger.model.Token;
import io.swagger.model.User;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public interface TokenService {
    Token insertToken(User user);
    Token findByToken(String token);
    void deleteToekn(String token);
}
