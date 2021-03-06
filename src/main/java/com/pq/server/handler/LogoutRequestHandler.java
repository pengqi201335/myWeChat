package com.pq.server.handler;

import com.pq.protocol.logout.LogoutRequestPacket;
import com.pq.protocol.logout.LogoutResponsePacket;
import com.pq.utils.sessionUtils.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 处理注销登录请求的逻辑处理器
 * @version 2.0
 * @author pengqi
 */
@ChannelHandler.Sharable
public class LogoutRequestHandler extends SimpleChannelInboundHandler<LogoutRequestPacket> {
    public static final LogoutRequestHandler INSTANCE = new LogoutRequestHandler();

    private LogoutRequestHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutRequestPacket logoutRequestPacket) {
        //创建注销登录响应对象
        LogoutResponsePacket lrp = new LogoutResponsePacket();

        ///将注册在当前channel上的session置为null
        SessionUtil.unBindSession(ctx.channel());

        lrp.setSuccess(true);

        ctx.channel().writeAndFlush(lrp);
    }
}
