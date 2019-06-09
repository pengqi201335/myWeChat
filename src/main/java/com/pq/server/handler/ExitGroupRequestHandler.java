package com.pq.server.handler;

import com.pq.protocol.groupChat.ExitGroupRequestPacket;
import com.pq.protocol.groupChat.ExitGroupResponsePacket;
import com.pq.utils.sessionUtils.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;

/**
 * 处理退群申请的逻辑处理器
 * @version 2.0
 * @author pengqi
 */
@ChannelHandler.Sharable
public class ExitGroupRequestHandler extends SimpleChannelInboundHandler<ExitGroupRequestPacket> {
    public static final ExitGroupRequestHandler INSTANCE = new ExitGroupRequestHandler();

    private ExitGroupRequestHandler(){}

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ExitGroupRequestPacket exitGroupRequestPacket) {
        //获取群号
        String groupID = exitGroupRequestPacket.getGroupID();

        //根据群号获取到ChannelGroup
        ChannelGroup channels = SessionUtil.getGroup(groupID);

        //创建退群申请响应对象
        ExitGroupResponsePacket egrp = new ExitGroupResponsePacket();
        egrp.setGroupID(groupID);

        if(channels!=null){
            if(channels.contains(ctx.channel())){
                //将当前用户从群聊中移除
                channels.remove(ctx.channel());

                egrp.setSuccess(true);

            }else{
                egrp.setSuccess(false);
                egrp.setReason("您目前不在该群聊中，无效的退群请求");
            }
        }else{
            egrp.setSuccess(false);
            egrp.setReason("您搜索的群组不存在");
        }

        //返回响应对象
        ctx.channel().writeAndFlush(egrp);
    }
}
