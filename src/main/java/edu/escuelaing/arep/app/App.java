package edu.escuelaing.arep.app;

import java.io.IOException;

/**
 * Create a HttpServer
 *
 */
public class App 
{
	public static void main(String[] args) throws IOException {
        HttpServer server = new HttpServer();
        server.run();
    }

}
