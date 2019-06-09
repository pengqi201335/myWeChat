package com.pq.client.handler;

import com.pq.protocol.logout.LogoutResponsePacket;
import com.pq.utils.sessionUtils.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 处理注销登录请求响应的逻辑处理器
 * @version 1.0
 * @author pengqi
 */
public class LogoutResponseHandler extends SimpleChannelInboundHandler<LogoutResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutResponsePacket logoutResponsePacket) {
        //解绑客户端的Session对象
        SessionUtil.unBindSession(ctx.channel());

        System.out.println("您已退出登录");
    }
}
