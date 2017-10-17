package io.swagger.service.impl;

import io.swagger.api.ApiException;
import io.swagger.dao.ScopeDao;
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
    public Scope findScope(Scope scope) throws Exception {
        String clientId = scope.getClientId();
        String scopeId = scope.getScopeId();
        if(isNull(clientId) || isNull(scopeId)){
            throw new ApiException("invalid data");
        }

        return scopeDao.findScope(scope);
    }

    /*
     * 클라이언트의 scope 조회
     * client id reqired
     */
    @Override
    public List<Scope> selectClientScope(String clientId) {
        Scope scope = new Scope();
        scope.setClientId(clientId);
        return scopeDao.findScopes(scope);
    }

    /*
     * scope 등록
     * client id reqired
     */
    @Override
    public Scope insertScope(Scope scope) throws Exception {

        String clientId = scope.getClientId();
        String scopeId = scope.getScopeId();
        if(isNull(clientId)){
            throw new ApiException("invalid clientId");
        }else if(isNull(scopeId)){
            throw new ApiException("invalid scopeId");
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
        scope.setIsDefault(null);
        return scopeDao.findScope(scope);
    }

    /*
     * scope 삭제
     * client id, scope id reqired
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteScope(Scope scope) throws Exception {

        String clientId = scope.getClientId();
        String scopeId = scope.getScopeId();
        if(isNull(clientId)){
            throw new ApiException("invalid clientId");
        }else if(isNull(scopeId)){
            throw new ApiException("invalid scopeId");
        }

        // 기존 스코프 삭제
        UserClientScope userClientScope = new UserClientScope();
        userClientScope.setClientId(scope.getClientId());
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
        if(isNull(clientId)){
            throw new ApiException("invalid clientId");
        }else if(isNull(scopeId)){
            throw new ApiException("invalid scopeId");
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

        return scopeDao.findScope(scope);
    }

    private boolean isNull(String clientId){
        return (clientId == null || "".equals(clientId));
    }


    public List<Scope> findDefaultByScopes(String clientId){
        return scopeDao.findDefaultByScopes(clientId);
    }
}
