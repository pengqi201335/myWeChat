package com.pq.server.handler;

import com.pq.heartBeatAndIdleCheck.HeartBeatRequestPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 心跳检测数据包逻辑处理器
 * @version 2.0
 * @author pengqi
 */
public class HeartBeatRequestHandler extends SimpleChannelInboundHandler<HeartBeatRequestPacket> {
    public static final HeartBeatRequestHandler INSTANCE = new HeartBeatRequestHandler();

    private HeartBeatRequestHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatRequestPacket requestPacket) {
        //服务端接收到了客户端的心跳信息，同时返回一个心跳信息
        ctx.writeAndFlush(new HeartBeatRequestPacket("connect still alive,do not close"));
    }
}
