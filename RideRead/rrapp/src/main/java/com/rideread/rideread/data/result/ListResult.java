package com.rideread.rideread.data.result;


import java.io.Serializable;
import java.util.List;

/**
 * 请求返回的列表数据
 *
 * @param <T>
 */
public final class ListResult<T> implements Serializable {
    private int count;
    private List<T> list;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
