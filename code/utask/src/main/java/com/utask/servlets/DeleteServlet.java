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
import com.utask.databaseHelper.DatabaseHelper;
import com.utask.lucene.LuceneHelper;
import com.utask.lucene.invalidFieldException;
import com.utask.lucene.luceneDirectory;
import com.utask.lucene.updateException;

/**
 * Servlet implementation class DeleteSevlet
 */
@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public DeleteServlet() {
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
    String filepath = request.getParameter("filepath2");
    luceneDirectory dir = LuceneHelper.init.getDir();
    DatabaseHelper.DeleteFile(filepath);
    // ArrayList<Document> result = dir.queryParser("filepath", filepath, 100);
    // System.out.println("BEFORE: " + result.get(0).get("privilege"));
    dir.deleteTermDoc("filepath", filepath);
    // result = dir.queryParser("filepath", filepath, 100);
    // System.out.println("AFTER: " + result.get(0).get("privilege"));
    request.getRequestDispatcher("studentDrive.jsp").forward(request, response);
  }

}
