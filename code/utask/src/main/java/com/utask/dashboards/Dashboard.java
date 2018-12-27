package com.utask.dashboards;

import java.io.File;
import java.util.ArrayList;

public interface Dashboard {
  
  /**
   * Authenticates a new user
   * @param UserId is the user to authenticate
   */
  public void authenticateUser(int UserId);
  
  
  /**
   * Logs out the current user if there is one
   */
  public void logoutUser();
  
  
  /**
   * Searches the indexed files for the given keywords;
   * @param keywords is the list of keywords
   */
  public ArrayList<File> searchIndexed(ArrayList<String> keywords);
}
