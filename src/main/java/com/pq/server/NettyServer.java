package com.pq.server;

import com.pq.codec.PacketDecoder;
import com.pq.codec.PacketEncoder;
import com.pq.codec.Spliter;
import com.pq.server.handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class NettyServer {
    public static void main(String[] args) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        serverBootstrap
                .group(boss,worker)     //配置线程模型
                .channel(NioServerSocketChannel.class)  //配置IO模型
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel){
                        //nioSocketChannel.pipeline().addLast(new LifeCycleTestHandler());
                        nioSocketChannel.pipeline().addLast(new Spliter());
                        nioSocketChannel.pipeline().addLast(new PacketDecoder());
                        nioSocketChannel.pipeline().addLast(LoginRequestHandler.INSTANCE);
                        nioSocketChannel.pipeline().addLast(AuthenticateHandler.INSTANCE);
                        //核心功能处理器，缩短事件传播路径
                        nioSocketChannel.pipeline().addLast(IMCoreRequestHandler.INSTANCE);
                        nioSocketChannel.pipeline().addLast(new PacketEncoder());
                    }
                });
                bind(serverBootstrap,1024);
    }

    private static void bind(final ServerBootstrap serverBootstrap, final int port){
        serverBootstrap.bind(port).addListener(future ->  {
            if(future.isSuccess()){
                System.out.println(new Date()+"：端口"+port+"绑定成功");
                Channel ch = ((ChannelFuture)future).channel();
                //添加一个定时任务，在端口绑定成功3s后，由evenLoop对应的线程执行此任务
                ch.eventLoop().schedule(()->System.out.println("welcome to netty!"),3, TimeUnit.SECONDS);
            }
            else{
                System.err.println(new Date()+"：端口"+port+"绑定失败");
                bind(serverBootstrap,port+1);   //递归调用绑定逻辑，实现端口自增绑定
            }

        });
    }
}
