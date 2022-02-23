package edu.escuelaing.WebApp;

import java.io.*;
import java.net.*;

public class Ejercicio2 {
    public static void main(String[] args) throws Exception {

        //URL myUrl = new URL("http://www.google.com/");

        BufferedReader r = new BufferedReader( new InputStreamReader( System.in ));

        URL myUrl = new URL(r.readLine());

        BufferedWriter writer = new BufferedWriter(new FileWriter("resultado.html"));

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(myUrl.openStream()))) {

            String inputLine = null;

            while ((inputLine = reader.readLine()) != null) {

                System.out.println(inputLine);
                writer.append(inputLine);
            }            

        } catch (IOException x) {

            System.err.println(x);
        }

        writer.close();
    }
}