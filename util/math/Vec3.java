package com.dawnfall.engine.util.math;

public class Vec3<T extends Number> {
  private final T x;
  private final T y;
  private final T z;
    public Vec3(T x,T y,T z){
       this.x = x;
       this.y = y;
       this.z = z;
    }
    public T getX() {
        return x;
    }

    public T getY() {
        return y;
    }

    public T getZ() {
        return z;
    }
}
