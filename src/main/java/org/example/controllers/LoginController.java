package org.example.controllers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.helpers.Parser;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.HttpCookie;
import java.util.Map;

public class LoginController implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) {
        try{
            String method = exchange.getRequestMethod();

            switch (method) {
                case "GET":
                    // RETURN LOGIN FORM
                    getLoginForm(exchange);
                    break;
                case "POST":
                    // GET DATA FROM LOGIN FORM
                    getLoginData(exchange);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getLoginData(HttpExchange exchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);

        Map<String, String> data = Parser.parseFormData(br.readLine());
        String login = data.get("login");
        String password = data.get("password");

        System.out.println(login);
        System.out.println(password);
        //TODO check in databse if user with given email and password exist

        //TODO login page with students

        HttpCookie cookie = new HttpCookie("user", login);
        exchange.getResponseHeaders().add("Set-cookie", cookie.toString());

        exchange.getResponseHeaders().set("Location", "/users");
        exchange.sendResponseHeaders(302, 0);
    }

    private void getLoginForm(HttpExchange exchange) throws IOException {
        JtwigTemplate jtwigTemplate = JtwigTemplate.classpathTemplate("templates/login.twig");
        String response = jtwigTemplate.render(JtwigModel.newModel());

        exchange.sendResponseHeaders(200, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}
