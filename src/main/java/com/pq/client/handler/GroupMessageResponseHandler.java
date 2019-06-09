package com.pq.client.handler;

import com.pq.protocol.groupChat.GroupMessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 接收群聊消息的逻辑处理器
 * @version 1.0
 * @author pengqi
 */
public class GroupMessageResponseHandler extends SimpleChannelInboundHandler<GroupMessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageResponsePacket responsePacket) {
        if(responsePacket.isSuccess()){
            System.out.println("群聊【"+responsePacket.getGroupID()+"】中【"+responsePacket.getFromUserID()+":"
                                +responsePacket.getFromUserName()+"】发来消息："+responsePacket.getMessage());
        }else{
            System.err.println("发送失败，失败原因:【"+responsePacket.getReason()+"】");
        }
    }
}
