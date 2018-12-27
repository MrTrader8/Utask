package com.utask.lucene;

public class luceneFile {

  public String filepath;
  public String filename;
  public String userid;
  public String privilege;
  public String size;

  /**
   * Constructor of the luceneFile.
   * 
   * @param filepath file path of the file
   * @param filename file name of the file
   * @param userid userid of the owner of the file
   * @param privilege privilege of the file(0 is private and 1 is public)
   * @param size size of the file
   */
  public luceneFile(String filepath, String filename, String userid, String privilege,
      String size) {
    this.filepath = filepath;
    this.filename = filename;
    this.userid = userid;
    this.privilege = privilege;
    this.size = size;
  }

  /**
   * Get the file path of the file.
   * 
   * @return file path of the file
   */
  public String getfilepath() {
    return this.filepath;
  }

  /**
   * Get the file name of the file.
   * 
   * @return the name of the file
   */
  public String getfilename() {
    return this.filename;
  }

  /**
   * Get userID of the owner of the file.
   * 
   * @return user ID if the file owner
   */
  public String getuserid() {
    return this.userid;
  }

  /**
   * Get the privilege of the file.
   * 
   * @return 0 if it's a private file and 1 if public
   */
  public String getprivilege() {
    return this.privilege;
  }

  /**
   * Get the size of the file.
   * 
   * @return the size of the file
   */
  public String getsize() {
    return this.size;
  }
}
