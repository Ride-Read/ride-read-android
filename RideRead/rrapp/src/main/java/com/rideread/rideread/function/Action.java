package com.rideread.rideread.function;

public interface Action<TObj> {
    void call(final TObj target) throws Exception;
}