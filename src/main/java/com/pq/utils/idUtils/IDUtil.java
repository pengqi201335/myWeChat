package com.pq.utils.idUtils;

import java.util.UUID;

/**
 * 创建随机ID的工具类
 * @version 1.0
 * @author pengqi
 */
public class IDUtil {
    /**
     * 服务端为连接的客户端生成一个随机数作为标识
     * @return 随机连接数
     */
    public static String randomID(){
        return UUID.randomUUID().toString().split("-")[0];
    }
}
