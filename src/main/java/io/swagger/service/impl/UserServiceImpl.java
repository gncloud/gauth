package io.swagger.service.impl;

import io.swagger.dao.UserDao;
import io.swagger.model.User;
import io.swagger.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 유저 관리를 위한 service 인터페이스
 *
 * @author 김준우
 * @version 1.0
 * @see
 * @since 2017.09.08
 */
@Service
public class UserServiceImpl implements UserService {

    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    /*
     * 유저 조회
     * 유저아이디로 조회
     * */
    @Override
    public User findByUser(String userId) {
        return userDao.findByUser(userId);
    }

    /*
     * 아이디 중복 확인
     * 아이디로 조회
     * */
    @Override
    public Integer isUserId(String userId) {
        return userDao.isUserId(userId);
    }

    /*
     * 회원가입
     * */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public User signup(User user) throws Exception {

        //필수 값 체크
        if (!requiredValueCheck(user)) {
            throw new Exception("user requred invalid");
        }

        //DB 중복 아이디 확인
        Integer isUserCount = isUserId(user.getUserId());
        if (isUserCount != 0) {
            throw new Exception("userId Duplicate ID");
        }

        // 회원 정보 등록
        userDao.insertUser(user);

        // TODO UserClientScope 등록


        // 등록된 DB 유저 가져오기
        return findByUser(user.getUserId());
    }

    /*
     * 회원탈퇴
     * */
    @Override
    public void deleteUser(String userId) {
        userDao.deleteUser(userId);
    }

    /*
     * 유저 수정
     * */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public User updateUser(User user) {
        userDao.updateUser(user);
        return userDao.findByUser(user.getUserId());
    }

    /*
     * 토근 아이디와 회원아이디 같은지 확인
     * */
    @Override
    public boolean isUserMatchToken(String token, String clientId) {

        return false;
    }

    /*
     * 필수값 유효성 체크
     */
    private boolean requiredValueCheck(User user) {
        boolean isValid = false;
        try {
            String userId = user.getUserId();
            if (userId == null || "".equals(userId)) {
                throw new Exception("no id");
            }

            String password = user.getPassword();
            if (password == null || "".equals(password)) {
                throw new Exception("no password");
            }

            String email = user.getEmail();
            if (email == null || "".equals(email)) {
                throw new Exception("no email");
            }

            String name = user.getName();
            if (name == null || "".equals(name)) {
                throw new Exception("no name");
            }

            isValid = true;
        } catch (Exception e) {
            logger.debug("requiredValue ::: {}", e.getMessage());
        }
        return isValid;
    }
}