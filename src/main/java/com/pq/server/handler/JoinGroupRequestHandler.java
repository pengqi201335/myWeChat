package com.pq.server.handler;

import com.pq.protocol.groupChat.JoinGroupRequestPacket;
import com.pq.protocol.groupChat.JoinGroupResponsePacket;
import com.pq.utils.sessionUtils.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * 处理入群申请的逻辑处理器
 * @version 1.0
 * @author pengqi
 */
public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupRequestPacket joinGroupRequestPacket) {
        //用户申请加入的群号
        String groupID = joinGroupRequestPacket.getGroupID();

        //群号对应的channelGroup对象
        ChannelGroup channels = SessionUtil.getGroup(groupID);

        //创建响应对象
        JoinGroupResponsePacket jgrp = new JoinGroupResponsePacket();
        jgrp.setGroupID(groupID);

        if(channels!=null){
            /* 申请的群号存在 */

            //将当前用户加入群组
            channels.add(ctx.channel());

            jgrp.setSuccess(true);

        }else {
            /* 申请的群号不存在 */

            jgrp.setSuccess(false);

        }

        ctx.channel().writeAndFlush(jgrp);
    }
}
