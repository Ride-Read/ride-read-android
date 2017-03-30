package com.rideread.rideread.data;

public interface Action<TObj> {
    void call(final TObj target) throws Exception;
}