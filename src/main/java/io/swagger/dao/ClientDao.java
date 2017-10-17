package io.swagger.dao;

import io.swagger.model.Client;
import io.swagger.model.Token;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClientDao {

    @Autowired
    private SqlSession sqlSession;

    /*
     * 클라이언트 등록
     */
    public void insertClient(Client client) {
        sqlSession.insert("client.insertClient", client);
    }
    /*
     * 클라이언트 삭제
     */
    public void deleteClient(String clientId) {
        sqlSession.delete("client.deleteClient", clientId);
    }
    /*
     * 클라이언트 조회
     */
    public Client findClient(String clientId) {
        return sqlSession.selectOne("client.findClient", clientId);
    }
    /*
     * 클라이언트 수정
     */
    public void updateClient(Client client) {
        sqlSession.update("client.updateClient", client);
    }
    /*
     * 클라이언트 전체 조회
     */
    public List<Client> selectClients(Token adminToken) {
        return sqlSession.selectList("client.selectClients", adminToken);
    }

    public Integer isDomain(String domain) {
        return sqlSession.selectOne("client.isDomain", domain);
    }
}
