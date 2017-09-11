package io.swagger.dao;

import io.swagger.model.User;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 데이터베이스에 유저 정보를 관리하는 클래스
 *
 * @author 김준우
 * @version 1.0
 * @see
 * @since 2017.09.08
 */
@Repository
public class UserDao {

    @Autowired
    private SqlSession sqlSession;

    /*
     * 유저 등록
     * */
    public void insertUser(User user) {
        sqlSession.insert("users.insertUser", user);
    }

    /*
     * 아이디 중복 확인
     * */
    public int isUserId(String id) {
        return sqlSession.selectOne("users.isUserId", id);
    }

    /*
     * 유저 조회
     * 유저아이디로 조회
     * */
    public User findByUser(String userId) {
        return sqlSession.selectOne("users.findByUser", userId);
    }

    /*
     * 유저 삭제
     * */
    public void deleteUser(String userId){
        sqlSession.delete("users.deleteUser", userId);
    }

    /*
     * 유저 수정
     * */
    public void updateUser(User user) {
        sqlSession.update("users.updateUser",user);
    }
}