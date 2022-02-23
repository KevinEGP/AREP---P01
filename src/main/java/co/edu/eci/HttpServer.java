package co.edu.eci;

import java.net.*;
import java.io.*;
import org.json.*;

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

			String weather = "";
			String s;
			while ((s = in.readLine()) != null) {
				System.out.println(s);

				if (s.isEmpty()) {
					break;
				}

				if (s.contains("GET") && s.contains("HTTP")) {
					String query = s.split(" ")[1];
					String path = "";
					String params = "";
					try {
						path = query.split("\\?")[0];
						params = query.split("\\?")[1];
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					if (path.equals("/consulta")) {
						String[] paramsArray;
						String city = "";
						try {
							paramsArray = params.split("&");
							city = paramsArray[0].split("=")[1];							
							weather = getWeather(city);
							break;
						} catch (Exception e) {
							e.printStackTrace();
						}
						System.out.println(weather);
					}
				}
				
			}


			// System.out.println("Salida__________________");

			// System.out.println(weather);

			

			out.write(	"HTTP/1.0 200 OK\r\n" +
						"Content-Type: text/html\r\n" +
						"\r\n" +
						"<TITLE>Clima</TITLE>" +
						"<p>"+ weather + "</p>" +
						"<p>Fin</p>" 
			);


			System.out.println("Petición terminada");
			out.close();
			in.close();
			clientSocket.close();
		}
		
	// serverSocket.close();
	}

	static private String getWeather(String city) throws Exception {

		final String KEY = "8f7be5f45471c12644241ab5a22f7d32";
		
		URL myUrl = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + KEY);

		String stringResponse = "";
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(myUrl.openStream()))) {

			String inputLine = null;

			while ((inputLine = reader.readLine()) != null) {
				stringResponse += inputLine;
			}            

		} catch (IOException x) {

			System.err.println(stringResponse);
		}

		// System.out.println(stringResponse);
		JSONObject jsonResponse = new JSONObject(stringResponse).getJSONArray("weather").getJSONObject(0);
		// System.out.println( jsonResponse.getJSONObject("description"));
		return jsonResponse.getString("main") + " - " + jsonResponse.getString("description");
	}

}