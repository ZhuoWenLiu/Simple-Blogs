package com.liuzhuowen.bolg.util;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBUtil {
    private static final DataSource dataSource;
    static{
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUrl("jdbc:mysql://127.0.0.1:3306/blog_01_08?characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai");
        mysqlDataSource.setUser("root");
        mysqlDataSource.setPassword("123456");
        dataSource = mysqlDataSource;
    }

    public static Connection connection() throws SQLException {
        return dataSource.getConnection();
    }
}
