package edu.escuelaing.WebApp.Reto1;


import java.io.*;
import java.net.*;

public class MyServerSocket extends Thread {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private String response = "";

    BufferedReader r = new BufferedReader( new InputStreamReader( System.in ));
    

    public void begin(int port) throws IOException {

        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();

        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
        String inputLine;
        String responseLine;
        
        while (!(inputLine = in.readLine()).equals("")) {
            
            URL myUrl = new URL(inputLine);
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(myUrl.openStream()));

            while ((responseLine = reader.readLine()) != null) {
                response += "" + responseLine;
            }

            out.println(response);
            
            response = "";
        }

        in.close();
        out.close();

        clientSocket.close();
        serverSocket.close();
    }


    @Override
    public void run() {

        MyServerSocket server = new MyServerSocket();
        try {
            server.begin(6666);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}