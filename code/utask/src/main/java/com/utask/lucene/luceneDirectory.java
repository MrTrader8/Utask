package com.utask.lucene;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MultiPhraseQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.html.HtmlParser;
import org.apache.tika.parser.microsoft.ooxml.OOXMLParser;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.parser.txt.TXTParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

public class luceneDirectory {
  private IndexWriter w;
  private StandardAnalyzer analyzer = new StandardAnalyzer(CharArraySet.EMPTY_SET);
  private IndexWriterConfig config = new IndexWriterConfig(analyzer);
  private Directory dir = new RAMDirectory();

  /**
   * lucene directory constructor
   * 
   * @throws IOException IOE exception will be thrown if luceneDirctory not constructed successfully
   */
  public luceneDirectory() throws IOException {
    w = new IndexWriter(dir, config);
  }


  /**
   * add document to the directory.
   * 
   * @param filepath file path of the file
   * @param filename name of the file
   * @param userid id of the file owner
   * @param privilege 0 if it is a private file, and 1 is a public file
   * @param size size of the file
   * @throws IOException IO exception be thrown if adding document is not successful
   */

  public void addDoc(String filepath, String filename, String userid, String privilege, String size)
      throws IOException {
    Document doc = new Document();
    doc.add(new StringField("filepath", filepath, Field.Store.YES));

    doc.add(new TextField("filename", filename, Field.Store.YES));

    doc.add(new StringField("userid", userid, Field.Store.YES));

    doc.add(new StringField("size", size, Field.Store.YES));

    doc.add(new StringField("privilege", privilege, Field.Store.YES));

    this.w.addDocument(doc);
    this.w.commit();
  }

  /**
   * add document to the directory.
   * 
   * @param filepath file path of the file
   * @param filename name of the file
   * @param userid id of the file owner
   * @param privilege 0 if it is a private file, and 1 is a public file
   * @param size size of the file
   * @param doc document to add
   * @throws IOException IO exception be thrown if adding document is not successful
   */
  public void addDoc(String filepath, String filename, String userid, String privilege, String size,
      Document doc) throws IOException {

    doc.add(new StringField("filepath", filepath, Field.Store.YES));

    doc.add(new TextField("filename", filename, Field.Store.YES));

    doc.add(new StringField("userid", userid, Field.Store.YES));

    doc.add(new StringField("size", size, Field.Store.YES));

    doc.add(new StringField("privilege", privilege, Field.Store.YES));

    this.w.commit();
  }

  /**
   * add content of the document.
   * 
   * @param filepath path of the file
   * @param filename name of the file
   * @param userid id of the owner of the file
   * @param privilege 0 is private and 1 is public
   * @param size size of the file
   * @param content content of the file
   * @throws IOException IO exception be thrown if adding document is not successful
   */
  public void addcontentDoc(String filepath, String filename, String userid, String privilege,
      String size, String content) throws IOException {
    Document doc = new Document();
    doc.add(new StringField("filepath", filepath, Field.Store.YES));

    doc.add(new TextField("filename", filename, Field.Store.YES));

    doc.add(new StringField("userid", userid, Field.Store.YES));

    doc.add(new StringField("size", size, Field.Store.YES));

    doc.add(new StringField("privilege", privilege, Field.Store.YES));

    doc.add(new TextField("content", content, Field.Store.YES));

    this.w.addDocument(doc);
    this.w.commit();
  }

  /**
   * add content of the document.
   * 
   * @param filepath path of the file
   * @param filename name of the file
   * @param userid id of the owner of the file
   * @param privilege 0 is private and 1 is public
   * @param size size of the file
   * @param content content of the file
   * @param doc document to add
   * @throws IOException IO exception be thrown if adding document is not successful
   */
  public void addcontentDoc(String filepath, String filename, String userid, String privilege,
      String size, String content, Document doc) throws IOException {

    doc.add(new StringField("filepath", filepath, Field.Store.YES));

    doc.add(new TextField("filename", filename, Field.Store.YES));

    doc.add(new StringField("userid", userid, Field.Store.YES));

    doc.add(new StringField("size", size, Field.Store.YES));

    doc.add(new StringField("privilege", privilege, Field.Store.YES));

    doc.add(new TextField("content", content, Field.Store.YES));

    this.w.commit();
  }

