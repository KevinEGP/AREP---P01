package edu.escuelaing.WebApp.Reto1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSocket {

    static BufferedReader r = new BufferedReader( new InputStreamReader( System.in ));
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) throws UnknownHostException, IOException {

        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public String sendMessage(String msg) throws IOException {

        out.println(msg);
        String resp = in.readLine();
        return resp;
    }

    public void stopConnection() throws IOException {

        in.close();
        out.close();
        clientSocket.close();
    }

    public static void main(String[] args) throws IOException {

        MyServerSocket server = new MyServerSocket();
        server.start();
        
        ClientSocket client = new ClientSocket();
        client.startConnection("127.0.0.1", 6666);
        System.out.print("URL: ");
        String input = r.readLine();
        String response = client.sendMessage(input);
        
        while (!"".equals(input)) {
            
            System.out.println(response);

            System.out.print("URL: ");
            r = new BufferedReader( new InputStreamReader( System.in ));
            input = r.readLine();
            response = client.sendMessage(input);
        }
    }
}
