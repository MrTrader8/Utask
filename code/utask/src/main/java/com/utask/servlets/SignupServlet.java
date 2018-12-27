package com.utask.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.mindrot.jbcrypt.BCrypt;
import com.utask.databaseHelper.DatabaseHelper;
import com.utask.security.PasswordHelpers;

/**
 * Servlet implementation class SignupServlet
 */
public class SignupServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public SignupServlet() {
    super();
    // TODO Auto-generated constructor stub
  }

  /**
   * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    response.getWriter().append("Served at: ").append(request.getContextPath());
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    String name = request.getParameter("name");
    int uoftnumber = Integer.parseInt(request.getParameter("uoftnumber"));
    String utorid = request.getParameter("utorid");
    String email = request.getParameter("email");
    String username = request.getParameter("username");
    String password = request.getParameter("signuppassword");
    boolean type = request.getParameter("userType").equals("student");
    PrintWriter responder = response.getWriter();
    response.setContentType("text/html");
    // perform sql
    if (DatabaseHelper.userExists(utorid, username, email, uoftnumber)) {
      responder.println("<script type=\"text/javascript\">");
      responder
          .println("alert('An account exists with that utorid, username, email, or uoftnumber');");
      responder.println("window.location.href='auth.jsp';");
      responder.println("</script>");
    } else {
      int privilege = 0;
      if (type)
        privilege = 1;
      DatabaseHelper.insertUser(username, email, password, utorid, uoftnumber, privilege, name);
      responder.println("<script type=\"text/javascript\">");
      responder.println("alert('Your account was created successfully!');");
      responder.println("window.location.href='index.jsp';");
      responder.println("</script>");
    }
  }
}
