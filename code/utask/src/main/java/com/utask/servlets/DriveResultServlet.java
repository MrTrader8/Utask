package com.utask.servlets;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import com.utask.lucene.LuceneHelper;
import com.utask.lucene.luceneDirectory;

/**
 * Servlet implementation class DriveResultServlet
 */
@WebServlet("/DriveResultServlet")
public class DriveResultServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public DriveResultServlet() {
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
    String filename = request.getParameter("searchtext");
    filename = filename.toLowerCase();
    System.out.println(filename);
    String userid = null;
    String privilege = null;
    Cookie cookiequery = null;;
    Cookie[] cks = request.getCookies();
    if (cks != null) {
      for (int i = 0; i < cks.length; i++) {
        String name = cks[i].getName();
        String value = cks[i].getValue();
        if (name.equals("userid")) {
          userid = value;
          System.out.println("LuceneServlet: userid" + value);
        }
        if (name.equals("privilege")) {
          privilege = value;
          System.out.println("LuceneServlet: privilege" + value);
        }
        i++;
      }
    }
    luceneDirectory dir = LuceneHelper.init.getDir();
    try {
      ArrayList<Document> results = new ArrayList<Document>();
      if (userid != null) {
        System.out.println("ran this");
        if (privilege.equals(new String("1"))) {
          results = dir.searchContent(filename, userid);
          System.out.println("SIZE: " + results.size());
        } else if (privilege.equals(new String("0"))) {
          results = dir.searchContentInstructor(filename);
        }
      } else {
        System.out.println("ran thot");
        ArrayList<String> fields = new ArrayList<String>();
        ArrayList<String> terms = new ArrayList<String>();
        fields.add("privilege");
        terms.add("1");
        fields.add("content");
        terms.add(filename);
        results = dir.booleanQuery(fields, terms, 100);
      }
      if (results.size() > 0) {
        request.setAttribute("SearchText", filename);
      } else {
        request.setAttribute("SearchText", null);
      }
      // System.out.println(results.get(0).get("content"));
      // System.out.println("Servlet size:" + results.size());
      request.setAttribute("LuceneServlet", results);
      // response.sendRedirect("studentDrive.jsp");
      request.getRequestDispatcher("driveResults.jsp").forward(request, response);
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    doGet(request, response);
  }

}
