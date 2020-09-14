package org.example.controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.models.User;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class UserController implements HttpHandler {

    List<User> users;


    public UserController() {
        users = new ArrayList<>();

        User user1 = new User("Adrian", "Widlak", "adi", "adrian.widlak@codecool.com", "admin");
        User user2 = new User("Jan", "Kowalski", "jan", "jan.kowalski@codecool.com", "admin");
        User user3 = new User("Barbara", "Kowalska", "basia", "basia.kowalska@codecool.com", "admin");


        users.add(user1);
        users.add(user2);
        users.add(user3);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        // HTTP get, post
        String method = exchange.getRequestMethod();

        switch (method) {
            case "GET":
                getUsers(exchange);
                break;
            case "POST":
                break;
        }
    }

    private void getUsers(HttpExchange exchange) throws IOException {
        //TODO GET USERS FROM DATABASE users
        JtwigTemplate jtwigTemplate = JtwigTemplate.classpathTemplate("templates/index.twig");
        JtwigModel jtwigModel = JtwigModel.newModel();
        jtwigModel.with("users", users);

        String response = jtwigTemplate.render(jtwigModel);

        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
