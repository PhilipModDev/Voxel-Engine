package com.dawnfall.engine.Blocknet.Client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.dawnfall.engine.Blocknet.BlockNetUtil;
import com.dawnfall.engine.Blocknet.Server.Server;
import com.dawnfall.engine.gen.ChunkDataGeneration;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.logging.Level;

public class ClientHandler extends BlockNetUtil implements Runnable {
    private final Server server;
    private final SocketChannel client;
    private final ChunkDataGeneration.GenerateServerData serverData;

    public ClientHandler(SocketChannel client,Server server){
        this.server = server;
        this.client = client;
        serverData = new ChunkDataGeneration.GenerateServerData(server);
    }

    @Override
    public void run() {
        try {
            ByteBuffer buffer = ByteBuffer.allocate(server.bufferSize);
            if (client.isConnected()){
                server.clients.add(client.socket().getLocalAddress());
                client.read(buffer);

                ClientProtocol.ClientRequestPacket requestPacket = (ClientProtocol.ClientRequestPacket) decompressBytesToObject(buffer.array(),server.bufferSize);
                Vector2 coordinates =  new Vector2(requestPacket.x,requestPacket.y);
                byte[][][] chunk;
                if (!server.WorldData.containsKey(coordinates)) {
                    /*
                     * generate the chunk add it to the WorldData.
                     */
                    serverData.generateData(coordinates);
                }
                chunk = server.WorldData.get(coordinates);
                byte[] chunkData = compressObjectToBytes(chunk, server.bufferSize);
                buffer.clear();
                buffer.put(chunkData);
                buffer.flip();
                client.write(buffer);
            }
        }catch (Exception exception){
            server.serverLogger.log(Level.SEVERE,"Error processing client request.");
            exception.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception exception){
                Gdx.app.error("Server","Error closing the client socket.");
                exception.printStackTrace();
            }
        }
//        messageCommunication();
    }

    public void messageCommunication(){
        try {
            ByteBuffer buffer = ByteBuffer.allocate(server.bufferSize);
            if (client.isConnected()){
                System.out.println("Client connected.");
                server.clients.add(client.socket().getLocalAddress());
                client.read(buffer);
                System.out.println( new String(buffer.array()).trim());
                String serverMessage = "I got your response client on:"+ client.getRemoteAddress();
                byte[] data = compressObjectToBytes(serverMessage, server.bufferSize);
                buffer.clear();
                buffer.put(data);
                buffer.flip();
                client.write(buffer);
            }
        }catch (Exception exception){
            Gdx.app.error("Server","Error processing client request.");
            exception.printStackTrace();
        }finally {
            try {
                client.close();
            }catch (Exception exception){
                Gdx.app.error("Server","Error closing the client socket.");
                exception.printStackTrace();
            }
        }
    }
}
