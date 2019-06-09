package com.pq.client.handler;

import com.pq.protocol.groupChat.ExitGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 处理申请入群响应的逻辑处理器
 * @version 1.0
 * @author pengqi
 */
public class ExitGroupResponseHandler extends SimpleChannelInboundHandler<ExitGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ExitGroupResponsePacket exitGroupResponsePacket) {
        if(exitGroupResponsePacket.isSuccess()){
            System.out.println("您已退出群聊【"+exitGroupResponsePacket.getGroupID()+"】");
        }else{
            System.err.println("退出群聊【"+exitGroupResponsePacket.getGroupID()+"】失败");
            System.err.println("失败原因："+exitGroupResponsePacket.getReason());
        }
    }
}
