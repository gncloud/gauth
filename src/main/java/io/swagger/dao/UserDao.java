package io.swagger.dao;

import io.swagger.model.User;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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
        sqlSession.insert("user.insertUser", user);
    }

    /*
     * 아이디 중복 확인 (갯수 조회)
     * */
    public int isUserId(String id) {
        return sqlSession.selectOne("user.isUserId", id);
    }

    /*
     * 유저 조회
     * 유저아이디로 조회
     * */
    public User findByUser(String userId) {
        return sqlSession.selectOne("user.findByUser", userId);
    }

    /*
     * 유저 삭제
     * */
    public void deleteUser(User user){
        sqlSession.delete("user.deleteUser", user);
    }

    /*
     * 유저 수정
     * */
    public void updateUser(User user) {
        sqlSession.update("user.updateUser",user);
    }

    /*
     *  토큰으로 회원정보 조회
     * */
    public User fienByTokenToUserInfo(String token) {
        return sqlSession.selectOne("user.fienByTokenToUserInfo", token);
    }

    /*
     * 유저 전체 조회
     */
    public List<User> findByUsers(String search) {
        return sqlSession.selectList("user.findByUsers", search);
    }
}