package com.dawnfall.engine.util.math;

public class Vec2<T extends Number> {
  private final T x;
  private final T y;
    public Vec2(T x, T y){
       this.x = x;
       this.y = y;
    }
    public T getX() {
        return x;
    }

    public T getY() {
        return y;
    }
}
