package com.pq.protocol.command;

/**
 * 指令集合
 * @version 1.0
 * @author pengqi
 */
public interface Command {
    /**
     * 请求登录指令标识
     */
    Byte LOGIN_REQ = 1;

    /**
     * 响应登录请求指令标识
     */
    Byte LOGIN_RES = 2;

    /**
     * 发送消息指令标识
     */
    Byte MESSAGE_SEND = 3;

    /**
     * 接收消息指令标识
     */
    Byte MESSAGE_RECV = 4;

    /**
     * 请求创建群组指令标识
     */
    Byte GROUP_CREATE_REQUEST = 5;

    /**
     * 响应创建群组指令标识
     */
    Byte GROUP_CREATE_RESPONSE = 6;

    /**
     * 申请入群指令标识
     */
    Byte GROUP_JOIN_REQUEST = 7;

    /**
     * 申请入群响应标识
     */
    Byte GROUP_JOIN_RESPONSE = 8;

    /**
     * 申请退群指令标识
     */
    Byte GROUP_EXIT_REQUEST = 9;

    /**
     * 申请退群响应标识
     */
    Byte GROUP_EXIT_RESPONSE = 10;

    /**
     * 注销登录请求标识
     */
    Byte LOGOUT_REQ = 11;

    /**
     * 注销登录响应标识
     */
    Byte LOGOUT_RES = 12;

    /**
     * 打印群组成员列表请求指令标识
     */
    Byte GROUP_MEMBERS_REQ = 13;

    /**
     * 打印群组成员列表请求响应指令标识
     */
    Byte GROUP_MEMBERS_RES = 14;

    /**
     * 发送群聊消息请求指令标识
     */
    Byte GROUP_MESSAGE_SEND_REQ = 15;

    /**
     * 响应发送群聊消息请求指令标识
     */
    Byte GROUP_MESSAGE_SEND_RES = 16;

}
