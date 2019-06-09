package com.pq.serializer;

import com.alibaba.fastjson.JSON;

public class JsonSerializer implements Serializer {
    /**
     * 获取序列化方法标识
     *
     * @return 序列化方法号
     */
    @Override
    public Byte getSerializerAlgorithm() {
        return SerializeAlgorithm.JSON;
    }

    /**
     * 序列化一个对象
     *
     * @param o 待序列化对象
     * @return 序列化后得到的二进制字节数组
     */
    @Override
    public byte[] serialize(Object o) {
        return JSON.toJSONBytes(o);
    }

    /**
     * 反序列化二进制数据包
     *
     * @param clazz 目标对象类类型
     * @param bytes 二进制源文件
     * @return 目标对象
     */
    @Override
    public <T> T deSerialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes,clazz);
    }
}
