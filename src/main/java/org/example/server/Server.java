package org.example.server;

import org.example.server.exception.UnsupportedProtocolException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public final int PORT = 10001;
    private ServerSocket server;

    private final Application application;

    public Server(Application application) {
        this.application = application;
    }

    /**
     * Starts the server.
     */
    public void start() throws IOException {
        System.out.println("Start server...");
        server = new ServerSocket(PORT, 5);
        System.out.println("Server running at: http://localhost:" + PORT);

        run();
    }

    /**
     * Main server loop.
     */
    private void run() {
        while (true) {
            try {
                Socket socket = server.accept();
                RequestHandler requestHandler = new RequestHandler(socket, application);
                requestHandler.run();
            } catch (UnsupportedProtocolException e) {
                System.err.println(e.getClass() + ": " + e.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
