package edu.escuelaing.WebApp;

import java.net.*;

public class Ejercicio1 {

  public static void main(String[] args) throws Exception {
    URL myUrl = new URL("https://www.google.com:8080/search?q=Url#downloading");

    System.out.println(myUrl.getProtocol());
    System.out.println(myUrl.getAuthority());
    System.out.println(myUrl.getHost());
    System.out.println(myUrl.getPort());
    System.out.println(myUrl.getPath());
    System.out.println(myUrl.getQuery());
    System.out.println(myUrl.getFile());
    System.out.println(myUrl.getRef());
  }
}
