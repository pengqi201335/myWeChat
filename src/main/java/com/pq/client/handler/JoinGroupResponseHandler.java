package com.pq.client.handler;

import com.pq.protocol.groupChat.JoinGroupResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 处理申请入群命令响应的逻辑处理器
 * @version 1.0
 * @author pengqi
 */
public class JoinGroupResponseHandler extends SimpleChannelInboundHandler<JoinGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupResponsePacket joinGroupResponsePacket) {
        if(joinGroupResponsePacket.isSuccess()){
            /* 申请成功 */

            System.out.println("您已加入群组【"+joinGroupResponsePacket.getGroupID()+"】，和群里的小伙伴尽情地聊天吧^_^");
        }else {
            /* 申请失败 */

            System.out.println("群组【"+joinGroupResponsePacket.getGroupID()+"】不存在或正在维护，当前无法加入该群聊>_<");
        }
    }
}
