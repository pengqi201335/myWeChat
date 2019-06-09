package com.pq.client.handler;

import com.pq.protocol.groupChat.ListGroupMembersResponsePacket;
import com.pq.session.Session;
import com.pq.utils.sessionUtils.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;

/**
 * 处理打印群聊成员列表响应消息的逻辑处理器
 * @version 1.0
 * @author pengqi
 */
public class ListGroupMembersResponseHandler extends SimpleChannelInboundHandler<ListGroupMembersResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ListGroupMembersResponsePacket responsePacket) {
        if(responsePacket.isSuccess()){
            //获取当前群聊session列表
            List<Session> sessions = responsePacket.getSessions();

            System.out.println("当前群聊的群号为【"+responsePacket.getGroupID()+"】，群聊成员列表如下：");
            for(Session session:sessions){
                String userID = session.getUserID();
                String userName = session.getUserName();
                System.out.println("【id:"+userID+",name:"+userName+"】");
            }
        }else{
            System.err.println("拉取群成员列表失败，失败原因："+responsePacket.getReason());
        }

    }
}
