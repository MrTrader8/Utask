package com.utask.servlets;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import com.utask.lucene.LuceneHelper;
import com.utask.lucene.luceneDirectory;

/**
 * Servlet implementation class PublicServlet
 */
@WebServlet("/PublicServlet")
public class PublicServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public PublicServlet() {
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
    String filename = request.getParameter("searchText");
    filename = filename.toLowerCase();
    luceneDirectory dir = LuceneHelper.init.getDir();
    ArrayList<String> fields = new ArrayList<String>();
    ArrayList<String> terms = new ArrayList<String>();
    fields.add("privilege");
    terms.add("1");
    fields.add("content");
    terms.add(filename);
    ArrayList<Document> results = new ArrayList<Document>();
    try {
      results = dir.booleanQuery(fields, terms, 100);
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
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

    doGet(request, response);
  }

}
