package co.edu.eci;

import java.net.*;
import java.io.*;
import org.json.*;

public class HttpServer {

	static final int PORT = 35000;
	static final String HEADER =	"HTTP/1.0 200 OK\r\n" +
																"Content-Type: text/html\r\n" +
																"\r\n";
	public static void main(String[] args) throws Exception {

		ServerSocket serverSocket = null;
		try { 
				serverSocket = new ServerSocket(getPort());

		} catch (IOException e) {

				System.err.format("No se puede usar el puerto: %d.\n", PORT);
				System.exit(1);
		}

		while (true) {
			Socket clientSocket = serverSocket.accept();
			// System.out.println("Nueva petición recibida");

			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
			// PrintWriter out = new PrintWriter(clientSocket.getOutputStream());

			String response = "";
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
					path = query.split("\\?")[0];
					if (query.split("\\?").length > 1){
						try {
							params = query.split("\\?")[1];
						} catch (Exception e) {
							// e.printStackTrace();
							System.out.println("Error al obtener arreglo de parametros");
						}
					}
					
					if (path.equals("/clima") || path.equals("/")) {
						response = getIndex();
					}
					
					if (path.equals("/consulta")) {
						String[] paramsArray;
						String city = "";
						try {
							paramsArray = params.split("&");
							city = paramsArray[0].split("=")[1];							
							System.out.println(city);
							response = getWeather(city);
							break;
						} catch (Exception e) {
							e.printStackTrace();
							System.out.println("Error al obtener parametro lugar");

						}
					}

					
					break;
				}
				
			}


			// System.out.println("Salida__________________");

			// System.out.println(weather);

			response = response != "" ? response: "No se encontró información";

			
			out.write(HEADER + response);
			// out.println("HTTP/1.1 200 OK");
			// out.println("Content-Type: text/html");
			// out.println("Content-Length: " + weather.length());
			// out.println();
			// out.println(weather);
			out.flush();


