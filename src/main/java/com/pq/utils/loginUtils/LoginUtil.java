package com.pq.utils.loginUtils;

import com.pq.attribute.Attributes;
import io.netty.channel.Channel;
import io.netty.util.Attribute;

/**
 * 判断当前连接是否登录
 * @version 1.0
 * @author pengqi
 */
public class LoginUtil {
    /**
     * 将已登录的连接添加已登录属性
     * @param channel 代表当前连接的管道
     */
    public static void markAsLogin(Channel channel){
        //根据键对象获取值对象，将值对象的值设为true
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean isLogin(Channel channel){
        //根据键对象获取值对象
        Attribute<Boolean> loginKey = channel.attr(Attributes.LOGIN);
        return loginKey.get()!=null;
    }
}
