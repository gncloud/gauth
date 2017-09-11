package io.swagger.service;


import io.swagger.model.Client;
import io.swagger.model.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public interface ScopeService {

    /*
     * scope 전체 조회
     */
    List<Scope> selectScope(Client client);

    /*
     * scope 등록
     */
    Scope insertScope(Scope scope);

    /*
     * scope 삭제
     */
    void deleteScope(String scopeId);

    /*
     * scope 조회
     */
    Scope findByScope(String scopeId);

    /*
     * scope 수정
     */
    Scope updateScope(String scopeId, Scope scope);

}
