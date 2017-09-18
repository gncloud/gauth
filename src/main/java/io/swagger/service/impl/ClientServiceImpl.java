package io.swagger.service.impl;

import io.swagger.api.ApiException;
import io.swagger.dao.ClientDao;
import io.swagger.model.Client;
import io.swagger.service.ClientService;
import io.swagger.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
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

    /*
     * 클라이언트 등록
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public Client insertClient(Client client) throws Exception {
        client.setClientSecret(RandomUtil.randomString(32));
        clientDao.insertClient(client);
        return findByClient(client.getClientId());
    }

    /*
     * 클라이언트 삭제
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void deleteClient(String clientId) {
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