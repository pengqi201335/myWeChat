package com.pq.server.handler;

import com.pq.protocol.message.MessageRequestPacket;
import com.pq.protocol.message.MessageResponsePacket;
import com.pq.session.Session;
import com.pq.utils.sessionUtils.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 用于处理客户端发送至服务端的消息的channelHandler
 * @version 2.0
 * @author pengqi
 */
@ChannelHandler.Sharable
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    public static final MessageRequestHandler INSTANCE = new MessageRequestHandler();

    private MessageRequestHandler(){}

    /**
     * 处理客户端发送的消息
     * @param channelHandlerContext ChannelPipeline链节点对象
     * @param mrp_c 经packetDecoder解析的Java对象
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageRequestPacket mrp_c) {
        //获取当前channel对象绑定的session对象
        Session session = SessionUtil.getSession(channelHandlerContext.channel());

        String message = mrp_c.getMessage();
        System.out.println("|----------------------------------------------");
        System.out.println(" 收到客户端【"+session.getUserName()+"】的消息："+message);

        //创建消息响应对象
        MessageResponsePacket mrp_s = new MessageResponsePacket();
        mrp_s.setMessage(message);
        mrp_s.setFromUserID(session.getUserID());
        mrp_s.setFromUserName(session.getUserName());

        //获取消息发送目标channel
        Channel toChannel = SessionUtil.getChannel(mrp_c.getToUserID());

        //将消息响应冲刷出站，发送给消息接收方
        if(toChannel!=null && SessionUtil.isLogin(toChannel)){
            toChannel.writeAndFlush(mrp_s);
        }else{
            System.err.println("用户"+mrp_c.getToUserID()+"不存在或不在线，发送失败！");
        }

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.channel().flush();
    }
}
