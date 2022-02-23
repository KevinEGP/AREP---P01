package edu.escuelaing.WebApp;

import java.net.*;
import java.io.*;

public class HttpServer {

	static final int PORT = 35000;
	public static void main(String[] args) throws Exception {

		ServerSocket serverSocket = null;
		try { 
				serverSocket = new ServerSocket(PORT);

		} catch (IOException e) {

				System.err.format("No se puede usar el puerto: %d.\n", PORT);
				System.exit(1);
		}

		while (true) {
			Socket clientSocket = serverSocket.accept();
			System.out.println("Nueva petición recibida");


			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));


			String s;
			while ((s = in.readLine()) != null) {
				System.out.println(s);

				if (s.contains("GET")){
					System.out.println("Petición GET");
					String path = s.split(" ")[1];
					path = path.replace("/", "");
					try {
						path = path.split("=")[1];
					} catch(Exception e) {
						// e.printStackTrace();
					}
					
					getClima(path.replace("/", ""));
				}
				if (s.isEmpty()) {
					break;
				}
			}

			out.write("HTTP/1.0 200 OK\r\n");
			out.write("Content-Type: text/html\r\n");
			// out.write("Date: Fri, 31 Dec 1999 23:59:59 GMT\r\n");
			// out.write("Server: Apache/0.8.4\r\n");
			// out.write("Content-Length: 59\r\n");
			// out.write("Expires: Sat, 01 Jan 2000 00:59:59 GMT\r\n");
			// out.write("Last-modified: Fri, 09 Aug 1996 14:21:40 GMT\r\n");
			out.write("\r\n");
			out.write("<TITLE>Clima</TITLE>");
			out.write("<P>Salida</P>");


			System.out.println("Petición terminada");
			out.close();
			in.close();
			clientSocket.close();
		}
		
	// serverSocket.close();
	}

	static private String getClima(String path) throws Exception {

		final String KEY = "8f7be5f45471c12644241ab5a22f7d32";
		
        URL myUrl;
		try {
			myUrl = new URL("https://api.openweathermap.org/data/2.5/weather?id=" + path + "&appid=");
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new Exception("Url Incorrecta");

		}

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(myUrl.openStream()))) {

            String inputLine = null;

            while ((inputLine = reader.readLine()) != null) {

                // System.out.println(inputLine);

            }            

        } catch (IOException x) {

            System.err.println(x);
        }

		return "";
	}

}