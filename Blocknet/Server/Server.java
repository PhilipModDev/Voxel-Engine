package com.dawnfall.engine.Blocknet.Server;

import com.badlogic.gdx.math.Vector2;
import com.dawnfall.engine.Blocknet.Client.ClientHandler;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server implements Serializable {
    public InetSocketAddress ServerAddress;
    //65507
    public int bufferSize = 114096;
    public boolean TerminateServer;
    private ServerThread serverThread;
    private Thread thread;
    public final Logger serverLogger = Logger.getLogger("Server");
    public final ArrayList<InetAddress> clients = new ArrayList<>();
    public Hashtable<Vector2,byte[][][]> WorldData;
    public Server(InetAddress address,int port,int bufferSize){
        try{
            this.bufferSize = bufferSize;
            WorldData = new Hashtable<>();
            ServerAddress = new InetSocketAddress(address,port);
            serverThread = new ServerThread(this);
            thread = new Thread(serverThread);
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
    public Server(){
        try{
            WorldData = new Hashtable<>();
            ServerAddress = new InetSocketAddress(3838);
            serverThread = new ServerThread(this);
            thread = new Thread(serverThread);
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
    public Thread StartServer(){
       thread.start();
       return thread;
    }
    public void StopServer(){
        TerminateServer = true;
        serverLogger.log(Level.OFF,"Server/Main","Server shutdown.");
        thread.interrupt();
    }
    public class ServerThread implements Runnable {
        private final Server server;
        public ServerThread(Server server){
            this.server = server;
        }

        @Override
        public void run() {
         try {
             ServerSocketChannel serverSocket = ServerSocketChannel.open();
             serverSocket.bind(ServerAddress);
             serverLogger.log(Level.WARNING,"Server is starting port on:"+ServerAddress.getPort());
             while (!TerminateServer) {

                 SocketChannel socketChannel = serverSocket.accept();

                 ClientHandler clientHandler = new ClientHandler(socketChannel,server);
                 new Thread(clientHandler).start();
             }
         }catch (Exception exception){
             serverLogger.log(Level.SEVERE,"Error Starting Server.");
             System.out.println("Or this maybe a bug.");
             exception.printStackTrace();
           }
        }
    }
    public static void main(String[] args) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Server server = new Server();
        server.serverLogger.log(Level.INFO,"To stop the server type (stop) in the console.");
        server.StartServer();
        try {
           do {
               String userInput = reader.readLine();
               if (userInput.equalsIgnoreCase("stop")){
                   server.StopServer();
                   return;
               }
           }while (true);
       }catch (Exception exception){
           exception.printStackTrace();
       }
    }
}
