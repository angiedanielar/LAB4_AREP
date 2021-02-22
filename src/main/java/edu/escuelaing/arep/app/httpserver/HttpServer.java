package edu.escuelaing.arep.app.httpserver;

import java.net.*;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import edu.escuelaing.arep.app.microspring.MicroSpring;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.net.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that generate a Http Server
 * 
 * @author Angie Daniela Ruiz Alfonso
 */
public class HttpServer {

	private MicroSpring iocServer;
	private boolean running = false;

	public HttpServer() {
	}

	public HttpServer(MicroSpring iocServer) {
		this.iocServer = iocServer;
	}

	/**
	 * Return the port that the http server use
	 * 
	 * @return the port to use
	 */
	private static int getPort() {
		if (System.getenv("PORT") != null) {
			return Integer.parseInt(System.getenv("PORT"));
		}
		return 36000;
	}

	/**
	 * Run the http server
	 * 
	 * @throws IOException
	 */
	public void run() throws IOException {
		try {
			ServerSocket serverSocket = null;
			int port = getPort();

			try {
				serverSocket = new ServerSocket(port);
			} catch (IOException e) {
				System.err.println("Could not listen on port: " + port);
				System.exit(1);
			}

			running = true;
			while (running) {
				try {
					Socket clientSocket = null;
					try {
						System.out.println("Listo para recibir ...");
						clientSocket = serverSocket.accept();
					} catch (IOException e) {
						System.err.println("Could not listen on port:" + getPort());
						System.exit(1);
					}

					processRequest(clientSocket);

					clientSocket.close();
				}

				catch (IOException ex) {
					Logger.getLogger(HttpServer.class.getName()).log(Level.SEVERE, null, ex);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			serverSocket.close();
		} catch (IOException ex) {
			Logger.getLogger(HttpServer.class.getName()).log(Level.SEVERE, null, ex);
		}

	}
	
	/**
	 * Process the request
	 * 
	 * @throws IOException, InvocationTargetException, IllegalAccessException
	 */
	private void processRequest(Socket clientSocket) throws IOException, InvocationTargetException, IllegalAccessException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        String inputLine;
        Map<String, String> request = new HashMap<>();
        boolean requestLineReady = false;
        while ((inputLine = in.readLine()) != null) {
            if (!requestLineReady) {
                request.put("requestLine", inputLine);
                requestLineReady = true;
            } else {
                String[] entry = createEntry(inputLine);
                if (entry.length > 1) {
                    request.put(entry[0], entry[1]);
                }
            }
            if (!in.ready()) {
                break;
            }
        }


        if(request.get("requestLine")!=null){
            Request req = new Request(request.get("requestLine"));

            System.out.println("RequestLine: " + req);
            if(req != null) {
                createResponse(req, new PrintWriter(
                        clientSocket.getOutputStream(), true),clientSocket.getOutputStream());
            }
            in.close();

        }

    }

	/**
	 * To create a entry
	 */
    private String[] createEntry(String rawEntry) {
        return rawEntry.split(":");
    }

    /**
	 * To create a response
	 * 
	 * @throws InvocationTargetException, IllegalAccessException
	 */
    private void createResponse(Request req, PrintWriter out,OutputStream outputStream) throws InvocationTargetException, IllegalAccessException {
        URI theuri = req.getTheuri();
        if (theuri.getPath().startsWith("/Apps")) {
            String appuri= theuri.getPath().substring(5);
            invokeApp(appuri,out);
        } else {
            getResource(theuri.getPath(), out,outputStream);
        }
        out.close();
    }

    /**
	 * To invoke the app
	 * 
	 * @throws InvocationTargetException, IllegalAccessException
	 */
    private void invokeApp(String appuri, PrintWriter out) throws InvocationTargetException, IllegalAccessException {

        String header = "HTTP/1.1 200 OK\r\n"
                + "Content-Type: text/html\r\n"
                + "\r\n";
        String methodresponse = iocServer.invoke(appuri);
        out.println(header + methodresponse);
    }

	/**
	 * Method to get a image to load
	 * 
	 * @param path         The path of the image
	 * @param outputStream The outputStream of the client socket
	 */
	public static void getImagen(String path, OutputStream outputStream) {
		try {
			BufferedImage image = ImageIO.read(new File(System.getProperty("user.dir") + path));
			ByteArrayOutputStream ArrBytes = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(outputStream);
			ImageIO.write(image, "PNG", ArrBytes);
			out.writeBytes("HTTP/1.1 200 OK \r\n" + "Content-Type: image/png \r\n" + "\r\n");
			out.write(ArrBytes.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method to get a resource as html, js or png
	 * 
	 * @param path         The path of the resource
	 * @param outputStream The outputStream of the client socket
	 * @param out          The print writer of the client socket
	 */
	private void getResource(String path, PrintWriter out,OutputStream outputStream) {
        if (path.contains("html") || path.contains("js") || path.contains("ico")) {
            js(path, out);
        }
        else if (path.contains("png")){
           png(path, outputStream);
        }
    }
	
	/**
	 * Method to get a resource as html or js
	 * 
	 * @param path         The path of the resource
	 * @param out          The print writer of the client socket
	 */
	private void js(String path, PrintWriter out) {
		Path file = Paths.get("target/classes/public_html" + path);
		System.out.println(file);
		try (InputStream in = Files.newInputStream(file);
                BufferedReader reader
                        = new BufferedReader(new InputStreamReader(in))) {

               String header = "HTTP/1.1 200 OK\r\n"
                       + "Content-Type: text/html\r\n"
                       + "\r\n";
               out.println(header);
               String line = null;
               while ((line = reader.readLine()) != null) {
                   out.println(line);
               }
           } catch (IOException ex) {
               Logger.getLogger(HttpServer.class.getName()).log(Level.SEVERE, null, ex);
           }
	}
	
	/**
	 * Method to get a resource as png
	 * 
	 * @param path         The path of the resource
	 * @param outputStream The outputStream of the client socket
	 */
	private void png(String path,  OutputStream outputStream) {
		Path file = Paths.get("target/classes/public_html" + path);
         try{
             BufferedImage image = ImageIO.read(new File(System.getProperty("user.dir")+"/src/main/resources/public_html/love.png"));
             ByteArrayOutputStream ArrBytes = new ByteArrayOutputStream();
             DataOutputStream out1 = new DataOutputStream(outputStream);
             ImageIO.write(image, "PNG", ArrBytes);
             out1.writeBytes("HTTP/1.1 200 OK \r\n"
                     + "Content-Type: image/png \r\n"
                     + "\r\n");
             out1.write(ArrBytes.toByteArray());
         }

         catch (IOException e) {
             e.printStackTrace();
         }
		
	}
}
