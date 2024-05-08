import java.io.*;
import java.net.*;

public class WebServer{

    public static void main(String[] args){
        ServerSocket server;
        Socket socket;
        try{
            server = new ServerSocket(8080);
            while (server.isBound() && !server.isClosed()) {
                System.out.println("Ready...");
                socket = server.accept();
                Thread thread = new Thread(new ClientHandler(socket));
                thread.start();
            }
        } catch (Exception e){
            e.printStackTrace();
        }


    }
}


