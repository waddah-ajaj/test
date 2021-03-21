package com.server.tests.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.text.MessageFormat;

public class MyPathHandler implements HttpHandler
{

    @Override
    public void handle(HttpExchange httpExchange) throws IOException
    {
        final StringBuilder response = new StringBuilder(String.format("Response serviced by MyHandler thread [%s] out of [%s]. Method: %s", Thread.currentThread().getId(), Thread.activeCount(), httpExchange.getRequestMethod()));
        response.append("header: ");
        httpExchange.getRequestHeaders().forEach((key, value) -> response.append(MessageFormat.format("[{0}: {1}]", key, value)));

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.toString().getBytes());
        os.close();
    }
}
