package tn.esprit;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import tn.esprit.utils.DataBase;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.util.Optional;

public class HeadlessServer {
    private HttpServer server;

    public void start(int port) throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 0);

        // liveness/readiness
        server.createContext("/health", exchange -> respond(exchange, 200, "OK"));

        // db ping
        server.createContext("/db-ping", exchange -> {
            try (Connection c = DataBase.getConnection()) {
                respond(exchange, 200, "DB OK");
            } catch (Exception e) {
                respond(exchange, 500, "DB ERROR: " + e.getMessage());
            }
        });

        // sample REST endpoints (expand later)
        server.createContext("/api/orders", new JsonHandler("{\"items\": []}"));

        server.setExecutor(null);
        server.start();
        System.out.println("Headless server started on port " + port);
    }

    public void stop() {
        if (server != null) server.stop(0);
    }

    static class JsonHandler implements HttpHandler {
        private final String body;
        JsonHandler(String body) { this.body = body; }
        @Override public void handle(HttpExchange ex) throws IOException {
            respond(ex, 200, body, "application/json");
        }
    }

    private static void respond(HttpExchange exchange, int status, String body) throws IOException {
        respond(exchange, status, body, "text/plain");
    }

    private static void respond(HttpExchange exchange, int status, String body, String type) throws IOException {
        byte[] bytes = body.getBytes();
        exchange.getResponseHeaders().set("Content-Type", type);
        exchange.sendResponseHeaders(status, bytes.length);
        try (OutputStream os = exchange.getResponseBody()) { os.write(bytes); }
    }

    public static int envInt(String name, int def) {
        return Optional.ofNullable(System.getenv(name)).map(Integer::parseInt).orElse(def);
    }
}

