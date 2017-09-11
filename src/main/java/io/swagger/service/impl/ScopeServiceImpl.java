package io.swagger.service.impl;

import io.swagger.dao.ScopeDao;
import io.swagger.model.Client;
import io.swagger.model.Scope;
import io.swagger.service.ScopeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class ScopeServiceImpl implements ScopeService {

    private static Logger logger = LoggerFactory.getLogger(ScopeServiceImpl.class);

    @Autowired
    private ScopeDao scopeDao;

    /*
     * scope 전체 조회
     */
    @Override
    public List<Scope> selectScope(Client client) {
        return scopeDao.selectScope(client);
    }

    /*
     * scope 등록
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Scope insertScope(Scope scope) {



        return null;
    }

    /*
     * scope 삭제
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteScope(String scopeId) {

    }

    /*
     * scope 조회
     */
    @Override
    public Scope findByScope(String scopeId) {
        return null;
    }

    /*
     * scope 수정
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Scope updateScope(String scopeId, Scope scope) {
        return null;
    }
}
