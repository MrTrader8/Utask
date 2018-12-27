package com.utask.databaseHelper;

import com.utask.users.User;

public class DatabaseSelector {

  /**
   * Get the hashed password for the user with the given User ID
   * @param id is the user's ID number
   * @return the hashed password
   */
  public static String getPassword(int id) {
    // TODO Auto-generated method stub
    return null;
  }
  
  
  /**
   * Given a user's unique ID, returns a User object
   * @param userId is a user's Unique ID
   * @return userInfo is a User Object with corresponding details
   */
  public static User getUser(int userId) {
    return null;
  }

  
  /**
   * Given a user's unique ID, return their type ID (1 for Instructor, 2 for student).
   * @param userId is a User's Unique ID
   * @return userRoleId is the user's role ID or -1 if undefined
   */
  public static int getUserType(int userId) {
    return -1;
  }
 
  // Document search methods are also needed
    // Need to be able to search for indexed documents
    // Need to be able to search for un-indexed documents

}
