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
     * scope 조회
     * client id, scope id reqired
     */
    @Override
    public Scope selectScope(Scope scope) throws Exception {
        if(!isClientId(scope.getClientId()) || !isScopeId(scope.getScopeId())){
            throw new Exception("client id invalid");
        }
        return scopeDao.findByScope(scope);
    }

    /*
     * 클라이언트의 scope 조회
     * client id reqired
     */
    @Override
    public List<Scope> selectClientScope(Client client) throws Exception {
        if(!isClientId(client.getClientId())){
            throw new Exception("client id invalid");
        }

        Scope scope = new Scope();
        scope.setClientId(client.getClientId());

        return scopeDao.selectClientScopeList(scope);
    }


    /*
     * scope 등록
     * client id reqired
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Scope insertScope(Scope scope) throws Exception {
        if(!isClientId(scope.getClientId())){
            throw new Exception("client id invalid");
        }

        if(scope.getIsDefault() != null && !"0".equals(scope.getIsDefault())){
            Client client = new Client();
            client.setClientId(scope.getClientId());
            scopeDao.updateDefaultN(client);
        }else{
            scope.setIsDefault("0");
        }
        scopeDao.insertScope(scope);
        return scopeDao.findByScope(scope);
    }

    /*
     * scope 삭제
     * client id, scope id reqired
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteScope(Scope scope) throws Exception {
        if(!isClientId(scope.getClientId()) || !isScopeId(scope.getScopeId())){
            throw new Exception("client id invalid");
        }

        scopeDao.deleteScope(scope);

    }

    /*
     * scope 수정
     * client id, scope id reqired
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Scope updateScope(Scope scope) throws Exception {
        if(!isClientId(scope.getClientId()) || !isScopeId(scope.getScopeId())){
            throw new Exception("client id invalid");
        }


        scopeDao.updateScope(scope);

        return scopeDao.findByScope(scope);
    }

    private boolean isClientId(String clientId){
        return !(clientId == null || "".equals(clientId));
    }

    private boolean isScopeId(String scopeId){
        return !(scopeId == null || "".equals(scopeId));
    }

    public Scope findByDefailtScope(String clientId){
        return scopeDao.findByDefailtScope(clientId);
    }
}
