package com.speedata.utils;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * Created by Administrator on 2016/3/3.
 */
public class ParseData<T> {
    public List<T> ParseDataList(String data, String key, Class<T> cls) {
        com.alibaba.fastjson.JSONObject parse1 = JSON.parseObject
                (data);
        String Results = parse1.getString
                (key);
        List<T> list = JSON
                .parseArray(Results,
                        cls);
        return list;
    }
}
