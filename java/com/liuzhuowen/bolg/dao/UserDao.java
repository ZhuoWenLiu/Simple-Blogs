package com.liuzhuowen.bolg.dao;

import com.liuzhuowen.bolg.model.User;
import com.liuzhuowen.bolg.util.DBUtil;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import java.sql.*;

public class UserDao {

    public User insert(String username, String nickname, String password, String avatar, String git) {
    String sql = "INSERT INTO users(username, nickname, password, avatar, git) VALUES (?, ?, ?, ?, ?)";
    try(Connection c = DBUtil.connection()){
        try(PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1,username);
            ps.setString(2,nickname);
            ps.setString(3,password);
            ps.setString(4,avatar);
            ps.setString(5,git);
            System.out.println("执行：SQL：" + ps);
            ps.executeLargeUpdate();
            try(ResultSet rs = ps.getGeneratedKeys()){
                rs.next();
                int uid = rs.getInt(1);

                return new User(uid, username, nickname , password, avatar, git);
            }
        }
    }catch(SQLException exc){

        if (exc instanceof MySQLIntegrityConstraintViolationException){
            return null;
        }
            throw new RuntimeException(exc);
    }

    }

    public User selectOneByUsername(String username) {
        String sql = "SELECT uid, nickname, username, password, avatar, git FROM users WHERE username = ?";
        try(Connection c = DBUtil.connection()){
            try(PreparedStatement ps = c.prepareStatement(sql)){
                   ps.setString(1,username);

                   try(ResultSet rs = ps.executeQuery()){
                       if (!rs.next()){
                           return null;
                       }
                       return new User(
                         rs.getInt("uid"),
                         rs.getString("username"),
                         rs.getString("nickname"),
                         rs.getString("password"),
                               rs.getString("avatar"),
                               rs.getString("git")

                       );
                   }

            }
        }catch (SQLException exc){
            throw new RuntimeException(exc);
        }
    }
}
