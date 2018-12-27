package com.utask.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class IndexServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  /**
   * @see HttpServlet#HttpServlet()
   */
  public IndexServlet() {
    super();
    // TODO Auto-generated constructor stub
  }
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
  }

  /**
   * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    // read form fields
    String searchTerm = request.getParameter("searchTerm");
    System.out.println(searchTerm);
    String webButtonAction = request.getParameter("webButton");
    System.out.println(webButtonAction);
    String docButtonAction = request.getParameter("docButton");
    System.out.println(docButtonAction);
    PrintWriter writer = response.getWriter();
    // return response
    if(webButtonAction != null) {
      String webUrl = "https://onesearch.library.utoronto.ca/onesearch/";
      String htmlResponse = "<html>";
      webUrl += searchTerm + "//";
      try {
        Document doc = Jsoup.connect(webUrl).get();
        Elements divs = doc.select("a");
        for (Element div : divs) {
          System.out.println(div.text());
        }
        htmlResponse += "</html>";
        writer.println(htmlResponse);
      } catch (IOException e){
        e.printStackTrace();
      }
    }
    /*String htmlResponse = "<html>";
    
    htmlResponse += "<h2>Your username is: " + username + "<br/>";
    htmlResponse += "Your password is: " + password + "</h2>";
    htmlResponse += "</html>";*/
  }

}
