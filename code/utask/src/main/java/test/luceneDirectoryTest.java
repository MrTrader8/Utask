package com.utask.lucene;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.soap.SOAPPart;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.tika.exception.TikaException;
import org.junit.Test;
import org.xml.sax.SAXException;
import com.utask.lucene.*;
import com.utask.security.luceneDirectory;

public class luceneDirectoryTest {

  @Test
  public void testAddDoc() throws IOException {
    luceneDirectory lDir = new luceneDirectory();
    lDir.addDoc("/Users/Documents/file1.txt", "file1.txt", "2", "1");

  }

  @Test
  public void testAddDocWithDoc() throws IOException {
    luceneDirectory lDir = new luceneDirectory();
    lDir.addDoc("/Users/Documents/file1.txt", "file1.txt", "2", "1", new Document());

  }

  @Test
  public void testAddDocWithNoSection() throws IOException {
    luceneDirectory lDir = new luceneDirectory();
    lDir.addDoc("/Users/Documents/file1.txt", "file1.txt", "2");

  }

  @Test
  public void testAddDocWithNoSectionWithDoc() throws IOException {
    luceneDirectory lDir = new luceneDirectory();
    lDir.addDoc("/Users/Documents/file1.txt", "file1.txt", "2", new Document());

  }

  @Test
  public void testAddContentDoc() throws IOException {
    luceneDirectory lDir = new luceneDirectory();
    lDir.addcontentDoc("/Users/Documents/file1.txt", "file1.txt", "2", "1", "new contents");

  }

  @Test
  public void testQueryParser() throws ParseException, IOException {
    luceneDirectory lDir = null;
    lDir = mock(luceneDirectory.class);
    ArrayList<Document> results = mock(ArrayList.class);
    Document doc = new Document();
    when(results.get(0)).thenReturn(doc);

    when(lDir.queryParser("filename", "newname.txt", 1)).thenReturn(results);

    assertEquals(doc, lDir.queryParser("filename", "newname.txt", 1).get(0));

  }

  @Test
  public void testUpdateDoc()
      throws IOException, ParseException, updateException, invalidFieldException {
    luceneDirectory lDir = mock(luceneDirectory.class);
    ArrayList<Document> doclist = new ArrayList<>();
    Document doc = new Document();
    doclist.add(doc);
    lDir.updateDoc("/Users/Documents/file1.txt", "filename", "newName");
  }

  @Test
  public void testWildcardQuenryParser() throws ParseException, IOException {
    luceneDirectory lDir = null;
    lDir = mock(luceneDirectory.class);
    ArrayList<Document> results = mock(ArrayList.class);
    Document doc = new Document();
    when(results.get(0)).thenReturn(doc);

    when(lDir.wildcardqueryParser("filename", "newname.txt", 1)).thenReturn(results);

    assertEquals(doc, lDir.wildcardqueryParser("filename", "newname.txt", 1).get(0));
  }

  @Test
  public void testValidFieldValidFilePath() throws IOException {
    luceneDirectory lDir = new luceneDirectory();
    assertTrue(lDir.validField("filepath"));
  }

  @Test
  public void testValidFieldInvalidFilePath() throws IOException {
    luceneDirectory lDir = new luceneDirectory();
    assertFalse(lDir.validField("fiepath"));
  }

  @Test
  public void testValidFieldValidFileName() throws IOException {
    luceneDirectory lDir = new luceneDirectory();
    assertTrue(lDir.validField("filename"));
  }

  @Test
  public void testValidFieldInvalidFileName() throws IOException {
    luceneDirectory lDir = new luceneDirectory();
    assertFalse(lDir.validField("fiem"));
  }

  @Test
  public void testValidFieldValidUserid() throws IOException {
    luceneDirectory lDir = new luceneDirectory();
    assertTrue(lDir.validField("userid"));
  }

  @Test
  public void testValidFieldInvalidUserid() throws IOException {
    luceneDirectory lDir = new luceneDirectory();
    assertFalse(lDir.validField("fiem"));
  }

  @Test
  public void testValidFieldValidSectionid() throws IOException {
    luceneDirectory lDir = new luceneDirectory();
    assertTrue(lDir.validField("section_id"));
  }

  @Test
  public void testValidFieldInvalidSectionid() throws IOException {
    luceneDirectory lDir = new luceneDirectory();
    assertFalse(lDir.validField("section_i"));
  }

  @Test
  public void testGetExtension() throws IOException {
    luceneDirectory lDir = new luceneDirectory();
    assertEquals(".pdf", lDir.getExtension("is/f/qoo.pdf"));

  }

  @Test
  public void testGetExtension1() throws IOException {
    luceneDirectory lDir = new luceneDirectory();
    assertEquals(".txt", lDir.getExtension("is/f/qoo/.txt"));

  }

  @Test
  public void testGetExtension2() throws IOException {
    luceneDirectory lDir = new luceneDirectory();
    assertEquals(".html/", lDir.getExtension("is/f/qoo.html/"));

  }

  @Test
  public void testGetContentTxt() throws IOException {
    luceneDirectory lDir = new luceneDirectory();
    try {
      assertEquals("1111Z", lDir.getContent("/Users/yaolan/Documents/1.txt").trim());
    } catch (SAXException | TikaException e) {
      e.printStackTrace();
    }
  }

  // @Test
  // public void testGetContentDocx() throws IOException, SAXException, TikaException{
  // luceneDirectory lDir = new luceneDirectory();
  // assertEquals("testing", lDir.getContent("/Users/yaolan/2.docx").trim());
  //
  // }





}
