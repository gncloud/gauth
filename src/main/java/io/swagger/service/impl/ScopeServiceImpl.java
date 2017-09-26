package io.swagger.service.impl;

import io.swagger.dao.ScopeDao;
import io.swagger.model.Client;
import io.swagger.model.Scope;
import io.swagger.model.UserClientScope;
import io.swagger.service.ScopeService;
import io.swagger.service.UserClientScopeService;
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

    @Autowired
    private UserClientScopeService userClientScopeService;

    /*
     * scope 조회
     * client id, scope id reqired
     */
    @Override
    public Scope selectScope(Scope scope) throws Exception {
        String clientId = scope.getClientId();
        String scopeId = scope.getScopeId();
        if(isNull(clientId) || isNull(scopeId)){
            throw new Exception("invalid");
        }

        return scopeDao.findByScope(scope);
    }

    /*
     * 클라이언트의 scope 조회
     * client id reqired
     */
    @Override
    public List<Scope> selectClientScope(Client client) throws Exception {
        String clientId = client.getClientId();
        if(isNull(clientId)){
            throw new Exception("invalid");
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

        String clientId = scope.getClientId();
        String scopeId = scope.getScopeId();
        if(isNull(clientId) || isNull(scopeId)){
            throw new Exception("invalid");
        }

        String isDefault = scope.getIsDefault();
        if(isDefault != null){
            if("true".equalsIgnoreCase(isDefault)
                    || "y".equalsIgnoreCase(isDefault)
                    || "yes".equalsIgnoreCase(isDefault)
                    || "1".equalsIgnoreCase(isDefault)){
                scope.setIsDefault("1");
            }else{
                scope.setIsDefault("0");
            }
        } else {
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

        userClientScopeService.deleteClient(scope.getClientId());

        String clientId = scope.getClientId();
        String scopeId = scope.getScopeId();
        if(isNull(clientId) || isNull(scopeId)){
            throw new Exception("invalid");
        }

        UserClientScope userClientScope = new UserClientScope();
        userClientScope.setClientId(clientId);
        userClientScope.setScopeId(scopeId);
        userClientScopeService.deleteClientScope(userClientScope);

        scopeDao.deleteScope(scope);
    }

    /*
     * scope 수정
     * client id, scope id reqired
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Scope updateScope(Scope scope) throws Exception {

        String clientId = scope.getClientId();
        String scopeId = scope.getScopeId();
        if(isNull(clientId) || isNull(scopeId)){
            throw new Exception("invalid");
        }

        String isDefault = scope.getIsDefault();
        if(isDefault != null){
            if("true".equalsIgnoreCase(isDefault)
                    || "y".equalsIgnoreCase(isDefault)
                    || "yes".equalsIgnoreCase(isDefault)){
                scope.setIsDefault("1");
            }else{
                scope.setIsDefault("0");
            }
        } else {
            scope.setIsDefault("0");
        }

        scopeDao.updateScope(scope);

        return scopeDao.findByScope(scope);
    }

    private boolean isNull(String clientId){
        return (clientId == null || "".equals(clientId));
    }


    public List<Scope> findByDefailtScopes(String clientId){
        return scopeDao.findByDefailtScopes(clientId);
    }
}
