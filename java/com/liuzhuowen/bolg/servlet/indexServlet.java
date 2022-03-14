package com.liuzhuowen.bolg.servlet;

import com.liuzhuowen.bolg.dao.ArticleDao;
import com.liuzhuowen.bolg.model.Article;
import com.liuzhuowen.bolg.model.User;
import com.liuzhuowen.bolg.service.ArticleService;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("")
public class indexServlet extends HttpServlet {
    private final ArticleService articleService = new ArticleService();
    //1. 当前登录用户从哪里来（隐含必须登录后使用） 从session获取
    //2. 文章列表（DESC逆序）
    //3. 文章数
    //4. 文章类数

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        Map<String,Object> indexData = articleService.getIndexData(user);
        ServletContext servletContext = req.getServletContext();
        WebContext webContext = new WebContext(req,resp,servletContext);
        webContext.setVariable("user",user);
        webContext.setVariables(indexData);
        TemplateEngine engine = (TemplateEngine) servletContext.getAttribute("engine");
        String body = engine.process("index",webContext);
          //写入正文
        writer.println(body);
    }
}
