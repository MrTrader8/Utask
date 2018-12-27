package com.utask.security;

import static org.junit.Assert.*;
import org.junit.Test;

public class PasswordHelpersTest {

  @Test
  public static void comparePswdTest1(){
    assertTrue(PasswordHelpers.comparePassword(PasswordHelpers.getPasswordHash("utask010"), "utask010"));
  }
  
  @Test
  public static void comparePswdTest2(){
    assertTrue(PasswordHelpers.comparePassword(PasswordHelpers.getPasswordHash(""), ""));
  }
  
  @Test
  public static void comparePswdTest3(){
    assertTrue(PasswordHelpers.comparePassword(PasswordHelpers.getPasswordHash("1111"), "1111"));
  }
}
