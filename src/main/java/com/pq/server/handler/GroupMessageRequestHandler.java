package com.pq.server.handler;

import com.pq.protocol.groupChat.GroupMessageRequestPacket;
import com.pq.protocol.groupChat.GroupMessageResponsePacket;
import com.pq.utils.sessionUtils.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * 处理群聊消息发送请求的逻辑处理器
 * @version 1.0
 * @author pengqi
 */
public class GroupMessageRequestHandler extends SimpleChannelInboundHandler<GroupMessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageRequestPacket requestPacket) {
        //获取群号
        String groupID = requestPacket.getGroupID();

        //获取群号对应的channelGroup对象
        ChannelGroup channels = SessionUtil.getGroup(groupID);

        //创建发送群聊请求响应对象
        GroupMessageResponsePacket responsePacket = new GroupMessageResponsePacket();


        if(channels!=null){
            if(channels.contains(ctx.channel())){
                //配置响应对象
                responsePacket.setSuccess(true);
                responsePacket.setGroupID(groupID);
                responsePacket.setFromUserID(SessionUtil.getSession(ctx.channel()).getUserID());
                responsePacket.setFromUserName(SessionUtil.getSession(ctx.channel()).getUserName());
                responsePacket.setMessage(requestPacket.getMessage());

                channels.writeAndFlush(responsePacket);

            }else{
                /* 当前用户不在该群聊中 */

                responsePacket.setSuccess(false);
                responsePacket.setReason("您还未加入该群聊，请先申请入群，再发送群聊消息");

                ctx.channel().writeAndFlush(responsePacket);
            }
        }else {
            /* 搜索的群聊不存在 */

            responsePacket.setSuccess(false);
            responsePacket.setReason("您搜索的群聊不存在或正在维护，操作无效");

            ctx.channel().writeAndFlush(responsePacket);
        }
    }
}
