package com.pq.utils.sessionUtils;

import com.pq.attribute.Attributes;
import com.pq.session.Session;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 处理客户端-服务端session对象的工具类
 * @version 1.0
 * @author pengqi
 */
public class SessionUtil {
    /**
     * 用户标识与TCP连接（Channel对象）的映射表
     */
    private static Map<String, Channel> userBindToChannel = new ConcurrentHashMap<>();

    /**
     * 群号与用户列表的映射表
     */
    private static Map<String, ChannelGroup> groupIDBindToChannelGroup = new ConcurrentHashMap<>();

    /**
     * 将此session代表的用户ID和对应的客户端连接放入userBindToChannel映射表中
     * 并将代表客户端与服务端连接状态的session对象与对应客户端的channel绑定
     * @param session session对象
     * @param channel channel对象
     */
    public static void bindSession(Session session,Channel channel){
        //将用户登录ID和其对应的channel放入map中
        userBindToChannel.put(session.getUserID(),channel);
        //将此session作为属性注册到channel中
        channel.attr(Attributes.SESSION).set(session);
    }

    /**
     * 解绑
     * @param channel channel对象
     */
    public static void unBindSession(Channel channel){
        if(isLogin(channel)){
            userBindToChannel.remove(getSession(channel).getUserID());
            channel.attr(Attributes.SESSION).set(null);
        }
    }

    /**
     * 判断当前channel是否被绑定了session对象，即判断当前channel是否登录
     * @param channel channel对象
     * @return 登录与否
     */
    public static Boolean isLogin(Channel channel){
        return getSession(channel)!=null;
    }

    /**
     * 根据channel获取其绑定的session
     * @param channel channel对象
     * @return session对象
     */
    public static Session getSession(Channel channel){
        return channel.attr(Attributes.SESSION).get();
    }

    /**
     * 根据用户标识找到对应的TCP连接（channel对象）
     * @param userID 用户ID
     * @return channel对象
     */
    public static Channel getChannel(String userID){
        return userBindToChannel.get(userID);
    }

    /**
     * 将群号与对应的channelGroup绑定
     * @param groupID 群号
     * @param channels channelGroup对象
     */
    public static void bindChannelGroup(String groupID,ChannelGroup channels){
        groupIDBindToChannelGroup.putIfAbsent(groupID,channels);
    }

    /**
     * 根据群号返回对应的channelGroup对象
     * @param groupID 群号
     * @return channelGroup对象
     */
    public static ChannelGroup getGroup(String groupID){
        return groupIDBindToChannelGroup.get(groupID);
    }
}
