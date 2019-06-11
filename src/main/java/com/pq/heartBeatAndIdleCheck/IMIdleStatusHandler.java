package com.pq.heartBeatAndIdleCheck;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class IMIdleStatusHandler extends IdleStateHandler {
    /**
     * 空闲等待时间，在这个时间内没有收到远程节点的数据就认为连接假死了
     */
    private static final int READER_IDLE_TIME = 15;

    public IMIdleStatusHandler(){
        super(READER_IDLE_TIME,0,0, TimeUnit.SECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) {
        //连接被判定为假死，直接关闭该连接
        System.err.println(READER_IDLE_TIME+"秒内未接收到请求数据，关闭此连接");
        ctx.channel().close();
        System.out.println("正在重连...");

    }
}
