package edu.escuelaing.arep.app.microspring;

/**
* Hello app
* @author dnielben
*/
public class Controller {

    @RequestMapping(value = "/hola")
    static public String index() {
        return "Greetings from micro Spring Boot!";
    }
}