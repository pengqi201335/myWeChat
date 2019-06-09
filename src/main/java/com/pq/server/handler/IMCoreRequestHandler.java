package com.pq.server.handler;

import com.pq.protocol.Packet;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;
import java.util.Map;

import static com.pq.protocol.command.Command.*;

/**
 * IM系统核心功能处理器，用于处理平行的Handler，缩短事件传播路径
 * @version 2.0
 * @author pengqi
 */
@ChannelHandler.Sharable
public class IMCoreRequestHandler extends SimpleChannelInboundHandler<Packet> {
    public static final IMCoreRequestHandler INSTANCE = new IMCoreRequestHandler();

    /**
     * 存放指令与对应的逻辑处理器，这些handler是平行的，即事件只会选择其中一个来执行
     * 可以提前通过指令来选择对应的Handler，从而缩短事件传播路径
     */
    private Map<Byte,SimpleChannelInboundHandler<? extends Packet>> handlerMap;

    private IMCoreRequestHandler(){
        handlerMap = new HashMap<>();

        handlerMap.put(MESSAGE_SEND,MessageRequestHandler.INSTANCE);
        handlerMap.put(GROUP_CREATE_REQUEST,CreateGroupRequestHandler.INSTANCE);
        handlerMap.put(GROUP_JOIN_REQUEST,JoinGroupRequestHandler.INSTANCE);
        handlerMap.put(GROUP_EXIT_REQUEST,ExitGroupRequestHandler.INSTANCE);
        handlerMap.put(GROUP_MEMBERS_REQ,ListGroupMembersRequestHandler.INSTANCE);
        handlerMap.put(GROUP_MESSAGE_SEND_REQ,GroupMessageRequestHandler.INSTANCE);
        handlerMap.put(LOGOUT_REQ,LogoutRequestHandler.INSTANCE);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        //先交由SimpleChannelInboundHandler对象执行，若判断该handler可以处理事件，则调用其实现类的channelRead0()方法
        handlerMap.get(packet.getCommand()).channelRead(ctx,packet);
    }
}
