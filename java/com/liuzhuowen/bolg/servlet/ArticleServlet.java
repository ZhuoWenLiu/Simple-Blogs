package com.liuzhuowen.bolg.servlet;


import com.liuzhuowen.bolg.model.User;
import com.liuzhuowen.bolg.service.ArticleService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet("/article.html")
public class ArticleServlet extends HttpServlet {
    private final ArticleService articleService = new ArticleService();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 读取文章id
        req.setCharacterEncoding("utf-8");
        String aidStr = req.getParameter("aid");
        if (aidStr == null){
            resp.sendError(400,"请输入正确的文章 id");
            return;
        }

        int aid;
        try{
            aid = Integer.parseInt(aidStr);
        }catch (NumberFormatException exc){
            resp.sendError(400,"请输入正确的文章 id");
            return;
        }
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

        Map<String,Object> articleData  = articleService.getArticleData(user,aid);
        if (articleData == null) {
            resp.sendError(404,"对应的文章不存在");
            return;
        }

        ServletContext servletContext = req.getServletContext();
        WebContext webContext = new WebContext(req,resp,servletContext);
        webContext.setVariable("user",user);
        webContext.setVariables(articleData);
        TemplateEngine engine = (TemplateEngine) servletContext.getAttribute("engine");
        String body = engine.process("article",webContext);

        writer.println(body);
    }
}
