package com.liuzhuowen.bolg.service;

import com.liuzhuowen.bolg.dao.UserDao;
import com.liuzhuowen.bolg.model.User;
import org.mindrot.jbcrypt.BCrypt;

public class UserService {
    private final UserDao userDao = new UserDao();

    public User register(String username, String nickname, String password, String avatar, String git) {
        //1.1 使用BCrypt做加密
        String salt = BCrypt.gensalt();
        String hashpw = BCrypt.hashpw(password, salt);

        //2.将信息保存在数据库去
        return userDao.insert(username, nickname, hashpw, avatar, git);
    }

    public User login(String username, String password) {
        // 获取用户信息
        User user = userDao.selectOneByUsername(username);
        // 验证密码是否正确
        if (user == null) {
            return null;
        }
        if (BCrypt.checkpw(password, user.password)) {
            return user;
        }
        return null;
    }
}
