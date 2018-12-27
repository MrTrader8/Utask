package com.utask.databaseHelper;

import static org.junit.Assert.*;
import java.sql.SQLException;
import java.util.ArrayList;
import org.junit.Test;
import com.utask.databaseHelper.DatabaseHelper;
import com.utask.servlets.luceneFile;
import junit.framework.TestCase;

public class DatabaseHelperTest {

  @Test
  public void insertUserTest1(){
    //DatabaseHelper.insertUser("BSmith1", "bob.smith@mail.utoronto.ca", "bobsmith", "bobsmith2", 1003894028, 1, "bob");
    assertEquals(1,DatabaseHelper.insertUser("BSmith1", "bob.smith@mail.utoronto.ca", "bobsmith", "bsmith2", 1003894028, 1, "bob"));
  }
  
  @Test
  public void testInsertUser() {
    assertEquals(2, DatabaseHelper.insertUser("John.smith", "js@mail.utoronto.ca", "something", "jsmith1", 1000399227, 1, "John smith"));
    
  }
  
  @Test
  public void testInsertUser3() {
    assertEquals(3, DatabaseHelper.insertUser("Martha.Stewart", "ms@mail.utoronto.ca", "osc9cbl1", "mStewart1", 1000399229, 2, "Martha Stewart"));
    
  }
  
  @Test
  public void testInsertUser2() {
    //assertEquals(1, DatabaseHelper.insertUser("John.smith", "js@mail.utoronto.ca", "something", "jsmith1", 1000399227, 1, "John smith"));

    assertEquals(4, DatabaseHelper.insertUser("Jane.Dev", "jd@mail.utoronto.ca", "shusikep11", "jdev1", 1000399228, 1, "Jane Dev"));
    
  }
  
  @Test
  public void testUserExsits0() {
    assertEquals(true, DatabaseHelper.userExists("bobsmith2", "BSmith1", "bob.smith@mail.utoronto.ca", 1003894028));
  }
  
  
  @Test
  public void testUserExsits1() {
    assertEquals(true, DatabaseHelper.userExists("jsmith1", "John.smith", "js@mail.utoronto.ca", 1000399227));
  }
  
  @Test
  public void testUserExsits2() {
    assertEquals(true, DatabaseHelper.userExists("shusikep11", "Jane.Dev", "jd@mail.utoronto.ca", 1000399228));

  }
  
  @Test
  public void testUserExsitsWithLogin() {
    assertEquals(true, DatabaseHelper.userExists("jd@mail.utoronto.ca"));

  }
  
  
  @Test
  public void testUserNotExsitsWithLogin() {
    assertEquals(false, DatabaseHelper.userExists("osc9c1"));

  }
  
  @Test
  public void testMatchesPasswordsWithUtorid(){
    assertTrue(DatabaseHelper.matchPassword("mStewart1", "osc9cbl1"));
  }
  
  @Test
  public void testMatchesPasswordsWithUtoridNotMatching(){
    assertFalse(DatabaseHelper.matchPassword("mStewart1", "osc9bl1"));
  }
  
  @Test
  public void testMatchesPasswordsWithEmail(){
    assertTrue(DatabaseHelper.matchPassword("js@mail.utoronto.ca", "something"));
  }
  
  @Test
  public void testMatchesPasswordsWithEmailNotMatching(){
    assertFalse(DatabaseHelper.matchPassword("js@mail.utoronto.ca", "somthing"));
  }
  
  @Test
  public void testMatchesPasswordsWithUsername(){
    assertTrue(DatabaseHelper.matchPassword("Jane.Dev", "shusikep11"));
  }
  
  @Test
  public void testMatchesPasswordsWithUsernameNotMatching(){
    assertFalse(DatabaseHelper.matchPassword("Jane.Dev", "shusi11"));
  }
  
  @Test
  public void testAllUserInfoMatches(){
    assertEquals(4, DatabaseHelper.allUserInfoMatches("Jane.Dev", "jd@mail.utoronto.ca", "jdev1", 1000399228));
  }
  
  @Test (expected = SQLException.class)
  public void testAllUserInfoNotMatches(){
    DatabaseHelper.allUserInfoMatches("Jan.Dev", "jd@mail.utoronto.ca", "jdev1", 100399228);
  }
  
  @Test
  public void testUpdatePasswrod(){
    DatabaseHelper.updatePassword(4, "newpasswordforJane");
    assertTrue(DatabaseHelper.matchPassword("Jan.Dev", "newpasswordforJane"));
  }
  
  @Test
  public void testGetUserDataWithUsername(){
    
    assertNotNull(DatabaseHelper.getUserData("Jan.Dev"));
  }
  
  @Test
  public void testGetUserDataWithUtorid(){
    
    assertNotNull(DatabaseHelper.getUserData("jdev1"));
  }
  
  @Test
  public void testGetUserDataWithEmail(){
    
    assertNotNull(DatabaseHelper.getUserData("jd@mail.utoronto.ca"));
  }
  
  @Test
  public void testGetUserDataNotAUser(){
    
    assertNull(DatabaseHelper.getUserData("not in database"));
  }
  
  @Test
  public void testInsertDocuments(){
    
    assertEquals(1, DatabaseHelper.insertDocument("file1.txt", "/Users/Bsmith1/cscc01_space", 1, 1, ".txt",(long) 28.3));
  }
  
  @Test
  public void testInsertDocuments1(){
    
    assertEquals(1, DatabaseHelper.insertDocument("file2.pdf", "/Users/jsmith1/cscc01_space", 2, 1, ".pdf",(long) 350.3));
  }
  
  @Test
  public void testGetAlldocuments(){
    ArrayList<luceneFile> documents = new ArrayList<luceneFile>();
    documents = DatabaseHelper.getAllDocuments();
    int times = 0;
    for(luceneFile file: documents){
      
       if ((file.getfilename()).equals("file1.txt")){
         times ++;
       } else if ((file.getfilename()).equals("file2.pdf")){
         times ++;
       } 
       
    }
    assertEquals(2, times);
  }
  
  

}
