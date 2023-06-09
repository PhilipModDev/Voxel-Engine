package com.dawnfall.engine.util;

import java.io.*;

public class SerializerBufferStream {
    //serialize and object.
    public static byte[] serialize(Object obj) throws IOException {
        try(ByteArrayOutputStream b = new ByteArrayOutputStream()){
            //Puts it into the objectOutputStream.
            try(ObjectOutputStream o = new ObjectOutputStream(b)){
                //Writes to the ByteArrayOutputStream.
                o.writeObject(obj);
            }
            //returns the bytes in an array from the ByteArrayOutputStream.
            return b.toByteArray();
        }
    }
    //deserialize and object.
    public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        try(ByteArrayInputStream b = new ByteArrayInputStream(bytes)){
            //Puts the ByteArrayInputStream into the ObjectInputStream.
            try(ObjectInputStream o = new ObjectInputStream(b)){
                //Reads the ByteArrayInputStream and returns the data.
                return o.readObject();
            }
        }
    }
}
