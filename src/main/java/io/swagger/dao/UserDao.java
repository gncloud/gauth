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
    public void insertUser(User insertUser) {
        sqlSession.insert("user.insertUser", insertUser);
    }

    /*
     * 아이디 중복 확인 (갯수 조회)
     * */
    public int isUserIdCount(String userId) {
        return sqlSession.selectOne("user.isUserIdCount", userId);
    }

    /*
     * 유저 조회
     * 유저아이디로 조회
     * */
    public User getUser(User user) {
        return sqlSession.selectOne("user.getUser", user);
    }

    /*
     * 유저 삭제
     * */
    public void deleteUser(int userCode){
        sqlSession.delete("user.deleteUser", userCode);
    }

    /*
     * 유저 수정
     * */
    public void updateUser(User user) {
        sqlSession.update("user.updateUser", user);
    }

    /*
     *  토큰으로 회원정보 조회
     * */
    public User findTokenByUser(String token) {
        return sqlSession.selectOne("user.findTokenByUser", token);
    }

    /*
     * 유저 전체 조회
     */
    public List<User> findUsers(Map<String, String> search) {
        return sqlSession.selectList("user.findUsers", search);
    }

    /*
     * 회원 가입 대기 유저 정보 등록
     */
    public void insertPendUser(PendingUserResponse pendingUserResponse) throws Exception{
        sqlSession.insert("user.insertPendUser", pendingUserResponse);
    }

    /*
     * 대기 회원 이메일 기준 삭제
     */
    public void deleteEmailByPendUser(String email){
        sqlSession.delete("user.deleteEmailByPendUser", email);
    }

    /*
     * 대기 회원 activateKey 기준 삭제
     */
    public void deletePendUserActivateKey(String activateKey){
        sqlSession.delete("user.deletePendUserByActivateKey", activateKey);
    }

    /*
     * 회원 가입 대기 유저 조회
     */
    public PendingUserResponse findPendUser(String activateKey) {
        return sqlSession.selectOne("user.findPendUser", activateKey);
    }

    /*
     * 회원 가입 대기 유저 조회 email
     */
    public PendingUserResponse findPendUserByEmail(String email) {
        return sqlSession.selectOne("user.findPendUserByEmail", email);
    }

    /*
     * 이메일 상태 pending 일괄 변경
     */
    public void updateStatusByPendUser(PendingUserResponse pendingUserResponse){
        sqlSession.update("user.updateStatusByPendUser", pendingUserResponse);
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
    public int findCountByUsers(Map<String, String> search) {
        return sqlSession.selectOne("user.findCountByUsers", search);
    }

    /*
     * 대기 유저 조회
     */
    public List<PendingUserResponse> selectPendUsers(Token adminToken){
        return sqlSession.selectList("user.selectPendUsers", adminToken);
    }

    /*
     * 대기 유저 전체 삭제
     */
    public void deleteAllPendUser(){
        sqlSession.delete("user.deleteAllPendUser");
    }

}