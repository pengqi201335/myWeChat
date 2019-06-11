package com.pq.client;

import com.pq.client.consoleCommand.ConsoleCommandManager;
import com.pq.client.consoleCommand.LoginConsoleCommand;
import com.pq.client.handler.*;
import com.pq.codec.PacketCodecHandler;
import com.pq.codec.Spliter;
import com.pq.heartBeatAndIdleCheck.HeartBeatTimerHandler;
import com.pq.heartBeatAndIdleCheck.IMIdleStatusHandler;
import com.pq.utils.sessionUtils.SessionUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class NettyClient {
    private static final int MAX_RETRY = 5;

    private static final Bootstrap bootstrap = new Bootstrap();

    public static void main(String[] args){
        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap
                .group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                //设置将被添加到ChannelPipeline中以接收事件通知的ChannelHandler（即new ChannelInitializer()对象）
                .handler(new ChannelInitializer<>() {
                    @Override
                    protected void initChannel(Channel channel){
                        //在pipeline最前面加上一个空闲检测处理器，若在指定时间内没有收到数据，则断开该连接
                        channel.pipeline().addLast(new IMIdleStatusHandler());
                        //添加基于长度域的拆包器，参数1表示数据包的最大长度，参数2表示协议长度域偏移量，参数3表示协议长度域大小
                        channel.pipeline().addLast(new Spliter());
                        // channelPipeline中添加编解码句柄
                        channel.pipeline().addLast(new PacketCodecHandler());
                        //channelPipeline中添加登录请求响应句柄
                        channel.pipeline().addLast(new LoginResponseHandler());
                        //channelPipeline中添加消息响应句柄
                        channel.pipeline().addLast(new MessageResponseHandler());
                        //channelPipeline中添加创建群聊响应句柄
                        channel.pipeline().addLast(new CreateGroupResponseHandler());
                        //channelPipeline中添加加入群聊响应句柄
                        channel.pipeline().addLast(new JoinGroupResponseHandler());
                        //channelPipeline中添加发送群消息的响应句柄
                        channel.pipeline().addLast(new GroupMessageResponseHandler());
                        //channelPipeline中添加退出群聊响应句柄
                        channel.pipeline().addLast(new ExitGroupResponseHandler());
                        //channelPipeline中添加打印群聊成员列表响应句柄
                        channel.pipeline().addLast(new ListGroupMembersResponseHandler());
                        //channelPipeline中添加登录注销请求响应句柄
                        channel.pipeline().addLast(new LogoutResponseHandler());
                        //如果客户端一直没有向服务端发送数据，才会发送心跳检测
                        channel.pipeline().addLast(new HeartBeatTimerHandler());

                    }
                });

        connect("115.156.251.15",1024,MAX_RETRY);

    }

    private static void connect(String host, int port, final int retry){
        bootstrap.connect(host,port).addListener((future) ->  {
            if(future.isSuccess()){
                System.out.println(new Date()+"：连接成功！");
                //开一个线程用于在控制台发送消息
                Channel channel = ((ChannelFuture)future).channel();
                startConsoleThread(channel);

                //连接断开触发此方法，
                channel.closeFuture().addListener(future1 ->
                    channel.eventLoop().schedule(() -> connect(host, port, MAX_RETRY), 3, TimeUnit.SECONDS)
                );
            }
            else if(retry==0)
                System.err.println(new Date()+"：重试次数已用完，放弃连接！");
            else{
                int rank = MAX_RETRY-retry+1;   //第几次连接
                int delay = 1<<(rank-1);
                System.err.println(new Date()+"：连接失败，第"+rank+"次重连...");
                //延迟调度，连接失败后等待若干秒，再尝试重连
                bootstrap.config().group().schedule(()->connect(host,port,retry-1),delay, TimeUnit.SECONDS);
            }
        });
    }

    /**
     * 开启一个新线程，用于控制台循环输入
     * @param channel 当前连接管道
     */
    private static void startConsoleThread(Channel channel){
        LoginConsoleCommand loginConsoleCommand = new LoginConsoleCommand();
        ConsoleCommandManager consoleCommandManager = new ConsoleCommandManager();
        //Scanner cin = new Scanner(System.in);

        new Thread(()->{
//            try{
//                Thread.sleep(1000);
//            }catch (InterruptedException e){
//                e.printStackTrace();
//            }

            //控制台键入消息并发送至服务端
            while (channel.isActive()){
                if(!SessionUtil.isLogin(channel)){
                    /* 当前客户端还未登录 */
                    loginConsoleCommand.exec(channel);

                }else{
                    /* 当前客户端已登录 */

                    //控制台输入待发送消息，并发送给服务端
                    consoleCommandManager.exec(channel);

                }

            }

            System.out.println("连接已断开，退出控制台命令线程");

        }).start();
    }
}
