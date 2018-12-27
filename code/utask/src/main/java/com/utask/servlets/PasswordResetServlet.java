package com.utask.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.utask.databaseHelper.DatabaseHelper;

/**
 * Servlet implementation class PasswordResetServlet
 */
@WebServlet("/PasswordResetServlet")
public class PasswordResetServlet extends HttpServlet{

  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public PasswordResetServlet() {
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
    String username = request.getParameter("username");
    String utorid = request.getParameter("utorid");
    String email = request.getParameter("email");
    int uoftnumber = Integer.parseInt(request.getParameter("uoftnumber"));
    PrintWriter responder = response.getWriter();
    int userid = DatabaseHelper.allUserInfoMatches(username, email, utorid, uoftnumber);
    if(userid == -1) {
      responder.println("<script type=\"text/javascript\">");
      responder
          .println("alert('Password could not be updated, information does not match any user');");
      responder.println("window.location.href='forgotPassword.jsp';");
      responder.println("</script>");
    } else {
      DatabaseHelper.updatePassword(userid, request.getParameter("desiredpass"));
      responder.println("<script type=\"text/javascript\">");
      responder
          .println("alert('Password updated, you may signin with your new password');");
      responder.println("window.location.href='auth.jsp';");
      responder.println("</script>");
    }
  }
}