  /**
   * Update the document
   * 
   * @param oldfilepath old file path of the doc
   * @param field field to be updated
   * @param updatedterm the new updated term
   * @throws IOException IO exception be thrown if adding document is not successful
   * @throws ParseException parser exception if the parser is not successful
   * @throws updateException update exception thrown if update is not successful
   * @throws invalidFieldException thrown if the field is invalid
   */
  public void updateDoc(String oldfilepath, String field, String updatedterm)
      throws IOException, ParseException, updateException, invalidFieldException {
    Document doc2 = new Document();
    ArrayList<Document> result = queryParser("filepath", oldfilepath, 1);
    if (result.size() != 1) {
      throw new updateException();
    } else if (!validField(field)) {
      throw new invalidFieldException();
    } else {
      String filepath = result.get(0).get("filepath");
      String filename = result.get(0).get("filename");
      String userid = result.get(0).get("userid");
      String privilege = result.get(0).get("privilege");
      String size = result.get(0).get("size");
      if (Objects.equals(field, "filename")) {
        filename = updatedterm;
      } else if (Objects.equals(field, "filepath")) {
        filepath = updatedterm;
      } else if (Objects.equals(field, "privilege")) {
        privilege = updatedterm;
      }

      if (result.get(0).get("content") != null) {
        addcontentDoc(filepath, filename, userid, privilege, size, result.get(0).get("content"),
            doc2);
      } else {
        addDoc(filepath, filename, userid, privilege, size, doc2);
      }


      this.w.updateDocument(new Term("filepath", oldfilepath), doc2);
      this.w.commit();
    }

  }

  /**
   * Delete the document.
   * 
   * @param field field of the file
   * @param term term to be deleted
   * @throws IOException thrown if there is IO exception
   */
  public void deleteTermDoc(String field, String term) throws IOException {
    w.deleteDocuments(new Term(field, term));
    w.commit();
  }

  /**
   * commit the changes.
   * 
   * @throws CorruptIndexException thrown if index is corrupted
   * @throws IOException thrown if there is IO exception
   */
  public void commit() throws CorruptIndexException, IOException {
    w.commit();
  }

  /**
   * Parse a query
   * 
   * @param field field of the query
   * @param term text in the term
   * @param hitsperpage
   * @return
   * @throws ParseException
   * @throws IOException
   */
  public ArrayList<Document> queryParser(String field, String term, int hitsperpage)
      throws ParseException, IOException {
    ArrayList<Document> results = new ArrayList<Document>();
    Query q = new TermQuery(new Term(field, term));
    w.commit();
    IndexReader reader = DirectoryReader.open(this.dir);
    IndexSearcher searcher = new IndexSearcher(reader);
    TopDocs docs = searcher.search(q, hitsperpage);
    ScoreDoc[] hits = docs.scoreDocs;
    for (int i = 0; i < hits.length; ++i) {
      int docId = hits[i].doc;
      Document d = searcher.doc(docId);
      results.add(d);
    }
    return results;
  }

