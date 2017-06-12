package com.speedata.bean;

import java.util.List;

/**
 * Created by Administrator on 2015/11/23.
 */
public class InforBean {
    private List<CommonForm> list;
    private  int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<CommonForm> getList() {
        return list;
    }

    public void setList(List<CommonForm> list) {
        this.list = list;
    }
}
