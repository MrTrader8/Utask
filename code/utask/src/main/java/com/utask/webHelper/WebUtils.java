package com.utask.webHelper;

public class WebUtils {

  /**
   * Convert the size of the files
   * 
   * @param filesize size of the file
   * @return the string size
   */
  public static String convertSize(long filesize) {
    String units = " B";
    if (filesize < 1000) {
      return Long.toString(filesize) + units;
    }
    filesize /= 1000;
    units = " KB";
    if (filesize < 1000) {
      return Long.toString(filesize) + units;
    }
    filesize /= 1000;
    units = " MB";
    if (filesize < 1000) {
      return Long.toString(filesize) + units;
    }
    filesize /= 1000;
    units = " GB";
    return Long.toString(filesize) + units;
  }

}