  /**
   * Parse queries using wildcards.s
   * @param field field of queries
   * @param term term of the quries
   * @param hitsperpage number of returns
   * @return a list of documents related to the queries
   * @throws ParseException thrown when there is a parse exception
   * @throws IOException  thrown when there is an IO exception
   */
  public ArrayList<Document> wildcardqueryParser(String field, String term, int hitsperpage)
      throws ParseException, IOException {
    String newTerm = "*" + term + "*";
    ArrayList<Document> results = new ArrayList<Document>();
    Query q = new WildcardQuery(new Term(field, newTerm));
    w.commit();
    IndexReader reader = DirectoryReader.open(this.dir);
    IndexSearcher searcher = new IndexSearcher(reader);
    TopDocs docs = searcher.search(q, hitsperpage);
    ScoreDoc[] hits = docs.scoreDocs;
    for (int i = 0; i < hits.length; ++i) {
      int docId = hits[i].doc;
      Document d = searcher.doc(docId);
      results.add(d);
    }
    return results;
  }
  
  /**
   * Get a list of documents related to the string queries
   * @param field field of quenries
   * @param stringQuery a list of queries
   * @param hitsperpage the number of returns
   * @return a list of documents related to the queries
   * @throws ParseException thrown when there is a parse exception
   * @throws IOException thrown when there is an IO exception
   */
  public ArrayList<Document> stringQuery(String field, String stringQuery, int hitsperpage)
      throws ParseException, IOException {
    ArrayList<Document> results = new ArrayList<Document>();
    QueryParser parser = new QueryParser(field, analyzer);
    parser.setAllowLeadingWildcard(true);
    Query q = parser.parse(stringQuery);
    System.out.println("Type of query: " + q.getClass().getSimpleName());
    System.out.println("Query: " + q.toString());
    w.commit();
    IndexReader reader = DirectoryReader.open(this.dir);
    IndexSearcher searcher = new IndexSearcher(reader);
    TopDocs docs = searcher.search(q, hitsperpage);
    ScoreDoc[] hits = docs.scoreDocs;
    for (int i = 0; i < hits.length; ++i) {
      int docId = hits[i].doc;
      Document d = searcher.doc(docId);
      results.add(d);
    }
    return results;
  }

  /**
   * Get a list of documents of fields with different queries
   * 
   * @param fields a list of fields
   * @param stringQueries a list of queries
   * @param hitsperpage the number of results returned
   * @return the list of documents related to the queries
   * @throws ParseException thrown when there is a parse exception
   * @throws IOException thrown when there is an IO exception
   */
  public ArrayList<Document> booleanQuery(ArrayList<String> fields, ArrayList<String> stringQueries,
      int hitsperpage) throws ParseException, IOException {
    BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
    ArrayList<Document> results = new ArrayList<Document>();
    for (int i = 0; i < fields.size(); i++) {
      QueryParser parser = new QueryParser(fields.get(i), analyzer);
      parser.setAllowLeadingWildcard(true);
      parser.setEnablePositionIncrements(true);
      parser.setDefaultOperator(QueryParser.Operator.AND);
      String[] words = stringQueries.get(i).split(" ");
      if (words.length > 1) {
        MultiPhraseQuery.Builder multi = new MultiPhraseQuery.Builder();
        for (String word: words) {
          multi.add(new Term(fields.get(i), word));
        }
        MultiPhraseQuery q = multi.build();
        booleanQuery.add(q, Occur.MUST);
      } else {
        Query q = parser.parse(stringQueries.get(i));
        booleanQuery.add(q, Occur.MUST);
      }
    }
    BooleanQuery q = booleanQuery.build();
    System.out.println("Type of query: " + q.getClass().getSimpleName());
    System.out.println("Query: " + q.toString());
    w.commit();
    IndexReader reader = DirectoryReader.open(this.dir);
    IndexSearcher searcher = new IndexSearcher(reader);
    TopDocs docs = searcher.search(q, hitsperpage);
    ScoreDoc[] hits = docs.scoreDocs;
    for (int i = 0; i < hits.length; ++i) {
      int docId = hits[i].doc;
      Document d = searcher.doc(docId);
      results.add(d);
    }
    return results;
  }

