package com.utask.databaseHelper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import org.mindrot.jbcrypt.BCrypt;
import com.utask.lucene.luceneFile;
import com.utask.security.PasswordHelpers;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseHelper {

  static DatabaseConnectionInit connInit = new DatabaseConnectionInit();
  static Connection conn = connInit.getConn();

  /**
   * check if user exists.
   * 
   * @param utorid utorid of the user
   * @param username username of the user
   * @param email email of the user
   * @param uoftnumber uoftnumber of the user
   * @return true if the user exits and false otherwise
   */
  public static boolean userExists(String utorid, String username, String email, int uoftnumber) {
    try {
      String sql = "SELECT id from users where utorid=? or username=? or email=? or uoftnumber=?";
      PreparedStatement statement = conn.prepareStatement(sql);
      statement.setString(1, utorid);
      statement.setString(2, username);
      statement.setString(3, email);
      statement.setInt(4, uoftnumber);
      ResultSet rows = statement.executeQuery();
      return (rows.next());
    } catch (SQLException ex) {
      ex.printStackTrace();
      return true;
    }
  }

  /**
   * check if user exists.
   * 
   * @param login utorid or username or email or uoftnumber of the user
   * @return true if the user exits and false otherwise
   */
  public static boolean userExists(String login) {
    try {
      String sql = "SELECT id from users where utorid=? or username=? or email=? or uoftnumber=?";
      PreparedStatement statement = conn.prepareStatement(sql);
      statement.setString(1, login);
      statement.setString(2, login);
      statement.setString(3, login);
      int uoftNumIns;
      try {
        uoftNumIns = Integer.parseInt(login);
      } catch (NumberFormatException e) {
        uoftNumIns = 0;
      }
      statement.setInt(4, uoftNumIns);
      ResultSet rows = statement.executeQuery();
      return (rows.next());
    } catch (SQLException ex) {
      ex.printStackTrace();
      return true;
    }
  }

  /**
   * check if the password matches the one in database.
   * 
   * @param login login information of the user
   * @param password the password input to check match with the one in database
   * @return true if the password matches and false otherwise
   */
  public static boolean matchPassword(String login, String password) {
    try {
      String sql = "SELECT passwordHash from users where utorid=? or username=? or email=?";
      PreparedStatement statement = conn.prepareStatement(sql);
      statement.setString(1, login);
      statement.setString(2, login);
      statement.setString(3, login);
      ResultSet rows = statement.executeQuery();
      rows.next();
      String hash = rows.getString("passwordHash");
      System.out.println(hash + " " + hash.length());
      System.out.println(BCrypt.checkpw(password, hash));
      return BCrypt.checkpw(password, hash);
    } catch (SQLException ex) {
      ex.printStackTrace();
      return false;
    }
  }

  /**
   * Check if the input of the user matches with ones in database.
   * 
   * @param username username of the user
   * @param email email of the user
   * @param utorid utorid of the user
   * @param uoftnumber uoftnumber of the user
   * @return true if the input information matches and false otherwise
   */
  public static int allUserInfoMatches(String username, String email, String utorid,
      int uoftnumber) {
    int id = -1;
    try {
      String sql =
          "select id from users where utorid=? and username=? and email=? and uoftnumber=?";
      PreparedStatement statement = conn.prepareStatement(sql);
      statement.setString(1, utorid);
      statement.setString(2, username);
      statement.setString(3, email);
      statement.setInt(4, uoftnumber);
      ResultSet rows = statement.executeQuery();
      if (rows.next()) {
        id = rows.getInt(1);
      } else {
        return id;
      }
    } catch (SQLException ex) {
      ex.printStackTrace();
      return id;
    }
    return id;
  }

  /**
   * Update the password of a user.
   * 
   * @param userid user id of the user
   * @param password new password to update
   * @return true if the update is successful and false otherwise
   */
  public static boolean updatePassword(int userid, String password) {
    try {
      String passwordHash = BCrypt.hashpw(password, BCrypt.gensalt());
      String sql = "update users set passwordHash=? where id=?";
      PreparedStatement statement = conn.prepareStatement(sql);
      statement.setString(1, passwordHash);
      statement.setInt(2, userid);
      int row_num = statement.executeUpdate();
      return true;
    } catch (SQLException ex) {
      ex.printStackTrace();
      return false;
    }
  }

  /**
   * Insert new user into the database.
   * 
   * @param username username of the user
   * @param email email of the user
   * @param password password of the user
   * @param utorid utorid of the user
   * @param uoftnumber uoftnumber of the user
   * @param privilege privilege of the user (1 for teacher, and 2 for student)
   * @param namename if the user
   * @return userid of the user if succeesful
   */
  public static int insertUser(String username, String email, String password, String utorid,
      int uoftnumber, int privilege, String name) {
    try {
      String sql =
          "INSERT INTO users (username, email, passwordHash, utorid, uoftnumber, privilege, name)"
              + " values (?, ?, ?, ?, ?, ?, ?)";
      PreparedStatement statement = conn.prepareStatement(sql);
      if (userExists(utorid, username, email, uoftnumber)) {
        return -1;
      }
      statement.setString(1, username);
      statement.setString(2, email);
      statement.setString(3, BCrypt.hashpw(password, BCrypt.gensalt()));
      statement.setString(4, utorid);
      statement.setInt(5, uoftnumber);
      statement.setInt(6, privilege);
      statement.setString(7, name);
      int row = statement.executeUpdate();
      return row;
    } catch (SQLException ex) {
      ex.printStackTrace();
      return -1;
    }
  }

  /**
   * Get user data of login from database
   * 
   * @param login login information of the user
   * @return arraylist information related to the user
   */
  public static ArrayList<Object> getUserData(String login) {
    if (!userExists(login)) {
      return null;
    } else {
      try {
        String sql =
            "SELECT id, username, email, uoftnumber, privilege, name, utorid from users where utorid=? or username=? or email=? or uoftnumber=?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, login);
        statement.setString(2, login);
        statement.setString(3, login);
        int uoftNumIns;
        try {
          uoftNumIns = Integer.parseInt(login);
        } catch (NumberFormatException e) {
          uoftNumIns = 0;
        }
        statement.setInt(4, uoftNumIns);
        ResultSet rows = statement.executeQuery();
        rows.next();
        ArrayList<Object> data = new ArrayList<Object>();
        for (int i = 1; i <= 7; i++) {
          data.add(rows.getObject(i));
        }
        return data;
      } catch (SQLException ex) {
        ex.printStackTrace();
        return null;
      }
    }
  }

  /**
   * Insert document into the database.
   * 
   * @param fileName filename of the file to be inserted
   * @param filePath file path of the file to be inserted
   * @param userID userID of the user to insert the file
   * @param visibility visibility of the file(0 private and 1 is public)
   * @param fileSize size of the file
   * @return fileID if the file is successfully inserted, and SQL exception otherwise
   */
  public static int insertDocument(String fileName, String filePath, int userID, int visibility,
      long fileSize) {
    try {
      String sql =
          "INSERT INTO files (fileName, filePath, owner, visibility, size) values (?, ?, ?, ?, ?)";
      PreparedStatement statement = conn.prepareStatement(sql);
      statement.setString(1, fileName);
      statement.setString(2, filePath);
      statement.setInt(3, userID);
      statement.setInt(4, visibility);
      statement.setLong(5, fileSize);
      int row = statement.executeUpdate();
      return row;
    } catch (SQLException ex) {
      ex.printStackTrace();
      return -1;
    }
  }

  /**
   * Get all the documents from database
   * 
   * @return list of lucene files in the database
   */
  public static ArrayList<luceneFile> getAllDocuments() {
    try {
      String sql = "select id, filename, filepath, owner, visibility, size from files";
      PreparedStatement statement = conn.prepareStatement(sql);
      ResultSet rows = statement.executeQuery();
      ArrayList<luceneFile> documents = new ArrayList<luceneFile>();
      while (rows.next()) {
        luceneFile doc;
        /*
         * System.out.println("Items in row:"); for (int i=1; i<=5; i++) {
         * System.out.println(rows.getString(i)); }
         */
        // edit based on lucene constructor
        doc = new luceneFile(rows.getString(3), rows.getString(2), rows.getString(4),
            rows.getString(5), rows.getString(6));
        documents.add(doc);
      }
      return documents;
    } catch (SQLException ex) {
      ex.printStackTrace();
      return null;
    }
  }

  public static int indexDocument() {
    return 0;
  }

  /**
   * chege the file publicity in the database
   * 
   * @param filepath file path of the file
   * @param updated the updated visibility of the file
   */
  public static void changeFilePublicity(String filepath, String updated) {
    try {
      String sql = "update files set visibility=? where filepath=?";
      PreparedStatement statement = conn.prepareStatement(sql);
      statement.setString(1, updated);
      statement.setString(2, filepath);
      int rows = statement.executeUpdate();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }

  public static String getNameGivenId(int id) {
    try {
      String sql = "select name from users where id=?";
      PreparedStatement statement = conn.prepareStatement(sql);
      statement.setInt(1, id);
      ResultSet rows = statement.executeQuery();
      rows.next();
      String name = rows.getString(1);
      return name;
    } catch (SQLException ex) {
      ex.printStackTrace();
      return "-1";
    }
  }

  public static void DeleteFile(String filepath) {
    // TODO Auto-generated method stub
    try {
      String sql = "delete from file where filepath=?";
      PreparedStatement statement = conn.prepareStatement(sql);
      statement.setString(1, filepath);
      int rows = statement.executeUpdate();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }
  
}
