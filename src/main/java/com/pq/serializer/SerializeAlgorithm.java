package com.pq.serializer;

/**
 * 序列化算法集合
 * @version 1.0
 * @author pengqi
 */
public interface SerializeAlgorithm {
    /**
     * JSON序列化算法
     */
    Byte JSON = 1;

    //其他类型的序列化算法，如JDK自带的序列化、hessian等
}