  /**
   * Get the shared files by a user
   * 
   * @param userid id of the user
   * @return the list of documents of the user
   * @throws ParseException thrown when there is a parse exception
   * @throws IOException thrown when there is an IO exception
   */
  public ArrayList<Document> getSharedFiles(String userid) throws ParseException, IOException {
    BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
    ArrayList<Document> results = new ArrayList<Document>();
    QueryParser parser = new QueryParser("userid", analyzer);
    parser.setAllowLeadingWildcard(true);
    Query query = parser.parse(userid);
    booleanQuery.add(query, Occur.MUST_NOT);
    parser = new QueryParser("privilege", analyzer);
    query = parser.parse("+1");
    booleanQuery.add(query, Occur.MUST);
    BooleanQuery q = booleanQuery.build();
    System.out.println("Type of query: " + q.getClass().getSimpleName());
    System.out.println("Query: " + q.toString());
    w.commit();
    IndexReader reader = DirectoryReader.open(this.dir);
    IndexSearcher searcher = new IndexSearcher(reader);
    TopDocs docs = searcher.search(q, 100);
    ScoreDoc[] hits = docs.scoreDocs;
    for (int i = 0; i < hits.length; ++i) {
      int docId = hits[i].doc;
      Document d = searcher.doc(docId);
      results.add(d);
    }
    return results;
  }

  /**
   * Search for the shared files with the term
   * 
   * @param userid id of the user who owns the file
   * @param term the term to be searched for
   * @return list of the files related to the term owned by the user
   * @throws ParseException thrown when there is a parse exception
   * @throws IOException thrown when there is an IO exception
   */
  public ArrayList<Document> searchSharedFiles(String userid, String term)
      throws ParseException, IOException {
    BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
    ArrayList<Document> results = new ArrayList<Document>();

    QueryParser parser = new QueryParser("userid", analyzer);
    parser.setAllowLeadingWildcard(true);
    Query query = parser.parse(userid);
    booleanQuery.add(query, Occur.MUST_NOT);

    parser = new QueryParser("privilege", analyzer);
    query = parser.parse("+1");
    booleanQuery.add(query, Occur.MUST);

    parser = new QueryParser("filename", analyzer);
    parser.setAllowLeadingWildcard(true);
    query = parser.parse("*" + term + "*");
    booleanQuery.add(query, Occur.MUST);

    BooleanQuery q = booleanQuery.build();
    System.out.println("Type of query: " + q.getClass().getSimpleName());
    System.out.println("Query: " + q.toString());
    w.commit();
    IndexReader reader = DirectoryReader.open(this.dir);
    IndexSearcher searcher = new IndexSearcher(reader);
    TopDocs docs = searcher.search(q, 100);
    ScoreDoc[] hits = docs.scoreDocs;
    for (int i = 0; i < hits.length; ++i) {
      int docId = hits[i].doc;
      Document d = searcher.doc(docId);
      results.add(d);
    }
    return results;
  }

