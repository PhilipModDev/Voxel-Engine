package com.dawnfall.engine.util;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.dawnfall.engine.gen.World.World;
import com.dawnfall.engine.gen.multithread.ChunkBuilder;
import com.dawnfall.engine.util.math.Size;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Hashtable;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class libUtil {
    public static FileHandle getInternalFile(String name){
        return Gdx.files.internal(name);
    }
    public static FileHandle getExternalFile(String name){
        return Gdx.files.external(name);
    }
    public static FileHandle getAbsoluteFile(String name){
        return Gdx.files.absolute(name);
    }
    public static FileHandle getClasspathFile(String name){
        return Gdx.files.classpath(name);
    }
    public static FileHandle getLocalFile(String name){
        return Gdx.files.local(name);
    }
    public static FileHandle getFileHandle(String name, Files.FileType fileType){
        return Gdx.files.getFileHandle(name,fileType);
    }
    public static boolean isNullCollection(Collection<Object> collection, Object object){
        return !collection.contains(object);
    }
    public static boolean isNullHashtable(Hashtable<Vector2, ChunkBuilder> dictionary, Object object){
        return !dictionary.keys().equals(object);
    }
    /** A cached screen size. */
    public static final Size screen = new Size();
    /** A cached world screen size. */
    public static final Size world  = new Size();

    /** Fast floor for double. */
    public static int floor(final double x) {
        final int xi = (int)x;
        return x < xi ? xi - 1 : xi;
    }

    /** Null-safe dispose method for disposable object. */
    public static void disposes(Disposable dis) {
        if (dis != null) dis.dispose();
    }

    /** Null-safe disposes method for disposable objects. */
    public static void disposes(Disposable... dis) {
        final int size = dis.length;
        for (final Disposable d : dis) {
            if (d != null) d.dispose();
        }
    }

    /** Resets the mouse position to the center of the screen. */
    public static void resetMouse() {
        Gdx.input.setCursorPosition(screen.w/2, screen.h/2);
    }

    /** Seconds to 60 tick update. */
    public static final int seconds(int second) {
        return second * 60;
    }

    /** Milliseconds to 60 tick update. */
    public static final int milseconds(int mils) {
        return MathUtils.roundPositive((mils / 1000f) * 60f);
    }

    /** Utility log. */
    public static void log(Object tag, Object obj) {
        if (tag instanceof Class) {
            Gdx.app.log(((Class<?>)tag).getSimpleName(), obj.toString());
        } else {
            Gdx.app.log(tag.toString(), obj.toString());
        }
    }

    public static int getIndexWithCoordinatesXYZ(int x, int y, int z){
       return x + (y * World.WORLD_SIZE) + (z * World.WORLD_SIZE * 256);
    }

    public static byte[] compressData(byte[] dataToCompress,int size){
        try {
            byte[] compressedData = new byte[size];
            Deflater compressor = new Deflater();
            compressor.setInput(dataToCompress);
            compressor.finish();
            compressor.deflate(compressedData);
            compressor.end();
           if (compressedData.length > 0){
               return compressedData;
           }
        }catch (Exception exception){
            Gdx.app.error("Compress Data","Could not proceed of compressing the data.");
            exception.printStackTrace();
        }
        return null;
    }

    public static byte[] decompressData(byte[] data){
        try {
            byte[] decompressedData = new byte[4096];
            Inflater decompressor = new Inflater();
            decompressor.setInput(data);
            decompressor.inflate(decompressedData);
            decompressor.end();
            return decompressedData;
        }catch (Exception exception){
            Gdx.app.error("Compress Data","Could not proceed of compressing the data.");
            exception.printStackTrace();
        }
        return null;
    }

    public static ByteBuffer compressToByteBuffer(Object object){
        try {
            byte[] data = compressData(SerializerBufferStream.serialize(object),4096);
            if (data != null) {
                return ByteBuffer.wrap(data);
            }
        }catch (Exception exception){
            Gdx.app.error("Compress Data","Could not proceed of compressing the data.");
            exception.printStackTrace();
        }
        return null;
    }

    public static Object compressToObject(byte[] data){
        try {
            byte[] decompressData = decompressData(data);
            return SerializerBufferStream.deserialize(decompressData);
        }catch (Exception exception){
            Gdx.app.error("Compress Data","Could not proceed of decompressing the data.");
            exception.printStackTrace();
        }
        return null;
    }

    public static int getRandomNumber(int size){
        RandomXS128 randomXS128 = new RandomXS128();
        return randomXS128.nextInt(size);
    }

    public static boolean ifChunkNotContainNullValues(Array<ModelInstance> chunks){
        for (ModelInstance chunk : chunks) {
            if (chunk == null) {
                return false;
            }
        }
       return true;
    }
}
