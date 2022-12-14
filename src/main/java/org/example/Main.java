package org.example;

import org.example.application.Gaming.Game;
import org.example.server.Server;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Server server = new Server(new Game());
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}