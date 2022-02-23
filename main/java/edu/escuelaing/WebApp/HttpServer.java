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
			// System.out.println("Nueva petición recibida");


			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));


			String s;
			while ((s = in.readLine()) != null) {
				// System.out.println(s);

				if (s.contains("Referer")) {
					String path = s.split(" ")[1];
					URL url = new URL(path);

					if (url.getPath().equals("/consulta")) {
						String[] params = url.getQuery().split("&");
						String city = params[0].split("=")[1];
						getWeather(city);
					}			
				}
				if (s.isEmpty()) {
					break;
				}
			}

			out.write(	"HTTP/1.0 200 OK\r\n" +
						"Content-Type: text/html\r\n" +
						"\r\n" +
						"<TITLE>Clima</TITLE>" +
						"<P>Salida</P>"
			);


			// System.out.println("Petición terminada");
			out.close();
			in.close();
			clientSocket.close();
		}
		
	// serverSocket.close();
	}

	static private String getWeather(String city) throws Exception {

		final String KEY = "8f7be5f45471c12644241ab5a22f7d32";
		
		URL myUrl = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + KEY);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(myUrl.openStream()))) {

            String inputLine = null;

            while ((inputLine = reader.readLine()) != null) {

                System.out.println(inputLine);
            }            

        } catch (IOException x) {

            System.err.println(x);
        }
		return "";
	}

}