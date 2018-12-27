package com.utask.databaseHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.TimeZone;

public class DatabaseConnectionInit {
  public Connection conn;

  /**
   * Database initiation.
   */
  public DatabaseConnectionInit() {
    try {
      TimeZone timezone = TimeZone.getTimeZone("-5:00");
      TimeZone.setDefault(timezone);
      Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
      this.conn = DriverManager.getConnection("jdbc:mysql://35.163.136.56/utask"
          + "?user=root&password=2444666668888888" + "&serverTimezone=UTC&useSSL=false");
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InstantiationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * Get the connection to database.
   * @return
   */
  public Connection getConn() {
    return this.conn;
  }

}
