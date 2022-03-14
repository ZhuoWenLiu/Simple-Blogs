package com.liuzhuowen.bolg.service;

import com.liuzhuowen.bolg.dao.ArticleDao;
import com.liuzhuowen.bolg.model.Article;
import com.liuzhuowen.bolg.model.User;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticleService {
    private final ArticleDao articleDao = new ArticleDao();
    public Map<String,Object> getIndexData(User user){
        List<Article> articleList = articleDao.selectListByUid(user.uid);
        // 文章总数
        int articleCount = articleDao.selectArticleCountByUid(user.uid);
        //文章类型总数
        int typeCount = articleDao.selectTypeCountArticleByUid(user.uid);
        HashMap<String,Object> data = new HashMap<>();
        data.put("articleList",articleList);
        data.put("articleCount",articleCount);
        data.put("typeCount",typeCount);
        return data;
    }

    public Map<String, Object> getArticleData(User user, int aid) {

        Article article = articleDao.selectOneByAid(aid);
        if (article == null) return null;
        // 文章总数
        int articleCount = articleDao.selectArticleCountByUid(user.uid);
        //文章类型总数
        int typeCount = articleDao.selectTypeCountArticleByUid(user.uid);

        HashMap<String,Object> data = new HashMap<>();
        data.put("article",article);
        data.put("articleCount",articleCount);
        data.put("typeCount",typeCount);

        return data;
    }

    public Article publishedArticle(User user ,String title, String content) {
        Article article = new Article();
        article.title = title;
        article.type = "博客";
        article.content = content;
        article.uid = user.uid;
        article.publishedAt = LocalDate.now();

        articleDao.insert(article);

        return article;
    }
}
