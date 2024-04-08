package org.zerock.w1.todo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="todoRegisetController", urlPatterns = "/todo/register")
public class TodoRegisterController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("입력화면을 볼 수 있도록 구성");

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/todo/register.jsp");
        dispatcher.forward(req, resp);

        // 입력 할 수 있는 jsp로 forward
        // jsp에서는 다시 "/todo/register" post 요청을 보내면 DB에 등록
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("입력을 처리하고 목록 페이지로 이동");

        // DB에 req의 parameter를 꺼내서 저장
        System.out.println("DB에 req의 parameter를 꺼내어 저장");
        
        // 브라우저한테 이 주소로 다시 접속해
        resp.sendRedirect("/todo/register");
        
        // redirect:list
    }
}
