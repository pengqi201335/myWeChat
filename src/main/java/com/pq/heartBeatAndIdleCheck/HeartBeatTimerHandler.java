package com.pq.heartBeatAndIdleCheck;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 心跳检测发送逻辑处理器
 * @version 2.0
 * @author pengqi
 */
public class HeartBeatTimerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 心跳检测发送周期
     */
    private static final int HEARTBEAT_INTERVAL = 5;

    private ScheduledFuture<?> future;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        scheduleSendHeartBeat(ctx);

        //将数据往后传递
        super.channelActive(ctx);
    }

    /**
     * 周期性任务调度执行器，每隔5秒向远程连接节点发送一个心跳数据包
     */
    private void scheduleSendHeartBeat(ChannelHandlerContext ctx){
        future = ctx.channel().eventLoop().scheduleAtFixedRate(()->
            ctx.writeAndFlush(new HeartBeatRequestPacket("connect still alive,do not close"))
        ,HEARTBEAT_INTERVAL,HEARTBEAT_INTERVAL,TimeUnit.SECONDS);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //连接断开之后，取消任务调度
        future.cancel(true);

        super.channelInactive(ctx);
    }
}
