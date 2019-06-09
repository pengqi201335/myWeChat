package com.pq.codec;

import com.pq.protocol.Packet;
import com.pq.protocol.PacketCodec;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;

import java.util.List;

/**
 * 编解码器的逻辑处理器，此handler同时继承了入站和出站事件处理器
 * @version 2.0
 * @author pengqi
 */
public class PacketCodecHandler extends MessageToMessageCodec<ByteBuf, Packet> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Packet packet, List<Object> list) {
        //分配内存存放编码数据
        ByteBuf byteBuf = ctx.alloc().ioBuffer();
        PacketCodec.INSTANCE.encode(byteBuf,packet);
        list.add(byteBuf);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) {
        list.add(PacketCodec.INSTANCE.decode(byteBuf));
    }
}
