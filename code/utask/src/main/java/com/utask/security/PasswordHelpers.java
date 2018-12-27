package com.utask.security;

import org.mindrot.jbcrypt.*;

public class PasswordHelpers {
  /**
   * Returns a hashed version of password to be stored in database.
   * @param password the unhashed password
   * @return the hashsed password
   */

  public static String getPasswordHash(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }
  
  /**
   * check if the database password matches user provided password.
   * @param hashed the password stored in the database.
   * @param plaintext the user provided password (unhashed).
   * @return true if passwords match, false otherwise.
   */
  public static boolean comparePassword(String hashed, String plaintext) {
    return BCrypt.checkpw(plaintext, hashed);
  }
}
