package io.swagger.service;

import io.swagger.api.ApiException;
import io.swagger.api.NotFoundException;
import io.swagger.model.Client;
import io.swagger.model.Token;
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
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public interface ClientService {

    static String ADMIN_CLIENT_ID = "gauth";
    static String SCOPE_ADMIN = "Admin";
    static String SCOPE_USER = "User";

    /*
     * 클라이언트 등록
     */
    Client insertClient(Client client) throws Exception;
    /*
     * 클라이언트 삭제
     */
    void deleteClient(String clientId) throws ApiException, Exception;
    /*
     * 클라이언트 조회
     */
    Client findByClient(String clientId) throws NotFoundException;
    /*
     * 클라이언트 수정
     */
    Client updateClient(Client client) throws NotFoundException;
    /*
     * 클라이언트 전체 조회
     */
    List<Client> selectClients(Token AdminToken);

}