package edu.escuelaing.arep.app.sparkd;

import edu.escuelaing.arep.app.httpserver.HttpServer;
import edu.escuelaing.arep.app.microspring.MicroSpring;

/**
* Class that generate a spark server
* @author dnielben
*/
public class SparkServer {
	
	public static void main(String[] args){
        String[] args1=new String[1];
        args1[0]="edu.escuelaing.arep.app.microspring.Controller";
        try {
        MicroSpring microSpring= new MicroSpring();
        microSpring.starts(args1);
        HttpServer server = new HttpServer(microSpring);
        server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

