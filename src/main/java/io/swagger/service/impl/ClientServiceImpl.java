package io.swagger.service.impl;

import io.swagger.api.ApiException;
import io.swagger.api.NotFoundException;
import io.swagger.dao.ClientDao;
import io.swagger.model.Client;
import io.swagger.model.Scope;
import io.swagger.model.Token;
import io.swagger.model.UserClientScope;
import io.swagger.service.ClientService;
import io.swagger.service.ScopeService;
import io.swagger.service.TokenService;
import io.swagger.service.UserClientScopeService;
import io.swagger.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 클라이언트 관리를 위한 인터페이스
 *
 * @author 김준우
 * @version 1.0
 * @see
 * @since 2017.09.08
 */
@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientDao clientDao;

    @Autowired
    private UserClientScopeService userClientScopeService;

    @Autowired
    private ScopeService scopeService;

    @Autowired
    private TokenService tokenService;

    /*
     * 클라이언트 등록
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Client insertClient(Client client) throws Exception {
        client.setClientSecret(RandomUtil.randomString(32));
        clientDao.insertClient(client);

        // User Scope
        Scope userScope = new Scope();
        userScope.setClientId(client.getClientId());
        userScope.setIsDefault("Y");
        userScope.setDescription("사용자");
        userScope.setScopeId(ClientService.SCOPE_USER);
        scopeService.insertScope(userScope);

        //Admin Scope
        Scope adminScope = new Scope();
        adminScope.setScopeId(ClientService.SCOPE_ADMIN);
        adminScope.setDescription("관리자");
        adminScope.setIsDefault("N");
        adminScope.setClientId(client.getClientId());
        scopeService.insertScope(adminScope);

        return findByClient(client.getClientId());
    }

    /*
     * 클라이언트 삭제
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteClient(String clientId) throws ApiException, Exception {

        Client registerClient = findByClient(clientId);

        if(registerClient == null){
            throw new ApiException("not found client");
        }

        Scope scope = new Scope();
        scope.setClientId(registerClient.getClientId());
        scopeService.deleteScope(scope);

        UserClientScope userClientScope = new UserClientScope();
        userClientScope.setClientId(registerClient.getClientId());
        userClientScopeService.deleteUserClientScope(userClientScope);

        tokenService.deleteClientIdByToken(clientId);

        clientDao.deleteClient(clientId);
    }

    /*
     * 클라이언트 조회
     */
    @Override
    public Client findByClient(String clientId) throws NotFoundException {
        Client registerClient = clientDao.findByClient(clientId);
        if(registerClient == null){
            throw new NotFoundException(404, "not found client");
        }
        return registerClient;
    }

    /*
     * 클라이언트 수정
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Client updateClient(Client client) throws NotFoundException {
        clientDao.updateClient(client);
        return findByClient(client.getClientId());
    }

    /*
     * 클라이언트 전체 조회
     */
    @Override
    public List<Client> selectClients(Token adminToken) {
        return clientDao.selectClients(adminToken);
    }




}