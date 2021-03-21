package com.server.tests;

import com.server.tests.handler.MyOtherPathHandler;
import com.server.tests.handler.MyPathHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SunServer
{
    Executor executor = Executors.newFixedThreadPool(2);
    HttpHandler myHandler = new MyPathHandler();
    HttpHandler myOtherHandler = new MyOtherPathHandler();

    public void createServer()
    {

        try {
            HttpServer httpServer = HttpServer.create(new InetSocketAddress(8000), 0);
            httpServer.createContext("/", new MyHandler());
            httpServer.createContext("/somePath", myHandler);
            httpServer.createContext("/someOtherPath", myOtherHandler);
            httpServer.setExecutor(executor);
            httpServer.start();
        } catch (IOException e) {
            System.err.println("Error " + e);
        }
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = String.format("Response serviced by thread [%s] out of [%s]. Method: %s", Thread.currentThread().getId(), Thread.activeCount(), t.getRequestMethod());

            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();

//            try {
//                Thread.sleep(1000 * 30);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
    }
}
