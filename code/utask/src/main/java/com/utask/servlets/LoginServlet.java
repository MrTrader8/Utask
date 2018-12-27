package com.utask.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.utask.databaseHelper.DatabaseHelper;
import com.utask.security.PasswordHelpers;

// import com.utask.databaseHelper.DatabaseHelper;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public LoginServlet() {
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
    // read form fields
    String login = request.getParameter("login");
    String password = request.getParameter("password");
    PrintWriter responder = response.getWriter();
    // Do processing here
    if (DatabaseHelper.userExists(login) && DatabaseHelper.matchPassword(login, password)) {
      ArrayList<Object> user = DatabaseHelper.getUserData(login);
      // CHANGE LATER
      Cookie name = new Cookie("name", (String) user.get(5));
      Cookie userid = new Cookie("userid", Integer.toString((Integer) user.get(0)));
      Cookie email = new Cookie("email", (String) user.get(2));
      Cookie utorid = new Cookie("utorid", (String) user.get(6));
      Cookie studentnum = new Cookie("studentnum", Integer.toString((Integer) user.get(3)));
      Cookie privilege = new Cookie("privilege", Integer.toString(((Boolean) user.get(4)) ? 1 : 0));
      Cookie username = new Cookie("username", (String) user.get(1));
      name.setMaxAge(60 * 60 * 24);
      userid.setMaxAge(60 * 60 * 24);
      email.setMaxAge(60 * 60 * 24);
      utorid.setMaxAge(60 * 60 * 24);
      studentnum.setMaxAge(60 * 60 * 24);
      privilege.setMaxAge(60 * 60 * 24);
      response.addCookie(name);
      response.addCookie(userid);
      response.addCookie(email);
      response.addCookie(utorid);
      response.addCookie(studentnum);
      response.addCookie(privilege);
      response.addCookie(username);
      responder.println("<script type=\"text/javascript\">");
      responder
          .println("alert('Login successful! Taking you back to the homepage');");
      responder.println("window.location.href='index.jsp';");
      responder.println("</script>");
    } else {
      responder.println("<script type=\"text/javascript\">");
      responder
          .println("alert('Could not log you in with that information, please try again');");
      responder.println("window.location.href='auth.jsp';");
      responder.println("</script>");
    }
  }

}
