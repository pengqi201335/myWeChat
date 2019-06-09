package com.pq.client.handler;

import com.pq.protocol.groupChat.CreateGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;

/**
 * 处理创建群聊命令响应的逻辑处理器
 * @version 1.0
 * @author pengqi
 */
public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupResponsePacket createGroupResponsePacket) {
        //获取群聊成员名列表
        List<String> groupUserNameList = createGroupResponsePacket.getGroupUserNameList();
        //获取群号
        String groupID = createGroupResponsePacket.getGroupID();

        //群聊创建成功
        if(createGroupResponsePacket.getSuccess()){
            System.out.println("群聊创建成功，群号为【"+groupID+"】，群成员为【"+groupUserNameList+"】");
        }
    }
}
