package org.example;

import org.example.application.demo.DemoApp;
import org.example.application.housing.HousingApp;
import org.example.server.Server;
import org.example.server.http.StatusCode;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Server server = new Server(new HousingApp());
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}