package com.pq.server.handler;

import com.pq.utils.sessionUtils.SessionUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 用户认证逻辑处理器，用来过滤未登录用户
 * 对客户端发来的消息进行登录鉴权
 * @version 2.0
 * @author pengqi
 */
@ChannelHandler.Sharable
public class AuthenticateHandler extends ChannelInboundHandlerAdapter {
    /**
     * 饿汉式单例模式实例
     */
    public static final AuthenticateHandler INSTANCE = new AuthenticateHandler();

    /**
     * private的构造函数
     */
    private AuthenticateHandler(){}

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //判断当前channel是否登录
        if(!SessionUtil.isLogin(ctx.channel())){
            //当前channel未登录，直接关闭连接
            ctx.channel().close();
        }
        // 利用channelPipeline的热插拔机制，当前channel已经登录的话，认证handler利用pipeline的remove()函数删除自身，
        // 避免已登录的channel后续无意义的判断
        ctx.pipeline().remove(this);
        // 当前channel已登录，将消息传递给下一个handler进行处理
        super.channelRead(ctx, msg);
    }

}
