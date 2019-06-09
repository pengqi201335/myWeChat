package com.pq.utils.trafficUtils;

/**
 * 流量统计对象
 * @version 1.0
 * @author pengqi
 */
public class Traffic {
    /**
     * 服务端入口流量
     */
    private static int traffic = 0;

    public static void increase(int len){
        traffic += len;
    }

    public static int statics(){
        return traffic;
    }
}
