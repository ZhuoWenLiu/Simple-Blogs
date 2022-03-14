package com.liuzhuowen.bolg.dao;

import com.liuzhuowen.bolg.model.Article;
import com.liuzhuowen.bolg.util.DBUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ArticleDao {
    public List<Article> selectListByUid(int uid) {
        List<Article> articleList = new ArrayList<>();
        String sql = "SELECT aid, title, type, content, published_at FROM articles WHERE uid = ? ORDER BY published_at DESC";
        try(Connection c = DBUtil.connection()){
            try(PreparedStatement ps = c.prepareStatement(sql)){
                ps.setInt(1,uid);
                try(ResultSet rs = ps.executeQuery()){
                    while(rs.next()){
                        int aid = rs.getInt("aid");
                        String title = rs.getString("title");
                        String type = rs.getString("type");
                        String content = rs.getString("content");
                        LocalDate publishedAt = rs.getDate("published_at").toLocalDate();
                        Article article = new Article(aid,uid,title,type,content,publishedAt);
                        articleList.add(article);
                    }
                }
            }
        }catch(SQLException exc){
            throw new RuntimeException(exc);
        }
        return articleList;
    }

    public int selectArticleCountByUid(int uid) {
        String sql = "SELECT COUNT(*) FROM articles WHERE uid = ? ";
        try(Connection c = DBUtil.connection()){
            try(PreparedStatement ps = c.prepareStatement(sql)){
                ps.setInt(1,uid);
                try(ResultSet rs = ps.executeQuery()){
                    rs.next();
                    return rs.getInt(1);
                }
            }
        }catch (SQLException exc){
            throw new RuntimeException(exc);
        }
    }

    public int selectTypeCountArticleByUid(int uid) {
        String sql = "SELECT COUNT(DISTINCT TYPE) FROM articles WHERE uid = ?";
        try(Connection c = DBUtil.connection()){
            try(PreparedStatement ps = c.prepareStatement(sql)){
                ps.setInt(1,uid);
                try(ResultSet rs = ps.executeQuery()){
                        rs.next();
                        return rs.getInt(1);
                }
            }
        }catch (SQLException exc){
            throw new RuntimeException(exc);
        }
    }


    public Article selectOneByAid(int aid) {
        String sql = "select aid, uid, title, type, content, published_at from articles where aid = ?";
        try(Connection c = DBUtil.connection()){
            try(PreparedStatement ps = c.prepareStatement(sql)){
                ps.setInt(1,aid);
                try(ResultSet rs = ps.executeQuery()){
                    if (!rs.next()){
                        return null;
                    }
                        int uid = rs.getInt("uid");
                        String title = rs.getString("title");
                        String type = rs.getString("type");
                        String content = rs.getString("content");
                        LocalDate publishedAt = rs.getDate("published_at").toLocalDate();
                        return new Article(aid,uid,title,type,content,publishedAt);
                }
            }
        }catch (SQLException exc){
            throw new RuntimeException(exc);
        }
    }

    public void insert(Article article) {
        String sql = "insert into articles (uid, title, type, content, published_at) values (?, ?, ?, ?, ?)";
        try(Connection c = DBUtil.connection()){
            try(PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
                ps.setInt(1,article.uid);
                ps.setString(2,article.title);
                ps.setString(3,article.type);
                ps.setString(4,article.content);
                ps.setDate(5,Date.valueOf(article.publishedAt));
                ps.executeUpdate();

                try(ResultSet rs = ps.getGeneratedKeys()){
                    rs.next();
                    article.aid = rs.getInt(1);
                }
            }
        }catch (SQLException exc){
            throw new RuntimeException(exc);
        }
    }
}
