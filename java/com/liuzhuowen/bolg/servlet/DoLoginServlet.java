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

@WebServlet("/do-login")
public class DoLoginServlet extends HttpServlet {
    private final UserService userService = new UserService();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.读取用户提交的信息
        req.setCharacterEncoding("utf-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        //3. 至少区别出3种情况
        //注册成功 / 不存在 / 系统操作导致的失败
        // user ！= null   user == null   异常
        User user = userService.login(username, password);

        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        if (user != null) {
            HttpSession session = req.getSession();
            session.setAttribute("currentUser", user);
            writer.printf("<p>登录成功，点击<a href='/'>此处</a>跳转首页</p>\r\n");
            return;
        }
        //不提示是哪个错误
        writer.printf("<p>登录失败，点击<a href='/login.html'>此处</a>重新登录</p>\r\n");
    }
}
