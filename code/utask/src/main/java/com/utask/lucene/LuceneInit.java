package com.utask.lucene;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;
import com.utask.databaseHelper.DatabaseHelper;

public class LuceneInit {
  public luceneDirectory dir;
  public ArrayList<luceneFile> existingDocs;

  /**
   * Constructor for lucene initiation
   */
  public LuceneInit() {
    try {
      dir = new luceneDirectory();
      existingDocs = DatabaseHelper.getAllDocuments();
      dir.addfromDatabase(existingDocs);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * Get the directory
   * 
   * @return the lucene directory
   */
  public luceneDirectory getDir() {
    return this.dir;
  }

  /**
   * Get the existing documents
   * 
   * @return a list of existing documents
   */
  public ArrayList<luceneFile> getExistingDocs() {
    return this.existingDocs;
  }
}
