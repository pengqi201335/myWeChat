package com.pq.serializer;

/**
 * 序列化接口
 * @version 1.0
 * @author pengqi
 */
public interface Serializer {
    Byte DEFAULT_ALGORITHM = 1;
    /**
     * 默认的序列化算法
     */
    Serializer DEFAULT = new JsonSerializer();

    /**
     * 获取序列化方法标识
     * @return 序列化方法号
     */
    Byte getSerializerAlgorithm();

    /**
     * 序列化一个对象
     * @param o 待序列化对象
     * @return 序列化后得到的二进制字节数组
     */
    byte[] serialize(Object o);

    /**
     * 反序列化二进制数据包
     * @param clazz 目标对象类类型
     * @param bytes 二进制源文件
     * @param <T> 目标对象类型
     * @return 目标对象
     */
    <T> T deSerialize(Class<T> clazz,byte[] bytes);
}
