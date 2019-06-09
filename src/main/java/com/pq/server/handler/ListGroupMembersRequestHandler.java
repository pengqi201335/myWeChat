package com.pq.server.handler;

import com.pq.protocol.groupChat.ListGroupMembersRequestPacket;
import com.pq.protocol.groupChat.ListGroupMembersResponsePacket;
import com.pq.session.Session;
import com.pq.utils.sessionUtils.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理打印群组成员列表请求的逻辑处理器
 * @version 2.0
 * @author pengqi
 */
@ChannelHandler.Sharable
public class ListGroupMembersRequestHandler extends SimpleChannelInboundHandler<ListGroupMembersRequestPacket> {
    public static final ListGroupMembersRequestHandler INSTANCE = new ListGroupMembersRequestHandler();

    private ListGroupMembersRequestHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersRequestPacket requestPacket) {
        //创建打印群组成员列表响应对象
        ListGroupMembersResponsePacket responsePacket = new ListGroupMembersResponsePacket();

        //获取群号
        String groupID = requestPacket.getGroupID();

        //根据群号获取成员列表
        ChannelGroup channels = SessionUtil.getGroup(groupID);

        if(channels!=null){
            if(channels.contains(ctx.channel())){
                List<Session> sessions = new ArrayList<>();
                for(Channel channel:channels){
                    Session session = SessionUtil.getSession(channel);
                    sessions.add(session);
                }

                responsePacket.setSessions(sessions);
                responsePacket.setSuccess(true);
                responsePacket.setGroupID(groupID);
            }else{
                //当前群聊存在，但此用户并非群成员
                responsePacket.setSuccess(false);
                responsePacket.setReason("您并非该群成员，没有权限查看群成员列表");
            }
        }else{
            responsePacket.setSuccess(false);
            responsePacket.setReason("该群不存在或正在维护中");
        }

        ctx.channel().writeAndFlush(responsePacket);
    }
}
