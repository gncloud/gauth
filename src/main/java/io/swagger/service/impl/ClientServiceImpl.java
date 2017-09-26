package io.swagger.service.impl;

import io.swagger.dao.ClientDao;
import io.swagger.model.Client;
import io.swagger.model.Scope;
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
        userScope.setScopeId("User");
        scopeService.insertScope(userScope);

        //Admin Scope
        Scope adminScope = new Scope();
        adminScope.setScopeId("Admin");
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
    public void deleteClient(String clientId) {
        List<Scope> scopes = scopeService.findByDefailtScopes(clientId);

        userClientScopeService.deleteClient(clientId);

        for (int i = 0; i < scopes.size(); i++) {
            Scope target = new Scope();
            target.setClientId(clientId);
            target.setScopeId(scopes.get(i).getScopeId());
            try {
                scopeService.deleteScope(target);
            } catch (Exception e) {

            }
        }

        tokenService.deleteClient(clientId);

        clientDao.deleteClient(clientId);
    }

    /*
     * 클라이언트 조회
     */
    @Override
    public Client findByClient(String clientId) {
        return clientDao.findByClient(clientId);
    }

    /*
     * 클라이언트 수정
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Client updateClient(Client client) {
        clientDao.updateClient(client);
        return findByClient(client.getClientId());
    }

    /*
     * 클라이언트 전체 조회
     */
    @Override
    public List<Client> selectClients() {
        return clientDao.selectClients();
    }




}