package io.swagger.dao;

import io.swagger.model.PendingUserResponse;
import io.swagger.model.Token;
import io.swagger.model.User;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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
    public void deleteUser(String user){
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
    public List<User> findByUsers(Map<String, String> search) {
        return sqlSession.selectList("user.findByUsers", search);
    }

    /*
     * 회원 가입 대기 유저 정보 등록
     */
    public void insertPendingUser(PendingUserResponse pendingUserResponse) throws Exception{
        sqlSession.insert("user.insertPendingUser", pendingUserResponse);
    }

    /*
     * 이메일 기준 전체 삭제
     */
    public void deletePendingUser(String email){
        sqlSession.delete("user.deletePendingUser", email);
    }

    /*
     * 회원 가입 대기 유저 조회
     */
    public PendingUserResponse findByPendingUser(PendingUserResponse pendingUserResponse) {
        return sqlSession.selectOne("user.findByPendingUser", pendingUserResponse);
    }

    /*
     * 회원 가입 대기 유저 조회 email
     */
    public PendingUserResponse findByLastPending(String email) {
        return sqlSession.selectOne("user.findByLastPending", email);
    }

    /*
     * 이메일 상태 pending 일괄 변경
     */
    public void updatePendingStatus(PendingUserResponse pendingUserResponse){
        sqlSession.update("user.updatePendingStatus", pendingUserResponse);
    }

    /*
     * 이메일 존재 여부
     */
    public boolean isEmail(String email) {
        Integer result = sqlSession.selectOne("user.isEmail", email);
        return result != null && result != 0;
    }

    /*
     * 유저 수
     */
    public int selectUserCount(Map<String, String> search) {
        return sqlSession.selectOne("user.selectUserCount", search);
    }

    /*
     * 대기 유저 조회
     */
    public List<PendingUserResponse> findByPendingUserInfoList(Token adminToken){
        return sqlSession.selectList("user.findByPendingUserInfoList", adminToken);
    }

    /*
     * 대기 유저 전체 삭제
     */
    public void deleteAllPendUser(){
        sqlSession.delete("user.deleteAllPendUser");
    }

}