package io.swagger.dao;

import io.swagger.model.Client;
import io.swagger.model.User;
import io.swagger.model.UserClientScope;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 클라이언트 등록 관리 DAO
 *
 * @author 김준우
 * @version 1.0
 * @see
 * @since 2017.09.08
 */
@Repository
public class RelationshipDao {

    @Autowired
    private SqlSession sqlSession;

    /*
     * 클라이언트 등록
     * */
    public void insertRelationship(UserClientScope UserClientScope) {
        sqlSession.insert("clients.insertUser", UserClientScope);
    }


}