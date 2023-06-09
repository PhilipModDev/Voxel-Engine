package com.dawnfall.engine.Blocknet;

import java.io.InputStream;

public interface NetUtil {
     byte[] compressObjectToBytes(Object object, int size);
     byte[] serializer(Object object);
     Object deserializer(byte[] data);
     Object decompressBytesToObject(byte[] data, int size);
     Object decompressBytesToObject(InputStream stream);
     String decompressMessage(byte[] data,int size);

     byte[] compressMessage(String message,int size);
}

