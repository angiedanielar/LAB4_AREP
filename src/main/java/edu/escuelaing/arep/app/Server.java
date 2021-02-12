package edu.escuelaing.arep.app;

import java.net.*;
import java.io.*;

/**
 * Class of a Server
 * @author Angie Daniela Ruiz Alfonso
 */
public class Server {
	
	public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(36000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 36000.");
            System.exit(1);
        }
        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
            System.out.println("Listo para recibir ...");
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        out.println("Type the number and press ENTER to be squared. Remember enter number by number please.");
        out.println("Type Bye to close the connection");
        out.println();
        String inputLine, outputLine;
        while ((inputLine = in.readLine()) != null) {
            if (inputLine.equals("Bye")) {
                out.println("Bye");
                break;
            }
            int number=Integer.parseInt(inputLine);
            outputLine = "The squared is:" + number*number;
            out.println(outputLine);



        }
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}