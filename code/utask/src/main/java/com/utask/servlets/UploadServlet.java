/**
 * 
 */
package com.utask.servlets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.commons.io.FilenameUtils;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;
import com.utask.databaseHelper.DatabaseHelper;
import com.utask.lucene.LuceneHelper;
import com.utask.lucene.luceneDirectory;

/**
 * Servlet implementation class FileUploadServlet
 */
@WebServlet("/UploadServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 *1024 * 1024 , // 1GB
    maxRequestSize = 1024 *1024 * 1024 * 5) // 5GB
public class UploadServlet extends HttpServlet {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  /**
   * Name of the directory where uploaded files will be saved, relative to the web application
   * directory.
   */
  private static final String SAVE_DIR = "uploadfiles/";

  /**
   * handles file upload
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    //load cookies
    Cookie[] cookies = null;
    String uid = null;
    int userID = -1;
    String username = null;
    cookies = request.getCookies();
    for (Cookie cookie : cookies) {
      if (cookie.getName().equals("userid")) {
        uid = cookie.getValue();
        userID = Integer.parseInt(uid);
      }
      if (cookie.getName().equals("username")) {
        username = cookie.getValue();
      }
    }
    // gets absolute path of the web application
    String appPath = request.getServletContext().getRealPath("");
    // constructs path of the directory to save uploaded file
    String savePath = appPath + File.separator + SAVE_DIR + username + File.separator;
    
    String fileName = request.getParameter("fileName");
    // creates the save directory if it does not exists
    File fileSaveDir = new File(savePath);
    if (!fileSaveDir.exists()) {
      fileSaveDir.mkdir();
    }
    // for (Part part : request.getParts()) {
    // String fileName = extractFileName(part);
    Part part = request.getPart("file");
    String originalName = getSubmittedFileName(part);
    fileName.trim();
    if(fileName.equals("") || fileName.equals(null) || fileName == null) {
      fileName = originalName;
    }
    String extension;
    if(!originalName.equals(null)) {
      extension = originalName.substring(originalName.lastIndexOf("."));
    } else {
      String contentType = part.getContentType();
      extension = contentType.substring(contentType.indexOf("/")+1);
    }
    // refines the fileName in case it is an absolute path
    // String fileName = part.getName();
    String fullpath = savePath + fileName;
    if(!fileName.equals(originalName)) {
      fullpath += extension;
      fileName += extension;
    }
    fileName = fileName.replace(" ", "_").toLowerCase();
    fullpath = fullpath.replace(" ", "_").toLowerCase();
    System.out.println("Verification prints:"+"\n"+"og name: " + originalName + "\n" + "new name: " + fileName + "\n" + "fullpath: " + fullpath + "\n" + "Extension: " + extension);
    part.write(fullpath);
    long fileSize = part.getSize();
    PrintWriter responder = response.getWriter();
    response.setContentType("text/html");
    int id = DatabaseHelper.insertDocument(fileName , fullpath, userID, 0, fileSize);
    if(id != -1) {
      luceneDirectory dir = LuceneHelper.init.getDir();
      if(extension.equals(".txt") || extension.equals(".doc") || extension.equals(".docx") || extension.equals(".html") || extension.equals(".pdf")) {
        System.out.println("DBHJBDJDBDJFDSJ CONTENT DOC AHFBSHJFBSJF");
        try {
          dir.addcontentDoc(fullpath, fileName, uid, "0", Long.toString(fileSize), luceneDirectory.getContent(fullpath));
        } catch (SAXException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } catch (TikaException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      } else {
        dir.addDoc(fullpath, fileName, uid, "0", Long.toString(fileSize));
      }
      responder.println("<script type=\"text/javascript\">");
      responder.println("alert('File uploaded successfully!');");
      responder.println("window.location.href='Upload.jsp';");
      responder.println("</script>");
    }
    else {
      responder.println("<script type=\"text/javascript\">");
      responder.println("alert('There was a problem uploading your file');");
      responder.println("window.location.href='Upload.jsp';");
      responder.println("</script>");
    }
  }
  
  private static String getSubmittedFileName(Part part) {
    for (String cd : part.getHeader("content-disposition").split(";")) {
      if(cd.trim().startsWith("filename")) {
        String filename = cd.substring(cd.indexOf("=") + 1).trim().replace("\"", "");
        return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); //MSIE FIX
      }
    }
    return null;
  }
}