  public ArrayList<Document> searchContent(String term,String userid) throws ParseException, IOException {
    BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
    MultiPhraseQuery.Builder mquery = new MultiPhraseQuery.Builder();
    term = new String(term);
    String[] words = term.split(" ");
    if (words.length > 1 ) {
      for (String word : words) {
        mquery.add(new Term("content", word));
      }
    } else {
      mquery.add(new Term("content", term));
    }
    MultiPhraseQuery multiquery = mquery.build();
    booleanQuery.add(multiquery, Occur.MUST);
    
    QueryParser parser = new QueryParser("userid", analyzer);
    parser.setAllowLeadingWildcard(true);
    Query query = parser.parse(userid);
    booleanQuery.add(query, Occur.MUST);
    BooleanQuery q = booleanQuery.build();
    
    ArrayList<Document> results = new ArrayList<Document>();
    System.out.println("Type of query: " + q.getClass().getSimpleName());
    System.out.println("Query: " + q.toString());
    w.commit();
    IndexReader reader = DirectoryReader.open(this.dir);
    IndexSearcher searcher = new IndexSearcher(reader);
    TopDocs docs = searcher.search(q, 100);
    ScoreDoc[] hits = docs.scoreDocs;
    for (int i = 0; i < hits.length; ++i) {
      int docId = hits[i].doc;
      Document d = searcher.doc(docId);
      results.add(d);
    }
    System.out.println("First part: " + results.size());
    BooleanQuery.Builder booleanQuery2 = new BooleanQuery.Builder();
    parser = new QueryParser("userid", analyzer);
    parser.setAllowLeadingWildcard(true);
    query = parser.parse(userid);
    booleanQuery2.add(query, Occur.MUST_NOT);
    
    TermQuery privilege = new TermQuery(new Term("privilege","1"));
    booleanQuery2.add(privilege, Occur.MUST);
    booleanQuery2.add(multiquery, Occur.MUST);
    q = booleanQuery2.build();
    
    System.out.println("Type of query: " + q.getClass().getSimpleName());
    System.out.println("Query: " + q.toString());
    reader = DirectoryReader.open(this.dir);
    searcher = new IndexSearcher(reader);
    docs = searcher.search(q, 100);
    hits = docs.scoreDocs;
    for (int i = 0; i < hits.length; ++i) {
      int docId = hits[i].doc;
      Document d = searcher.doc(docId);
      results.add(d);
    }
    
    reader.close();
    return results;
  }

  /**
   * Search all the documents' content with instructor's permission
   * 
   * @param term term of the query
   * @return all the contents that the instructor can view
   * @throws ParseException thrown when there is a parsing exception
   * @throws IOException thrown if there is an IO exception
   */
  public ArrayList<Document> searchContentInstructor(String term)
      throws ParseException, IOException {
    BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
    MultiPhraseQuery.Builder mquery = new MultiPhraseQuery.Builder();
    ArrayList<Document> results = new ArrayList<Document>();
    term = new String(term);
    String[] words = term.split(" ");
    if (words.length > 1 ) {
      for (String word : words) {
        mquery.add(new Term("content", word));
      }
    } else {
      mquery.add(new Term("content", term));
    }
    MultiPhraseQuery multiquery = mquery.build();
    booleanQuery.add(multiquery, Occur.MUST);
    BooleanQuery q = booleanQuery.build();
    System.out.println("Type of query: " + q.getClass().getSimpleName());
    System.out.println("Query: " + q.toString());
    w.commit();
    IndexReader reader = DirectoryReader.open(this.dir);
    IndexSearcher searcher = new IndexSearcher(reader);
    TopDocs docs = searcher.search(q, 100);
    ScoreDoc[] hits = docs.scoreDocs;
    for (int i = 0; i < hits.length; ++i) {
      int docId = hits[i].doc;
      Document d = searcher.doc(docId);
      results.add(d);
    }
    return results;
  }

  /**
   * Searches for documents of the given query with instructor's permission
   * 
   * @param userid id of user
   * @param term term of the query
   * @return all the documents instructor can view
   * @throws ParseException thrown if there is a parse exception
   * @throws IOException thrown if there is an IO exception
   */
  public ArrayList<Document> searchInstructor(String userid, String term)
      throws ParseException, IOException {
    BooleanQuery.Builder booleanQuery = new BooleanQuery.Builder();
    ArrayList<Document> results = new ArrayList<Document>();

    QueryParser parser = new QueryParser("userid", analyzer);
    parser.setAllowLeadingWildcard(true);
    Query query = parser.parse(userid);
    booleanQuery.add(query, Occur.MUST_NOT);

    parser = new QueryParser("filename", analyzer);
    parser.setAllowLeadingWildcard(true);
    query = parser.parse("*" + term + "*");
    booleanQuery.add(query, Occur.MUST);

    BooleanQuery q = booleanQuery.build();
    System.out.println("Type of query: " + q.getClass().getSimpleName());
    System.out.println("Query: " + q.toString());
    w.commit();
    IndexReader reader = DirectoryReader.open(this.dir);
    IndexSearcher searcher = new IndexSearcher(reader);
    TopDocs docs = searcher.search(q, 100);
    ScoreDoc[] hits = docs.scoreDocs;
    for (int i = 0; i < hits.length; ++i) {
      int docId = hits[i].doc;
      Document d = searcher.doc(docId);
      results.add(d);
    }
    return results;
  }

