package com.dawnfall.engine.Blocknet.Server;

import com.badlogic.gdx.Gdx;
import com.dawnfall.engine.Blocknet.Client.ClientProtocol;
import com.dawnfall.engine.util.libUtil;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ProtocolFamily;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

public class ServerProtocol {
    private InetSocketAddress serverAddress;
    private InetSocketAddress clientAddress;
    private SocketChannel clientSocket;
    private byte[] data;
    public ServerProtocol(int port){
      try {
        serverAddress = new InetSocketAddress(port);
      }catch (Exception exception){
          exception.printStackTrace();
      }
    }
    private SocketChannel listenConnection(){
        try {
            ServerSocketChannel socketChannel = ServerSocketChannel.open();
            socketChannel.bind(serverAddress);
            clientSocket = socketChannel.accept();
            socketChannel.close();
            if (clientSocket.isConnected()){
                System.out.println(true);
               return clientSocket;
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return null;
    }

    public ClientProtocol.ClientRequestPacket getClientRequestPacket(){
        try {
            SocketChannel channel = listenConnection();
            ByteBuffer buffer = ByteBuffer.allocate(4000);
            if (channel != null) {
                channel.read(buffer);
                channel.close();
                Socket socket = clientSocket.socket();
                clientAddress = new InetSocketAddress(socket.getLocalAddress(),socket.getLocalPort());
                byte[] data = buffer.array();
                return (ClientProtocol.ClientRequestPacket) libUtil.compressToObject(data);
            }
        }catch (Exception exception){
            Gdx.app.error("Server/TCP","Could not receive connection from the client");
            exception.printStackTrace();
        }
        return null;
    }

    public void sendClientRequest(byte[][][] chunk){
       try {
           // FIXME: 5/29/2023 Not Send the server data to client.
           ByteBuffer buffer = libUtil.compressToByteBuffer(chunk);
           if (clientAddress != null) {
               DatagramChannel datagramChannel = DatagramChannel.open();
               datagramChannel.send(buffer,clientAddress);
               datagramChannel.close();
           }
       }catch (Exception exception){
           Gdx.app.error("Server/UDP","Could not send client a request.");
           exception.printStackTrace();
       }
    }
    public byte[] getData() {
        return data;
    }
}
