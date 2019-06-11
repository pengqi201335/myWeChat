package com.pq.client.handler;

import com.pq.protocol.login.LogInResponsePacket;
import com.pq.session.Session;
import com.pq.utils.sessionUtils.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


/**
 * 用于处理登录请求响应消息的Handler
 * @version 1.0
 * @author pengqi
 */
public class LoginResponseHandler extends SimpleChannelInboundHandler<LogInResponsePacket> {
    /**
     * 处理登录请求的响应消息
     * @param channelHandlerContext ChannelPipeline链节点对象
     * @param logInResponsePacket 登录请求响应对象
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LogInResponsePacket logInResponsePacket) {
        String userID = logInResponsePacket.getUserID();        //服务端给登录用户随机分配的ID
        String userName = logInResponsePacket.getUserName();    //登录用户名

        if(logInResponsePacket.isSuccess()){
            System.out.println("【"+userName+"】：登录成功，用户ID为 "+userID);
            //将当前管道（连接）与对应的session绑定
            SessionUtil.bindSession(new Session(userName,userID),channelHandlerContext.channel());
        }else{
            System.err.println("【"+userName+"】：登录失败！原因："+logInResponsePacket.getReason());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception{
        System.out.println("与myWeChat服务端连接已断开，请检查是否登录！");
        super.channelInactive(ctx);
    }
}
