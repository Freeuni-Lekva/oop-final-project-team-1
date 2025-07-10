package servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
@WebServlet("/QuizDisplay")
public class QuizDisplayServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        RequestDispatcher rd;
        if (request.getParameter("isSinglePage").equals("true")) {
            rd = request.getRequestDispatcher("TakeQuizServlet");
        } else {
            rd = request.getRequestDispatcher("/FeedBackMethod.jsp");
        }
        rd.forward(request, response);

    }

}
