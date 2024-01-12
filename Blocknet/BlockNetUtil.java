package com.dawnfall.engine.Blocknet;

import io.github.pixee.security.ObjectInputFilters;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.zip.Deflater;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;

public abstract class BlockNetUtil implements NetUtil {
    ///////////////////////////////////////////////////////////////////////////
    // Compresses bytes with the fixed compressionSize.
    ///////////////////////////////////////////////////////////////////////////
    public byte[] compressBytes(byte[] bytes,int compressionSize){
        try{
            byte[] result = new byte[compressionSize];
            Deflater compressor = new Deflater();
            compressor.setInput(bytes);
            compressor.finish();
            compressor.deflate(result);
            return result;
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return null;
    }
    ///////////////////////////////////////////////////////////////////////////
    // Compresses bytes with the fixed compressionSize.
    //Here (speed) is used to change the compress state.
    ///////////////////////////////////////////////////////////////////////////
    public byte[] compressBytes(byte[] bytes,int compressionSize,int speed){
        try{
            byte[] result = new byte[compressionSize];
            Deflater compressor = new Deflater(speed);
            compressor.setInput(bytes);
            compressor.finish();
            compressor.deflate(result);
            return result;
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return null;
    }
    ///////////////////////////////////////////////////////////////////////////
    // Compresses a ByteBuffer with a fixed compressionSize.
    //Here (speed) is used to change the compress state.
    ///////////////////////////////////////////////////////////////////////////
    public ByteBuffer compressBytes(ByteBuffer buffer,int compressionSize,int speed){
        try{
            ByteBuffer result = ByteBuffer.allocate(compressionSize);
            Deflater compressor = new Deflater(speed);
            compressor.setInput(buffer);
            compressor.finish();
            compressor.deflate(result);
            return result;
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return null;
    }
    ///////////////////////////////////////////////////////////////////////////
    // Decompresses to array of bytes with the fixed decompressionSize
    ///////////////////////////////////////////////////////////////////////////
    public byte[] decompressBytes(byte[] bytes,int decompressionSize){
        try {
            byte[] result = new byte[decompressionSize];
            Inflater decompressor = new Inflater();
            decompressor.setInput(bytes);
            decompressor.inflate(result);
            decompressor.end();
            return result;
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return null;
    }
    ///////////////////////////////////////////////////////////////////////////
    // Decompresses to a ByteBuffer object with the fixed decompressionSize
    ///////////////////////////////////////////////////////////////////////////
    public ByteBuffer decompressBytes(ByteBuffer buffer, int decompressionSize){
        try {
            ByteBuffer result = ByteBuffer.allocate(decompressionSize);
            Inflater decompressor = new Inflater();
            decompressor.setInput(buffer);
            decompressor.inflate(result);
            decompressor.end();
            return result;
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return null;
    }


    public byte[] compressObjectToBytes(Object object, int size){
        try {
            byte[] data = serializer(object);
            Deflater deflater = new Deflater();
            deflater.setInput(data);
            deflater.finish();
            byte[] compressedData = new byte[size];
            deflater.deflate(compressedData);
            deflater.end();
            return compressedData;
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return null;
    }


    @Override
    public Object decompressBytesToObject(byte[] data, int size) {
        try {
            Inflater inflater = new Inflater();
            inflater.setInput(data);
            byte[] output = new byte[size];
            inflater.inflate(output);
            inflater.end();
            return deserializer(output);
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public Object decompressBytesToObject(InputStream stream) {
            try(GZIPInputStream inputStream = new GZIPInputStream(stream)) {
                byte[] decompressedData = inputStream.readAllBytes();
                if (decompressedData != null){
                    return deserializer(decompressedData);
                }
            }catch (Exception exception){
                exception.printStackTrace();
            }
        return null;
    }

    //Compreses the message with a fixed size.
    @Override
    public byte[] compressMessage(String message, int size) {
        try {
           byte[] msgData = message.getBytes();
           byte[] result = new byte[size];
           Deflater compressor = new Deflater();
           compressor.setInput(msgData);
           compressor.finish();
           compressor.deflate(result);
           compressor.end();
           return result;
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return null;
    }

    //Decompresses a String message with a fixed size.
    @Override
    public String decompressMessage(byte[] data, int size) {
      try {
          byte[] decompressedData = new byte[size];
          Inflater inflater = new Inflater();
          inflater.setInput(data);
          inflater.inflate(decompressedData);
          inflater.end();
          return new String(decompressedData);
      }catch (Exception exception){
          exception.printStackTrace();
      }
        return null;
    }

    //Object serializer.
    public byte[] serializer(Object object){
        try(ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            try(ObjectOutputStream outputStream1 = new ObjectOutputStream(outputStream)) {
                outputStream1.writeObject(object);
                return outputStream.toByteArray();
            }catch (Exception exception){
                exception.printStackTrace();
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return null;
    }

    //Object deserializer.
    public Object deserializer(byte[] data){
        try(ByteArrayInputStream bytesInputStream = new ByteArrayInputStream(data)) {
            try(ObjectInputStream inputStream = new ObjectInputStream(bytesInputStream)) {
                ObjectInputFilters.enableObjectFilterIfUnprotected(inputStream);
                return inputStream.readObject();
            }catch (Exception exception){
                exception.printStackTrace();
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return null;
    }
}
