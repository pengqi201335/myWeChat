package com.pq.server.handler;

import com.pq.protocol.groupChat.CreateGroupRequestPacket;
import com.pq.protocol.groupChat.CreateGroupResponsePacket;
import com.pq.utils.idUtils.IDUtil;
import com.pq.utils.sessionUtils.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理创建群组命令的逻辑处理器
 * @version 1.0
 * @author pengqi
 */
public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket createGroupRequestPacket) {
        //为群聊创建一个群聊ID
        String groupID = IDUtil.randomID();
        //获取群聊成员ID列表
        List<String > groupUserIDList = createGroupRequestPacket.getGroupMemberIdList();
        //群聊成员名列表
        List<String> groupUserNameList = new ArrayList<>();

        //创建一个channel分组对象，该对象可对分组内的channel批量发送消息
        ChannelGroup channels = new DefaultChannelGroup(ctx.executor());

        //将channel对象加入分组，并将对应的用户名加入群聊成员名列表
        for(String userID:groupUserIDList){
            Channel channel = SessionUtil.getChannel(userID);
            if(channel!=null){
                channels.add(channel);
                groupUserNameList.add(SessionUtil.getSession(channel).getUserName());
            }
        }

        //将群号和对应的channelGroup绑定起来
        SessionUtil.bindChannelGroup(groupID,channels);

        //创建群聊请求响应对象
        CreateGroupResponsePacket cgrp = new CreateGroupResponsePacket();
        cgrp.setSuccess(true);
        cgrp.setGroupID(groupID);
        cgrp.setGroupUserNameList(groupUserNameList);

        //向所有群聊成员群发入群通知
        channels.writeAndFlush(cgrp);

        System.out.println("群聊创建成功，群号为【"+groupID+"】，群成员为【"+groupUserNameList+"】");
    }
}
