package org.example;

import com.sun.net.httpserver.HttpServer;
import org.example.controllers.LoginController;
import org.example.controllers.StaticController;
import org.example.controllers.User2Controller;
import org.example.controllers.UserController;

import java.io.IOException;
import java.net.InetSocketAddress;

public class App 
{
    public static void main( String[] args ) throws IOException {
        System.out.println( "Hello World!" );

        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        server.createContext("/users", new UserController());
        server.createContext("/static", new StaticController());
        server.createContext("/login", new LoginController());

        server.setExecutor(null); // creates a default executor

        server.start();
    }
}
