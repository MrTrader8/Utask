package com.utask.lucene;

import java.io.IOException;
import java.util.ArrayList;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException, ParseException, updateException, invalidFieldException, SAXException, TikaException
    {
      luceneDirectory dir = new luceneDirectory();
      dir.addcontentDoc("1.pdf", "filename1","userid1","1","15", "These are the best sandwiches in town");
      dir.addcontentDoc("2.pdf", "filename2","userid2","1","15", "These are the best sandwiches in town");
      //dir.addcontentDoc("2.pdf", "filename2","userid2","0","15", luceneDirectory.getContent("mat-b41-homework-6.pdf"));
      //dir.addcontentDoc("3.pdf", "filename3","userid2","0","15", luceneDirectory.getContent("mat-b41-homework-6.pdf"));
      //dir.addcontentDoc("4.pdf", "filename4","userid1","1","15", luceneDirectory.getContent("mat-b41-homework-6.pdf"));
      //dir.addcontentDoc("5.pdf", "filename5","userid1","0","15", luceneDirectory.getContent("mat-b41-homework-6.pdf"));
      //dir.addcontentDoc("6.pdf", "filename6","userid2","1","15", luceneDirectory.getContent("mat-b41-homework-6.pdf"));
      
      dir.addDoc("x.txt", "filename10","", "15MB", "1");
      dir.addDoc("filepath11", "filename11","userid1","15", "1");
      dir.addDoc("filepath12", "filename12","userid1","15", "1");
      dir.addDoc("filepath13", "filename13","userid1","15", "1");
      dir.addDoc("filepath14", "filename14","userid1","15", "1");
      dir.addDoc("filepath2", "filename2","userid2","15", "1");
      dir.addDoc("filepath3", "filename3","userid3","15", "1");
      dir.addDoc("filepath4", "filename4","these are the things we want","15", "1");
      dir.addDoc("filepath5", "filename4","these the are","15","0");
      dir.deleteTermDoc("filepath", "filepath11");
      System.out.println(luceneDirectory.getContent("test.txt"));
     ArrayList<String> fields = new ArrayList<String>();
     ArrayList<String> terms = new ArrayList<String>();
     fields.add("privilege");
     terms.add("1");
     fields.add("content");
     terms.add("These");
     //dir.updateDoc("filepath2", "privilege", "0");
     //dir.updateDoc("filepath5", "privilege", "1");
     ArrayList<Document> hits = dir.booleanQuery(fields, terms, 10);
     //ArrayList<Document> hits = dir.searchContentInstructor("the paper is about the role");

     //ArrayList<Document> hits = dir.searchSharedFiles("userid1","path");
     //ArrayList<Document> hits = dir.wildcardqueryParser("userid", "*", 10);
     //ArrayList<Document> results = dir.commonIndex(hits, hits2);
     int i = 0;
     for(Document d : hits) {
       System.out.println("search 1: " + (i + 1) + ". " + d.get("filepath") + "\t" + d.get("filename")/* + luceneDirectory.generatePreview(d, "sameed")*/);
       //System.out.println(d.get("content"));
       //System.out.println(luceneDirectory.generatePreview(d, "These homework"));
     }
     /*
     ArrayList<Document> hits2 = dir.searchContent("these homework","userid1");

     i = 0;
     for(Document y : hits2) {
       System.out.println((i + 1) + ". " + y.get("filepath") + "\t" + y.get("filename"));
       i++;
     }
     /*
     dir.updateDoc("filepath6", "filename", "FILENAME6");
     dir.commit();
     hits = dir.queryParser("userid", "userid6", 10);
     i = 0;
     for(Document y : hits) {
       System.out.println((i + 1) + ". " + y.get("filepath") + "\t" + y.get("filename"));
       i++;
     }
     dir.deleteQueryDoc("filepath", "FILENAME6");
     dir.commit();
     hits = dir.queryParser("userid", "userid6", 10);
     i = 0;
     for(Document y : hits) {
       System.out.println((i + 1) + ". " + y.get("filepath") + "\t" + y.get("filename"));
       i++;
     }
  
     System.out.println(hits.size() + " hits");
     */
    }
}
