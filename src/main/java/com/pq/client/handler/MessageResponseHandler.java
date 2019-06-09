package com.pq.client.handler;

import com.pq.protocol.message.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


/**
 * 用于处理客户端发送至服务端的消息响应的Handler
 * @version 1.0
 * @author pengqi
 */
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    /**
     * 处理发送至服务器的消息响应
     * @param channelHandlerContext ChannelPipeline链节点对象
     * @param messageResponsePacket 消息响应对象
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageResponsePacket messageResponsePacket) {
        //当前请求为消息响应请求
        System.out.println("【"+messageResponsePacket.getFromUserName()+"】:"+messageResponsePacket.getMessage());
    }
}
