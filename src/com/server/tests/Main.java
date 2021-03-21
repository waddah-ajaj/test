package com.server.tests;

public class Main
{
    public static void main(String[] args)
    {
        SunServer sunServer = new SunServer();
        sunServer.createServer();
        System.out.println("Server running");
    }
}
