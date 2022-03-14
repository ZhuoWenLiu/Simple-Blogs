package com.liuzhuowen.bolg.servlet;

import com.liuzhuowen.bolg.dao.UserDao;
import com.liuzhuowen.bolg.model.User;
import com.liuzhuowen.bolg.service.UserService;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/do-register")
public class DoRegisterServlet extends HttpServlet {
    private final UserService userService = new UserService();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //读取用户提交的信息
        req.setCharacterEncoding("utf-8");
        String username = req.getParameter("username");
        String nickname = req.getParameter("nickname");
        String password = req.getParameter("password");
        String avatar = req.getParameter("avatar");
        String git = req.getParameter("git");


        User user = userService.register(username,nickname,password,avatar,git);
           // 至少区别出3种情况
           //注册成功 / 用户名重复导致的失败 / 系统操作导致的失败
           // user ！= null   user == null   异常
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        if (user != null){
            HttpSession session = req.getSession();
            session.setAttribute("currentUser",user);
            writer.printf("<p>注册成功，点击<a href='/'>此处</a>跳转首页</p>\r\n");
        }else{
            writer.printf("<p>注册失败，点击<a href='/register.html'>此处</a>重新注册</p>\r\n");
        }

    }
}
