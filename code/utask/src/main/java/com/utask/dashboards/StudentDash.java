package com.utask.dashboards;

import java.io.File;
import java.util.ArrayList;

public class StudentDash implements Dashboard {

  public void authenticateUser(int UserId) {
    // TODO Auto-generated method stub
    
  }

  public void logoutUser() {
    // TODO Auto-generated method stub
    
  }

  public ArrayList<File> searchIndexed(ArrayList<String> keywords) {
    // TODO Auto-generated method stub
    return null;
  }
  
  
  public int storeFile(File file, String filename) {
    return 0;
  }
  
  
  // Stores a file in a specific folder
  public int storeFile(File file, String filename, File courseFolder) {
    return 0;
  }
  
  // Creates a new folder to store stuff in 
  public boolean makeNewFolder(String foldername) {
    return false;
  }
  
  // Deletes a folder 
  public boolean deleteFolder(String foldername) {
    return false;
  }
  

}
