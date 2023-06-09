package com.dawnfall.engine.Blocknet.Server;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.dawnfall.engine.util.SerializerBufferStream;
import com.dawnfall.engine.util.libUtil;
import java.io.BufferedOutputStream;
import java.io.Serializable;
import java.net.*;

public class ServerConnection {
    //Default Server connection.
    private final int port;
    public ServerConnection(int port){
        this.port = port;
    }
    public void sendRequestToClient(InetAddress address,byte[] data,int port){
        try{
            DatagramSocket socket = new DatagramSocket();
            DatagramPacket packet = new DatagramPacket(data,data.length,address,port);
            socket.send(packet);
            socket.close();
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
    public Socket TCPConnection(){
        try(ServerSocket serverSocket = new ServerSocket(port)){
            Socket socket = serverSocket.accept();
            serverSocket.close();
            if (socket != null){
               return socket;
            }
        }catch (Exception exception){
            Gdx.app.error("Server/TCP","Error, could not proceed with TCP connection.");
            exception.printStackTrace();
        }
       return null;
    }
    public static class ClientConnection {
      private final byte[] buffer;
      public ClientConnection(int bufferSize){
          buffer = new byte[bufferSize];
       }

        public void sendTCPRequest(byte[] data,int serverPort,InetAddress serverAddress) {
            try(Socket socket = new Socket(serverAddress,serverPort)){
                byte[] compressedData = libUtil.compressData(data,buffer.length);
              if (compressedData != null) {
                  if (compressedData.length > 0) {
                       BufferedOutputStream outputStream =
                               new BufferedOutputStream(socket.getOutputStream());
                       outputStream.write(compressedData);
                       outputStream.close();
                  } else {
                      Gdx.app.log("Client", "Could not send data because data is empty.");
                  }
              }
            }catch (Exception exception){
                Gdx.app.error("Client","Could not establish a connection with the server.");
                exception.printStackTrace();
            }
        }
        public void sendTCPRequestLocal(byte[] data) {
            try(Socket socket = new Socket(InetAddress.getLocalHost(), 3838)){
                byte[] compressedData = libUtil.compressData(data,buffer.length);
                if (compressedData != null) {
                    if (compressedData.length > 0) {
                        BufferedOutputStream outputStream =
                                new BufferedOutputStream(socket.getOutputStream());
                        outputStream.write(compressedData);
                        outputStream.close();
                    } else {
                        Gdx.app.log("Client", "Could not send data because data is empty.");
                    }
                }
            }catch (Exception exception){
                Gdx.app.error("Client","Could not establish a connection with the server.");
                exception.printStackTrace();
            }
        }
        public byte[] receiveRequestFromServer(){
            try{
                DatagramSocket socket = new DatagramSocket(3838);
                DatagramPacket packet = new DatagramPacket(buffer,buffer.length);

                socket.receive(packet);
                socket.close();
                return packet.getData();
            }catch (Exception exception){
                Gdx.app.log("Client","Could not receive data from the server.");
                exception.printStackTrace();
            }
            return null;
        }
        public byte[] SerializerDefaultPacket(Vector2 coordinates){
            try {
                ClientPacket clientPacket = new ClientPacket(3838,InetAddress.getLocalHost(),coordinates);
                return SerializerBufferStream.serialize(clientPacket);
            }catch (Exception exception){
                exception.printStackTrace();
            }
            return null;
        }
    }
    public final static class ClientPacket implements Serializable {
        public int port;
        public InetAddress address;
        public Vector2 coordinates;
        public ClientPacket(int port, InetAddress address,
                            Vector2 coordinates) {
            this.coordinates = coordinates;
            this.port = port;
            this.address = address;
        }
    }
}