			// System.out.println("Petición terminada");
			out.close();
			in.close();
			clientSocket.close();
		}
		
	// serverSocket.close();
	}

	private static String getWeather(String city) throws Exception {

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

	private static int getPort() {
		if (System.getenv("PORT") != null) {
		return Integer.parseInt(System.getenv("PORT"));
		}
		return PORT;
}

	private static String getIndex() {
		String view = 	"<!DOCTYPE html>" +
										"<html lang=\"en\">" +
										"<head>" +
										"<meta charset=\"UTF-8\">" +
										"<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">" +
										"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
										"<title>Weather App</title>" +
										"<link rel=\"icon\" type=\"image/x-icon\" href=\"https://cdn.iconscout.com/icon/free/png-256/weather-2191838-1846632.png\">" +
										"<link href=\"https://fonts.googleapis.com/css2?family=Roboto:wght@300&display=swap\" rel=\"stylesheet\">" +
										"<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js\"></script>" +
										"<script type=\"text/javascript\">" +
										"function search() {" +
										"let endpoint = `https://app-weather-cities.herokuapp.com/consulta?lugar=`;" +
										"let city =  $(`#city`).val();" +
										"$(`#city`).text();" +
										"$.ajax({" +
										"url: `${endpoint}${city}`," +
										"type: 'GET'," +
										"dataType: 'jsonp'," +
										"cors: true ," +
										"contentType:'application/json'," +
										"secure: true," +
										"headers: {" +
										"'Access-Control-Allow-Origin': '*'," +
										"}," +
										"beforeSend: function (xhr) {" +
										"xhr.setRequestHeader (\"Authorization\", \"Basic \" + btoa(\"\"));" +
										"}," +
										"success: function(result){" +
										"console.log(result);" +
										"$(`result`).text(`El resultado es: ${result}`);" +
										"}," +
										"error: function(error) {" +
										"console.log(error);" +
										"$(`#result`).text('Error al consultar');" +
										"}" +
										"})" +
										"}" +
										"</script>" +
										"<style>" +
										"html, body {" +
										"height: 100%;" +
										"overflow: hidden;" +
										"}" +
										"" +
										".container {" +
										"background-color: none;" +
										"margin: auto;" +
										"width: fit-content;" +
										"text-align: center;" +
										"margin: 0;" +
										"position: absolute;" +
										"top: 50%;" +
										"left: 50%;" +
										"-ms-transform: translate(-50%, -50%);" +
										"transform: translate(-50%, -50%);" +
										"font-family: 'Roboto', sans-serif;" +
										"box-shadow: rgb(0 0 0 / 20%) 0 3px 5px -1px, rgb(0 0 0 / 14%) 0 6px 10px 0, rgb(0 0 0 / 12%) 0 1px 18px 0;" +
										"box-sizing: border-box;" +
										"padding: 40px ;" +
										"}" +
										"" +
										"h1 {" +
										"color: #0276FF;" +
										"}" +
										"" +
										".field {" +
										"text-align: left;" +
										"margin-bottom: 30px;" +
										"}" +
										"" +
										"button {" +
										"align-items: center;" +
										"appearance: button;" +
										"background-color: #0276FF;" +
										"border-radius: 8px;" +
										"border-style: none;" +
										"box-shadow: rgb(255 255 255 / 26%) 0 1px 2px inset;" +
										"box-sizing: border-box;" +
										"color: #fff;" +
										"cursor: pointer;" +
										"display: flex;" +
										"flex-direction: row;" +
										"flex-shrink: 0;" +
										"font-family: \"RM Neue\",sans-serif;" +
										"font-size: 100%;" +
										"line-height: 1.15;" +
										"margin: 0;" +
										"padding: 10px 21px;" +
										"margin-left: 1rem;      " +
										"text-align: center;" +
										"text-transform: none;" +
										"transition: color .13s ease-in-out,background .13s ease-in-out,opacity .13s ease-in-out,box-shadow .13s ease-in-out;" +
										"user-select: none;" +
										"-webkit-user-select: none;" +
										"touch-action: manipulation;" +
										"}" +
										"" +
										"label {" +
										"font-family: 'Roboto', sans-serif;" +
										"padding-left: 1rem;" +
										"font-size: 1.2rem;" +
										"margin-top: 0.7rem;" +
										"display: block;" +
										"transition: all 0.3s;" +
										"transform: translateY(0rem);" +
										"}" +
										"" +
										"input {" +
										"font-family: 'Roboto', sans-serif;" +
										"color: #333;" +
										"font-size: 1rem;" +
										"margin: 0 auto 15px auto;" +
										"padding: 1rem 2rem;" +
										"border-radius: 0.2rem;" +
										"background-color: rgb(255, 255, 255);" +
										"border: solid 1px #333;" +
										"width: 80%;" +
										"display: block;" +
										"transition: all 0.3s;" +
										"}" +
										"" +
										"input:placeholder-shown + label {" +
										"opacity: 0;" +
										"visibility: hidden;" +
										"-webkit-transform: translateY(-4rem);" +
										"transform: translateY(-4rem);" +
										"}" +
										"" +
										".result {" +
										"margin: 0 15px 0 0;" +
										"}" +
										"</style>" +
										"</head>" +
										"<body>" +
										"<div class=\"container\">" +
										"<h1>Consulte el clima de una ciudad</h1>" +
										"<div class=\"field\">" +
										"<label for=\"city\">Ciudad</label><br>" +
										"<input id=\"city\" placeholder=\"Nombre ciudad\" type=\"text\">" +
										"<div class=\"result\">" +
										"<p id=\"result\"></p>" +
										"</div>" +
										"<button onclick=\"search()\">Consultar</button>" +
										"</div>" +
										"</div>" +
										"</body>" +
										"</html>";
										
		return view;
	}

}