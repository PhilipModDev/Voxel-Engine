package com.dawnfall.engine.Blocknet.Client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Vector2;
import com.dawnfall.engine.Blocknet.BlockNetUtil;
import com.dawnfall.engine.gen.ChunkManager;
import com.dawnfall.engine.util.libUtil;
import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SocketChannel;

public class ClientProtocol extends BlockNetUtil {
    private InetSocketAddress ClientAddress;
    private static byte id = 0;
    public int bufferSize = 114096;
    public ClientProtocol(int port, InetAddress address,int bufferSize){
        this.bufferSize = bufferSize;
        this.ClientAddress = new InetSocketAddress(address,port);
    }
    public ClientProtocol(int port){
        try {
            this.ClientAddress = new InetSocketAddress(port);
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
    public ClientProtocol(int port,int bufferSize){
        this.bufferSize = bufferSize;
        try {
            this.ClientAddress = new InetSocketAddress(InetAddress.getLocalHost(),port);
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
    public static void setClientId(){
        RandomXS128 randomXS128 = new RandomXS128();
        id = (byte) randomXS128.nextInt(5000);
    }
    public static byte getClientId(){
        return id;
    }
    public void serverConnection(Vector2 coordinates, ChunkManager chunkManager){
        try(SocketChannel socketChannel = SocketChannel.open(ClientAddress)) {
            if (socketChannel.isConnected()) {
                ByteBuffer buffer = ByteBuffer.allocate(bufferSize);
                int X = (int) coordinates.x;
                int Y = (int) coordinates.y;
                //Writes a message to the Server.
                ClientRequestPacket requestPacket = new ClientRequestPacket(X, Y, ClientAddress);
                buffer.put(compressObjectToBytes(requestPacket,bufferSize));
                buffer.flip();
                socketChannel.write(buffer);
                //receives the response from the server.
                buffer.clear();
                socketChannel.read(buffer);
                byte[][][] chunk = (byte[][][]) decompressBytesToObject(buffer.array(),bufferSize);
                if (chunk != null) {
                   chunkManager.ChunkData.put(coordinates, chunk);
                }
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
    public byte[][][] receiveChunkUDP(){
        try {
            DatagramChannel channel = DatagramChannel.open();
            ByteBuffer buffer = ByteBuffer.allocate(65536);
            channel.bind(ClientAddress);
            channel.receive(buffer);
            if (buffer.array().length > 0){
                return (byte[][][]) libUtil.compressToObject(buffer.array());
            }
            channel.close();
        }catch (Exception exception){
            Gdx.app.log("Client","Could not receive data from the server.");
            exception.printStackTrace();
        }
        return null;
    }
    public InetSocketAddress getClientAddress() {
        return ClientAddress;
    }
    public static class ClientRequestPacket implements Serializable {
        public int x;
        public int y;
        public InetSocketAddress address;
        public byte id;
        public ClientRequestPacket(int x,int y,InetSocketAddress address){
            this.x = x;
            this.y = y;
            this.address = address;
            this.id= ClientProtocol.id;
        }
    }
}
