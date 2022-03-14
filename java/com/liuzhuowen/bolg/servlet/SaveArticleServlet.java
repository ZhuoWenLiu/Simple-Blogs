package com.liuzhuowen.bolg.servlet;



import com.liuzhuowen.bolg.model.Article;
import com.liuzhuowen.bolg.model.User;
import com.liuzhuowen.bolg.service.ArticleService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/save-article")
public class SaveArticleServlet extends HttpServlet {
    private final ArticleService articleService = new ArticleService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String title = req.getParameter("title");
        String content = req.getParameter("content");

        User user =null;  //获取当前用户
        HttpSession session = req.getSession(false);
        if (session != null){
            user = (User) session.getAttribute("currentUser");
        }

        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();

        if (user == null){
            writer.printf("<p>登录后才能使用，点击<a href='/login.html'>此处</a>进行登录</p>\r\n");
            return;
        }
        Article article = articleService.publishedArticle(user,title,content);

        //发表完了直接跳转详情页面
        resp.sendRedirect("/article.html?aid=" + article.aid);

    }
}
