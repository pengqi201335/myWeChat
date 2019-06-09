package com.pq.server.handler;

import com.pq.protocol.login.LogInRequestPacket;
import com.pq.protocol.login.LogInResponsePacket;
import com.pq.session.Session;
import com.pq.utils.idUtils.IDUtil;
import com.pq.utils.sessionUtils.SessionUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * 登录请求逻辑处理器
 * @version 1.0
 * @author pengqi
 */
public class LoginRequestHandler extends SimpleChannelInboundHandler<LogInRequestPacket> {
    /**
     * 处理登录请求的handler，如数据不是登录请求，则传递给下一个handler处理
     * @param channelHandlerContext ChannelPipeline链节点对象
     * @param logInRequestPacket 登录请求对象，经packetDecoder解析好的Java对象
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LogInRequestPacket logInRequestPacket) {
        //创建登录响应对象
        LogInResponsePacket lrp_s = new LogInResponsePacket();
        lrp_s.setVersion(logInRequestPacket.getVersion());
        String userName = logInRequestPacket.getUserName();
        lrp_s.setUserName(userName);
        lrp_s.setSuccess(true);
        if(valid(logInRequestPacket)){
            String userID = IDUtil.randomID();
            //创建一个session对象
            Session session = new Session(userName,userID);

            //将此session对象绑定到当前channel对象上
            SessionUtil.bindSession(session,channelHandlerContext.channel());

            lrp_s.setUserID(userID);
            System.out.println("【"+userName+"】登录成功");
        }else{
            lrp_s.setSuccess(false);
            lrp_s.setReason(new Date()+"：登录账号/密码校验失败");
            System.err.println(new Date()+"：登录失败");
        }
        channelHandlerContext.channel().writeAndFlush(lrp_s);
    }

    /**
     * 当前channel断开连接时，将对应的session解绑
     * @param ctx ChannelHandlerContext对象
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        SessionUtil.unBindSession(ctx.channel());
    }

    /**
     * 验证登录信息
     * @param lrp 登录请求对象
     * @return 登录信息是否正确
     */
    private boolean valid(LogInRequestPacket lrp){
        return true;
    }

}