  /**
   * check the field is valid
   * 
   * @param field field to check
   * @return true of the field is valid and false otherwise
   */
  public boolean validField(String field) {
    if (Objects.equals("filepath", field)) {
      return true;
    } else if (Objects.equals("filename", field)) {
      return true;
    } else if (Objects.equals("userid", field)) {
      return true;
    } else if (Objects.equals("privilege", field)) {
      return true;
    }
    return false;
  }

  /**
   * add documents from database.
   * 
   * @param fromdb list of lucene files
   */
  public void addfromDatabase(ArrayList<luceneFile> fromdb) {
    if (fromdb != null) {
      System.out.println("files from db");
      for (luceneFile file : fromdb) {
        System.out.println(file.getfilename() + "\n" + file.getfilepath() + "\n"
            + file.getprivilege() + "\n" + file.getuserid());
        try {
          System.out.println("got to add db else");
          String extension = luceneDirectory.getExtension(file.getfilepath());
          System.out.println("extension: " + extension);
          if (extension.equals(".txt") || extension.equals(".doc") || extension.equals(".docx")
              || extension.equals(".html") || extension.equals(".pdf")) {
            System.out.println("extension matched");
            addcontentDoc(file.getfilepath(), file.getfilename(), file.getuserid(),
                file.getprivilege(), file.getsize(),
                luceneDirectory.getContent(file.getfilepath()));
          } else {
            addDoc(file.getfilepath(), file.getfilename(), file.getuserid(), file.getprivilege(),
                file.getsize());
          }
        } catch (IOException | TikaException | SAXException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * Get the common index of two arrays of documents.
   * 
   * @param a a list of documents
   * @param b a list of documents
   * @return get the common indees of two lists of files
   */
  public ArrayList<Document> commonIndex(ArrayList<Document> a, ArrayList<Document> b) {
    ArrayList<Document> result = new ArrayList<Document>();
    for (Document index1 : a) {
      for (Document index2 : b) {
        if (docequals(index1, index2)) {
          result.add(index1);
        }
      }
    }
    return result;
  }

  /**
   * Check if the documents are equal.
   * 
   * @param a document a to be compared
   * @param b document b to be compared
   * @return true if the documents are equal and false otherwise
   */
  public boolean docequals(Document a, Document b) {
    if (new String(a.get("filename")).equals(new String(b.get("filename")))) {
      return false;
    } else if (new String(a.get("filepath")).equals(new String(b.get("filepath")))) {
      return false;
    } else if (new String(a.get("userid")).equals(new String(b.get("userid")))) {
      return false;
    }
    return true;
  }

  /**
   * Get the extension from file path.
   * 
   * @param filepath path of file
   * @return get the extension
   */
  public static String getExtension(String filepath) {
    return filepath.substring(filepath.lastIndexOf("."), filepath.length());
  }

  /**
   * Get the content of from file path.
   * 
   * @param filepath path of file
   * @return
   * @throws IOException thrown if there is an IO exception
   * @throws SAXException SAX exception thrown
   * @throws TikaException tika exception thrown
   */
  public static String getContent(String filepath) throws IOException, SAXException, TikaException {
    String extension = luceneDirectory.getExtension(filepath);
    System.out.println("GET CONTENT FILEPATH:" + filepath + " " + extension);
    BodyContentHandler handler = new BodyContentHandler();
    Metadata metadata = new Metadata();
    FileInputStream inputstream = new FileInputStream(new File(filepath));
    ParseContext pcontext = new ParseContext();
    if (new String(".txt").equals(extension)) {
      // Text document parser
      TXTParser TexTParser = new TXTParser();
      TexTParser.parse(inputstream, handler, metadata, pcontext);
      return handler.toString();
    } else if (new String(".pdf").equals(extension)) {
      PDFParser pdfparser = new PDFParser();
      pdfparser.parse(inputstream, handler, metadata, pcontext);
      return handler.toString();
    } else if (new String(".doc").equals(extension) || new String(".docx").equals(extension)) {
      OOXMLParser msofficeparser = new OOXMLParser();
      msofficeparser.parse(inputstream, handler, metadata, pcontext);
      return handler.toString();
    } else if (new String(".html").equals(extension) || new String(".jsp").equals(extension)) {
      HtmlParser htmlparser = new HtmlParser();
      htmlparser.parse(inputstream, handler, metadata, pcontext);
      return handler.toString();
    }
    inputstream.close();
    return null;
  }

  /**
   * Get the preview of the document
   * 
   * @param doc the document to have a preview for
   * @param query the input query
   * @return preview of the document
   */
  public static String generatePreview(Document doc, String query) {
    System.out.println("QUERY IS THIS: " + query);
    int words = query.split(" ").length;
    String[] split = doc.get("content").replace("\n", " ").replace("\r", "").toLowerCase()
        .split(query, 2);
    System.out.println("Content without new lines:" + split[0]);
    System.out.println("length of split in gen preview:" + split.length);
    String[] firsthalf = split[0].split(" ");
    String[] secondhalf = split[1].split(" ");
    String result = "";
    if (words < 32) {
      if (firsthalf.length == 0) {
        if (secondhalf.length == 0) {
          result = query;
        } else if (secondhalf.length < 32 - words) {
          result = query;
          for (String a : secondhalf) {
            result += " " + a;
          }
        } else {
          for (int i = 0; i <= 32 - words; i++) {
            result += " " + secondhalf[i];
          }
          result += " ...";
        }
      } else if (firsthalf.length <= 5) {
        int firstwords = firsthalf.length;
        for (String a : firsthalf) {
          result = a + " ";
        }
        result += query;
        if (secondhalf.length < 32 - firstwords - words) {
          for (String a : secondhalf) {
            result += " " + a;
          }
        } else {
          for (int i = 0; i <= 32 - words - firstwords; i++) {
            result += " " + secondhalf[i];
          }
          result += " ...";
        }
      } else {
        result = "... ";
        for (int i = 0; i <= 5; i++) {
          result = firsthalf[i] + " ";
        }
        result += query;
        if (secondhalf.length < 32 - 5 - words) {
          for (String a : secondhalf) {
            result += " " + a;
          }
        } else {
          for (int i = 0; i <= 32 - words - 5; i++) {
            result += " " + secondhalf[i];
          }
          result += " ...";
        }
      }
    } else {
      result = query;
    }
    return result;
  }

  /**
   * Get the path of the file icon
   * 
   * @param doc docuement related to the icon
   * @return the path of the file icon
   */
  public static String fileicon(Document doc) {
    return "./images/fileIcons/"
        + luceneDirectory.getExtension(doc.get("filepath")).replace(".", "") + ".png";
  }
  
  public HashMap<String,Integer> countExtensions(ArrayList<Document> result){
    HashMap<String,Integer> contentmap = new HashMap<String,Integer>();
    for (Document doc: result) {
      String filepath = doc.get("filepath");
      String extension = luceneDirectory.getExtension(filepath).replace(".","");
      putcontentHash(extension,contentmap);
    }
    return contentmap;
  }
  
  public void putcontentHash(String x,HashMap<String,Integer> contentmap) {
    if (contentmap.containsKey(x)) {
      int value = contentmap.get(x);
      value++;
      contentmap.put(x, value);
    } else {
      contentmap.put(x, 1);
    }
  }
}
